package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DoubleVisitEmp(
    @SerializedName("ActivityEnName")
    @Expose
    var activityEnName: String? = null,

    @SerializedName("DoubleVisitEmpName")
    @Expose
    var doubleVisitEmpName: String? = null,

    @SerializedName("StartPoint")
    @Expose
    var startPoint: String? = null,

    @SerializedName("BrickEnName")
    @Expose
    var brickEnName: String? = null,

    @SerializedName("CusSerial")
    @Expose
    var cusSerial: Int? = null,

    @SerializedName("CustomerEnName")
    @Expose
    var customerEnName: String? = null,

    @SerializedName("CusTypeEnName")
    @Expose
    var cusTypeEnName: String? = null,

    @SerializedName("CusClassEnName")
    @Expose
    var cusClassEnName: String? = null,

    @SerializedName("BranchType")
    @Expose
    var branchType: String? = null,

    @SerializedName("PlaceName")
    @Expose
    var placeName: String? = null,

    @SerializedName("Address")
    @Expose
    var address: String? = null,

    @SerializedName("Notes")
    @Expose
    var notes: String? = null,

    @SerializedName("CustomerId")
    @Expose
    var customerId: Int? = null,

    @SerializedName("CustomerBranchid")
    @Expose
    var customerBranchid: Int? = null,

    @SerializedName("BranchPlaceId")
    @Expose
    var branchPlaceId: Int? = null,

    @SerializedName("CallObjectives")
    @Expose
    var callObjectives: String? = null,

    @SerializedName("ActivityId")
    @Expose
    var activityId: Int? = null,

    @SerializedName("ShiftId")
    @Expose
    var shiftId: Int? = null,

    @SerializedName("StartPointId")
    @Expose
    var startPointId: Int? = null,

    @SerializedName("TerriotryEmpId")
    @Expose
    var terriotryEmpId: Int? = null,

    @SerializedName("IsReported")
    @Expose
    var isReported: Boolean? = null,

    @SerializedName("TerriotryAssigntId")
    @Expose
    var terriotryAssigntId: Int? = null,

    @SerializedName("TerriotryAccountId")
    @Expose
    var terriotryAccountId: Int? = null,

    @SerializedName("DoubleVAccountIdStr")
    @Expose
    var doubleVAccountIdStr: String? = null,

    @SerializedName("BrickId")
    @Expose
    var brickId: Int? = null,

    @SerializedName("TerritoryId")
    @Expose
    var territoryId: Int? = null,

    @SerializedName("TerriotryName")
    @Expose
    var terriotryName: String? = null,

    @SerializedName("StartTime")
    @Expose
    var startTime: String? = null

)