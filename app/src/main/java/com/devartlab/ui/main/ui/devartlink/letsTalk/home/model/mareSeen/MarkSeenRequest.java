package com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.mareSeen;

import com.google.gson.annotations.SerializedName;

public class MarkSeenRequest{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("to_id")
	private String toId;

	@SerializedName("id")
	private String id;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setToId(String toId){
		this.toId = toId;
	}

	public String getToId(){
		return toId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"MarkSeenRequest{" + 
			"user_id = '" + userId + '\'' + 
			",to_id = '" + toId + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}