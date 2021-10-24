package com.devartlab.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomerList(
    @ColumnInfo(name = "listSerial")
    @Expose
    @SerializedName("ListSerial")
    var listSerial: Int? = null,

    @ColumnInfo(name = "customerTypeId")
    @Expose
    @SerializedName("CustomerTypeId")
    var customerTypeId: Int? = null,


    @ColumnInfo(name = "listDescription")
    @Expose
    @SerializedName("ListDescription")
    var listDescription: String? = null,


    @ColumnInfo(name = "empId")
    @Expose
    @SerializedName("EmpId")
    var empId: Int? = null,


    @ColumnInfo(name = "salAreaId")
    @Expose
    @SerializedName("SalAreaId")
    var salAreaId: Int? = null,


    @ColumnInfo(name = "districtId")
    @Expose
    @SerializedName("DistrictId")
    var districtId: Int? = null,


    @ColumnInfo(name = "customerId")
    @Expose
    @SerializedName("CustomerId")
    var customerId: Int? = null,


    @ColumnInfo(name = "branchId")
    @Expose
    @SerializedName("BranchId")
    var branchId: Int? = null,


    @ColumnInfo(name = "customerState")
    @Expose
    @SerializedName("CustomerState")
    var customerState: Int? = null,


    @ColumnInfo(name = "notes")
    @Expose
    @SerializedName("Notes")
    var notes: String? = null,


    @ColumnInfo(name = "placeName")
    @Expose
    @SerializedName("PlaceName")
    var placeName: String? = null,


    @ColumnInfo(name = "brickEnName")
    @Expose
    @SerializedName("BrickEnName")
    var brickEnName: String? = null,


    @ColumnInfo(name = "bricId")
    @Expose
    @SerializedName("BricId")
    var bricId: Int? = null,


    @ColumnInfo(name = "branchtypeId")
    @Expose
    @SerializedName("BranchtypeId")
    var branchtypeId: Int? = null,


    @ColumnInfo(name = "branchType")
    @Expose
    @SerializedName("BranchType")
    var branchType: String? = null,


    @ColumnInfo(name = "cusSerial")
    @Expose
    @SerializedName("CusSerial")
    var cusSerial: Int? = null,


    @ColumnInfo(name = "customerEnName")
    @Expose
    @SerializedName("CustomerEnName")
    var customerEnName: String? = null,


    @ColumnInfo(name = "oldSpeciality")
    @Expose
    @SerializedName("oldSpeciality")
    var oldSpeciality: String? = null,


    @ColumnInfo(name = "oldClass")
    @Expose
    @SerializedName("oldClass")
    var oldClass: String? = null,


    @ColumnInfo(name = "oldClassId")
    @Expose
    @SerializedName("OldClassId")
    var oldClassId: Int? = null,


    @ColumnInfo(name = "oldSpecialityId")
    @Expose
    @SerializedName("OldSpecialityId")
    var oldSpecialityId: Int? = null,


    @ColumnInfo(name = "address")
    @Expose
    @SerializedName("Address")
    var address: String? = null,


    @ColumnInfo(name = "listId")
    @Expose
    @SerializedName("ListId")
    var listId: Int? = null,


    @ColumnInfo(name = "listDetId")
    @Expose
    @SerializedName("ListDetId")
    var listDetId: Int? = null,


    @ColumnInfo(name = "cusTypeEnName")
    @Expose
    @SerializedName("CusTypeEnName")
    var cusTypeEnName: String? = null,


    @ColumnInfo(name = "cusClassEnName")
    @Expose
    @SerializedName("CusClassEnName")
    var cusClassEnName: String? = null,


    @ColumnInfo(name = "cusTypeId")
    @Expose
    @SerializedName("CusTypeId")
    var cusTypeId: Int? = null,


    @ColumnInfo(name = "cusClassId")
    @Expose
    @SerializedName("CusClassId")
    var cusClassId: Int? = null,


    @ColumnInfo(name = "empArName")
    @Expose
    @SerializedName("EmpArName")
    var empArName: String? = null,


    @ColumnInfo(name = "empEnName")
    @Expose
    @SerializedName("EmpEnName")
    var empEnName: String? = null,


    @ColumnInfo(name = "deptId")
    @Expose
    @SerializedName("DeptId")
    var deptId: Int? = null,


    @ColumnInfo(name = "secId")
    @Expose
    @SerializedName("SecId")
    var secId: Int? = null,


    @ColumnInfo(name = "jobId")
    @Expose
    @SerializedName("JobId")
    var jobId: Int? = null,


    @ColumnInfo(name = "assigntId")
    @Expose
    @SerializedName("AssigntId")
    var assigntId: Int? = null,


    @ColumnInfo(name = "accountId")
    @Expose
    @SerializedName("AccountId")
    var accountId: Int? = null,


    @ColumnInfo(name = "addressNotes")
    @Expose
    @SerializedName("Address Notes")
    var addressNotes: String? = null,


    @ColumnInfo(name = "branchPlaceId")
    @Expose
    @SerializedName("BranchPlaceId")
    var branchPlaceId: Int? = null,


    @ColumnInfo(name = "branchTel1")
    @Expose
    @SerializedName("BranchTel1")
    var branchTel1: String? = null,


    @ColumnInfo(name = "branchTel2")
    @Expose
    @SerializedName("BranchTel2")
    var branchTel2: String? = null,


    @ColumnInfo(name = "terriotryId")
    @Expose
    @SerializedName("TerriotryId")
    var terriotryId: Int? = null,


    @ColumnInfo(name = "branchDesc")
    @Expose
    @SerializedName("BranchDesc")
    var branchDesc: String
) 