package com.devartlab.ui.main.ui.devartlink.model

import com.google.gson.annotations.SerializedName

class WelcomePostResponse{
	@field:SerializedName("data")
	val data: WelcomePostData=WelcomePostData()

	@field:SerializedName("code")
	val code: Int = 0
}



