package com.apps.datamodel;

public class ReviewInfo {
	
	private int id;
	private String title;
	private String details;
	private String pulishedDate;
	private int author;
	private String author_name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getPulishedDate() {
		return pulishedDate;
	}
	public void setPulishedDate(String pulishedDate) {
		this.pulishedDate = pulishedDate;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

}
