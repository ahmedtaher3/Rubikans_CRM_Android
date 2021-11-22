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
	val recyclerPosition: String? = null
)

data class SlideImagesItem(

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)
