package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Post;

public class ResponseCreatePost {
    Boolean succes;
    @SerializedName("results")
    Post results;
    private String message;

    public ResponseCreatePost(Boolean succes, Post results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseCreatePost() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public Post getResults() {
        return results;
    }

    public void setResults(Post results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

