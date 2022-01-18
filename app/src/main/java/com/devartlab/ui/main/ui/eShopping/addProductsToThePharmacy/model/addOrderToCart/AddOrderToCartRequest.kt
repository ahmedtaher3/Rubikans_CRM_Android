package com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart

data class AddOrderToCartRequest(
    val cart: List<Cart>,
    val user_id: Int// id pharmacies
)