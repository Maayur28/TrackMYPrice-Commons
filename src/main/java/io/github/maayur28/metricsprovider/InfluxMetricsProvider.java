package io.github.maayur28.metricsprovider;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import java.time.Instant;

public class InfluxMetricsProvider implements MetricsProvider {

    private final WriteApiBlocking write;
    private final String serviceName;

    public InfluxMetricsProvider(String url, String token, String org, String bucket, String serviceName) {
        InfluxDBClient client = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
        this.write = client.getWriteApiBlocking();
        this.serviceName = serviceName;
    }

    @Override
    public final void incrementCount(String name) {
        Point point = Point
                .measurement(name)
                .addTag("service", serviceName)
                .addField("value", 1)
                .time(Instant.now(), WritePrecision.NS);

        write.writePoint(point);
    }

    @Override
    public final void addTimer(String metricName, long durationMs) {
        Point point = Point
                .measurement(metricName)
                .addTag("service", serviceName)
                .addField("duration_ms", durationMs)
                .time(Instant.now(), WritePrecision.NS);

        write.writePoint(point);
    }

    @Override
    public final void gauge(String name, double value) {
        Point point = Point
                .measurement(name)
                .addTag("service", serviceName)
                .addField("value", value)
                .time(Instant.now(), WritePrecision.NS);

        write.writePoint(point);
    }
}
