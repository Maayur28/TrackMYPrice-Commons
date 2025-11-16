package io.github.maayur28.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OpenObserveHttpAppender extends AppenderBase<ILoggingEvent> {

    // Configurable via logback.xml setters
    private String url;
    private String user;
    private String password;

    private int queueSize = 8192;
    private int batchSize = 100;
    private long batchIntervalMs = 2000;
    private int maxRetries = 3;
    private String service = "unknown";

    // spool config
    private String spoolPath = "logs/spool.ndjson";
    private int spoolBatchSize = 500; // max lines to read from spool per flush
    private boolean enableSpool = true;

    // graceful shutdown wait
    private long shutdownWaitMs = 5000;

    // internals
    private BlockingQueue<ILoggingEvent> queue;
    private ScheduledExecutorService scheduler;
    private HttpClient http;
    private ObjectMapper mapper;
    private volatile boolean running = false;
    private String authHeaderValue;
    private String replicaId;

    // counters
    private final AtomicInteger totalSpoolWrites = new AtomicInteger(0);
    private final AtomicInteger totalFailures = new AtomicInteger(0);

    // file lock for spool writes and truncation
    private final Object spoolFileLock = new Object();

    // setters for logback.xml
    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setBatchIntervalMs(long batchIntervalMs) {
        this.batchIntervalMs = batchIntervalMs;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setSpoolPath(String spoolPath) {
        this.spoolPath = spoolPath;
    }

    public void setSpoolBatchSize(int spoolBatchSize) {
        this.spoolBatchSize = spoolBatchSize;
    }

    public void setEnableSpool(boolean enableSpool) {
        this.enableSpool = enableSpool;
    }

    public void setShutdownWaitMs(long shutdownWaitMs) {
        this.shutdownWaitMs = shutdownWaitMs;
    }

    @Override
    public void start() {
        // read env fallback if not provided via xml
        if (this.user == null || this.user.isEmpty()) {
            this.user = System.getenv("OO_USER");
        }
        if (this.password == null || this.password.isEmpty()) {
            this.password = System.getenv("OO_PASS");
        }

        if (url == null || url.isBlank()) {
            addError("OpenObserveHttpAppender: url property must be set");
            return;
        }
        if ((user == null || user.isBlank()) || (password == null || password.isBlank())) {
            addError("OpenObserveHttpAppender: credentials not provided (OO_USER / OO_PASS). Appender will not start.");
            return;
        }

        this.queue = new ArrayBlockingQueue<>(Math.max(16, queueSize));
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "oo-http-appender-flush");
            t.setDaemon(true);
            return t;
        });

        this.http = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.mapper = new ObjectMapper();

        String b64 = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
        this.authHeaderValue = "Basic " + b64;

        this.replicaId = Optional.ofNullable(System.getenv("HOSTNAME")).orElse("local");

        // ensure spool folder exists if spool enabled
        if (enableSpool) {
            Path p = Paths.get(spoolPath).toAbsolutePath();
            try {
                if (p.getParent() != null) {
                    Files.createDirectories(p.getParent());
                }
                // create file if missing
                if (!Files.exists(p)) {
                    Files.createFile(p);
                }
            } catch (IOException e) {
                addWarn("OpenObserveHttpAppender: cannot create spool file at " + p + " ; disabling spool. error=" + e.getMessage());
                enableSpool = false;
            }
        }

        // schedule periodic flush
        scheduler.scheduleAtFixedRate(this::flush, batchIntervalMs, batchIntervalMs, TimeUnit.MILLISECONDS);

        running = true;
        super.start();
    }

    @Override
    public void stop() {
        running = false;
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                scheduler.awaitTermination(Math.max(1, shutdownWaitMs / 1000), TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // final flush
        flush();
        super.stop();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (!running) return;

        // convert event to JSON line (we need this only if we must spool)
        String jsonLine = null;

        // 1) try to enqueue quickly
        try {
            boolean offered = queue.offer(eventObject, 200, TimeUnit.MILLISECONDS);
            if (offered) {
                // enqueued -> durability guaranteed by in-memory queue until flush sends it
                return;
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            // fall through to spool for durability
        }

        // 2) fallback: write to spool (durable)
        try {
            if (jsonLine == null) {
                jsonLine = toJsonLine(eventObject);
            }
            if (enableSpool) {
                writeLineToSpool(jsonLine);
                totalSpoolWrites.incrementAndGet();
                int c = totalFailures.incrementAndGet();
                if (c == 1 || c % 100 == 0) {
                    addWarn("OpenObserveHttpAppender: queue offer failed; event persisted to spool. totalQueueFailures=" + c);
                }
            } else {
                // spool disabled and cannot enqueue — at least log a warning (but don't print secrets)
                addWarn("OpenObserveHttpAppender: queue full and spool disabled; dropping event.");
            }
        } catch (Exception e) {
            // if spool write fails, warn occasionally
            int c = totalFailures.incrementAndGet();
            if (c == 1 || c % 100 == 0) {
                addWarn("OpenObserveHttpAppender: failed to write event to spool after queue failure: " + e.getMessage());
            }
        }
    }


    /**
     * Flush: called periodically on background thread.
     * Strategy:
     * - Collect up to batchSize events from in-memory queue and convert to JSON nodes.
     * - Read up to spoolBatchSize lines from spool file (first N lines), convert to JSON nodes.
     * - If we have any events, send them in a single JSON array to OpenObserve (POST to url).
     * - On success: remove shipped lines from spool (atomic rewrite)
     */
    private void flush() {
        try {
            if (!running && (queue.isEmpty() && (!enableSpool || isSpoolEmpty()))) {
                return;
            }

            List<ObjectNode> nodes = new ArrayList<>(batchSize + spoolBatchSize);

            // 1) drain in-memory queue
            List<ILoggingEvent> drained = new ArrayList<>(batchSize);
            queue.drainTo(drained, batchSize);
            for (ILoggingEvent ev : drained) {
                nodes.add(toJsonNode(ev));
            }

            // 2) read from spool file (first N lines)
            List<String> spoolLines = Collections.emptyList();
            if (enableSpool) {
                spoolLines = readSpoolLines(spoolBatchSize);
                for (String line : spoolLines) {
                    try {
                        ObjectNode n = (ObjectNode) mapper.readTree(line);
                        nodes.add(n);
                    } catch (Exception ex) {
                        // malformed line; skip it but log occasionally
                        addWarn("OpenObserveHttpAppender: skipping malformed spool line: " + ex.getMessage());
                    }
                }
            }

            if (nodes.isEmpty()) return;

            // prepare body (JSON array)
            String body = mapper.writeValueAsString(nodes);

            // send with retries
            boolean success = sendWithRetries(body, maxRetries);
            if (success && enableSpool && !spoolLines.isEmpty()) {
                // remove exactly the spoolLines count from head of file
                removeSpoolLines(spoolLines.size());
            }
            // if failed, keep spool lines intact (they remain for next flush)
        } catch (Exception ex) {
            addWarn("OpenObserveHttpAppender.flush() failed: " + ex.getMessage());
        }
    }

    /* ======= Spool file helpers ======= */

    // atomic append of a single line to spool file (synchronized)
    private void writeLineToSpool(String line) throws IOException {
        synchronized (spoolFileLock) {
            Path p = Paths.get(spoolPath);
            // ensure parent exists
            if (p.getParent() != null) {
                Files.createDirectories(p.getParent());
            }
            // append with newline
            Files.write(p, (line + "\n").getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        }
    }

    // read up to 'maxLines' lines from spool, return as list (does not remove them)
    private List<String> readSpoolLines(int maxLines) {
        synchronized (spoolFileLock) {
            Path p = Paths.get(spoolPath);
            if (!Files.exists(p)) return Collections.emptyList();
            List<String> out = new ArrayList<>(maxLines);
            try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
                String line;
                int count = 0;
                while ((line = br.readLine()) != null && count < maxLines) {
                    if (!line.trim().isEmpty()) {
                        out.add(line);
                        count++;
                    }
                }
            } catch (IOException e) {
                addWarn("OpenObserveHttpAppender: failed to read spool: " + e.getMessage());
                return Collections.emptyList();
            }
            return out;
        }
    }

    // check if spool file empty
    private boolean isSpoolEmpty() {
        Path p = Paths.get(spoolPath);
        try {
            if (!Files.exists(p)) return true;
            return Files.size(p) == 0;
        } catch (IOException e) {
            return true;
        }
    }

    // remove first 'n' lines from spool file atomically (rewrite remainder to temp and replace)
    private void removeSpoolLines(int n) {
        if (n <= 0) return;
        synchronized (spoolFileLock) {
            Path p = Paths.get(spoolPath);
            if (!Files.exists(p)) return;
            Path tmp = p.resolveSibling(p.getFileName().toString() + ".tmp");
            try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8);
                 BufferedWriter bw = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8,
                         StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                String line;
                int count = 0;
                // skip first n lines
                while ((line = br.readLine()) != null && count < n) {
                    count++;
                }
                // write remainder to tmp
                while (line != null) {
                    bw.write(line);
                    bw.newLine();
                    line = br.readLine();
                }
                bw.flush();
            } catch (IOException e) {
                addWarn("OpenObserveHttpAppender: failed to rotate spool tmp: " + e.getMessage());
                // if tmp exists, try to delete it
                try {
                    Files.deleteIfExists(tmp);
                } catch (Exception ignore) {
                }
                return;
            }

            // replace original with tmp (atomic move if possible)
            try {
                Files.move(tmp, p, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException amnse) {
                // fallback to non-atomic move
                try {
                    Files.move(tmp, p, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    addWarn("OpenObserveHttpAppender: failed to replace spool file: " + e.getMessage());
                }
            } catch (IOException e) {
                addWarn("OpenObserveHttpAppender: failed to replace spool file: " + e.getMessage());
            }
        }
    }

    /* ======= JSON helpers and HTTP ======= */

    private ObjectNode toJsonNode(ILoggingEvent ev) {
        ObjectNode node = mapper.createObjectNode();
        Instant ts = Instant.ofEpochMilli(ev.getTimeStamp());
        node.put("@timestamp", ts.toString());
        node.put("message", ev.getFormattedMessage());
        node.put("level", ev.getLevel().toString());
        node.put("logger", ev.getLoggerName());
        node.put("thread", ev.getThreadName());
        node.put("service", service);
        node.put("replicaId", replicaId);
        Map<String, String> mdc = ev.getMDCPropertyMap();
        if (mdc != null && !mdc.isEmpty()) {
            ObjectNode mdcNode = mapper.createObjectNode();
            for (Map.Entry<String, String> e : mdc.entrySet()) {
                mdcNode.put(e.getKey(), e.getValue());
            }
            node.set("mdc", mdcNode);
        }
        if (ev.getThrowableProxy() != null) {
            node.put("stackTrace", ev.getThrowableProxy().getClassName() + ":" + ev.getThrowableProxy().getMessage());
        }
        return node;
    }

    private String toJsonLine(ILoggingEvent ev) throws IOException {
        ObjectNode n = toJsonNode(ev);
        return mapper.writeValueAsString(n);
    }

    private boolean sendWithRetries(String body, int retries) {
        int attempt = 0;
        long backoff = 500;
        while (attempt <= retries) {
            try {
                HttpRequest.Builder rb = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(java.time.Duration.ofSeconds(20))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body));
                if (authHeaderValue != null) rb.header("Authorization", authHeaderValue);
                HttpRequest req = rb.build();
                HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
                int code = resp.statusCode();
                if (code >= 200 && code < 300) {
                    return true;
                } else {
                    addWarn("OpenObserveHttpAppender: HTTP error code=" + code + " body=" + resp.body());
                }
            } catch (Exception e) {
                addWarn("OpenObserveHttpAppender: HTTP send failed attempt " + attempt + ": " + e.getMessage());
            }
            attempt++;
            try {
                Thread.sleep(backoff);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                return false;
            }
            backoff = Math.min(backoff * 2, 10_000);
        }
        return false;
    }
}
