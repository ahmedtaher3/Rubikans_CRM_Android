package com.devartlab.data.room.contract

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ContractList(

    @field:SerializedName("ItemGroupId") var itemGroupId: Int? = null,

    @field:SerializedName("PublicPrice") var publicPrice: Int? = null,

    @field:SerializedName("ItemEnName") var itemEnName: String? = null,

    @field:SerializedName("Price") var price: Double? = null,

    @field:SerializedName("ItemArName") var itemArName: String? = null,

    @field:SerializedName("ItemId") var itemId: Int? = null,

    @field:SerializedName("ItemPrincipalUnitId") var itemPrincipalUnitId: Int? = null,

    @field:SerializedName("UnitArName") var unitArName: String? = null,

    @field:SerializedName("ItemSerial") var itemSerial: String? = null,

    var image: String? = null,

    var count: Int? = 1,

    var maxCount: Int? = null,

    @field:SerializedName("ContractDescription") var contractDescription: String? = null,

    @field:SerializedName("CashDisc") var cashDisc: Double? = null,

    @field:SerializedName("ContractId") var contractId: Int? = null) : Parcelable