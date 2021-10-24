package com.devartlab.data.room.slidetimer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SlideTimerEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "planDetId")
    var planDetId: Int? = null,

    @ColumnInfo(name = "shiftId")
    var shiftId: Int? = null,

    @ColumnInfo(name = "salesRptDate")
    var salesRptDate: String? = null,

    @ColumnInfo(name = "accountId")
    var accountId: Int? = null,

    @ColumnInfo(name = "customerId")
    var customerId: Int? = null,

    @ColumnInfo(name = "branchId")
    var branchId: Int? = null,

    @ColumnInfo(name = "isExtraVisit")
    var isExtraVisit: Boolean? = null,

    @ColumnInfo(name = "callIndex")
    var callIndex: Int? = null,

    @ColumnInfo(name = "itemId")
    var itemId: Int? = null,

    @ColumnInfo(name = "messageId")
    var messageId: Int? = null,

    @ColumnInfo(name = "productId")
    var customMessage: Boolean? = null,

    @ColumnInfo(name = "customMessageNotes")
    var customMessageNotes: String? = null,

    @ColumnInfo(name = "slideTimeinSec")
    var slideTimeinSec: Int? = null,

    @ColumnInfo(name = "slideId")
    var slideId: Int? = null


) {
    constructor(
        planDetId: Int?,
        shiftId: Int?,
        salesRptDate: String?,
        accountId: Int?,
        customerId: Int?,
        branchId: Int?,
        isExtraVisit: Boolean?,
        callIndex: Int?,
        itemId: Int?,
        messageId: Int?,
        customMessage: Boolean?,
        customMessageNotes: String?,
        slideTimeinSec: Int?,
        slideId: Int?
    ) : this() {
        this.planDetId = planDetId
        this.shiftId = shiftId
        this.salesRptDate = salesRptDate
        this.accountId = accountId
        this.customerId = customerId
        this.branchId = branchId
        this.isExtraVisit = isExtraVisit
        this.callIndex = callIndex
        this.itemId = itemId
        this.messageId = messageId
        this.customMessage = customMessage
        this.customMessageNotes = customMessageNotes
        this.slideTimeinSec = slideTimeinSec
        this.slideId = slideId
    }
}