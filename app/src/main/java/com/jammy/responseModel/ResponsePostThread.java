package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.PostThread;

public class ResponsePostThread {
    Boolean succes;
    @SerializedName("results")
    PostThread results;
    private String message;

    public ResponsePostThread(Boolean succes, PostThread results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponsePostThread() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public PostThread getResults() {
        return results;
    }

    public void setResults(PostThread results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
