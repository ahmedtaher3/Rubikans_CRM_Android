package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncReport {
    @SerializedName("PlanDetId")
    @Expose
    private Integer planDetId;
    @SerializedName("ReportEmpId")
    @Expose
    private Integer reportEmpId;
    @SerializedName("ReportAccountId")
    @Expose
    private Integer reportAccountId;
    @SerializedName("SalesRptDate")
    @Expose
    private String salesRptDate;
    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("BranchplaceId")
    @Expose
    private Integer branchplaceId;
    @SerializedName("Visited")
    @Expose
    private Boolean visited;
    @SerializedName("IsExtraVisit")
    @Expose
    private Boolean isExtraVisit;
    @SerializedName("ActivityId")
    @Expose
    private Integer activityId;
    @SerializedName("TerrManId")
    @Expose
    private Integer terrManId;
    @SerializedName("TerrAssignId")
    @Expose
    private Integer terrAssignId;
    @SerializedName("TerrAccountId")
    @Expose
    private Integer terrAccountId;
    @SerializedName("DoubleVAccountId")
    @Expose
    private Integer doubleVAccountId;
    @SerializedName("DoubleVAccountIdStr")
    @Expose
    private String doubleVAccountIdStr;
    @SerializedName("HospitalID")
    @Expose
    private Integer hospitalID;
    @SerializedName("IsHospital")
    @Expose
    private Boolean isHospital;
    @SerializedName("VistLat")
    @Expose
    private String vistLat;
    @SerializedName("VistLang")
    @Expose
    private String vistLang;
    @SerializedName("FrstCallProdId")
    @Expose
    private Integer frstCallProdId;
    @SerializedName("ScndCallProdId")
    @Expose
    private Integer scndCallProdId;
    @SerializedName("ThirdCallProdId")
    @Expose
    private Integer thirdCallProdId;
    @SerializedName("FourthCallProdId")
    @Expose
    private Integer fourthCallProdId;
    @SerializedName("EventsId")
    @Expose
    private Object eventsId;
    @SerializedName("MetingMemberEmpId")
    @Expose
    private Object metingMemberEmpId;
    @SerializedName("ActivityUserDescription")
    @Expose
    private Object activityUserDescription;
    @SerializedName("TaskOfficeDescription")
    @Expose
    private Object taskOfficeDescription;

    public SyncReport(Integer planDetId, Integer reportEmpId, Integer reportAccountId, String salesRptDate, Integer shiftId, Integer customerId, Integer branchId, Integer branchplaceId, Boolean visited, Boolean isExtraVisit, Integer activityId, Integer terrManId, Integer terrAssignId, Integer terrAccountId, Integer doubleVAccountId, String doubleVAccountIdStr, Integer hospitalID, Boolean isHospital, String vistLat, String vistLang, Integer frstCallProdId, Integer scndCallProdId, Integer thirdCallProdId, Integer fourthCallProdId, Object eventsId, Object metingMemberEmpId, Object activityUserDescription, Object taskOfficeDescription) {
        this.planDetId = planDetId;
        this.reportEmpId = reportEmpId;
        this.reportAccountId = reportAccountId;
        this.salesRptDate = salesRptDate;
        this.shiftId = shiftId;
        this.customerId = customerId;
        this.branchId = branchId;
        this.branchplaceId = branchplaceId;
        this.visited = visited;
        this.isExtraVisit = isExtraVisit;
        this.activityId = activityId;
        this.terrManId = terrManId;
        this.terrAssignId = terrAssignId;
        this.terrAccountId = terrAccountId;
        this.doubleVAccountId = doubleVAccountId;
        this.doubleVAccountIdStr = doubleVAccountIdStr;
        this.hospitalID = hospitalID;
        this.isHospital = isHospital;
        this.vistLat = vistLat;
        this.vistLang = vistLang;
        this.frstCallProdId = frstCallProdId;
        this.scndCallProdId = scndCallProdId;
        this.thirdCallProdId = thirdCallProdId;
        this.fourthCallProdId = fourthCallProdId;
        this.eventsId = eventsId;
        this.metingMemberEmpId = metingMemberEmpId;
        this.activityUserDescription = activityUserDescription;
        this.taskOfficeDescription = taskOfficeDescription;
    }

    public Integer getPlanDetId() {
        return planDetId;
    }

    public void setPlanDetId(Integer planDetId) {
        this.planDetId = planDetId;
    }

    public Integer getReportEmpId() {
        return reportEmpId;
    }

    public void setReportEmpId(Integer reportEmpId) {
        this.reportEmpId = reportEmpId;
    }

    public Integer getReportAccountId() {
        return reportAccountId;
    }

    public void setReportAccountId(Integer reportAccountId) {
        this.reportAccountId = reportAccountId;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getBranchplaceId() {
        return branchplaceId;
    }

    public void setBranchplaceId(Integer branchplaceId) {
        this.branchplaceId = branchplaceId;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Boolean getIsExtraVisit() {
        return isExtraVisit;
    }

    public void setIsExtraVisit(Boolean isExtraVisit) {
        this.isExtraVisit = isExtraVisit;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getTerrManId() {
        return terrManId;
    }

    public void setTerrManId(Integer terrManId) {
        this.terrManId = terrManId;
    }

    public Integer getTerrAssignId() {
        return terrAssignId;
    }

    public void setTerrAssignId(Integer terrAssignId) {
        this.terrAssignId = terrAssignId;
    }

    public Integer getTerrAccountId() {
        return terrAccountId;
    }

    public void setTerrAccountId(Integer terrAccountId) {
        this.terrAccountId = terrAccountId;
    }

    public Integer getDoubleVAccountId() {
        return doubleVAccountId;
    }

    public void setDoubleVAccountId(Integer doubleVAccountId) {
        this.doubleVAccountId = doubleVAccountId;
    }

    public String getDoubleVAccountIdStr() {
        return doubleVAccountIdStr;
    }

    public void setDoubleVAccountIdStr(String doubleVAccountIdStr) {
        this.doubleVAccountIdStr = doubleVAccountIdStr;
    }

    public Integer getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(Integer hospitalID) {
        this.hospitalID = hospitalID;
    }

    public Boolean getIsHospital() {
        return isHospital;
    }

    public void setIsHospital(Boolean isHospital) {
        this.isHospital = isHospital;
    }

    public String getVistLat() {
        return vistLat;
    }

    public void setVistLat(String vistLat) {
        this.vistLat = vistLat;
    }

    public String getVistLang() {
        return vistLang;
    }

    public void setVistLang(String vistLang) {
        this.vistLang = vistLang;
    }

    public Integer getFrstCallProdId() {
        return frstCallProdId;
    }

    public void setFrstCallProdId(Integer frstCallProdId) {
        this.frstCallProdId = frstCallProdId;
    }

    public Integer getScndCallProdId() {
        return scndCallProdId;
    }

    public void setScndCallProdId(Integer scndCallProdId) {
        this.scndCallProdId = scndCallProdId;
    }

    public Integer getThirdCallProdId() {
        return thirdCallProdId;
    }

    public void setThirdCallProdId(Integer thirdCallProdId) {
        this.thirdCallProdId = thirdCallProdId;
    }

    public Integer getFourthCallProdId() {
        return fourthCallProdId;
    }

    public void setFourthCallProdId(Integer fourthCallProdId) {
        this.fourthCallProdId = fourthCallProdId;
    }

    public Object getEventsId() {
        return eventsId;
    }

    public void setEventsId(Object eventsId) {
        this.eventsId = eventsId;
    }

    public Object getMetingMemberEmpId() {
        return metingMemberEmpId;
    }

    public void setMetingMemberEmpId(Object metingMemberEmpId) {
        this.metingMemberEmpId = metingMemberEmpId;
    }

    public Object getActivityUserDescription() {
        return activityUserDescription;
    }

    public void setActivityUserDescription(Object activityUserDescription) {
        this.activityUserDescription = activityUserDescription;
    }

    public Object getTaskOfficeDescription() {
        return taskOfficeDescription;
    }

    public void setTaskOfficeDescription(Object taskOfficeDescription) {
        this.taskOfficeDescription = taskOfficeDescription;
    }

}
