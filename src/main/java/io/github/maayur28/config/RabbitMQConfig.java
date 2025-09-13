package io.github.maayur28.config;

import jakarta.annotation.PreDestroy;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicReference;

@Configuration
@ConditionalOnProperty(name = "trackmyprice.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class RabbitMQConfig {

    private final AtomicReference<ConnectionFactory> connectionFactoryRef = new AtomicReference<>();

    @Value("${spring.rabbitmq.uri:}")
    private String uri;

    @Bean
    public ConnectionFactory connectionFactory() {
        if (connectionFactoryRef.get() == null) {
            synchronized (RabbitMQConfig.class) {
                if (connectionFactoryRef.get() == null) {
                    CachingConnectionFactory factory = new CachingConnectionFactory();
                    factory.setUri(uri);
                    connectionFactoryRef.set(factory);
                }
            }
        }
        return connectionFactoryRef.get();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @PreDestroy
    public void destroy() {
        ConnectionFactory connectionFactory = connectionFactoryRef.get();
        if (connectionFactory != null) {
            ((CachingConnectionFactory) connectionFactory).destroy();
        }
    }
}
