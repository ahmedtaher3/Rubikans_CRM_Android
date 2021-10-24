package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartPointReportTitle {

    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("StartPointEmpId")
    @Expose
    private Integer startPointEmpId;
    @SerializedName("StartPointAccountId")
    @Expose
    private Integer startPointAccountId;
    @SerializedName("StartPointSalesRptDate")
    @Expose
    private String startPointSalesRptDate;
    @SerializedName("StartLat")
    @Expose
    private String startLat;
    @SerializedName("StartLang")
    @Expose
    private String startLang;
    @SerializedName("ReportStartTime")
    @Expose
    private String reportStartTime;
    @SerializedName("PlanStartTime")
    @Expose
    private Object planStartTime;
    @SerializedName("Diff")
    @Expose
    private Integer diff;
    @SerializedName("StartPointStatus")
    @Expose
    private Integer startPointStatus;
    @SerializedName("ReportEndTime")
    @Expose
    private Object reportEndTime;
    @SerializedName("endLat")
    @Expose
    private Integer endLat;
    @SerializedName("EndLang")
    @Expose
    private Integer endLang;
    @SerializedName("JobId")
    @Expose
    private Integer jobId;
    @SerializedName("EmpArName")
    @Expose
    private String empArName;
    @SerializedName("EmpEnName")
    @Expose
    private String empEnName;
    @SerializedName("JobArName")
    @Expose
    private String jobArName;
    @SerializedName("JobEnName")
    @Expose
    private String jobEnName;
    @SerializedName("ManagerArName")
    @Expose
    private String managerArName;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getStartPointEmpId() {
        return startPointEmpId;
    }

    public void setStartPointEmpId(Integer startPointEmpId) {
        this.startPointEmpId = startPointEmpId;
    }

    public Integer getStartPointAccountId() {
        return startPointAccountId;
    }

    public void setStartPointAccountId(Integer startPointAccountId) {
        this.startPointAccountId = startPointAccountId;
    }

    public String getStartPointSalesRptDate() {
        return startPointSalesRptDate;
    }

    public void setStartPointSalesRptDate(String startPointSalesRptDate) {
        this.startPointSalesRptDate = startPointSalesRptDate;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLang() {
        return startLang;
    }

    public void setStartLang(String startLang) {
        this.startLang = startLang;
    }

    public String getReportStartTime() {
        return reportStartTime;
    }

    public void setReportStartTime(String reportStartTime) {
        this.reportStartTime = reportStartTime;
    }

    public Object getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Object planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Integer getDiff() {
        return diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }

    public Integer getStartPointStatus() {
        return startPointStatus;
    }

    public void setStartPointStatus(Integer startPointStatus) {
        this.startPointStatus = startPointStatus;
    }

    public Object getReportEndTime() {
        return reportEndTime;
    }

    public void setReportEndTime(Object reportEndTime) {
        this.reportEndTime = reportEndTime;
    }

    public Integer getEndLat() {
        return endLat;
    }

    public void setEndLat(Integer endLat) {
        this.endLat = endLat;
    }

    public Integer getEndLang() {
        return endLang;
    }

    public void setEndLang(Integer endLang) {
        this.endLang = endLang;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getEmpArName() {
        return empArName;
    }

    public void setEmpArName(String empArName) {
        this.empArName = empArName;
    }

    public String getEmpEnName() {
        return empEnName;
    }

    public void setEmpEnName(String empEnName) {
        this.empEnName = empEnName;
    }

    public String getJobArName() {
        return jobArName;
    }

    public void setJobArName(String jobArName) {
        this.jobArName = jobArName;
    }

    public String getJobEnName() {
        return jobEnName;
    }

    public void setJobEnName(String jobEnName) {
        this.jobEnName = jobEnName;
    }

    public String getManagerArName() {
        return managerArName;
    }

    public void setManagerArName(String managerArName) {
        this.managerArName = managerArName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
