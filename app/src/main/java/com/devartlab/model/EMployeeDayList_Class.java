package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EMployeeDayList_Class {

    @SerializedName("_Rowindex")
    @Expose
    private int _Rowindex;

    @SerializedName("Day")
    @Expose
    private String Day;

    @SerializedName("DayDate")
    @Expose
    private String DayDate;

    @SerializedName("DayNatural")
    @Expose
    private String DayNatural;

    @SerializedName("EmployeeStatus")
    @Expose
    private String EmployeeStatus;

    @SerializedName("Checkin")
    @Expose
    private String Checkin;

    @SerializedName("CheckOut")
    @Expose
    private String CheckOut;

    @SerializedName("LateTime")
    @Expose
    private String LateTime;

    @SerializedName("LeaveEarlyTime")
    @Expose
    private String LeaveEarlyTime;

    @SerializedName("PenaltyQty")
    @Expose
    private float PenaltyQty;

    @SerializedName("PenaltyArName")
    @Expose
    private String PenaltyArName;

    @SerializedName("IsStopSalaryPenalties")
    @Expose
    private Boolean IsStopSalaryPenalties;

    @SerializedName("IsPermitionToLeave")
    @Expose
    private Boolean IsPermitionToLeave;

    @SerializedName("IsErrand")
    @Expose
    private Boolean IsErrand;

    @SerializedName("IsTradinessPenalties")
    @Expose
    private Boolean IsTradinessPenalties;

    @SerializedName("IsNoFingerFpPenalties")
    @Expose
    private Boolean IsNoFingerFpPenalties;

    @SerializedName("ISAbsentWithoutPermission")
    @Expose
    private Boolean ISAbsentWithoutPermission;

    @SerializedName("IsOverTime")
    @Expose
    private Boolean IsOverTime;

    @SerializedName("EMpID")
    @Expose
    private int EMpID;

    @SerializedName("IsErrand1")
    @Expose
    private Boolean IsErrand1;

    @SerializedName("EmpRegularityId")
    @Expose
    private int EmpRegularityId;

    public int get_Rowindex() {
        return _Rowindex;
    }

    public void set_Rowindex(int _Rowindex) {
        this._Rowindex = _Rowindex;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getDayDate() {
        return DayDate;
    }

    public void setDayDate(String dayDate) {
        DayDate = dayDate;
    }

    public String getDayNatural() {
        return DayNatural;
    }

    public void setDayNatural(String dayNatural) {
        DayNatural = dayNatural;
    }

    public String getEmployeeStatus() {
        return EmployeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        EmployeeStatus = employeeStatus;
    }

    public String getCheckin() {
        return Checkin;
    }

    public void setCheckin(String checkin) {
        Checkin = checkin;
    }

    public String getCheckOut() {
        return CheckOut;
    }

    public void setCheckOut(String checkOut) {
        CheckOut = checkOut;
    }

    public String getLateTime() {
        return LateTime;
    }

    public void setLateTime(String lateTime) {
        LateTime = lateTime;
    }

    public String getLeaveEarlyTime() {
        return LeaveEarlyTime;
    }

    public void setLeaveEarlyTime(String leaveEarlyTime) {
        LeaveEarlyTime = leaveEarlyTime;
    }

    public float getPenaltyQty() {
        return PenaltyQty;
    }

    public void setPenaltyQty(float penaltyQty) {
        PenaltyQty = penaltyQty;
    }

    public String getPenaltyArName() {
        return PenaltyArName;
    }

    public void setPenaltyArName(String penaltyArName) {
        PenaltyArName = penaltyArName;
    }

    public Boolean getIsStopSalaryPenalties() {
        return IsStopSalaryPenalties;
    }

    public void setIsStopSalaryPenalties(Boolean isStopSalaryPenalties) {
        IsStopSalaryPenalties = isStopSalaryPenalties;
    }

    public Boolean getPermitionToLeave() {
        return IsPermitionToLeave;
    }

    public void setPermitionToLeave(Boolean permitionToLeave) {
        IsPermitionToLeave = permitionToLeave;
    }

    public Boolean getErrand() {
        return IsErrand;
    }

    public void setErrand(Boolean errand) {
        IsErrand = errand;
    }

    public Boolean getTradinessPenalties() {
        return IsTradinessPenalties;
    }

    public void setTradinessPenalties(Boolean tradinessPenalties) {
        IsTradinessPenalties = tradinessPenalties;
    }

    public Boolean getNoFingerFpPenalties() {
        return IsNoFingerFpPenalties;
    }

    public void setNoFingerFpPenalties(Boolean noFingerFpPenalties) {
        IsNoFingerFpPenalties = noFingerFpPenalties;
    }

    public Boolean getISAbsentWithoutPermission() {
        return ISAbsentWithoutPermission;
    }

    public void setISAbsentWithoutPermission(Boolean ISAbsentWithoutPermission) {
        this.ISAbsentWithoutPermission = ISAbsentWithoutPermission;
    }

    public Boolean getOverTime() {
        return IsOverTime;
    }

    public void setOverTime(Boolean overTime) {
        IsOverTime = overTime;
    }

    public int getEMpID() {
        return EMpID;
    }

    public void setEMpID(int EMpID) {
        this.EMpID = EMpID;
    }

    public Boolean getErrand1() {
        return IsErrand1;
    }

    public void setErrand1(Boolean errand1) {
        IsErrand1 = errand1;
    }

    public int getEmpRegularityId() {
        return EmpRegularityId;
    }

    public void setEmpRegularityId(int empRegularityId) {
        EmpRegularityId = empRegularityId;
    }
}
