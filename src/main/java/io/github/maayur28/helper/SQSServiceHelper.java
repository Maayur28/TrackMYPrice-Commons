package io.github.maayur28.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import static io.github.maayur28.utils.Constants.CONST_AWS_SQS_URL;

@Service
public class SQSServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(SQSServiceHelper.class);
    private final SqsClient sqsClient;

    public SQSServiceHelper(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public final SendMessageResponse publishMessage(final String awsSqsURI, final String message, final String messageGroupId) {
        logger.info("SQS publish message: {} and messageGroupId: {}", message, messageGroupId);
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(awsSqsURI)
                .messageBody(message)
                .messageGroupId(messageGroupId)
                .build();
        SendMessageResponse sendMessageResponse = sqsClient.sendMessage(sendMessageRequest);
        logger.info("SQS Message sen for messageId: {}", sendMessageResponse.messageId());
        return sendMessageResponse;
    }
}
