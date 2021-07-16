package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Jam;

import java.util.List;

public class ResponseJam {

    Boolean succes;
    @SerializedName("results")
    List<Jam> results;

    public ResponseJam(Boolean succes, List<Jam> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseJam() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Jam> getResults() {
        return results;
    }


    public void setResults(List<Jam> results) {
        this.results = results;
    }

}
