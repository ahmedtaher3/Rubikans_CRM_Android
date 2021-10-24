package com.devartlab.model;

import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartPointReport {

    @SerializedName("ShiftId")
    @Expose
    @Nullable
    private Integer shiftId;
    @SerializedName("StartPointEmpId")
    @Expose
    @Nullable
    private Integer startPointEmpId;
    @SerializedName("StartPointAccountId")
    @Expose
    @Nullable
    private Integer startPointAccountId;
    @SerializedName("StartPointSalesRptDate")
    @Expose
    @Nullable
    private String startPointSalesRptDate;
    @SerializedName("StartLat")
    @Expose
    @Nullable
    private String startLat;
    @SerializedName("StartLang")
    @Expose
    @Nullable
    private String startLang;
    @SerializedName("ReportStartTime")
    @Expose
    @Nullable
    private String reportStartTime;
    @SerializedName("PlanStartTime")
    @Expose
    @Nullable
    private String planStartTime;
    @SerializedName("Diff")
    @Expose
    @Nullable
    private Integer diff;
    @SerializedName("StartPointStatus")
    @Expose
    @Nullable
    private Integer startPointStatus;
    @SerializedName("ReportEndTime")
    @Expose
    @Nullable
    private String reportEndTime;
    @SerializedName("endLat")
    @Expose
    @Nullable
    private Double endLat;
    @SerializedName("EndLang")
    @Expose
    @Nullable
    private Double endLang;
    @SerializedName("JobId")
    @Expose
    @Nullable
    private Integer jobId;
    @SerializedName("EmpArName")
    @Expose
    @Nullable
    private String empArName;
    @SerializedName("EmpEnName")
    @Expose
    @Nullable
    private String empEnName;
    @SerializedName("JobArName")
    @Expose
    @Nullable
    private String jobArName;
    @SerializedName("JobEnName")
    @Expose
    @Nullable
    private String jobEnName;
    @SerializedName("ManagerArName")
    @Expose
    @Nullable
    private String managerArName;
    @SerializedName("ImagePath")
    @Expose
    @Nullable
    private String imagePath;
    private String address;

    @Nullable
    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(@Nullable Integer shiftId) {
        this.shiftId = shiftId;
    }

    @Nullable
    public Integer getStartPointEmpId() {
        return startPointEmpId;
    }

    public void setStartPointEmpId(@Nullable Integer startPointEmpId) {
        this.startPointEmpId = startPointEmpId;
    }

    @Nullable
    public Integer getStartPointAccountId() {
        return startPointAccountId;
    }

    public void setStartPointAccountId(@Nullable Integer startPointAccountId) {
        this.startPointAccountId = startPointAccountId;
    }

    @Nullable
    public String getStartPointSalesRptDate() {
        return startPointSalesRptDate;
    }

    public void setStartPointSalesRptDate(@Nullable String startPointSalesRptDate) {
        this.startPointSalesRptDate = startPointSalesRptDate;
    }

    @Nullable
    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(@Nullable String startLat) {
        this.startLat = startLat;
    }

    @Nullable
    public String getStartLang() {
        return startLang;
    }

    public void setStartLang(@Nullable String startLang) {
        this.startLang = startLang;
    }

    @Nullable
    public String getReportStartTime() {
        return reportStartTime;
    }

    public void setReportStartTime(@Nullable String reportStartTime) {
        this.reportStartTime = reportStartTime;
    }

    @Nullable
    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(@Nullable String planStartTime) {
        this.planStartTime = planStartTime;
    }

    @Nullable
    public Integer getDiff() {
        return diff;
    }

    public void setDiff(@Nullable Integer diff) {
        this.diff = diff;
    }

    @Nullable
    public Integer getStartPointStatus() {
        return startPointStatus;
    }

    public void setStartPointStatus(@Nullable Integer startPointStatus) {
        this.startPointStatus = startPointStatus;
    }

    @Nullable
    public String getReportEndTime() {
        return reportEndTime;
    }

    public void setReportEndTime(@Nullable String reportEndTime) {
        this.reportEndTime = reportEndTime;
    }

    @Nullable
    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(@Nullable Double endLat) {
        this.endLat = endLat;
    }

    @Nullable
    public Double getEndLang() {
        return endLang;
    }

    public void setEndLang(@Nullable Double endLang) {
        this.endLang = endLang;
    }

    @Nullable
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(@Nullable Integer jobId) {
        this.jobId = jobId;
    }

    @Nullable
    public String getEmpArName() {
        return empArName;
    }

    public void setEmpArName(@Nullable String empArName) {
        this.empArName = empArName;
    }

    @Nullable
    public String getEmpEnName() {
        return empEnName;
    }

    public void setEmpEnName(@Nullable String empEnName) {
        this.empEnName = empEnName;
    }

    @Nullable
    public String getJobArName() {
        return jobArName;
    }

    public void setJobArName(@Nullable String jobArName) {
        this.jobArName = jobArName;
    }

    @Nullable
    public String getJobEnName() {
        return jobEnName;
    }

    public void setJobEnName(@Nullable String jobEnName) {
        this.jobEnName = jobEnName;
    }

    @Nullable
    public String getManagerArName() {
        return managerArName;
    }

    public void setManagerArName(@Nullable String managerArName) {
        this.managerArName = managerArName;
    }

    @Nullable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@Nullable String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}