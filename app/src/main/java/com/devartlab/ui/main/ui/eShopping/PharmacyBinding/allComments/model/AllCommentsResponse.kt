package com.devartlab.a4eshopping.PharmacyBinding.allComments.model

data class AllCommentsResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: Boolean
)