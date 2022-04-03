package com.devartlab.a4eshopping.orientationVideos.model

data class ResponseVideos(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val code: Int,
    val pageInfo: PageInfo
)