package com.devartlab.a4eshopping.addProductsToThePharmacy.model.Pharmacy

data class Prod(
    val cats: List<Double>,
    val childcats: List<Any>,
    val counted_stock: Double,
    val id: Int,
    val invetory_count: Double,
    val limit_stock: Any,
    val name: String,
    val name_ar: String,
    val pendingstock: Any,
    val pharmaciesCoins: Any,
    val previous_price: Double,
    val price: Double,
    val processingstock: Any,
    val slug: String,
    val sku: String,
    val subcats: List<Subcat>,
    val thumbnail: String
)