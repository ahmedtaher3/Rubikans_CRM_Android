package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartPointData {

    @SerializedName("ConfDetId")
    @Expose
    private Integer confDetId;
    @SerializedName("SalesRptId")
    @Expose
    private Object salesRptId;
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("AssignId")
    @Expose
    private Object assignId;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("SalesRptDate")
    @Expose
    private String salesRptDate;
    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("StartPointDateTime")
    @Expose
    private String startPointDateTime;
    @SerializedName("StartPointId")
    @Expose
    private Integer startPointId;
    @SerializedName("StartPointBranchId")
    @Expose
    private Integer startPointBranchId;
    @SerializedName("LatVal")
    @Expose
    private String latVal;
    @SerializedName("LangVal")
    @Expose
    private String langVal;
    @SerializedName("IsStartPoint")
    @Expose
    private Boolean isStartPoint;
    @SerializedName("StartTime")
    @Expose
    private String startTime;

    public Integer getConfDetId() {
        return confDetId;
    }

    public void setConfDetId(Integer confDetId) {
        this.confDetId = confDetId;
    }

    public Object getSalesRptId() {
        return salesRptId;
    }

    public void setSalesRptId(Object salesRptId) {
        this.salesRptId = salesRptId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Object getAssignId() {
        return assignId;
    }

    public void setAssignId(Object assignId) {
        this.assignId = assignId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getSalesRptDate() {
        return salesRptDate;
    }

    public void setSalesRptDate(String salesRptDate) {
        this.salesRptDate = salesRptDate;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public String getStartPointDateTime() {
        return startPointDateTime;
    }

    public void setStartPointDateTime(String startPointDateTime) {
        this.startPointDateTime = startPointDateTime;
    }

    public Integer getStartPointId() {
        return startPointId;
    }

    public void setStartPointId(Integer startPointId) {
        this.startPointId = startPointId;
    }

    public Integer getStartPointBranchId() {
        return startPointBranchId;
    }

    public void setStartPointBranchId(Integer startPointBranchId) {
        this.startPointBranchId = startPointBranchId;
    }

    public String getLatVal() {
        return latVal;
    }

    public void setLatVal(String latVal) {
        this.latVal = latVal;
    }

    public String getLangVal() {
        return langVal;
    }

    public void setLangVal(String langVal) {
        this.langVal = langVal;
    }

    public Boolean getIsStartPoint() {
        return isStartPoint;
    }

    public void setIsStartPoint(Boolean isStartPoint) {
        this.isStartPoint = isStartPoint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

}
