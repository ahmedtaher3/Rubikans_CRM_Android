package com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest

data class VoucherRequest(
    val count: Int,
    val created_at: String,
    val doctor_id: String,
    val doctor_name: String,
    val id: Int,
    val mr_id: Int,
    val pharmacy_id: Int,
    val status: Int,
    val updated_at: String,
    val voucher_code_id: String
)