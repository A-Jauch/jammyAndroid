package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Friends;

import java.util.ArrayList;
import java.util.List;

public class ResponseFriend {
    Boolean succes;
    @SerializedName("results")
    List<Friends> results;

    public ResponseFriend(Boolean succes, List<Friends> results) {
        this.succes = succes;
        this.results = results;
    }

    public ResponseFriend() {
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public List<Friends> getResults() {
        return results;
    }

    public void setResults(ArrayList<Friends> results) {
        this.results = results;
    }
}
