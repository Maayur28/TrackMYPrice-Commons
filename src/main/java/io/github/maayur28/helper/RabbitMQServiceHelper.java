package io.github.maayur28.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static io.github.maayur28.utils.Constants.CONST_REQUEST_ID;
import static io.github.maayur28.utils.Constants.RabbitMQ_EXCHANGE.CONST_RABBIT_MQ_NOTIFICATION_EXCHANGE;
import static io.github.maayur28.utils.Constants.RoutingKeys.CONST_BROADCAST_MESSAGE_ROUTING_KEY;
import static io.github.maayur28.utils.Constants.RoutingKeys.CONST_SEND_MESSAGE_ROUTING_KEY;

@Service
public class RabbitMQServiceHelper {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQServiceHelper.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQServiceHelper(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        try {
            Message rabbitMessage = MessageBuilder.withBody(message.getBytes()).setHeader(CONST_REQUEST_ID, MDC.get(CONST_REQUEST_ID)).build();
            rabbitTemplate.send(CONST_RABBIT_MQ_NOTIFICATION_EXCHANGE, CONST_SEND_MESSAGE_ROUTING_KEY, rabbitMessage);
        } catch (Exception e) {
            logger.error("Failed to publishMessage_sendMessage : {}", e.getMessage());
        } finally {
            logger.info("publishMessage_sendMessage published");
        }
    }

    public void broadcastMessage(String message) {
        try {
            Message rabbitMessage = MessageBuilder.withBody(message.getBytes()).setHeader(CONST_REQUEST_ID, MDC.get(CONST_REQUEST_ID)).build();
            rabbitTemplate.send(CONST_RABBIT_MQ_NOTIFICATION_EXCHANGE, CONST_BROADCAST_MESSAGE_ROUTING_KEY, rabbitMessage);
        } catch (Exception e) {
            logger.error("Failed to publishMessage_broadcastMessage : {}", e.getMessage());
        } finally {
            logger.info("publishMessage_broadcastMessage published");
        }
    }
}
