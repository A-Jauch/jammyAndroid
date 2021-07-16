package com.jammy.model;

public class CreateComment {
    private int id;
    private String content;
    private int user;
    private String createdAt;
    private int post;
    private int post_id;
    private int user_id;

    public CreateComment() {
    }

    public CreateComment(int id, String content, int user, String createdAt, int post, int post_id, int user_id) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.post = post;
        this.post_id = post_id;
        this.user_id = user_id;
    }

    public CreateComment(String content, int user, int post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
