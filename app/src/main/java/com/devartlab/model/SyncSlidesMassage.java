package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncSlidesMassage {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("AllowToUpdate")
    @Expose
    private Boolean allowToUpdate;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAllowToUpdate() {
        return allowToUpdate;
    }

    public void setAllowToUpdate(Boolean allowToUpdate) {
        this.allowToUpdate = allowToUpdate;
    }

}