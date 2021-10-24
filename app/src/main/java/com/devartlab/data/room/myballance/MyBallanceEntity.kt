package com.devartlab.data.room.myballance

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class MyBallanceEntity(


    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "itemGroupId")
    @field:SerializedName("ItemGroupId")
    var itemGroupId: Int? = null,

    @ColumnInfo(name = "publicPrice")
    @field:SerializedName("PublicPrice")
    var publicPrice: Int? = null,

    @ColumnInfo(name = "itemEnName")
    @field:SerializedName("ItemEnName")
    var itemEnName: String? = null,

    @ColumnInfo(name = "price")
    @field:SerializedName("Price")
    var price: Double? = null,

    @ColumnInfo(name = "itemArName")
    @field:SerializedName("ItemArName")
    var itemArName: String? = null,

    @ColumnInfo(name = "itemId")
    @field:SerializedName("ItemId")
    var itemId: Int? = null,

    @ColumnInfo(name = "itemPrincipalUnitId")
    @field:SerializedName("ItemPrincipalUnitId")
    var itemPrincipalUnitId: Int? = null,

    @ColumnInfo(name = "unitArName")
    @field:SerializedName("UnitArName")
    var unitArName: String? = null,

    @ColumnInfo(name = "itemSerial")
    @field:SerializedName("ItemSerial")
    var itemSerial: String? = null,

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "count")
    var count: Int? = 1,

    @ColumnInfo(name = "maxCount")
    var maxCount: Int? = null,

    @ColumnInfo(name = "contractDescription")
    @field:SerializedName("ContractDescription")
    var contractDescription: String? = null,

    @ColumnInfo(name = "cashDisc")
    @field:SerializedName("CashDisc")
    var cashDisc: Double? = null,

    @ColumnInfo(name = "contractId")
    @field:SerializedName("ContractId")
    var contractId: Int? = null
) : Parcelable {
    constructor(
        itemGroupId: Int?,
        publicPrice: Int?,
        itemEnName: String?,
        price: Double?,
        itemArName: String?,
        itemId: Int?,
        itemPrincipalUnitId: Int?,
        unitArName: String?,
        itemSerial: String?,
        image: String?,
        count: Int?,
        maxCount: Int?,
        contractDescription: String?,
        cashDisc: Double?,
        contractId: Int?
    ) : this() {
        this.itemGroupId = itemGroupId
        this.publicPrice = publicPrice
        this.itemEnName = itemEnName
        this.price = price
        this.itemArName = itemArName
        this.itemId = itemId
        this.itemPrincipalUnitId = itemPrincipalUnitId
        this.unitArName = unitArName
        this.itemSerial = itemSerial
        this.image = image
        this.count = count
        this.maxCount = maxCount
        this.contractDescription = contractDescription
        this.cashDisc = cashDisc
        this.contractId = contractId
    }
}
