package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Instrument;

import java.util.List;

public class ResponseInstrument {
    Boolean succes;
    @SerializedName("results")
    List<Instrument> results;

    public ResponseInstrument(Boolean succes, List<Instrument> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseInstrument() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Instrument> getResults() {
        return results;
    }


    public void setResults(List<Instrument> results) {
        this.results = results;
    }
}
