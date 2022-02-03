package com.devartlab.ui.main.ui.eShopping.requestVoucher.model.compaignVouchers

data class CompaignVouchersResponseItem(
    val active: Int,
    val admin_title: String,
    val category_id: String,
    val created_at: String,
    val form_type: String,
    val id: Int,
    val order_count: String,
    val prize_image: String,
    val sku: String,
    val type: String,
    val updated_at: String,
    val voucher_translates_title: List<VoucherTranslatesTitle>,
    val wallet_amount: String
)