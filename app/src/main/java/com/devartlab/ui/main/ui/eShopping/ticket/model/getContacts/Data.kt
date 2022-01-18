package com.devartlab.a4eshopping.ticket.model.getContacts

data class Data(
    val admin_rate: Any,
    val clos_by: String,
    val clos_from: Any,
    val closed_at: String?,
    val closed_by: Any,
    val created_at: String,
    val id: Int,
    val message: String,
    val open_by: String,
    val open_from: Any,
    val opened_at: Any,
    val opened_by: Any,
    val order_number: Any,
    val pro_by: String,
    val pro_from: Any,
    val processing_at: Any,
    val processing_by: Any,
    val rate: Rate,
    val status: Int,
    val subject: String,
    val ticket_id: String,
    val ticket_type: String,
    val type: String,
    val updated_at: String,
    val user: User,
    val user_id: Int
)