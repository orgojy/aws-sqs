package com.example.awssqs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.sqs.queue")
public class AmazonSqsProperties {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AmazonSqsConfiguration{" +
                "url='" + url + '\'' +
                '}';
    }
}
