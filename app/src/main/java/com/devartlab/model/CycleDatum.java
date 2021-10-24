
package com.devartlab.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CycleDatum {

    @SerializedName("CurrentCycleId")
    @Expose
    @Nullable
    private int currentCycleId;
    @SerializedName("CurrentCyclePlanId")
    @Expose
    @Nullable
    private Integer currentCyclePlanId;
    @SerializedName("CurrentCycleFromDate")
    @Expose
    @Nullable
    private String currentCycleFromDate;
    @SerializedName("CurrentCycleToDate")
    @Expose
    @Nullable
    private String currentCycleToDate;
    @SerializedName("OpenCycleId")
    @Expose
    @Nullable
    private Integer openCycleId;
    @SerializedName("OpenPLanCycleId")
    @Expose
    @Nullable
    private int openPLanCycleId;
    @SerializedName("OpenCycleFromDate")
    @Expose
    @Nullable
    private String openCycleFromDate;
    @SerializedName("OpenCycleToDate")
    @Expose
    @Nullable
    private String openCycleToDate;
    @SerializedName("CurrentFromDateMs")
    @Expose
    @Nullable
    private long currentFromDateMs;
    @SerializedName("CurrentToDateMs")
    @Expose
    @Nullable
    private long currentToDateMs;
    @SerializedName("OpenFromDateMs")
    @Expose
    @Nullable
    private long openFromDateMs;
    @SerializedName("OpenToDateMs")
    @Expose
    @Nullable
    private long openToDateMs;


    public CycleDatum(int currentCycleId, @Nullable Integer currentCyclePlanId, @Nullable String currentCycleFromDate, @Nullable String currentCycleToDate, @Nullable Integer openCycleId, int openPLanCycleId, @Nullable String openCycleFromDate, @Nullable String openCycleToDate, long currentFromDateMs, long currentToDateMs, long openFromDateMs, long openToDateMs) {
        this.currentCycleId = currentCycleId;
        this.currentCyclePlanId = currentCyclePlanId;
        this.currentCycleFromDate = currentCycleFromDate;
        this.currentCycleToDate = currentCycleToDate;
        this.openCycleId = openCycleId;
        this.openPLanCycleId = openPLanCycleId;
        this.openCycleFromDate = openCycleFromDate;
        this.openCycleToDate = openCycleToDate;
        this.currentFromDateMs = currentFromDateMs;
        this.currentToDateMs = currentToDateMs;
        this.openFromDateMs = openFromDateMs;
        this.openToDateMs = openToDateMs;
    }

    @Nullable
    public int getCurrentCycleId() {
        return currentCycleId;
    }

    public void setCurrentCycleId(@Nullable int currentCycleId) {
        this.currentCycleId = currentCycleId;
    }

    @Nullable
    public Integer getCurrentCyclePlanId() {
        return currentCyclePlanId;
    }

    public void setCurrentCyclePlanId(@Nullable Integer currentCyclePlanId) {
        this.currentCyclePlanId = currentCyclePlanId;
    }

    @Nullable
    public String getCurrentCycleFromDate() {
        return currentCycleFromDate;
    }

    public void setCurrentCycleFromDate(@Nullable String currentCycleFromDate) {
        this.currentCycleFromDate = currentCycleFromDate;
    }

    @Nullable
    public String getCurrentCycleToDate() {
        return currentCycleToDate;
    }

    public void setCurrentCycleToDate(@Nullable String currentCycleToDate) {
        this.currentCycleToDate = currentCycleToDate;
    }

    @Nullable
    public Integer getOpenCycleId() {
        return openCycleId;
    }

    public void setOpenCycleId(@Nullable Integer openCycleId) {
        this.openCycleId = openCycleId;
    }

    public int getOpenPLanCycleId() {
        return openPLanCycleId;
    }

    public void setOpenPLanCycleId(int openPLanCycleId) {
        this.openPLanCycleId = openPLanCycleId;
    }

    @Nullable
    public String getOpenCycleFromDate() {
        return openCycleFromDate;
    }

    public void setOpenCycleFromDate(@Nullable String openCycleFromDate) {
        this.openCycleFromDate = openCycleFromDate;
    }

    @Nullable
    public String getOpenCycleToDate() {
        return openCycleToDate;
    }

    public void setOpenCycleToDate(@Nullable String openCycleToDate) {
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
