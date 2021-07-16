package com.jammy.model;

public class CreateQuery {
    private int jam;
    private int user;
    private int id;
    private int status;

    public CreateQuery() {
    }

    public CreateQuery(int jam, int user) {
        this.jam = jam;
        this.user = user;
    }

    public int getJam() {
        return jam;
    }

    public void setJam(int jam) {
        this.jam = jam;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
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
