package com.devartlab.data.room.contract

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class ContractEntity(


    @PrimaryKey(autoGenerate = true) var id: Int? = null,

    @ColumnInfo(name = "StoreId") @field:SerializedName("StoreId") var storeId: Int? = null,

    @ColumnInfo(name = "ContractId") @field:SerializedName("ContractId") var contractId: Int? = null,

    @ColumnInfo(name = "CashDisc") @field:SerializedName("CashDisc") var cashDisc: Int? = null,

    @ColumnInfo(name = "BatchNumber") @field:SerializedName("BatchNumber") var batchNumber: String? = null,

    @ColumnInfo(name = "Qty") @field:SerializedName("Qty") var qty: Int? = null,

    @ColumnInfo(name = "ItemArName") @field:SerializedName("ItemArName") var itemArName: String? = null,

    @ColumnInfo(name = "PatchExpireDate") @field:SerializedName("PatchExpireDate") var patchExpireDate: String? = null,

    @ColumnInfo(name = "ItemPrincipalUnitId") @field:SerializedName("ItemPrincipalUnitId") var itemPrincipalUnitId: Int? = null,

    @ColumnInfo(name = "Itemid") @field:SerializedName("Itemid") var itemId: Int? = null,

    @ColumnInfo(name = "UnitArName") @field:SerializedName("UnitArName") var unitArName: String? = null,

    @ColumnInfo(name = "Pending") @field:SerializedName("Pending") var pending: Int? = null,

    @ColumnInfo(name = "price") @field:SerializedName("price") var price: Double? = null,

    @ColumnInfo(name = "Reserved") @field:SerializedName("Reserved") var reserved: Int? = null,

    var count: Int? = 1,
    var maxCount: Int? = 0

) : Parcelable {

    constructor(storeId: Int?,
                contractId: Int?,
                cashDisc: Int?,
                batchNumber: String?,
                qty: Int?,
                itemArName: String?,
                patchExpireDate: String?,
                itemPrincipalUnitId: Int?,
                itemId: Int?,
                unitArName: String?,
                pending: Int?,
                price: Double?,
                reserved: Int?) : this() {
        this.storeId = storeId
        this.contractId = contractId
        this.cashDisc = cashDisc
        this.batchNumber = batchNumber
        this.qty = qty
        this.itemArName = itemArName
        this.patchExpireDate = patchExpireDate
        this.itemPrincipalUnitId = itemPrincipalUnitId
        this.itemId = itemId
        this.unitArName = unitArName
        this.pending = pending
        this.price = price
        this.reserved = reserved
    }
}
