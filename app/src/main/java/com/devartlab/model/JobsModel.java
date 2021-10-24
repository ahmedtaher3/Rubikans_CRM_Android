package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobsModel {

    @SerializedName("JobtId")
    @Expose
    private Integer jobtId;
    @SerializedName("JobSerial")
    @Expose
    private String jobSerial;
    @SerializedName("ParentjobId")
    @Expose
    private Integer parentjobId;
    @SerializedName("JobArName")
    @Expose
    private String jobArName;
    @SerializedName("JobEnName")
    @Expose
    private String jobEnName;
    @SerializedName("JobNotes")
    @Expose
    private String jobNotes;
    @SerializedName("IsManager")
    @Expose
    private Boolean isManager;
    @SerializedName("ManagerialLevel")
    @Expose
    private Object managerialLevel;
    @SerializedName("DeptId")
    @Expose
    private Object deptId;
    @SerializedName("AddUserId")
    @Expose
    private Object addUserId;
    @SerializedName("AddMAc")
    @Expose
    private String addMAc;
    @SerializedName("AddDateTime")
    @Expose
    private String addDateTime;
    @SerializedName("ModifyUserId")
    @Expose
    private Integer modifyUserId;
    @SerializedName("ModifyMac")
    @Expose
    private String modifyMac;
    @SerializedName("ModifyDatetime")
    @Expose
    private String modifyDatetime;
    @SerializedName("IsOuterJob")
    @Expose
    private Object isOuterJob;
    @SerializedName("Status")
    @Expose
    private Object status;

    public Integer getJobtId() {
        return jobtId;
    }

    public void setJobtId(Integer jobtId) {
        this.jobtId = jobtId;
    }

    public String getJobSerial() {
        return jobSerial;
    }

    public void setJobSerial(String jobSerial) {
        this.jobSerial = jobSerial;
    }

    public Integer getParentjobId() {
        return parentjobId;
    }

    public void setParentjobId(Integer parentjobId) {
        this.parentjobId = parentjobId;
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

    public String getJobNotes() {
        return jobNotes;
    }

    public void setJobNotes(String jobNotes) {
        this.jobNotes = jobNotes;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }

    public Object getManagerialLevel() {
        return managerialLevel;
    }

    public void setManagerialLevel(Object managerialLevel) {
        this.managerialLevel = managerialLevel;
    }

    public Object getDeptId() {
        return deptId;
    }

    public void setDeptId(Object deptId) {
        this.deptId = deptId;
    }

    public Object getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Object addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddMAc() {
        return addMAc;
    }

    public void setAddMAc(String addMAc) {
        this.addMAc = addMAc;
    }

    public String getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(String addDateTime) {
        this.addDateTime = addDateTime;
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyMac() {
        return modifyMac;
    }

    public void setModifyMac(String modifyMac) {
        this.modifyMac = modifyMac;
    }

    public String getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(String modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Object getIsOuterJob() {
        return isOuterJob;
    }

    public void setIsOuterJob(Object isOuterJob) {
        this.isOuterJob = isOuterJob;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

}
