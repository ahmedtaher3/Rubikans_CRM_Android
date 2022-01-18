package com.devartlab.a4eshopping.ticket.model.sendMessages

data class SendMessagesResponse(
    val message: List<String>,
    val id: Int,
    val status: String
)