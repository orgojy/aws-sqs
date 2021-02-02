package com.example.awssqs;

import com.example.awssqs.config.AmazonSqsProperties;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageSender {
    private final AmazonSqsProperties amazonSqsProperties;
    private final QueueMessagingTemplate queueMessagingTemplate;

    public SqsMessageSender(AmazonSqsProperties amazonSqsProperties, QueueMessagingTemplate queueMessagingTemplate) {
        this.amazonSqsProperties = amazonSqsProperties;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    public void send(String message) {
        queueMessagingTemplate.send(amazonSqsProperties.getUrl(), MessageBuilder.withPayload(message).build());
    }
}
