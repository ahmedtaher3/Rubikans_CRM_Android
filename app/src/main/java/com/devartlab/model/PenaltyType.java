package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenaltyType {
    @SerializedName("PenaltyTypeId")
    @Expose
    private Integer penaltyTypeId;
    @SerializedName("PenaltyArName")
    @Expose
    private String penaltyArName;
    @SerializedName("PenaltyEnName")
    @Expose
    private String penaltyEnName;

    public Integer getPenaltyTypeId() {
        return penaltyTypeId;
    }

    public void setPenaltyTypeId(Integer penaltyTypeId) {
        this.penaltyTypeId = penaltyTypeId;
    }

    public String getPenaltyArName() {
        return penaltyArName;
    }

    public void setPenaltyArName(String penaltyArName) {
        this.penaltyArName = penaltyArName;
    }

    public String getPenaltyEnName() {
        return penaltyEnName;
    }

    public void setPenaltyEnName(String penaltyEnName) {
        this.penaltyEnName = penaltyEnName;
    }

}
