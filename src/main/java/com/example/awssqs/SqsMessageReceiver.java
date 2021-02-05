package com.example.awssqs;

import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqsMessageReceiver {
    /**
     * Using SQS Queue URL, we can set the name of queue.
     */
    @SqsListener(value = "${cloud.aws.sqs.queue.url}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receiveForQueueUrl(String message, @Header("SenderId") String senderId) {
        System.out.println(String.format("%s %s", message, senderId));
    }

    /**
     * Using name of SQS Queue, we can set the name of queue.
     */
    @SqsListener(value = "z-standard-queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receiveForQueueName(String message, @Header("SenderId") String senderId) {
        System.out.println(String.format("%s %s", message, senderId));
    }

    /**
     * When we receive message, The message is immediately deleted.
     */
    @SqsListener(value = "z-standard-queue", deletionPolicy = SqsMessageDeletionPolicy.NO_REDRIVE)
    public void receiveWithNoRedrive(String message, @Header("SenderId") String senderId) {
        System.out.println(String.format("%s %s", message, senderId));
    }

    /**
     * Even if we receive message, we cannot delete it.
     * NEVER <-> ALWAYS
     */
    @SqsListener(value = "z-standard-queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receiveWithNever(String message, @Header("SenderId") String senderId) {
        System.out.println(String.format("%s %s", message, senderId));
    }

    /**
     * Always deletes message in case of success (no exception thrown) or failure (exception thrown).
     * ALWAYS <-> NEVER
     */
    @SqsListener(value = "z-standard-queue", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
    public void receiveWithAlways(String message, @Header("SenderId") String senderId) {
        throw new RuntimeException("Even if an exception occurs, message is unconditionally deleted.");
    }

    /**
     * If an exception is thrown by the listener method, the message will not be deleted.
     * NO_REDRIVE <-> ON_SUCCESS
     */
    @SqsListener(value = "z-standard-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveWithOnSuccess(String message, @Header("SenderId") String senderId) {
        throw new RuntimeException("If it is thrown exception, message is not deleted.");
    }
}
