package com.jammy.model;

public class Message {
    private int id;
    private String content;
    private int receiver_id;
    private int sender_id;
    private User sender;
    private User receiver;

    public Message() {
    }

    public Message(String content, int receiver_id, int sender_id) {
        this.content = content;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
    }

    public Message(int id, String content, int receiver_id, int sender_id, User sender, User receiver) {
        this.id = id;
        this.content = content;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
        this.sender = sender;
        this.receiver = receiver;
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

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
