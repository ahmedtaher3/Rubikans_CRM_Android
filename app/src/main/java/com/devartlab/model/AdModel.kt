package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class AdModelList(

    val ads: ArrayList<AdModel>? = null


)

data class AdModel(

    @field:SerializedName("webPageLink")
    val webPageLink: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("slideImages")
    val slideImages: List<SlideImagesItem?>? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("resourceLink")
    val resourceLink: String? = null,

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("pageCode")
    val pageCode: String? = null,

    @field:SerializedName("recyclerPosition")
    val recyclerPosition: String? = null,

    @field:SerializedName("default_ad_image")
    val default_ad_image: String? = null,

    @field:SerializedName("from_date")
    val from_date: String? = null,

    @field:SerializedName("to_date")
    val to_date: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("view_more_title")
    val view_more_title: String? = null,

    @field:SerializedName("view_more_text")
    val view_more_text: String? = null,

    @field:SerializedName("view_more_type")
    val view_more_type: String? = null,

    @field:SerializedName("show_ad")
    val show_ad: Boolean? = null,

    @field:SerializedName("show_more")
    val show_more: Boolean? = null,

    @field:SerializedName("is_external")
    val is_external: Boolean? = null,

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("view_more_paragraph")
    val view_more_paragraph: String? = null,

    @field:SerializedName("paragraph")
    val paragraph: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("view_more_image")
    val view_more_image: List<viewMoreImage?>? = null
)

data class SlideImagesItem(

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("text")
    val text: String? = null
)

data class viewMoreImage(

    @field:SerializedName("link")
    val link: String? = null
)
