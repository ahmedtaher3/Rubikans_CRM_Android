package com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping

data class Login4EShoppingRequest(
    var email: String,
    var password: String,
    var mr: String,
    var fcm: String,
    var device_type:String
)