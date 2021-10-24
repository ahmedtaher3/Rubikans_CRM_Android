package com.devartlab.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlanAndCoverCustomers (
    @SerializedName("AreaManger")
    @Expose
    var areaManger: String? = null,

    @SerializedName("SalAreaEnName")
    @Expose
    var salAreaEnName: String? = null,

    @SerializedName("LineEnName")
    @Expose
    var lineEnName: String? = null,

    @SerializedName("DistrictManger")
    @Expose
    var districtManger: String? = null,

    @SerializedName("DistrictEnName")
    @Expose
    var districtEnName: String? = null,

    @SerializedName("MR")
    @Expose
    var mr: String? = null,

    @SerializedName("SalTerriotryEnName")
    @Expose
    var salTerriotryEnName: String? = null,

    @SerializedName("BrickEnName")
    @Expose
    var brickEnName: String? = null,

    @SerializedName("CusSerial")
    @Expose
    var cusSerial: String? = null,

    @SerializedName("CustomerEnName")
    @Expose
    var customerEnName: String? = null,

    @SerializedName("CusTypeEnName")
    @Expose
    var cusTypeEnName: String? = null,

    @SerializedName("CusClassEnName")
    @Expose
    var cusClassEnName: String? = null,

    @SerializedName("PlanCounter")
    @Expose
    var planCounter: Int? = null,

    @SerializedName("CoverCounter")
    @Expose
    var coverCounter: Int? = null,

    @SerializedName("CusTypeId")
    @Expose
    var cusTypeId: Int? = null,

    @SerializedName("CusClassId")
    @Expose
    var cusClassId: Int? = null,

    @SerializedName("BrickId")
    @Expose
    var brickId: Int? = null,

    @SerializedName("DistrictId")
    @Expose
    var districtId: Int? = null,

    @SerializedName("CustomerId")
    @Expose
    var customerId: Int? = null,

    @SerializedName("BranchId")
    @Expose
    var branchId: Int? = null,

    @SerializedName("EmpId")
    @Expose
    var empId: Int? = null,

    @SerializedName("TOtalPaied")
    @Expose
    var tOtalPaied: Double? = null,

    @SerializedName("TOtalPending")
    @Expose
    var tOtalPending: Double? = null,

    @SerializedName("RequestCount")
    @Expose
    var requestCount: Int? = null

) : Parcelable