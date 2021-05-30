package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Thread;

import java.util.List;

public class ResponseThread {
    Boolean succes;
    @SerializedName("results")
    List<Thread> results;
    private String message;

    public ResponseThread(Boolean succes, List<Thread> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseThread() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Thread> getResults() {
        return results;
    }

    public void setResults(List<Thread> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
