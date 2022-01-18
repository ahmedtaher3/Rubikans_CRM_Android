package com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.ImageModel;

import com.google.gson.annotations.SerializedName;

public class ImageProfileResponse{

	@SerializedName("img")
	private String img;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	@Override
 	public String toString(){
		return 
			"ImageProfileResponse{" + 
			"img = '" + img + '\'' + 
			"}";
		}
}