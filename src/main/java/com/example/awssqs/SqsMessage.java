package com.example.awssqs;

public class SqsMessage {
    private long id;
    private String content;

    public static SqsMessage of(long id, String content) {
        return new SqsMessage(id, content);
    }

    private SqsMessage(long id, String content) {
        this.id = id;
        this.content = content;
    }

    private SqsMessage() {
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "SqsMessage{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
