package com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model.sendMessages;

import com.google.gson.annotations.SerializedName;

public class SendMessagesResponse{

	@SerializedName("tempID")
	private Object tempID;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setTempID(Object tempID){
		this.tempID = tempID;
	}

	public Object getTempID(){
		return tempID;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"SendMessagesResponse{" + 
			"tempID = '" + tempID + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}