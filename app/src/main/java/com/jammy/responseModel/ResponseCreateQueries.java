package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.CreateQuery;

public class ResponseCreateQueries {
    Boolean succes;
    @SerializedName("results")
    CreateQuery results;
    private String message;

    public ResponseCreateQueries(Boolean succes, CreateQuery results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseCreateQueries() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public CreateQuery getResults() {
        return results;
    }

    public void setResults(CreateQuery results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
