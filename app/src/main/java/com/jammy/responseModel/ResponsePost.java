package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Post;

import java.util.List;

public class ResponsePost {
    Boolean succes;
    @SerializedName("results")
    List<Post> results;

    public ResponsePost(Boolean succes, List<Post> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponsePost() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Post> getResults() {
        return results;
    }


    public void setResults(List<Post> results) {
        this.results = results;
    }

}
