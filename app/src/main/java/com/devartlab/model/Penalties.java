package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Penalties {

    @SerializedName("PenaltyId")
    @Expose
    private Integer penaltyId;
    @SerializedName("PenaltyDate")
    @Expose
    private String penaltyDate;
    @SerializedName("PenaltyTypeId")
    @Expose
    private Integer penaltyTypeId;
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("PenaltyReasonId")
    @Expose
    private Integer penaltyReasonId;
    @SerializedName("Year")
    @Expose
    private Integer year;
    @SerializedName("Month")
    @Expose
    private Integer month;
    @SerializedName("PenaltyQty")
    @Expose
    private Double penaltyQty;
    @SerializedName("PenaltyArName")
    @Expose
    private String penaltyArName;
    @SerializedName("PenReasonArName")
    @Expose
    private String penReasonArName;
    @SerializedName("PenReasonEnName")
    @Expose
    private String penReasonEnName;
    @SerializedName("PenaltyEnName")
    @Expose
    private String penaltyEnName;
    @SerializedName("PenStatus")
    @Expose
    private Integer penStatus;
    @SerializedName("AllowToUpdate")
    @Expose
    private Boolean allowToUpdate;
    @SerializedName("PendingUser")
    @Expose
    private String pendingUser;

    public Penalties(Integer penaltyId, String penaltyDate, Integer penaltyTypeId, Integer empId, String notes, Integer penaltyReasonId, Integer year, Integer month, Double penaltyQty, String penaltyArName, String penReasonArName, String penReasonEnName, String penaltyEnName, Integer penStatus, Boolean allowToUpdate, String pendingUser) {
        this.penaltyId = penaltyId;
        this.penaltyDate = penaltyDate;
        this.penaltyTypeId = penaltyTypeId;
        this.empId = empId;
        this.notes = notes;
        this.penaltyReasonId = penaltyReasonId;
        this.year = year;
        this.month = month;
        this.penaltyQty = penaltyQty;
        this.penaltyArName = penaltyArName;
        this.penReasonArName = penReasonArName;
        this.penReasonEnName = penReasonEnName;
        this.penaltyEnName = penaltyEnName;
        this.penStatus = penStatus;
        this.allowToUpdate = allowToUpdate;
        this.pendingUser = pendingUser;
    }

    public Penalties() {
    }

    public String getPendingUser() {
        return pendingUser;
    }

    public void setPendingUser(String pendingUser) {
        this.pendingUser = pendingUser;
    }

    public Integer getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(Integer penaltyId) {
        this.penaltyId = penaltyId;
    }

    public String getPenaltyDate() {
        return penaltyDate;
    }

    public void setPenaltyDate(String penaltyDate) {
        this.penaltyDate = penaltyDate;
    }

    public Integer getPenaltyTypeId() {
        return penaltyTypeId;
    }

    public void setPenaltyTypeId(Integer penaltyTypeId) {
        this.penaltyTypeId = penaltyTypeId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPenaltyReasonId() {
        return penaltyReasonId;
    }

    public void setPenaltyReasonId(Integer penaltyReasonId) {
        this.penaltyReasonId = penaltyReasonId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getPenaltyQty() {
        return penaltyQty;
    }

    public void setPenaltyQty(Double penaltyQty) {
        this.penaltyQty = penaltyQty;
    }

    public String getPenaltyArName() {
        return penaltyArName;
    }

    public void setPenaltyArName(String penaltyArName) {
        this.penaltyArName = penaltyArName;
    }

    public String getPenReasonArName() {
        return penReasonArName;
    }

    public void setPenReasonArName(String penReasonArName) {
        this.penReasonArName = penReasonArName;
    }

    public String getPenReasonEnName() {
        return penReasonEnName;
    }

    public void setPenReasonEnName(String penReasonEnName) {
        this.penReasonEnName = penReasonEnName;
    }

    public String getPenaltyEnName() {
        return penaltyEnName;
    }

    public void setPenaltyEnName(String penaltyEnName) {
        this.penaltyEnName = penaltyEnName;
    }

    public Integer getPenStatus() {
        return penStatus;
    }

    public void setPenStatus(Integer penStatus) {
        this.penStatus = penStatus;
    }

    public Boolean getAllowToUpdate() {
        return allowToUpdate;
    }

    public void setAllowToUpdate(Boolean allowToUpdate) {
        this.allowToUpdate = allowToUpdate;
    }


    @Override
    public String toString() {
        return "Penalties{" +
                "penaltyId=" + penaltyId +
                ", penaltyDate='" + penaltyDate + '\'' +
                ", penaltyTypeId=" + penaltyTypeId +
                ", empId=" + empId +
                ", notes='" + notes + '\'' +
                ", penaltyReasonId=" + penaltyReasonId +
                ", year=" + year +
                ", month=" + month +
                ", penaltyQty=" + penaltyQty +
                ", penaltyArName='" + penaltyArName + '\'' +
                ", penReasonArName='" + penReasonArName + '\'' +
                ", penReasonEnName='" + penReasonEnName + '\'' +
                ", penaltyEnName='" + penaltyEnName + '\'' +
                ", penStatus=" + penStatus +
                ", allowToUpdate=" + allowToUpdate +
                ", pendingUser='" + pendingUser + '\'' +
                '}';
    }
}