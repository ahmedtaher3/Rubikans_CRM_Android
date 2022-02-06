package com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest

data class VoucherRequestRequest(
    val campaign_id: Int,
    val count: String,
    val doctor_id: Int,
    val doctor_name: String
)