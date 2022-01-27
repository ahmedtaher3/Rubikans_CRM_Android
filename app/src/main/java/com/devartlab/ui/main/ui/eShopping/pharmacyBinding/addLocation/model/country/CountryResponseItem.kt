package com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.country

data class CountryResponseItem(
    val country_code: String,
    val country_name: String,
    val id: Int,
    val iso3: Any,
    val name_ar: String,
    val nicename: Any,
    val numcode: Any,
    val phonecode: Int,
    val photo: Any,
    val status: Int
)