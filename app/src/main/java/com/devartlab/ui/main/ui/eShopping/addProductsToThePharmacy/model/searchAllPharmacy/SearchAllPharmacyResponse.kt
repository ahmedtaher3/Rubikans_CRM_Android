package com.devartlab.a4eshopping.addProductsToThePharmacy.model.searchAllPharmacy

data class SearchAllPharmacyResponse(
    val `data`: List<Data>,
    val code: Int,
    val status: String
)