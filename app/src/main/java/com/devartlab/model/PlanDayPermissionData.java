package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanDayPermissionData {

    @SerializedName("Day")
    @Expose
    private String day;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("EnName")
    @Expose
    private String enName;
    @SerializedName("ActivityArName")
    @Expose
    private String activityArName;
    @SerializedName("Double Visit Employee")
    @Expose
    private String doubleVisitEmployee;
    @SerializedName("Show Plan")
    @Expose
    private String showPlan;
    @SerializedName("OPenDay")
    @Expose
    private Boolean oPenDay;
    @SerializedName("Expire Date")
    @Expose
    private String expireDate;
    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("PlanId")
    @Expose
    private Integer planId;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("ActivityId")
    @Expose
    private Integer activityId;
    @SerializedName("ActivityType")
    @Expose
    private Integer activityType;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getActivityArName() {
        return activityArName;
    }

    public void setActivityArName(String activityArName) {
        this.activityArName = activityArName;
    }

    public String getDoubleVisitEmployee() {
        return doubleVisitEmployee;
    }

    public void setDoubleVisitEmployee(String doubleVisitEmployee) {
        this.doubleVisitEmployee = doubleVisitEmployee;
    }

    public String getShowPlan() {
        return showPlan;
    }

    public void setShowPlan(String showPlan) {
        this.showPlan = showPlan;
    }

    public Boolean getOPenDay() {
        return oPenDay;
    }

    public void setOPenDay(Boolean oPenDay) {
        this.oPenDay = oPenDay;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

}
