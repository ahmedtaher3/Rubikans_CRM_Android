package com.devartlab.ui.main.ui.eShopping.pharmacySales.model.detailsPharmacySales

data class DetailsPharmacySalesResponse(
    val details: List<Detail>,
    val order: Order,
    val status: String
)