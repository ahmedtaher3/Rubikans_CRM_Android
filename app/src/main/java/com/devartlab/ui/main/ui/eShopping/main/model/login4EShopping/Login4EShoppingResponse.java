package com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping;

import com.google.gson.annotations.SerializedName;

public class Login4EShoppingResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("token")
	private String token;

	@SerializedName("type_code")
	private String type_code;

	@SerializedName("status")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Login4EShoppingResponse{" + 
			"id = '" + id + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}