package com.jammy.model;

public class Comment {
    private int id;
    private String content;
    private String createdAt;
    private int user_id;
    private int post_id;
    private User user;
    private Post post;

    public Comment() {
    }

    public Comment(int id, String content, String createdAt, int user_id, int post_id, User user, Post post) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user_id = user_id;
        this.post_id = post_id;
        this.user = user;
        this.post = post;
    }

    public Comment(String content, int user_id, int post_id) {
        this.content = content;
        this.user_id = user_id;
        this.post_id = post_id;
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

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
