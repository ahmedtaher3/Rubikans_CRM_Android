
package com.devartlab.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("MarkReqTypeId")
     @Expose
    @Nullable
    private Integer markReqTypeId;
    @SerializedName("Activity")
     @Expose
    @Nullable
    private String activity;
    @SerializedName("NumberOfRequest")
     @Expose
    @Nullable
    private Integer numberOfRequest;
    @SerializedName("TotalCoast")
     @Expose
    @Nullable
    private Double totalCoast;
    @SerializedName("MonthlyCoast")
     @Expose
    @Nullable
    private Double monthlyCoast;
    @SerializedName("TotalGain")
     @Expose
    @Nullable
    private Double totalGain;

    @Nullable
    public Integer getMarkReqTypeId() {
        return markReqTypeId;
    }

    public void setMarkReqTypeId(@Nullable Integer markReqTypeId) {
        this.markReqTypeId = markReqTypeId;
    }

    @Nullable
    public String getActivity() {
        return activity;
    }

    public void setActivity(@Nullable String activity) {
        this.activity = activity;
    }

    @Nullable
    public Integer getNumberOfRequest() {
        return numberOfRequest;
    }

    public void setNumberOfRequest(@Nullable Integer numberOfRequest) {
        this.numberOfRequest = numberOfRequest;
    }

    @Nullable
    public Double getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(@Nullable Double totalCoast) {
        this.totalCoast = totalCoast;
    }

    @Nullable
    public Double getMonthlyCoast() {
        return monthlyCoast;
    }

    public void setMonthlyCoast(@Nullable Double monthlyCoast) {
        this.monthlyCoast = monthlyCoast;
    }

    @Nullable
    public Double getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(@Nullable Double totalGain) {
        this.totalGain = totalGain;
    }
}
