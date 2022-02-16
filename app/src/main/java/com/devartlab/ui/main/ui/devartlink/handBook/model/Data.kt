package com.devartlab.ui.main.ui.devartlink.handBook.model

data class Data(
    val _id: String,
    val arrange: String,
    val created_at: String,
    val image: String,
    val parent_id: String,
    val subs: List<Sub>,
    val title: String,
    val updated_at: String
)