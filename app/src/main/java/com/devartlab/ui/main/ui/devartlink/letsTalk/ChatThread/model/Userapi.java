package com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model;

import com.google.gson.annotations.SerializedName;

public class Userapi{

	@SerializedName("name")
	private String name;

	@SerializedName("_id")
	private String id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"Userapi{" + 
			"name = '" + name + '\'' + 
			",_id = '" + id + '\'' + 
			"}";
		}
}