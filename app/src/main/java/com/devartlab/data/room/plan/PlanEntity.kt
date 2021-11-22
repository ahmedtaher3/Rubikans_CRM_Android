package com.devartlab.data.room.plan

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PlanEntity(


    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "PlanAccountId")
    var planAccountId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "PlanCycleId")
    var planCycleId: Int? = null,

    @ColumnInfo(defaultValue = "", name = "CycleArName")
    var cycleArName: String? = null,

    @ColumnInfo(defaultValue = "", name = "FromDate")
    var fromDate: String? = null,

    @ColumnInfo(defaultValue = "", name = "ToDate")
    var toDate: String? = null,

    @NonNull
    @ColumnInfo(defaultValue = "", name = "Shift")
    var shift: String? = null,

    @ColumnInfo(defaultValue = "", name = "Day")
    var day: String? = null,

    @NonNull
    @ColumnInfo(defaultValue = "", name = "Date")
    var date: String? = null,

    @ColumnInfo(defaultValue = "", name = "ActivityEnName")
    var activityEnName: String? = null,

    @ColumnInfo(defaultValue = "", name = "DoubleVisitEmpName")
    var doubleVisitEmpName: String? = null,

    @ColumnInfo(defaultValue = "", name = "StartPoint")
    var startPoint: String? = null,

    @ColumnInfo(defaultValue = "", name = "Brick")
    var brick: String? = null,

    @ColumnInfo(defaultValue = "0", name = "CusSerial")
    var cusSerial: Int? = null,

    @NonNull
    @ColumnInfo(defaultValue = "", name = "CustomerName")
    var customerName: String? = null,

    @ColumnInfo(defaultValue = "", name = "Speciality")
    var speciality: String? = null,

    @ColumnInfo(defaultValue = "", name = "Class")
    var _class: String? = null,

    @ColumnInfo(defaultValue = "", name = "BranchType")
    var branchType: String? = null,

    @ColumnInfo(defaultValue = "", name = "PlaceName")
    var placeName: String? = null,

    @ColumnInfo(defaultValue = "", name = "Address")
    var address: String? = null,

    @ColumnInfo(defaultValue = "", name = "Notes")
    var notes: String? = null,

    @ColumnInfo(defaultValue = "", name = "EventsEnName")
    var eventsEnName: String? = null,

    @ColumnInfo(defaultValue = "", name = "EventDescription")
    var eventDescription: String? = null,

    @ColumnInfo(defaultValue = "", name = "TaskText")
    var taskText: String? = null,

    @ColumnInfo(defaultValue = "", name = "OfficeDescription")
    var officeDescription: String? = null,

    @ColumnInfo(defaultValue = "", name = "MeetingMembers")
    var meetingMembers: String? = null,

    @ColumnInfo(defaultValue = "0", name = "Customerid")
    var customerid: Int? = null,

    @ColumnInfo(defaultValue = "0", name = " CustomerBranchid")
    var customerBranchid: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "BranchPlaceId")
    var branchPlaceId: Int? = null,

    @ColumnInfo(defaultValue = "", name = "CallObjectives")
    var callObjectives: String? = null,

    @ColumnInfo(defaultValue = "0", name = "ActivityId")
    var activityId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "ShiftId")
    var shiftId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "StartPointId")
    var startPointId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "TerriotryEmpId")
    var terriotryEmpId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "EventsId")
    var eventsId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "MeetingMemberId")
    var meetingMemberId: Int? = null,

    @ColumnInfo(defaultValue = "false", name = "IsReported")
    var reported: Boolean? = null,

    @ColumnInfo(defaultValue = "0", name = "TerriotryAssigntId")
    var terriotryAssigntId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "TerriotryAccountId")
    var terriotryAccountId: Int? = null,

    @ColumnInfo(defaultValue = "", name = "DoubleVAccountId")
    var doubleVAccountId: Int? = 0,

    @ColumnInfo(defaultValue = "", name = "DoubleVAccountIdStr")
    var doubleVAccountIdStr: String? = null,

    @ColumnInfo(defaultValue = "0", name = "BrickId")
    var brickId: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "TerritoryId")
    var territoryId: Int? = null,

    @ColumnInfo(defaultValue = "false", name = "IsVisit")
    var visit: Boolean? = null,

    @ColumnInfo(defaultValue = "false", name = "IsExtraVisit")
    var extraVisit: Boolean? = null,

    @ColumnInfo(defaultValue = "", name = "CusLat")
    var cusLat: String? = null,

    @ColumnInfo(defaultValue = "", name = "CusLang")
    var cusLang: String? = null,

    @ColumnInfo(defaultValue = "false", name = "call1")
    var call1: Int? = null,

    @ColumnInfo(defaultValue = "false", name = "call2")
    var call2: Int? = null,

    @ColumnInfo(defaultValue = "false", name = "call3")
    var call3: Int? = null,

    @ColumnInfo(defaultValue = "false", name = "call4")
    var call4: Int? = null,

    @ColumnInfo(defaultValue = "false", name = "isStarted")
    var isStarted: Boolean? = null,

    @ColumnInfo(defaultValue = "", name = "startAt")
    var startAt: String? = null,

    @NonNull
    @ColumnInfo(defaultValue = "", name = "planId")
    var planId: Int? = null,

    @ColumnInfo(defaultValue = "", name = "PlanDetID")
    var planDetID: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "PlanColor") var planColor: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "ActivityTypeID") var activityTypeID: Int? = null,

    @ColumnInfo(defaultValue = "0", name = "deleted") var deleted: Boolean? = null,

    @ColumnInfo(defaultValue = "0", name = "updated") var updated: Boolean? = null,


    @ColumnInfo(defaultValue = "0", name = "isAd") var isAd: Boolean? = null,
    @ColumnInfo(defaultValue = "0", name = "adModel") var adModel: String? = null

) : Parcelable {
    constructor(
        planAccountId: Int?,
        planCycleId: Int?,
        cycleArName: String?,
        fromDate: String?,
        toDate: String?,
        shift: String?,
        day: String?,
        date: String?,
        activityEnName: String?,
        doubleVisitEmpName: String?,
        startPoint: String?,
        brick: String?,
        cusSerial: Int?,
        customerName: String?,
        speciality: String?,
        _class: String?,
        branchType: String?,
        placeName: String?,
        address: String?,
        notes: String?,
        eventsEnName: String?,
        eventDescription: String?,
        taskText: String?,
        officeDescription: String?,
        meetingMembers: String?,
        customerid: Int?,
        customerBranchid: Int?,
        branchPlaceId: Int?,
        callObjectives: String?,
        activityId: Int?,
        shiftId: Int?,
        startPointId: Int?,
        terriotryEmpId: Int?,
        eventsId: Int?,
        meetingMemberId: Int?,
        reported: Boolean?,
        terriotryAssigntId: Int?,
        terriotryAccountId: Int?,
        doubleVAccountId: Int?,
        doubleVAccountIdStr: String?,
        brickId: Int?,
        territoryId: Int?,
        visit: Boolean?,
        extraVisit: Boolean?,
        cusLat: String?,
        cusLang: String?,
        call1: Int?,
        call2: Int?,
        call3: Int?,
        call4: Int?,
        isStarted: Boolean?,
        startAt: String?,
        planId: Int?,
        planDetID: Int?,
        planColor: Int?,
        activityTypeID: Int?,
        deleted: Boolean?,
        updated: Boolean?,
        isAd: Boolean?,
        adModel: String?
    ) : this() {
        this.planAccountId = planAccountId
        this.planCycleId = planCycleId
        this.cycleArName = cycleArName
        this.fromDate = fromDate
        this.toDate = toDate
        this.shift = shift
        this.day = day
        this.date = date
        this.activityEnName = activityEnName
        this.doubleVisitEmpName = doubleVisitEmpName
        this.startPoint = startPoint
        this.brick = brick
        this.cusSerial = cusSerial
        this.customerName = customerName
        this.speciality = speciality
        this._class = _class
        this.branchType = branchType
        this.placeName = placeName
        this.address = address
        this.notes = notes
        this.eventsEnName = eventsEnName
        this.eventDescription = eventDescription
        this.taskText = taskText
        this.officeDescription = officeDescription
        this.meetingMembers = meetingMembers
        this.customerid = customerid
        this.customerBranchid = customerBranchid
        this.branchPlaceId = branchPlaceId
        this.callObjectives = callObjectives
        this.activityId = activityId
        this.shiftId = shiftId
        this.startPointId = startPointId
        this.terriotryEmpId = terriotryEmpId
        this.eventsId = eventsId
        this.meetingMemberId = meetingMemberId
        this.reported = reported
        this.terriotryAssigntId = terriotryAssigntId
        this.terriotryAccountId = terriotryAccountId
        this.doubleVAccountId = doubleVAccountId
        this.doubleVAccountIdStr = doubleVAccountIdStr
        this.brickId = brickId
        this.territoryId = territoryId
        this.visit = visit
        this.extraVisit = extraVisit
        this.cusLat = cusLat
        this.cusLang = cusLang
        this.call1 = call1
        this.call2 = call2
        this.call3 = call3
        this.call4 = call4
        this.isStarted = isStarted
        this.startAt = startAt
        this.planId = planId
        this.planDetID = planDetID
        this.planColor = planColor
        this.activityTypeID = activityTypeID
        this.deleted = deleted
        this.updated = updated
        this.isAd = isAd
        this.adModel = adModel
    }
}

