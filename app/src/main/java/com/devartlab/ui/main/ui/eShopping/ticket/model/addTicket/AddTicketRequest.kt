package com.devartlab.a4eshopping.ticket.model.addTicket

data class AddTicketRequest(
    val message: String,
    val order_number: String?,
    val subject: String,
    val ticket_type: String
)