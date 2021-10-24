package com.devartlab.data.room.specialty

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
data class SpecialtyParentEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,


    @ColumnInfo(name = "lIstId")
    @SerializedName("LIstId")
    @Expose
    var lIstId: Int? = null,

    @ColumnInfo(name = "accountId")
    @SerializedName("AccountId")
    @Expose
    var accountId: Int? = null,

    @ColumnInfo(name = "assigntId")
    @SerializedName("AssigntId")
    @Expose
    var assigntId: Int? = null,

    @ColumnInfo(name = "iSReadOnly")
    @SerializedName("ISReadOnly")
    @Expose
    var iSReadOnly : Int? = null,

    @ColumnInfo(name = "listTypeId")
    @SerializedName("ListTypeId")
    @Expose
    var listTypeId: Int? = null,

    @ColumnInfo(name = "listType")
    @SerializedName("ListType")
    @Expose
    var listType: String? = null,

    @ColumnInfo(name = "listDescription")
    @SerializedName("ListDescription")
    @Expose
    var listDescription: String? = null,

    @ColumnInfo(name = "totalCustomer")
    @SerializedName("TotalCustomer")
    @Expose
    var totalCustomer: Int? = null,

    @ColumnInfo(name = "iconImageUrl")
    @SerializedName("IconImageUrl")
    @Expose
    var iconImageUrl: String? = null

){

    constructor(
        lIstId: Int?,
        accountId: Int?,
        assigntId: Int?,
        iSReadOnly: Int?,
        listTypeId: Int?,
        listType: String?,
        listDescription: String?,
        totalCustomer: Int?,
        iconImageUrl: String?
    ) :this() {
        this.lIstId = lIstId
        this.accountId = accountId
        this.assigntId = assigntId
        this.iSReadOnly = iSReadOnly
        this.listTypeId = listTypeId
        this.listType = listType
        this.listDescription = listDescription
        this.totalCustomer = totalCustomer
        this.iconImageUrl = iconImageUrl
    }

}