package com.devartlab.model

data class SlideModel(
        var id: Int  = 0,
        var itemId: Int  = 0,
        var messageId: Int  = 0,
        var messageDetId: Int  = 0,
        var slideName: String  = "",
        var slideUrl: String  = "",
        var checked: Boolean  = false,
        var checkedIndex: Int? = 0,
        var base64: String  = "",
        var converted: Boolean  = false
)