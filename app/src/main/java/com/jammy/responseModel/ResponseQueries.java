package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Query;

import java.util.List;

public class ResponseQueries {

    Boolean succes;
    @SerializedName("results")
    List<Query> results;
    private String message;

    public ResponseQueries(Boolean succes, List<Query> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseQueries() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Query> getResults() {
        return results;
    }

    public void setResults(List<Query> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
