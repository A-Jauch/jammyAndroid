package com.jammy.model;

public class Post {
    private int id;
    private String content;
    private String createdAt;
    private int user_id;
    private int thread_id;
    private User user;
    private Thread thread;

    public Post() {
    }

    public Post(int id, String content, String createdAt, int user_id, int thread_id, User user, Thread thread) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user_id = user_id;
        this.thread_id = thread_id;
        this.user = user;
        this.thread = thread;
    }

    public Post(String content, String createdAt, int user_id, int thread_id, User user, Thread thread) {
        this.content = content;
        this.createdAt = createdAt;
        this.user_id = user_id;
        this.thread_id = thread_id;
        this.user = user;
        this.thread = thread;
    }

    public Post(String content, int user_id, int thread_id) {
        this.content = content;
        this.user_id = user_id;
        this.thread_id = thread_id;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }


}
