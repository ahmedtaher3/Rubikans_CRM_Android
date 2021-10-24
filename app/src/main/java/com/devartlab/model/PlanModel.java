package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanModel {

    @SerializedName("PLanAccountId")
    @Expose
    private Integer pLanAccountId;
    @SerializedName("PlanCycleId")
    @Expose
    private Integer planCycleId;
    @SerializedName("CycleArName")
    @Expose
    private String cycleArName;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("Shift")
    @Expose
    private String shift;
    @SerializedName("Day")
    @Expose
    private String day;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("ActivityEnName")
    @Expose
    private String activityEnName;
    @SerializedName("DoubleVisitEmpName")
    @Expose
    private String doubleVisitEmpName;
    @SerializedName("StartPoint")
    @Expose
    private String startPoint;
    @SerializedName("Brick")
    @Expose
    private String brick;
    @SerializedName("CusSerial")
    @Expose
    private Integer cusSerial;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("Speciality")
    @Expose
    private String speciality;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("BranchType")
    @Expose
    private String branchType;
    @SerializedName("PlaceName")
    @Expose
    private String placeName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("EventsEnName")
    @Expose
    private String eventsEnName;
    @SerializedName("EventDescription")
    @Expose
    private String eventDescription;
    @SerializedName("TaskText")
    @Expose
    private String taskText;
    @SerializedName("OfficeDescription")
    @Expose
    private String officeDescription;
    @SerializedName("MeetingMembers")
    @Expose
    private String meetingMembers;
    @SerializedName("Customerid")
    @Expose
    private Integer customerid;
    @SerializedName("CustomerBranchid")
    @Expose
    private Integer customerBranchid;
    @SerializedName("BranchPlaceId")
    @Expose
    private Integer branchPlaceId;
    @SerializedName("CallObjectives")
    @Expose
    private String callObjectives;
    @SerializedName("ActivityId")
    @Expose
    private Integer activityId;
    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("StartPointId")
    @Expose
    private Integer startPointId;
    @SerializedName("TerriotryEmpId")
    @Expose
    private Integer terriotryEmpId;
    @SerializedName("EventsId")
    @Expose
    private Integer eventsId;
    @SerializedName("MeetingMemberId")
    @Expose
    private Integer meetingMemberId;
    @SerializedName("IsReported")
    @Expose
    private Boolean isReported;
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
    @SerializedName("BrickId")
    @Expose
    private Integer brickId;
    @SerializedName("TerritoryId")
    @Expose
    private Integer territoryId;
    @SerializedName("IsVisit")
    @Expose
    private Boolean isVisit;
    @SerializedName("IsExtraVisit")
    @Expose
    private Boolean isExtraVisit;
    @SerializedName("CusLat")
    @Expose
    private String cusLat;
    @SerializedName("CusLang")
    @Expose
    private String cusLang;
    @SerializedName("PlanId")
    @Expose
    private Integer planId;
    @SerializedName("PlanDetID")
    @Expose
    private Integer planDetID;
    @SerializedName("ActivityTypeID")
    @Expose
    private Integer activityTypeID;
    @SerializedName("FromDateMs")
    @Expose
    private long fromDateMs;
    @SerializedName("ToDateMs")
    @Expose
    private long toDateMs;
    @SerializedName("DateMs")
    @Expose
    private long dateMs;

    public Integer getPLanAccountId() {
        return pLanAccountId;
    }

    public void setPLanAccountId(Integer pLanAccountId) {
        this.pLanAccountId = pLanAccountId;
    }

    public Integer getPlanCycleId() {
        return planCycleId;
    }

    public void setPlanCycleId(Integer planCycleId) {
        this.planCycleId = planCycleId;
    }

    public String getCycleArName() {
        return cycleArName;
    }

    public void setCycleArName(String cycleArName) {
        this.cycleArName = cycleArName;
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

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivityEnName() {
        return activityEnName;
    }

    public void setActivityEnName(String activityEnName) {
        this.activityEnName = activityEnName;
    }

    public String getDoubleVisitEmpName() {
        return doubleVisitEmpName;
    }

    public void setDoubleVisitEmpName(String doubleVisitEmpName) {
        this.doubleVisitEmpName = doubleVisitEmpName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getBrick() {
        return brick;
    }

    public void setBrick(String brick) {
        this.brick = brick;
    }

    public Integer getCusSerial() {
        return cusSerial;
    }

    public void setCusSerial(Integer cusSerial) {
        this.cusSerial = cusSerial;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEventsEnName() {
        return eventsEnName;
    }

    public void setEventsEnName(String eventsEnName) {
        this.eventsEnName = eventsEnName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getOfficeDescription() {
        return officeDescription;
    }

    public void setOfficeDescription(String officeDescription) {
        this.officeDescription = officeDescription;
    }

    public String getMeetingMembers() {
        return meetingMembers;
    }

    public void setMeetingMembers(String meetingMembers) {
        this.meetingMembers = meetingMembers;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getCustomerBranchid() {
        return customerBranchid;
    }

    public void setCustomerBranchid(Integer customerBranchid) {
        this.customerBranchid = customerBranchid;
    }

    public Integer getBranchPlaceId() {
        return branchPlaceId;
    }

    public void setBranchPlaceId(Integer branchPlaceId) {
        this.branchPlaceId = branchPlaceId;
    }

    public String getCallObjectives() {
        return callObjectives;
    }

    public void setCallObjectives(String callObjectives) {
        this.callObjectives = callObjectives;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getStartPointId() {
        return startPointId;
    }

    public void setStartPointId(Integer startPointId) {
        this.startPointId = startPointId;
    }

    public Integer getTerriotryEmpId() {
        return terriotryEmpId;
    }

    public void setTerriotryEmpId(Integer terriotryEmpId) {
        this.terriotryEmpId = terriotryEmpId;
    }

    public Integer getEventsId() {
        return eventsId;
    }

    public void setEventsId(Integer eventsId) {
        this.eventsId = eventsId;
    }

    public Integer getMeetingMemberId() {
        return meetingMemberId;
    }

    public void setMeetingMemberId(Integer meetingMemberId) {
        this.meetingMemberId = meetingMemberId;
    }

    public Boolean getIsReported() {
        return isReported;
    }

    public void setIsReported(Boolean isReported) {
        this.isReported = isReported;
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

    public Integer getBrickId() {
        return brickId;
    }

    public void setBrickId(Integer brickId) {
        this.brickId = brickId;
    }

    public Integer getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(Integer territoryId) {
        this.territoryId = territoryId;
    }

    public Boolean getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(Boolean isVisit) {
        this.isVisit = isVisit;
    }

    public Boolean getIsExtraVisit() {
        return isExtraVisit;
    }

    public void setIsExtraVisit(Boolean isExtraVisit) {
        this.isExtraVisit = isExtraVisit;
    }

    public String getCusLat() {
        return cusLat;
    }

    public void setCusLat(String cusLat) {
        this.cusLat = cusLat;
    }

    public String getCusLang() {
        return cusLang;
    }

    public void setCusLang(String cusLang) {
        this.cusLang = cusLang;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlanDetID() {
        return planDetID;
    }

    public void setPlanDetID(Integer planDetID) {
        this.planDetID = planDetID;
    }

    public Integer getActivityTypeID() {
        return activityTypeID;
    }

    public void setActivityTypeID(Integer activityTypeID) {
        this.activityTypeID = activityTypeID;
    }

    public long getFromDateMs() {
        return fromDateMs;
    }

    public void setFromDateMs(long fromDateMs) {
        this.fromDateMs = fromDateMs;
    }

    public long getToDateMs() {
        return toDateMs;
    }

    public void setToDateMs(long toDateMs) {
        this.toDateMs = toDateMs;
    }

    public long getDateMs() {
        return dateMs;
    }

    public void setDateMs(long dateMs) {
        this.dateMs = dateMs;
    }

    public PlanModel(Integer pLanAccountId, Integer planCycleId, String cycleArName, String fromDate, String toDate, String shift, String day, String date, String activityEnName, String doubleVisitEmpName, String startPoint, String brick, Integer cusSerial, String customerName, String speciality, String _class, String branchType, String placeName, String address, String notes, String eventsEnName, String eventDescription, String taskText, String officeDescription, String meetingMembers, Integer customerid, Integer customerBranchid, Integer branchPlaceId, String callObjectives, Integer activityId, Integer shiftId, Integer startPointId, Integer terriotryEmpId, Integer eventsId, Integer meetingMemberId, Boolean isReported, Integer terriotryAssigntId, Integer terriotryAccountId, Integer doubleVAccountId, String doubleVAccountIdStr, Integer brickId, Integer territoryId, Boolean isVisit, Boolean isExtraVisit, String cusLat, String cusLang, Integer planId, Integer planDetID, Integer activityTypeID, long fromDateMs, long toDateMs, long dateMs) {
        this.pLanAccountId = pLanAccountId;
        this.planCycleId = planCycleId;
        this.cycleArName = cycleArName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.shift = shift;
        this.day = day;
        this.date = date;
        this.activityEnName = activityEnName;
        this.doubleVisitEmpName = doubleVisitEmpName;
        this.startPoint = startPoint;
        this.brick = brick;
        this.cusSerial = cusSerial;
        this.customerName = customerName;
        this.speciality = speciality;
        this._class = _class;
        this.branchType = branchType;
        this.placeName = placeName;
        this.address = address;
        this.notes = notes;
        this.eventsEnName = eventsEnName;
        this.eventDescription = eventDescription;
        this.taskText = taskText;
        this.officeDescription = officeDescription;
        this.meetingMembers = meetingMembers;
        this.customerid = customerid;
        this.customerBranchid = customerBranchid;
        this.branchPlaceId = branchPlaceId;
        this.callObjectives = callObjectives;
        this.activityId = activityId;
        this.shiftId = shiftId;
        this.startPointId = startPointId;
        this.terriotryEmpId = terriotryEmpId;
        this.eventsId = eventsId;
        this.meetingMemberId = meetingMemberId;
        this.isReported = isReported;
        this.terriotryAssigntId = terriotryAssigntId;
        this.terriotryAccountId = terriotryAccountId;
        this.doubleVAccountId = doubleVAccountId;
        this.doubleVAccountIdStr = doubleVAccountIdStr;
        this.brickId = brickId;
        this.territoryId = territoryId;
        this.isVisit = isVisit;
        this.isExtraVisit = isExtraVisit;
        this.cusLat = cusLat;
        this.cusLang = cusLang;
        this.planId = planId;
        this.planDetID = planDetID;
        this.activityTypeID = activityTypeID;
        this.fromDateMs = fromDateMs;
        this.toDateMs = toDateMs;
        this.dateMs = dateMs;
    }

    @Override
    public String toString() {
        return "PlanModel{" +
                "pLanAccountId=" + pLanAccountId +
                ", planCycleId=" + planCycleId +
                ", cycleArName='" + cycleArName + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", shift='" + shift + '\'' +
                ", day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", activityEnName='" + activityEnName + '\'' +
                ", doubleVisitEmpName='" + doubleVisitEmpName + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", brick='" + brick + '\'' +
                ", cusSerial=" + cusSerial +
                ", customerName='" + customerName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", _class='" + _class + '\'' +
                ", branchType='" + branchType + '\'' +
                ", placeName='" + placeName + '\'' +
                ", address='" + address + '\'' +
                ", notes='" + notes + '\'' +
                ", eventsEnName='" + eventsEnName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", taskText='" + taskText + '\'' +
                ", officeDescription='" + officeDescription + '\'' +
                ", meetingMembers='" + meetingMembers + '\'' +
                ", customerid=" + customerid +
                ", customerBranchid=" + customerBranchid +
                ", branchPlaceId=" + branchPlaceId +
                ", callObjectives='" + callObjectives + '\'' +
                ", activityId=" + activityId +
                ", shiftId=" + shiftId +
                ", startPointId=" + startPointId +
                ", terriotryEmpId=" + terriotryEmpId +
                ", eventsId=" + eventsId +
                ", meetingMemberId=" + meetingMemberId +
                ", isReported=" + isReported +
                ", terriotryAssigntId=" + terriotryAssigntId +
                ", terriotryAccountId=" + terriotryAccountId +
                ", doubleVAccountId=" + doubleVAccountId +
                ", doubleVAccountIdStr='" + doubleVAccountIdStr + '\'' +
                ", brickId=" + brickId +
                ", territoryId=" + territoryId +
                ", isVisit=" + isVisit +
                ", isExtraVisit=" + isExtraVisit +
                ", cusLat='" + cusLat + '\'' +
                ", cusLang='" + cusLang + '\'' +
                ", planId=" + planId +
                ", planDetID=" + planDetID +
                ", activityTypeID=" + activityTypeID +
                ", fromDateMs=" + fromDateMs +
                ", toDateMs=" + toDateMs +
                ", dateMs=" + dateMs +
                '}';
    }
}


