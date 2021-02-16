package com.example.awssqs;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SqsMessageSenderTest {
    @Autowired
    private SqsMessageSender sqsMessageSender;

    @Test
    void send() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Ryan An");
        jsonObject.put("age", 30);
        sqsMessageSender.send(jsonObject.toString());
    }

    @Test
    void convertAndSend() {
        int testId = 1;
        SqsMessage message = SqsMessage.of(testId, "value");
        sqsMessageSender.convertAndSend(message);
    }
}
