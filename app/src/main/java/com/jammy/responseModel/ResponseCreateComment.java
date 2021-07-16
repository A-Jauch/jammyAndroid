package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.CreateComment;

public class ResponseCreateComment {
    Boolean succes;
    @SerializedName("results")
    CreateComment results;
    private String message;

    public ResponseCreateComment(Boolean succes, CreateComment results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseCreateComment() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public CreateComment getResults() {
        return results;
    }

    public void setResults(CreateComment results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
