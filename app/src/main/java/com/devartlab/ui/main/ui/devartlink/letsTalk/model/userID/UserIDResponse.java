package com.devartlab.ui.main.ui.devartlink.letsTalk.model.userID;

import com.google.gson.annotations.SerializedName;

public class UserIDResponse{

	@SerializedName("id")
	private String id;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"UserIDResponse{" + 
			"id = '" + id + '\'' + 
			"}";
		}
}