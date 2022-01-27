package com.devartlab.ui.main.ui.eShopping.ticket.model.fetchMessages

data class Rate(
    val created_at: String,
    val id: Int,
    val message: String,
    val rate: Double,
    val ticket_id: Int,
    val updated_at: String
)