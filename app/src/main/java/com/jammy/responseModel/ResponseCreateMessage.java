package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Message;

public class ResponseCreateMessage {


    Boolean succes;
    @SerializedName("results")
   Message results;

    public ResponseCreateMessage(Boolean succes, Message results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseCreateMessage() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public Message getResults() {
        return results;
    }


    public void setResults(Message results) {
        this.results = results;
    }

}
