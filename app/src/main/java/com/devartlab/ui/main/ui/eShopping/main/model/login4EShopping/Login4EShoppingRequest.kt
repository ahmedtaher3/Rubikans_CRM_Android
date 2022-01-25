package com.devartlab.a4eshopping.main.model.login4EShopping

data class Login4EShoppingRequest(
    val email: String,
    val password: String,
    val mr: String,
    val fcm: String,
    var device_type:String
)