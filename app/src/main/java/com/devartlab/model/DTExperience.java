package com.devartlab.model;


import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DTExperience {

    @SerializedName("FromDate")
    @Expose
    @Nullable
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    @Nullable
    private String toDate;
    @SerializedName("Company")
    @Expose
    @Nullable
    private String company;
    @SerializedName("JobArName")
    @Expose
    @Nullable
    private String jobArName;
    @SerializedName("Descriptions")
    @Expose
    @Nullable
    private String descriptions;
    @SerializedName("ISTraining")
    @Expose
    @Nullable
    private Boolean iSTraining;
    @SerializedName("JobId")
    @Expose
    @Nullable
    private Integer jobId;
    @SerializedName("Expid")
    @Expose
    @Nullable
    private Integer expid;

    public DTExperience(@Nullable String fromDate, @Nullable String toDate, @Nullable String company, @Nullable String jobArName, @Nullable String descriptions, @Nullable Boolean iSTraining, @Nullable Integer jobId, @Nullable Integer expid) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.company = company;
        this.jobArName = jobArName;
        this.descriptions = descriptions;
        this.iSTraining = iSTraining;
        this.jobId = jobId;
        this.expid = expid;
    }

    public DTExperience() {
    }

    @Nullable
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(@Nullable String fromDate) {
        this.fromDate = fromDate;
    }

    @Nullable
    public String getToDate() {
        return toDate;
    }

    public void setToDate(@Nullable String toDate) {
        this.toDate = toDate;
    }

    @Nullable
    public String getCompany() {
        return company;
    }

    public void setCompany(@Nullable String company) {
        this.company = company;
    }

    @Nullable
    public String getJobArName() {
        return jobArName;
    }

    public void setJobArName(@Nullable String jobArName) {
        this.jobArName = jobArName;
    }

    @Nullable
    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(@Nullable String descriptions) {
        this.descriptions = descriptions;
    }

    @Nullable
    public Boolean getiSTraining() {
        return iSTraining;
    }

    public void setiSTraining(@Nullable Boolean iSTraining) {
        this.iSTraining = iSTraining;
    }

    @Nullable
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(@Nullable Integer jobId) {
        this.jobId = jobId;
    }

    @Nullable
    public Integer getExpid() {
        return expid;
    }

    public void setExpid(@Nullable Integer expid) {
        this.expid = expid;
    }
}
