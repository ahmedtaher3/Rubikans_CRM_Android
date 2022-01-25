package com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.getUserAddress

data class GetUserAddressResponse(
    val area_id: Int,
    val area_name: String?,
    val bl_num: Any,
    val city_id: Int,
    val city_name: String?,
    val country_id: Int,
    val country_name: String?,
    val created_at: String,
    val district_id: Int,
    val district_name: String?,
    val fulladdress: String?,
    val id: Int,
    val pharmacy_id: Int,
    val updated_at: String,
    val lat_lng: String
)