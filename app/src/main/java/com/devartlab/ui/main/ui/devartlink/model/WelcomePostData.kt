package com.devartlab.ui.main.ui.devartlink.model

import com.google.gson.annotations.SerializedName

class WelcomePostData {

    @field:SerializedName("_id")
    val id: String= ""

    @field:SerializedName("link")
    val link: String= ""

    @field:SerializedName("v")
    val v: String= ""

    @field:SerializedName("image")
    val image: String= ""

    @field:SerializedName("show_intro")
    val showIntro: Int = 0

    @field:SerializedName("updated_at")
    val updatedAt: String= ""

    @field:SerializedName("created_at")
    val createdAt: String= ""
}