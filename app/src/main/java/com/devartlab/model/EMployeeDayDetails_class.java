package com.devartlab.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EMployeeDayDetails_class {

    @SerializedName("DayROwIndex")
    @Expose
    private Integer dayROwIndex;
    @SerializedName("PenaltyQty")
    @Expose
    private Double penaltyQty;
    @SerializedName("PenaltyArName")
    @Expose
    private String penaltyArName;
    @SerializedName("Penaltyindex")
    @Expose
    private String penaltyindex;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Day")
    @Expose
    private String day;
    @SerializedName("DayDate")
    @Expose
    private String dayDate;

    public Integer getDayROwIndex() {
        return dayROwIndex;
    }

    public void setDayROwIndex(Integer dayROwIndex) {
        this.dayROwIndex = dayROwIndex;
    }

    public Double getPenaltyQty() {
        return penaltyQty;
    }

    public void setPenaltyQty(Double penaltyQty) {
        this.penaltyQty = penaltyQty;
    }

    public String getPenaltyArName() {
        return penaltyArName;
    }

    public void setPenaltyArName(String penaltyArName) {
        this.penaltyArName = penaltyArName;
    }

    public String getPenaltyindex() {
        return penaltyindex;
    }

    public void setPenaltyindex(String penaltyindex) {
        this.penaltyindex = penaltyindex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

}