package com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress

data class UpdateAddressRequest(
    val pharmacy_id: String?,
    val country_id: Int,
    val city_id: Int,
    val area_id: Int,
    val district_id: Int,
    val fulladdress: String
)