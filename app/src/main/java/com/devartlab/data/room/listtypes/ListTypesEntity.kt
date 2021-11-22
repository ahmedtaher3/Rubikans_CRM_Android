package com.devartlab.data.room.listtypes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class ListTypesEntity(
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @JvmField
    @ColumnInfo(name = "lIstId")
    @SerializedName("LIstId")
    var lIstId: Int? = null,

    @JvmField
    @ColumnInfo(name = "AccountId")
    @SerializedName("AccountId")
    var accountId: Int? = null,

    @JvmField
    @ColumnInfo(name = "AssigntId")
    @SerializedName("AssigntId")
    var assigntId: Int? = null,

    @JvmField
    @ColumnInfo(name = "ISReadOnly")
    @SerializedName("ISReadOnly")
    var iSReadOnly: Int? = null,

    @JvmField
    @ColumnInfo(name = "ListTypeId")
    @SerializedName("ListTypeId")
    var listTypeId: Int? = null,

    @JvmField
    @ColumnInfo(name = "ListType")
    @SerializedName("ListType")
    var listType: String? = null,

    @JvmField
    @ColumnInfo(name = "ListDescription")
    @SerializedName("ListDescription")
    var listDescription: String? = null,

    @JvmField
    @ColumnInfo(name = "TotalCustomer")
    @SerializedName("TotalCustomer")
    var totalCustomer: Int? = null,

    @JvmField
    @ColumnInfo(name = "IconImageUrl")
    @SerializedName("IconImageUrl")
    var iconImageUrl: String? = null


    ) : Serializable {
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
    ) : this() {
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