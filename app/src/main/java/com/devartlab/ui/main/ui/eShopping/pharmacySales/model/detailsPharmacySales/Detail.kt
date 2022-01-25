package com.devartlab.a4eshopping.pharmacySales.model.detailsPharmacySales

data class Detail(
    val balance: Double,
    val created_at: String,
    val id: Int,
    val mandwob_hrms: Double,
    val mandwob_id: Double,
    val mandwob_name: String,
    val method: String,
    val name: String,
    val notes: Any,
    val order_id: Double,
    val order_number: String,
    val order_status: String,
    val package_id: Any,
    val payment_status: String,
    val pharmaciesBoughtProduct: Double,
    val pharmaciesStatus: String,
    val product: Product,
    val product_id: Double,
    val product_name: String,
    val product_price: Double,
    val product_qty: Double,
    val productsCount: Double,
    val productsValue: Double,
    val ref_number: String,
    val stock: Double,
    val stock_after: Double,
    val stock_photo: Any,
    val stock_store: Double,
    val store: Store,
    val total_price: Double,
    val trans_inv: Any,
    val type: String,
    val updated_at: String,
    val sku: String,
    val user_id: Int
)