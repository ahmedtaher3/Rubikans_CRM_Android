package com.devartlab.ui.main.ui.eShopping.pharmacySales.model.detailsPharmacySales

data class Item(
    val sku: String,
    val name: String,
    val qty: Double,
    val previous_price: Double,
    val price: Double
)