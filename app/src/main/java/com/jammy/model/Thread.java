package com.jammy.model;

public class Thread {
    private int id;
    private String name;
    private Category category;
    private int category_id;

    public Thread() {
    }

    public Thread(int id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Thread(String name, Category category) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
