package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class UserStore(

	@field:SerializedName("StoreId")
	val storeId: Int? = null,

	@field:SerializedName("UserId")
	val userId: Int? = null,

	@field:SerializedName("UserStoreDetId")
	val userStoreDetId: Int? = null
)
