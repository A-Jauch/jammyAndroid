package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Category;

import java.util.ArrayList;
import java.util.List;

public class ResponseCategory {
    Boolean succes;
    @SerializedName("results")
    List<Category> results;

    public ResponseCategory(Boolean succes, List<Category> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseCategory() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Category> getResults() {
        return results;
    }

    public void setResults(ArrayList<Category> results) {
        this.results = results;
    }
}
