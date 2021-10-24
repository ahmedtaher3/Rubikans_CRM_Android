package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cycle {
    @SerializedName("PlanId")
    @Expose
    private Integer planId;
    @SerializedName("CycleId")
    @Expose
    private Integer cycleId;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("CycleArName")
    @Expose
    private String cycleArName;
    @SerializedName("IsOpen")
    @Expose
    private Boolean isOpen;
    @SerializedName("FromDateMs")
    @Expose
    private Long fromDateMs;
    @SerializedName("ToDateMs")
    @Expose
    private Long toDateMs;

    public Cycle(Integer planId, Integer cycleId, String fromDate, String toDate, Integer accountId, String cycleArName, Boolean isOpen, Long fromDateMs, Long toDateMs) {
        this.planId = planId;
        this.cycleId = cycleId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.accountId = accountId;
        this.cycleArName = cycleArName;
        this.isOpen = isOpen;
        this.fromDateMs = fromDateMs;
        this.toDateMs = toDateMs;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getCycleId() {
        return cycleId;
    }

    public void setCycleId(Integer cycleId) {
        this.cycleId = cycleId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getCycleArName() {
        return cycleArName;
    }

    public void setCycleArName(String cycleArName) {
        this.cycleArName = cycleArName;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Long getFromDateMs() {
        return fromDateMs;
    }

    public void setFromDateMs(Long fromDateMs) {
        this.fromDateMs = fromDateMs;
    }

    public Long getToDateMs() {
        return toDateMs;
    }

    public void setToDateMs(Long toDateMs) {
        this.toDateMs = toDateMs;
    }

}
