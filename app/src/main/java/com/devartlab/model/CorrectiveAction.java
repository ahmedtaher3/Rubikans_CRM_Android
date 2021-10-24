package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CorrectiveAction {

    @SerializedName("CorrectiveActionId")
    @Expose
    private Integer correctiveActionId;
    @SerializedName("CorrectiveActionName")
    @Expose
    private String correctiveActionName;
    @SerializedName("CorrectiveActionDescription")
    @Expose
    private String correctiveActionDescription;

    public Integer getCorrectiveActionId() {
        return correctiveActionId;
    }

    public void setCorrectiveActionId(Integer correctiveActionId) {
        this.correctiveActionId = correctiveActionId;
    }

    public String getCorrectiveActionName() {
        return correctiveActionName;
    }

    public void setCorrectiveActionName(String correctiveActionName) {
        this.correctiveActionName = correctiveActionName;
    }

    public String getCorrectiveActionDescription() {
        return correctiveActionDescription;
    }

    public void setCorrectiveActionDescription(String correctiveActionDescription) {
        this.correctiveActionDescription = correctiveActionDescription;
    }

}