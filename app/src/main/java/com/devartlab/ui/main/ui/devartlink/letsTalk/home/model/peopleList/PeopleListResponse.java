package com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.peopleList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleListResponse{

	@SerializedName("first_page_url")
	private String firstPageUrl;

	@SerializedName("path")
	private String path;

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("next_page_url")
	private Object nextPageUrl;

	@SerializedName("from")
	private int from;

	@SerializedName("to")
	private int to;

	@SerializedName("prev_page_url")
	private Object prevPageUrl;

	@SerializedName("current_page")
	private int currentPage;

	public void setFirstPageUrl(String firstPageUrl){
		this.firstPageUrl = firstPageUrl;
	}

	public String getFirstPageUrl(){
		return firstPageUrl;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setNextPageUrl(Object nextPageUrl){
		this.nextPageUrl = nextPageUrl;
	}

	public Object getNextPageUrl(){
		return nextPageUrl;
	}

	public void setFrom(int from){
		this.from = from;
	}

	public int getFrom(){
		return from;
	}

	public void setTo(int to){
		this.to = to;
	}

	public int getTo(){
		return to;
	}

	public void setPrevPageUrl(Object prevPageUrl){
		this.prevPageUrl = prevPageUrl;
	}

	public Object getPrevPageUrl(){
		return prevPageUrl;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	@Override
 	public String toString(){
		return 
			"PeopleListResponse{" + 
			"first_page_url = '" + firstPageUrl + '\'' + 
			",path = '" + path + '\'' + 
			",per_page = '" + perPage + '\'' + 
			",data = '" + data + '\'' + 
			",next_page_url = '" + nextPageUrl + '\'' + 
			",from = '" + from + '\'' + 
			",to = '" + to + '\'' + 
			",prev_page_url = '" + prevPageUrl + '\'' + 
			",current_page = '" + currentPage + '\'' + 
			"}";
		}
}