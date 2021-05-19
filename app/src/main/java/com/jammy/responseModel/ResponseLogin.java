package com.jammy.responseModel;

public class ResponseLogin {
    private Boolean success;
    private String accessToken;
    private String message;

    public ResponseLogin(Boolean success, String accessToken) {
        this.success = success;
        this.accessToken = accessToken;
    }

    public ResponseLogin(Boolean success, String accessToken, String message) {
        this.success = success;
        this.accessToken = accessToken;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
