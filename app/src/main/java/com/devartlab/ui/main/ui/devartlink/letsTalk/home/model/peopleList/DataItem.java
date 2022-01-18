package com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.peopleList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem{

	@SerializedName("departements_user_nameapi")
	private DepartementsUserNameapi departementsUserNameapi;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("userapi")
	private Userapi userapi;

	@SerializedName("unseenapi")
	private List<UnseenapiItem> unseenapi;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("_id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("departements_user")
	private String departementsUser;

	@SerializedName("desc")
	private String desc;

	@SerializedName("lastaction")
	private int lastaction;

	public void setDepartementsUserNameapi(DepartementsUserNameapi departementsUserNameapi){
		this.departementsUserNameapi = departementsUserNameapi;
	}

	public DepartementsUserNameapi getDepartementsUserNameapi(){
		return departementsUserNameapi;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserapi(Userapi userapi){
		this.userapi = userapi;
	}

	public Userapi getUserapi(){
		return userapi;
	}

	public void setUnseenapi(List<UnseenapiItem> unseenapi){
		this.unseenapi = unseenapi;
	}

	public List<UnseenapiItem> getUnseenapi(){
		return unseenapi;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setDepartementsUser(String departementsUser){
		this.departementsUser = departementsUser;
	}

	public String getDepartementsUser(){
		return departementsUser;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}

	public void setLastaction(int lastaction){
		this.lastaction = lastaction;
	}

	public int getLastaction(){
		return lastaction;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"departements_user_nameapi = '" + departementsUserNameapi + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",userapi = '" + userapi + '\'' + 
			",unseenapi = '" + unseenapi + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",_id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",departements_user = '" + departementsUser + '\'' + 
			",desc = '" + desc + '\'' + 
			",lastaction = '" + lastaction + '\'' + 
			"}";
		}
}