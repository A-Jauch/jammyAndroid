package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Request;

import java.util.List;

public class ResponseRequest {

    Boolean succes;
    @SerializedName("results")
    List<Request> results;

    public ResponseRequest(Boolean succes, List<Request> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseRequest() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Request> getResults() {
        return results;
    }

    public void setResults(List<Request> results) {
        this.results = results;
    }

}
