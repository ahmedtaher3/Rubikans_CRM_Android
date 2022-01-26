package com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct

data class AddToCardRequest(
    val mr: String,
    var no_product: Int,
    val user_id: Int
)