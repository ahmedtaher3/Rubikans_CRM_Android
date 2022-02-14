package com.devartlab.ui.main.ui.devartlink.faq.model

data class SubsItem(
    val _id: String,
    val arrange: String,
    val created_at: String,
    val image: String,
    val parent_id: String,
    val sections: List<Section>,
    val title: String,
    val updated_at: String
)