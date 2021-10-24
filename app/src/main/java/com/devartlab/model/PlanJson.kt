package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlanJson(
    @field:Expose @field:SerializedName("PlanId") var planId: Int,
    @field:Expose @field:SerializedName("Date") var date: String,
    @field:Expose @field:SerializedName("CustomerId") var customerId: Int,
    @field:Expose @field:SerializedName("CustomerBranchid") var customerBranchid: Int,
    @field:Expose @field:SerializedName("BranchPlaceId") var branchPlaceId: Any,
    @field:Expose @field:SerializedName("ShiftId") var shiftId: Int,
    @field:Expose @field:SerializedName("ActivityId") var activityId: Int,
    @field:Expose @field:SerializedName("StartTime") var startTime: String,
    @field:Expose @field:SerializedName("IsStartPoint") var isStartPoint: Boolean,
    @field:Expose @field:SerializedName("TerriotryEmpId") var terriotryEmpId: Int,
    @field:Expose @field:SerializedName("TerritoryId") var territoryId: Int,
    @field:Expose @field:SerializedName("BrickId") var brickId: Int,
    @field:Expose @field:SerializedName("CallObjectives") var callObjectives: String,
    @field:Expose @field:SerializedName("EventsId") var eventsId: Any,
    @field:Expose @field:SerializedName("EventDescription") var eventDescription: String,
    @field:Expose @field:SerializedName("EmpId") var empId: Any,
    @field:Expose @field:SerializedName("Notes") var notes: String,
    @field:Expose @field:SerializedName("StartPointId") var startPointId: Int,
    @field:Expose @field:SerializedName("IsReported") var isReported: Boolean,
    @field:Expose @field:SerializedName("TerriotryAssigntId") var terriotryAssigntId: Int,
    @field:Expose @field:SerializedName("TerriotryAccountId") var terriotryAccountId: Int,
    @field:Expose @field:SerializedName("DoubleVAccountId") var doubleVAccountId: Any,
    @field:Expose @field:SerializedName("DoubleVAccountIdStr") var doubleVAccountIdStr: String,
    @field:Expose @field:SerializedName("TaskText") var taskText: String,
    @field:Expose @field:SerializedName("OfficeDescription") var officeDescription: String,
    @field:Expose @field:SerializedName("PendingVac") var pendingVac: Any,
    @field:Expose @field:SerializedName("ApprovedVac") var approvedVac: Any,
    @field:Expose @field:SerializedName("HospitalID") var hospitalID: Any,
    @field:Expose @field:SerializedName("IsHospital") var isHospital: Any,
    @field:Expose @field:SerializedName("IsDeleted") var isDeleted: Boolean,
    @field:Expose @field:SerializedName("PlanDetId") var planDetId: Int
) 