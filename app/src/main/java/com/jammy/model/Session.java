package com.jammy.model;

public class Session {
    private int jam_id;
    private int participant;
    User user;
    Jam jam;

    public Session() {
    }

    public int getJam_id() {
        return jam_id;
    }

    public void setJam_id(int jam_id) {
        this.jam_id = jam_id;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
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
