package com.devartlab.ui.main.ui.eShopping.orientationVideos.model

import com.google.gson.annotations.SerializedName

data class ResponseVideos(

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("pageInfo")
	val pageInfo: PageInfo,

	@field:SerializedName("etag")
	val etag: String,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("items")
	val itemsVideos: List<ItemsVideos>
)

data class PageInfo(

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("resultsPerPage")
	val resultsPerPage: Int
)

data class Thumbnails(

	@field:SerializedName("standard")
	val standard: Standard,

	@field:SerializedName("default")
	val jsonMemberDefault: JsonMemberDefault,

	@field:SerializedName("high")
	val high: High,

	@field:SerializedName("maxres")
	val maxres: Maxres,

	@field:SerializedName("medium")
	val medium: Medium
)

data class Maxres(

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("height")
	val height: Int
)

data class Medium(

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("height")
	val height: Int
)

data class ItemsVideos(

	@field:SerializedName("snippet")
	val snippet: Snippet,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("etag")
	val etag: String,

	@field:SerializedName("id")
	val id: String
)

data class High(

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("height")
	val height: Int
)

data class Standard(

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("height")
	val height: Int
)

data class JsonMemberDefault(

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("height")
	val height: Int
)

data class ResourceId(

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("videoId")
	val videoId: String
)

data class Snippet(

	@field:SerializedName("playlistId")
	val playlistId: String,

	@field:SerializedName("resourceId")
	val resourceId: ResourceId,

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("position")
	val position: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("thumbnails")
	val thumbnails: Thumbnails,

	@field:SerializedName("channelId")
	val channelId: String,

	@field:SerializedName("videoOwnerChannelId")
	val videoOwnerChannelId: String,

	@field:SerializedName("channelTitle")
	val channelTitle: String,

	@field:SerializedName("videoOwnerChannelTitle")
	val videoOwnerChannelTitle: String
)
