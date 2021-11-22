package com.devartlab.data.room.list

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ListEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

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
    @SerializedName("AccountId") var accountId: Int? = null,


    @ColumnInfo(name = "addressNotes") @Expose @SerializedName("Address Notes") var addressNotes: String? = null,


    @ColumnInfo(name = "branchPlaceId") @Expose @SerializedName("BranchPlaceId") var branchPlaceId: Int? = null,


    @ColumnInfo(name = "branchTel1") @Expose @SerializedName("BranchTel1") var branchTel1: String? = null,


    @ColumnInfo(name = "branchTel2") @Expose @SerializedName("BranchTel2") var branchTel2: String? = null,


    @ColumnInfo(name = "terriotryId") @Expose @SerializedName("TerriotryId") var terriotryId: Int? = null,


    @ColumnInfo(name = "branchDesc") @Expose @SerializedName("BranchDesc") var branchDesc: String? = null,

    @ColumnInfo(name = "isSynced") var isSynced: Boolean? = null,

    @ColumnInfo(name = "specialityDefaultContractId") var specialityDefaultContractId: Int? = null,


    @ColumnInfo(name = "ReferenceID") var referenceID: Int? = null,

    @ColumnInfo(name = "AddressList") var addressList: String? = null,
    @ColumnInfo(name = "GlAccountsList") var glAccountsList: String? = null,
    @ColumnInfo(name = "KeyPersonList") var keyPersonList: String? = null
) : Parcelable {
    constructor(
        listSerial: Int?,
        customerTypeId: Int?,
        listDescription: String?,
        empId: Int?,
        salAreaId: Int?,
        districtId: Int?,
        customerId: Int?,
        branchId: Int?,
        customerState: Int?,
        notes: String?,
        placeName: String?,
        brickEnName: String?,
        bricId: Int?,
        branchtypeId: Int?,
        branchType: String?,
        cusSerial: Int?,
        customerEnName: String?,
        oldSpeciality: String?,
        oldClass: String?,
        oldClassId: Int?,
        oldSpecialityId: Int?,
        address: String?,
        listId: Int?,
        listDetId: Int?,
        cusTypeEnName: String?,
        cusClassEnName: String?,
        cusTypeId: Int?,
        cusClassId: Int?,
        empArName: String?,
        empEnName: String?,
        deptId: Int?,
        secId: Int?,
        jobId: Int?,
        assigntId: Int?,
        accountId: Int?,
        addressNotes: String?,
        branchPlaceId: Int?,
        branchTel1: String?,
        branchTel2: String?,
        terriotryId: Int?,
        branchDesc: String,
        isSynced: Boolean,
        specialityDefaultContractId: Int,
        referenceID: Int,
        addressList: String,
        glAccountsList: String,
        keyPersonList: String

    ) :this(){
        this.listSerial = listSerial
        this.customerTypeId = customerTypeId
        this.listDescription = listDescription
        this.empId = empId
        this.salAreaId = salAreaId
        this.districtId = districtId
        this.customerId = customerId
        this.branchId = branchId
        this.customerState = customerState
        this.notes = notes
        this.placeName = placeName
        this.brickEnName = brickEnName
        this.bricId = bricId
        this.branchtypeId = branchtypeId
        this.branchType = branchType
        this.cusSerial = cusSerial
        this.customerEnName = customerEnName
        this.oldSpeciality = oldSpeciality
        this.oldClass = oldClass
        this.oldClassId = oldClassId
        this.oldSpecialityId = oldSpecialityId
        this.address = address
        this.listId = listId
        this.listDetId = listDetId
        this.cusTypeEnName = cusTypeEnName
        this.cusClassEnName = cusClassEnName
        this.cusTypeId = cusTypeId
        this.cusClassId = cusClassId
        this.empArName = empArName
        this.empEnName = empEnName
        this.deptId = deptId
        this.secId = secId
        this.jobId = jobId
        this.assigntId = assigntId
        this.accountId = accountId
        this.addressNotes = addressNotes
        this.branchPlaceId = branchPlaceId
        this.branchTel1 = branchTel1
        this.branchTel2 = branchTel2
        this.terriotryId = terriotryId
        this.branchDesc = branchDesc
        this.isSynced = isSynced
        this.specialityDefaultContractId = specialityDefaultContractId
        this.referenceID = referenceID
        this.addressList = addressList
        this.glAccountsList = glAccountsList
        this.keyPersonList = keyPersonList
    }

}


data class DefCustomerAddressModel(var listSerial: Int? = null,
                                   var CusAddDetId: Int? = null,
                                   var CustomerId: Int? = null,
                                   var BranchtypeId: Int? = null,
                                   var BranchPlaceId: Int? = null,
                                   var BranchRef: Int? = null,
                                   var CityId: Int? = null,
                                   var AreaId: Int? = null,
                                   var BricId: Int? = null,
                                   var Description: String? = null,
                                   var Address: String? = null,
                                   var BranchTel1: String? = null,
                                   var BranchTel2: String? = null,
                                   var WorkingDays: String? = null,
                                   var WorkingTimeFrom: String? = null,
                                   var WorkingTimeTo: String? = null,
                                   var Notes: String? = null,
                                   var BranchCode: String? = null,
                                   var Notes2: String? = null,
                                   var BranchStatus: Int? = null,
                                   var BranchClassId: Int? = null)


data class DefCustomerGlAccountsModel(var lAccountDetId: Int? = null,
                                      var CustomerId: Int? = null,
                                      var AccountId: Int? = null,
                                      var ParentAccountId: Int? = null,
                                      var AccountTypeId: Int? = null,
                                      var Notes: String? = null)


data class DefSupplierKeyPersonModel(

    var RespDetId: Int? = null,
    var SuppId: Int? = null,
    var KeyPersonName: String? = null,
    var Department: String? = null,
    var JobName: String? = null,
    var Tel1: String? = null,
    var Tel2: String? = null,
    var Branch: String? = null,
    var Notes: String? = null
)