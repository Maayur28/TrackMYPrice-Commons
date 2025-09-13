package io.github.maayur28.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@Configuration
@ConditionalOnProperty(name = "trackmyprice.redis.enabled", havingValue = "true", matchIfMissing = true)
public class RedisConfig implements DisposableBean {

    private RedisClient redisClient;
    private boolean isLocalhost;

    public RedisConfig(@Value("${spring.redis.uri}") String redisURI) {
        try {
            SocketOptions socketOptions = SocketOptions.builder()
                    .connectTimeout(Duration.ofMillis(30000))
                    .build();

            // Detect if running with local Redis
            URI uri = new URI(redisURI);
            isLocalhost = "localhost".equalsIgnoreCase(uri.getHost()) || "127.0.0.1".equals(uri.getHost());

            this.redisClient = RedisClient.create(redisURI);
            this.redisClient.setOptions(ClientOptions.builder().socketOptions(socketOptions).build());

        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid Redis URI: " + redisURI, e);
        } catch (Exception e) {
            if (isLocalhost) {
                System.out.println("⚠️ Redis is not available locally. Skipping Redis connection.");
                this.redisClient = null; // Allow local dev without Redis
            } else {
                throw new RuntimeException("Failed to initialize Redis connection", e);
            }
        }
    }

    @Bean
    public StatefulRedisConnection<String, String> redisConnection() {
        if (redisClient == null) {
            return null; // App will boot without Redis locally
        }
        return redisClient.connect();
    }

    @Override
    public void destroy() {
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }
}
