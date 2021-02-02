package com.example.awssqs;

import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqsMessageReceiver {
    @SqsListener(value = "${cloud.aws.sqs.queue.url}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receive(String message, @Header("SenderId") String senderId) {
        System.out.println(String.format("%s %s", message, senderId));
    }
}
