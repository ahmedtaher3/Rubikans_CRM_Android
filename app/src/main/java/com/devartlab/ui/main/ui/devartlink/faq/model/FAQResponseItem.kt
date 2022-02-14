package com.devartlab.ui.main.ui.devartlink.faq.model

data class FAQResponseItem(
    val _id: String,
    val arrange: String,
    val created_at: String,
    val image: String,
    val parent_id: String,
    val sections: List<Section>,
    val subs: List<SubsItem>,
    val title: String,
    val updated_at: String
)