package com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatListResponse{

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("last_page")
	private int lastPage;

	@SerializedName("next_page_url")
	private String nextPageUrl;

	@SerializedName("prev_page_url")
	private Object prevPageUrl;

	@SerializedName("first_page_url")
	private String firstPageUrl;

	@SerializedName("path")
	private String path;

	@SerializedName("total")
	private int total;

	@SerializedName("last_page_url")
	private String lastPageUrl;

	@SerializedName("from")
	private int from;

	@SerializedName("links")
	private List<LinksItem> links;

	@SerializedName("to")
	private int to;

	@SerializedName("current_page")
	private int currentPage;

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

	public void setLastPage(int lastPage){
		this.lastPage = lastPage;
	}

	public int getLastPage(){
		return lastPage;
	}

	public void setNextPageUrl(String nextPageUrl){
		this.nextPageUrl = nextPageUrl;
	}

	public String getNextPageUrl(){
		return nextPageUrl;
	}

	public void setPrevPageUrl(Object prevPageUrl){
		this.prevPageUrl = prevPageUrl;
	}

	public Object getPrevPageUrl(){
		return prevPageUrl;
	}

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

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setLastPageUrl(String lastPageUrl){
		this.lastPageUrl = lastPageUrl;
	}

	public String getLastPageUrl(){
		return lastPageUrl;
	}

	public void setFrom(int from){
		this.from = from;
	}

	public int getFrom(){
		return from;
	}

	public void setLinks(List<LinksItem> links){
		this.links = links;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public void setTo(int to){
		this.to = to;
	}

	public int getTo(){
		return to;
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
			"ChatListResponse{" + 
			"per_page = '" + perPage + '\'' + 
			",data = '" + data + '\'' + 
			",last_page = '" + lastPage + '\'' + 
			",next_page_url = '" + nextPageUrl + '\'' + 
			",prev_page_url = '" + prevPageUrl + '\'' + 
			",first_page_url = '" + firstPageUrl + '\'' + 
			",path = '" + path + '\'' + 
			",total = '" + total + '\'' + 
			",last_page_url = '" + lastPageUrl + '\'' + 
			",from = '" + from + '\'' + 
			",links = '" + links + '\'' + 
			",to = '" + to + '\'' + 
			",current_page = '" + currentPage + '\'' + 
			"}";
		}
}