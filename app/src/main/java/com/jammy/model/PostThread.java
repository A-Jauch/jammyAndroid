package com.jammy.model;

public class PostThread {
    private int id;
    private String name;
    private int category;
    private int category_id;


    public PostThread() {
    }

    public PostThread(int id, String name, int category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public PostThread(String name, int category) {
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
