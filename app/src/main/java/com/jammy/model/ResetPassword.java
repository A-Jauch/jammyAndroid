package com.jammy.model;

public class ResetPassword {
    private String token;
    private String password;
    private String confirm;

    public ResetPassword() {
    }

    public ResetPassword(String token, String password, String confirm) {
        this.token = token;
        this.password = password;
        this.confirm = confirm;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
