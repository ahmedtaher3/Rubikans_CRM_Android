package com.devartlab.ui.main.ui.devartlink.letsTalk.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse{

	@SerializedName("hrms")
	private int hrms;

	@SerializedName("dep_id")
	private String depId;

	@SerializedName("hrms_password")
	private String hrmsPassword;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("role_ids")
	private List<String> roleIds;

	@SerializedName("hrms_JobEnName")
	private String hrmsJobEnName;

	@SerializedName("hrms_ManagerId")
	private int hrmsManagerId;

	@SerializedName("fcm")
	private String fcm;

	@SerializedName("active_status")
	private int activeStatus;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("_id")
	private String id;

	@SerializedName("type_code_en")
	private String typeCodeEn;

	@SerializedName("hrms_user_name")
	private String hrmsUserName;

	public void setHrms(int hrms){
		this.hrms = hrms;
	}

	public int getHrms(){
		return hrms;
	}

	public void setDepId(String depId){
		this.depId = depId;
	}

	public String getDepId(){
		return depId;
	}

	public void setHrmsPassword(String hrmsPassword){
		this.hrmsPassword = hrmsPassword;
	}

	public String getHrmsPassword(){
		return hrmsPassword;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setRoleIds(List<String> roleIds){
		this.roleIds = roleIds;
	}

	public List<String> getRoleIds(){
		return roleIds;
	}

	public void setHrmsJobEnName(String hrmsJobEnName){
		this.hrmsJobEnName = hrmsJobEnName;
	}

	public String getHrmsJobEnName(){
		return hrmsJobEnName;
	}

	public void setHrmsManagerId(int hrmsManagerId){
		this.hrmsManagerId = hrmsManagerId;
	}

	public int getHrmsManagerId(){
		return hrmsManagerId;
	}

	public void setFcm(String fcm){
		this.fcm = fcm;
	}

	public String getFcm(){
		return fcm;
	}

	public void setActiveStatus(int activeStatus){
		this.activeStatus = activeStatus;
	}

	public int getActiveStatus(){
		return activeStatus;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	public void setTypeCodeEn(String typeCodeEn){
		this.typeCodeEn = typeCodeEn;
	}

	public String getTypeCodeEn(){
		return typeCodeEn;
	}

	public void setHrmsUserName(String hrmsUserName){
		this.hrmsUserName = hrmsUserName;
	}

	public String getHrmsUserName(){
		return hrmsUserName;
	}

	@Override
 	public String toString(){
		return 
			"UserResponse{" + 
			"hrms = '" + hrms + '\'' + 
			",dep_id = '" + depId + '\'' + 
			",hrms_password = '" + hrmsPassword + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",role_ids = '" + roleIds + '\'' + 
			",hrms_JobEnName = '" + hrmsJobEnName + '\'' + 
			",hrms_ManagerId = '" + hrmsManagerId + '\'' + 
			",fcm = '" + fcm + '\'' + 
			",active_status = '" + activeStatus + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",_id = '" + id + '\'' + 
			",type_code_en = '" + typeCodeEn + '\'' + 
			",hrms_user_name = '" + hrmsUserName + '\'' + 
			"}";
		}
}