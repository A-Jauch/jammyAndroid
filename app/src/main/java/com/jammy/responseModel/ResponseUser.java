package com.jammy.responseModel;

import com.google.gson.annotations.SerializedName;
import com.jammy.model.Friends;
import com.jammy.model.User;

import java.util.List;

public class ResponseUser {
    Boolean succes;
    @SerializedName("results")
    User results;

    public ResponseUser(Boolean succes, User results) {
        this.succes = succes;
        this.results = results;
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public User getResults() {
        return results;
    }

    public void setResults(User results) {
        this.results = results;
    }
}
