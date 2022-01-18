package com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.mareSeen;

import com.google.gson.annotations.SerializedName;

public class MarkSeenResponse{

	@SerializedName("status")
	private int status;

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"MarkSeenResponse{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}