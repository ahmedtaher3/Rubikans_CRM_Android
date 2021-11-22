package com.devartlab.data.room.collect

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CollectEntity(

    @PrimaryKey(autoGenerate = true) var id: Int? = null,


    @ColumnInfo(name = "PaymentOpId") var PaymentOpId: Int? = null,
    @ColumnInfo(name = "InvoiceId") var InvoiceId: Int? = null,
    @ColumnInfo(name = "InvoiceTypeId") var InvoiceTypeId: Int? = null,
    @ColumnInfo(name = "VoucherId") var VoucherId: Int? = null,
    @ColumnInfo(name = "VoucherTypeId") var VoucherTypeId: Int? = null,
    @ColumnInfo(name = "PaymentOpDate") var PaymentOpDate: String? = null,
    @ColumnInfo(name = "PaymentValue") var PaymentValue: Double? = null,
    @ColumnInfo(name = "VOucherTypeAndSerial") var VOucherTypeAndSerial: String? = null,
    @ColumnInfo(name = "VoucherDescription") var VoucherDescription: String? = null,
    @ColumnInfo(name = "VoucherSerial") var VoucherSerial: Int? = null,
    @ColumnInfo(name = "VoucherTypeDescription") var VoucherTypeDescription: String? = null,
    @ColumnInfo(name = "VoucherValue") var VoucherValue: Double? = null,
    @ColumnInfo(name = "Customerid") var Customerid: Int? = null,
    @ColumnInfo(name = "SuppID") var SuppID: Int? = null,
    @ColumnInfo(name = "PaymentOpNotes") var PaymentOpNotes: String? = null


){
    constructor(PaymentOpId: Int?,
                InvoiceId: Int?,
                InvoiceTypeId: Int?,
                VoucherId: Int?,
                VoucherTypeId: Int?,
                PaymentOpDate: String?,
                PaymentValue: Double?,
                VOucherTypeAndSerial: String?,
                VoucherDescription: String?,
                VoucherSerial: Int?,
                VoucherTypeDescription: String?,
                VoucherValue: Double?,
                Customerid: Int?,
                SuppID: Int?,
                PaymentOpNotes: String?):this() {
        this.PaymentOpId = PaymentOpId
        this.InvoiceId = InvoiceId
        this.InvoiceTypeId = InvoiceTypeId
        this.VoucherId = VoucherId
        this.VoucherTypeId = VoucherTypeId
        this.PaymentOpDate = PaymentOpDate
        this.PaymentValue = PaymentValue
        this.VOucherTypeAndSerial = VOucherTypeAndSerial
        this.VoucherDescription = VoucherDescription
        this.VoucherSerial = VoucherSerial
        this.VoucherTypeDescription = VoucherTypeDescription
        this.VoucherValue = VoucherValue
        this.Customerid = Customerid
        this.SuppID = SuppID
        this.PaymentOpNotes = PaymentOpNotes
    }
}