package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeVacation {

    @SerializedName("VacId")
    @Expose
    private Integer vacId;
    @SerializedName("VacTypeId")
    @Expose
    private Integer vacTypeId;
    @SerializedName("VacTypeArName")
    @Expose
    private String vacTypeArName;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("DayNum")
    @Expose
    private Double dayNum;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("VacStatus")
    @Expose
    private Integer vacStatus;
    @SerializedName("AllowToUpdate")
    @Expose
    private Boolean allowToUpdate;
    @SerializedName("PendingUser")
    @Expose
    private String pendingUser;

    public EmployeeVacation(Integer vacId, Integer vacTypeId, String vacTypeArName, String fromDate, String toDate, Double dayNum, String notes, Integer vacStatus, Boolean allowToUpdate, String pendingUser) {
        this.vacId = vacId;
        this.vacTypeId = vacTypeId;
        this.vacTypeArName = vacTypeArName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.dayNum = dayNum;
        this.notes = notes;
        this.vacStatus = vacStatus;
        this.allowToUpdate = allowToUpdate;
        this.pendingUser = pendingUser;
    }

    public EmployeeVacation() {
    }

    public String getPendingUser() {
        return pendingUser;
    }

    public void setPendingUser(String pendingUser) {
        this.pendingUser = pendingUser;
    }
    public Integer getVacId() {
        return vacId;
    }

    public void setVacId(Integer vacId) {
        this.vacId = vacId;
    }

    public Integer getVacTypeId() {
        return vacTypeId;
    }

    public void setVacTypeId(Integer vacTypeId) {
        this.vacTypeId = vacTypeId;
    }

    public String getVacTypeArName() {
        return vacTypeArName;
    }

    public void setVacTypeArName(String vacTypeArName) {
        this.vacTypeArName = vacTypeArName;
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

    public Double getDayNum() {
        return dayNum;
    }

    public void setDayNum(Double dayNum) {
        this.dayNum = dayNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getVacStatus() {
        return vacStatus;
    }

    public void setVacStatus(Integer vacStatus) {
        this.vacStatus = vacStatus;
    }

    public Boolean getAllowToUpdate() {
        return allowToUpdate;
    }

    public void setAllowToUpdate(Boolean allowToUpdate) {
        this.allowToUpdate = allowToUpdate;
    }

}