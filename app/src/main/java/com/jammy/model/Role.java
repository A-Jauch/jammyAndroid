package com.jammy.model;

public class Role {
    private String name;
    private String description;
    private Boolean is_admin;
    private Boolean is_dev;

    public Role(String name, String description, Boolean is_admin, Boolean is_dev) {
        this.name = name;
        this.description = description;
        this.is_admin = is_admin;
        this.is_dev = is_dev;
    }

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public Boolean getIs_dev() {
        return is_dev;
    }

    public void setIs_dev(Boolean is_dev) {
        this.is_dev = is_dev;
    }
}