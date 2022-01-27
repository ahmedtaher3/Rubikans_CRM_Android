package com.devartlab.ui.main.ui.eShopping.ticket.model.addTicket

data class AddTicketRequest(
    val message: String,
    val order_number: String?,
    val subject: String,
    val ticket_type: String
)