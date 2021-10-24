
package com.devartlab.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {
    @SerializedName("DocId")
    @Expose
    @Nullable
    private Integer docId;
    @SerializedName("AssignId")
    @Expose
    @Nullable
    private Integer assignId;
    @SerializedName("DocModule")
    @Expose
    @Nullable
    private Integer docModule;
    @SerializedName("DocWorkFlowDetId")
    @Expose
    @Nullable
    private Integer docWorkFlowDetId;
    @SerializedName("MarkReqTypeId")
    @Expose
    @Nullable
    private Integer markReqTypeId;
    @SerializedName("Approved")
    @Expose
    @Nullable
    private String approved;
    @SerializedName("DisApprove")
    @Expose
    @Nullable
    private String disApprove;
    @SerializedName("Cancel")
    @Expose
    @Nullable
    private String cancel;
    @SerializedName("ExecuteDate")
    @Expose
    @Nullable
    private String executeDate;
    @SerializedName("RequestCode")
    @Expose
    @Nullable
    private String requestCode;
    @SerializedName("FromAccount")
    @Expose
    @Nullable
    private String fromAccount;
    @SerializedName("AccDescription")
    @Expose
    @Nullable
    private String accDescription;
    @SerializedName("requestType")
    @Expose
    @Nullable
    private String requestType;
    @SerializedName("ReqDescription")
    @Expose
    @Nullable
    private String reqDescription;
    @SerializedName("TotalCoast")
    @Expose
    @Nullable
    private Double totalCoast;
    @SerializedName("MOnthlyGain")
    @Expose
    @Nullable
    private Double mOnthlyGain;
    @SerializedName("TotalGain")
    @Expose
    @Nullable
    private Double totalGain;
    @SerializedName("LastApproval")
    @Expose
    @Nullable
    private String lastApproval;
    @SerializedName("IsRead")
    @Expose
    @Nullable
    private Boolean isRead;
    @SerializedName("ParentRequestId")
    @Expose
    @Nullable
    private Integer parentRequestId;
    @SerializedName("ReqApplicantEmpId")
    @Expose
    @Nullable
    private Integer reqApplicantEmpId;
    @SerializedName("EmpArName")
    @Expose
    @Nullable
    private String empArName;
    @SerializedName("EmpEnName")
    @Expose
    @Nullable
    private String empEnName;

    private Boolean selected = false;


    @Nullable
    public Integer getDocId() {
        return docId;
    }

    public void setDocId(@Nullable Integer docId) {
        this.docId = docId;
    }

    @Nullable
    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(@Nullable Integer assignId) {
        this.assignId = assignId;
    }

    @Nullable
    public Integer getDocModule() {
        return docModule;
    }

    public void setDocModule(@Nullable Integer docModule) {
        this.docModule = docModule;
    }

    @Nullable
    public Integer getDocWorkFlowDetId() {
        return docWorkFlowDetId;
    }

    public void setDocWorkFlowDetId(@Nullable Integer docWorkFlowDetId) {
        this.docWorkFlowDetId = docWorkFlowDetId;
    }

    @Nullable
    public Integer getMarkReqTypeId() {
        return markReqTypeId;
    }

    public void setMarkReqTypeId(@Nullable Integer markReqTypeId) {
        this.markReqTypeId = markReqTypeId;
    }

    @Nullable
    public String getApproved() {
        return approved;
    }

    public void setApproved(@Nullable String approved) {
        this.approved = approved;
    }

    @Nullable
    public String getDisApprove() {
        return disApprove;
    }

    public void setDisApprove(@Nullable String disApprove) {
        this.disApprove = disApprove;
    }

    @Nullable
    public String getCancel() {
        return cancel;
    }

    public void setCancel(@Nullable String cancel) {
        this.cancel = cancel;
    }

    @Nullable
    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(@Nullable String executeDate) {
        this.executeDate = executeDate;
    }

    @Nullable
    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(@Nullable String requestCode) {
        this.requestCode = requestCode;
    }

    @Nullable
    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(@Nullable String fromAccount) {
        this.fromAccount = fromAccount;
    }

    @Nullable
    public String getAccDescription() {
        return accDescription;
    }

    public void setAccDescription(@Nullable String accDescription) {
        this.accDescription = accDescription;
    }

    @Nullable
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(@Nullable String requestType) {
        this.requestType = requestType;
    }

    @Nullable
    public String getReqDescription() {
        return reqDescription;
    }

    public void setReqDescription(@Nullable String reqDescription) {
        this.reqDescription = reqDescription;
    }

    @Nullable
    public Double getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(@Nullable Double totalCoast) {
        this.totalCoast = totalCoast;
    }

    @Nullable
    public Double getmOnthlyGain() {
        return mOnthlyGain;
    }

    public void setmOnthlyGain(@Nullable Double mOnthlyGain) {
        this.mOnthlyGain = mOnthlyGain;
    }

    @Nullable
    public Double getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(@Nullable Double totalGain) {
        this.totalGain = totalGain;
    }

    @Nullable
    public String getLastApproval() {
        return lastApproval;
    }

    public void setLastApproval(@Nullable String lastApproval) {
        this.lastApproval = lastApproval;
    }

    @Nullable
    public Boolean getRead() {
        return isRead;
    }

    public void setRead(@Nullable Boolean read) {
        isRead = read;
    }

    @Nullable
    public Integer getParentRequestId() {
        return parentRequestId;
    }

    public void setParentRequestId(@Nullable Integer parentRequestId) {
        this.parentRequestId = parentRequestId;
    }

    @Nullable
    public Integer getReqApplicantEmpId() {
        return reqApplicantEmpId;
    }

    public void setReqApplicantEmpId(@Nullable Integer reqApplicantEmpId) {
        this.reqApplicantEmpId = reqApplicantEmpId;
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

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }


}