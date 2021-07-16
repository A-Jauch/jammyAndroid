package com.jammy.model;

public class Query {
    private int jam_id;
    private int user_id;
    private int id;
    private int status;
    User user;
    Jam jam;

    public Query() {
    }

    public int getJam_id() {
        return jam_id;
    }

    public void setJam_id(int jam_id) {
        this.jam_id = jam_id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jam getJam() {
        return jam;
    }

    public void setJam(Jam jam) {
        this.jam = jam;
    }
}
