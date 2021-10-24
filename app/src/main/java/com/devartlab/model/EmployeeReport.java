package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeReport {

    @SerializedName("Activity")
    @Expose
    private String activity;
    @SerializedName("DoubleVisitEmpName")
    @Expose
    private String doubleVisitEmpName;
    @SerializedName("Brick")
    @Expose
    private String brick;
    @SerializedName("Doctor")
    @Expose
    private String doctor;
    @SerializedName("Specility")
    @Expose
    private String specility;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("AMAddress")
    @Expose
    private String aMAddress;
    @SerializedName("PMAddress")
    @Expose
    private String pMAddress;
    @SerializedName("Visited")
    @Expose
    private Boolean visited;
    @SerializedName("IsExtraVisit")
    @Expose
    private Boolean isExtraVisit;
    @SerializedName("SalesOrder")
    @Expose
    private Boolean salesOrder;
    @SerializedName("ActivityId")
    @Expose
    private Integer activityId;
    @SerializedName("TerrEmpId")
    @Expose
    private Integer terrEmpId;
    @SerializedName("TerriotryAssigntId")
    @Expose
    private Integer terriotryAssigntId;
    @SerializedName("TerriotryAccountId")
    @Expose
    private Integer terriotryAccountId;
    @SerializedName("DoubleVAccountId")
    @Expose
    private Integer doubleVAccountId;
    @SerializedName("DoubleVAccountIdStr")
    @Expose
    private String doubleVAccountIdStr;
    @SerializedName("VisitLat")
    @Expose
    private String visitLat;
    @SerializedName("VisitLang")
    @Expose
    private String visitLang;
    @SerializedName("ISExpired")
    @Expose
    private Boolean isExpired;
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDoubleVisitEmpName() {
        return doubleVisitEmpName;
    }

    public void setDoubleVisitEmpName(String doubleVisitEmpName) {
        this.doubleVisitEmpName = doubleVisitEmpName;
    }

    public String getBrick() {
        return brick;
    }

    public void setBrick(String brick) {
        this.brick = brick;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSpecility() {
        return specility;
    }

    public void setSpecility(String specility) {
        this.specility = specility;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getAMAddress() {
        return aMAddress;
    }

    public void setAMAddress(String aMAddress) {
        this.aMAddress = aMAddress;
    }

    public String getPMAddress() {
        return pMAddress;
    }

    public void setPMAddress(String pMAddress) {
        this.pMAddress = pMAddress;
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

    public Boolean getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(Boolean salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getTerrEmpId() {
        return terrEmpId;
    }

    public void setTerrEmpId(Integer terrEmpId) {
        this.terrEmpId = terrEmpId;
    }

    public Integer getTerriotryAssigntId() {
        return terriotryAssigntId;
    }

    public void setTerriotryAssigntId(Integer terriotryAssigntId) {
        this.terriotryAssigntId = terriotryAssigntId;
    }

    public Integer getTerriotryAccountId() {
        return terriotryAccountId;
    }

    public void setTerriotryAccountId(Integer terriotryAccountId) {
        this.terriotryAccountId = terriotryAccountId;
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

    public String getVisitLat() {
        return visitLat;
    }

    public void setVisitLat(String visitLat) {
        this.visitLat = visitLat;
    }

    public String getVisitLang() {
        return visitLang;
    }

    public void setVisitLang(String visitLang) {
        this.visitLang = visitLang;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getaMAddress() {
        return aMAddress;
    }

    public void setaMAddress(String aMAddress) {
        this.aMAddress = aMAddress;
    }

    public String getpMAddress() {
        return pMAddress;
    }

    public void setpMAddress(String pMAddress) {
        this.pMAddress = pMAddress;
    }

    public Boolean getExtraVisit() {
        return isExtraVisit;
    }

    public void setExtraVisit(Boolean extraVisit) {
        isExtraVisit = extraVisit;
    }


    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }
}
