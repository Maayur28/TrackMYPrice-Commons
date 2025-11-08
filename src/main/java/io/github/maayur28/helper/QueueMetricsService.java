package io.github.maayur28.helper;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class QueueMetricsService {
    private final RabbitAdmin rabbitAdmin;

    public QueueMetricsService(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    /** total = ready + unacked (same “Total” you see in UI) */
    public int getMessageCount(String queueName) {
        Properties p = rabbitAdmin.getQueueProperties(queueName);
        if (p == null) return 0; // queue not found / no access
        Object v = p.get("QUEUE_MESSAGE_COUNT");
        return (v instanceof Integer) ? (Integer) v : Integer.parseInt(String.valueOf(v));
    }
}
