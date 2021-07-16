package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Comment;

import java.util.List;

public class ResponseComment {

    Boolean succes;
    @SerializedName("results")
    List<Comment> results;

    public ResponseComment(Boolean succes, List<Comment> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseComment() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Comment> getResults() {
        return results;
    }

    public void setResults(List<Comment> results) {
        this.results = results;
    }

}
