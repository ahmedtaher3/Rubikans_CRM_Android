package com.devartlab.a4eshopping.pharmacySales.model

data class Data(
    val created_at: String,
    val id: Int,
    val method: String,
    val name: String,
    val order_number: String,
    val pay_amount: Double,
    val payment_status: String,
    val status: String,
    val updated_at: String,
    val user_id: Int
)