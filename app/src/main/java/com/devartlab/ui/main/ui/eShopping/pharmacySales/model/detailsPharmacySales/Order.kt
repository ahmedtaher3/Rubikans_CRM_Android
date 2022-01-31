package com.devartlab.ui.main.ui.eShopping.pharmacySales.model.detailsPharmacySales

data class Order(
    val created_at: String,
    val customer_name: String,
    val customer_email: String,
    val customer_phone: String,
    val customer_address: String,
    val customer_city: String,
    val shipping: Shipping,
    val status: String,
    val pay_amount: Double,
    val currency_sign: String,
    val method: String,
    val id: Int
)