package com.devartlab.a4eshopping.pharmacySales.model.detailsPharmacySales

data class DetailsPharmacySalesResponse(
    val details: List<Detail>,
    val order: Order,
    val status: String
)