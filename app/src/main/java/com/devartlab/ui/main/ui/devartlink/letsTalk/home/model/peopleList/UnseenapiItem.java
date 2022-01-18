package com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.peopleList;

import com.google.gson.annotations.SerializedName;

public class UnseenapiItem{

	@SerializedName("conversation_id")
	private String conversationId;

	@SerializedName("_id")
	private String id;

	public void setConversationId(String conversationId){
		this.conversationId = conversationId;
	}

	public String getConversationId(){
		return conversationId;
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
			"UnseenapiItem{" + 
			"conversation_id = '" + conversationId + '\'' + 
			",_id = '" + id + '\'' + 
			"}";
		}
}