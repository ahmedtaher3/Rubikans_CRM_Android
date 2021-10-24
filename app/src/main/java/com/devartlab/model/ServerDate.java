package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerDate {

    @SerializedName("MaxDate")
    @Expose
    private String maxDate;
    @SerializedName("MinDate")
    @Expose
    private String minDate;
    @SerializedName("CurrentDayDate")
    @Expose
    private String currentDayDate;
    @SerializedName("VatPercent")
    @Expose
    private Double vatPercent;

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getCurrentDayDate() {
        return currentDayDate;
    }

    public void setCurrentDayDate(String currentDayDate) {
        this.currentDayDate = currentDayDate;
    }

    public Double getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(Double vatPercent) {
        this.vatPercent = vatPercent;
    }

}
