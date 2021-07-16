package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Session;

import java.util.List;

public class ResponseSession {

    Boolean succes;
    @SerializedName("results")
    List<Session> results;

    public ResponseSession(Boolean succes, List<Session> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseSession() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Session> getResults() {
        return results;
    }


    public void setResults(List<Session> results) {
        this.results = results;
    }
}
