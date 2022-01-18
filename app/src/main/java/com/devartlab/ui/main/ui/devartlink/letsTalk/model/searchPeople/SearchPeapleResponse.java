package com.devartlab.ui.main.ui.devartlink.letsTalk.model.searchPeople;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchPeapleResponse implements Serializable {

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private List<PeopleItem> data;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(List<PeopleItem> data){
		this.data = data;
	}

	public List<PeopleItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"SearchPeapleResponse{" + 
			"code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}