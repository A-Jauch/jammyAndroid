package com.jammy.model;

public class Request {
        private int jam_or_cat;
        private int user_id;
        private int id;
        private int status;
        private String content;
        private String createdAt;
        private String answer;
        User user;

    public Request() {
    }

    public Request(int jam_or_cat, int user_id, String content) {
        this.jam_or_cat = jam_or_cat;
        this.user_id = user_id;
        this.content = content;
    }

    public int getJam_or_cat() {
        return jam_or_cat;
    }

    public void setJam_or_cat(int jam_or_cat) {
        this.jam_or_cat = jam_or_cat;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
