package com.devartlab.ui.main.ui.eShopping.ticket.model.getContacts

data class GetContactsResponse(
    val current_page: Int,
    val `data`: List<Data>,
    val first_page_url: String,
    val from: Int,
    val next_page_url: Any,
    val path: String,
    val code: Int,
    val per_page: Int,
    val prev_page_url: Any,
    val to: Int
)