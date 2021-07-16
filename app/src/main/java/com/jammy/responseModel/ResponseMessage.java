package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Message;

import java.util.List;

public class ResponseMessage {

    Boolean succes;
    @SerializedName("results")
    List<Message> results;

    public ResponseMessage(Boolean succes, List<Message> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseMessage() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Message> getResults() {
        return results;
    }


    public void setResults(List<Message> results) {
        this.results = results;
    }


}
