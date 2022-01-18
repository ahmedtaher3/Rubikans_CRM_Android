package com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.peopleList;

import com.google.gson.annotations.SerializedName;

public class Userapi{

	@SerializedName("hrms_JobEnName")
	private String hrmsJobEnName;

	@SerializedName("name")
	private String name;

	@SerializedName("_id")
	private String id;

	public void setHrmsJobEnName(String hrmsJobEnName){
		this.hrmsJobEnName = hrmsJobEnName;
	}

	public String getHrmsJobEnName(){
		return hrmsJobEnName;
	}

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
			"hrms_JobEnName = '" + hrmsJobEnName + '\'' + 
			",name = '" + name + '\'' + 
			",_id = '" + id + '\'' + 
			"}";
		}
}