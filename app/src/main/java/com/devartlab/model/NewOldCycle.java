package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOldCycle {
    @SerializedName("CurrentCycleId")
    @Expose
    private Integer currentCycleId;
    @SerializedName("CurrentCyclePlanId")
    @Expose
    private Integer currentCyclePlanId;
    @SerializedName("CurrentCycleFromDate")
    @Expose
    private String currentCycleFromDate;
    @SerializedName("CurrentCycleToDate")
    @Expose
    private String currentCycleToDate;
    @SerializedName("OpenCycleId")
    @Expose
    private Integer openCycleId;
    @SerializedName("OpenPLanCycleId")
    @Expose
    private int openPLanCycleId;
    @SerializedName("OpenCycleFromDate")
    @Expose
    private String openCycleFromDate;
    @SerializedName("OpenCycleToDate")
    @Expose
    private String openCycleToDate;
    @SerializedName("CurrentFromDateMs")
    @Expose
    private long currentFromDateMs;
    @SerializedName("CurrentToDateMs")
    @Expose
    private long currentToDateMs;
    @SerializedName("OpenFromDateMs")
    @Expose
    private long openFromDateMs;
    @SerializedName("OpenToDateMs")
    @Expose
    private long openToDateMs;

    public Integer getCurrentCycleId() {
        return currentCycleId;
    }

    public void setCurrentCycleId(Integer currentCycleId) {
        this.currentCycleId = currentCycleId;
    }

    public Integer getCurrentCyclePlanId() {
        return currentCyclePlanId;
    }

    public void setCurrentCyclePlanId(Integer currentCyclePlanId) {
        this.currentCyclePlanId = currentCyclePlanId;
    }

    public String getCurrentCycleFromDate() {
        return currentCycleFromDate;
    }

    public void setCurrentCycleFromDate(String currentCycleFromDate) {
        this.currentCycleFromDate = currentCycleFromDate;
    }

    public String getCurrentCycleToDate() {
        return currentCycleToDate;
    }

    public void setCurrentCycleToDate(String currentCycleToDate) {
        this.currentCycleToDate = currentCycleToDate;
    }

    public Integer getOpenCycleId() {
        return openCycleId;
    }

    public void setOpenCycleId(Integer openCycleId) {
        this.openCycleId = openCycleId;
    }

    public int getOpenPLanCycleId() {
        return openPLanCycleId;
    }

    public void setOpenPLanCycleId(int openPLanCycleId) {
        this.openPLanCycleId = openPLanCycleId;
    }

    public String getOpenCycleFromDate() {
        return openCycleFromDate;
    }

    public void setOpenCycleFromDate(String openCycleFromDate) {
        this.openCycleFromDate = openCycleFromDate;
    }

    public String getOpenCycleToDate() {
        return openCycleToDate;
    }

    public void setOpenCycleToDate(String openCycleToDate) {
        this.openCycleToDate = openCycleToDate;
    }

    public long getCurrentFromDateMs() {
        return currentFromDateMs;
    }

    public void setCurrentFromDateMs(long currentFromDateMs) {
        this.currentFromDateMs = currentFromDateMs;
    }

    public long getCurrentToDateMs() {
        return currentToDateMs;
    }

    public void setCurrentToDateMs(long currentToDateMs) {
        this.currentToDateMs = currentToDateMs;
    }

    public long getOpenFromDateMs() {
        return openFromDateMs;
    }

    public void setOpenFromDateMs(long openFromDateMs) {
        this.openFromDateMs = openFromDateMs;
    }

    public long getOpenToDateMs() {
        return openToDateMs;
    }

    public void setOpenToDateMs(long openToDateMs) {
        this.openToDateMs = openToDateMs;
    }

}
