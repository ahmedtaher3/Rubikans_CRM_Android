package com.devartlab.a4eshopping.ticket.model.getContacts

data class Rate(
    val created_at: String,
    val id: Int,
    val message: String,
    val rate: Int,
    val ticket_id: Int,
    val updated_at: String
)