package com.jammy.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Friends {
    private int id_user1;
    private int id_user2;
    User user1;
    User user2;

    public Friends() {
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
}
