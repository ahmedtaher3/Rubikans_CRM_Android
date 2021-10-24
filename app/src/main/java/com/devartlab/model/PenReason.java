package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenReason {
    @SerializedName("PenReasonId")
    @Expose
    private Integer penReasonId;
    @SerializedName("PenReasonArName")
    @Expose
    private String penReasonArName;
    @SerializedName("PenReasonEnName")
    @Expose
    private String penReasonEnName;

    public Integer getPenReasonId() {
        return penReasonId;
    }

    public void setPenReasonId(Integer penReasonId) {
        this.penReasonId = penReasonId;
    }

    public String getPenReasonArName() {
        return penReasonArName;
    }

    public void setPenReasonArName(String penReasonArName) {
        this.penReasonArName = penReasonArName;
    }

    public String getPenReasonEnName() {
        return penReasonEnName;
    }

    public void setPenReasonEnName(String penReasonEnName) {
        this.penReasonEnName = penReasonEnName;
    }

}
