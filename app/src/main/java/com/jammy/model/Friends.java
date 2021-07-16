package com.jammy.model;

public class Friends {
    private int id_user1;
    private int id_user2;
    private int id;
    private int status;
    User user1;
    User user2;

    public Friends() {
    }

    public Friends(int status) {
        this.status = status;
    }

    public Friends(int id_user1, int id_user2, int id, int status, User user1, User user2) {
        this.id_user1 = id_user1;
        this.id_user2 = id_user2;
        this.id = id;
        this.status = status;
        this.user1 = user1;
        this.user2 = user2;
    }

    public Friends(int id_user1, int id_user2, int status) {
        this.id_user1 = id_user1;
        this.id_user2 = id_user2;
        this.status = status;
    }

    public Friends(int id_user1, int id_user2, User user1, User user2) {
        this.id_user1 = id_user1;
        this.id_user2 = id_user2;
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getId_user1() {
        return id_user1;
    }

    public void setId_user1(int id_user1) {
        this.id_user1 = id_user1;
    }

    public int getId_user2() {
        return id_user2;
    }

    public void setId_user2(int id_user2) {
        this.id_user2 = id_user2;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
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
}
