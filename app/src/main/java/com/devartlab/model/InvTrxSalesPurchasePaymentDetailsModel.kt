package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InvTrxSalesPurchasePaymentDetailsModel(

        val PaymentOpId: Int? = null,
        val InvoiceId: Int? = null,
        val InvoiceTypeId: Int? = null,
        val VoucherId: Int? = null,
        val VoucherTypeId: Int? = null,
        val PaymentOpDate: String? = null,
        val PaymentValue:  Double? = null,
        val VOucherTypeAndSerial: String? = null,
        val VoucherDescription: String? = null,
        val VoucherSerial: Int? = null,
        val VoucherTypeDescription: String? = null,
        val VoucherValue: Double? = null,
        val Customerid: Int? = null,
        val SuppID: Int? = null,
        val PaymentOpNotes: String? = null


)