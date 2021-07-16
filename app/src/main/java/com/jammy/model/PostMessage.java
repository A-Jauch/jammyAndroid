package com.jammy.model;

public class PostMessage {
    private int id;
    private String content;
    private int receiver_id;
    private int sender_id;

    public PostMessage() {
    }

    public PostMessage(String content, int receiver_id, int sender_id) {
        this.content = content;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
    }

    public PostMessage(int id, String content, int receiver_id, int sender_id) {
        this.id = id;
        this.content = content;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReceiver() {
        return receiver_id;
    }

    public void setReceiver(int receiver) {
        this.receiver_id = receiver;
    }

    public int getSender() {
        return sender_id;
    }

    public void setSender(int sender) {
        this.sender_id = sender;
    }
}
