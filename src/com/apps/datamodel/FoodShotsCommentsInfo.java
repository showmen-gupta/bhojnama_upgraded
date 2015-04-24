package com.apps.datamodel;

import java.util.ArrayList;

public class FoodShotsCommentsInfo {
	public int commentId;
	public int authorId;
	public String authorName;
	public String commentsTitle;
	public String commentDetails;
	public String publishDate;
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public String getCommentsTitle() {
		return commentsTitle;
	}
	public void setCommentsTitle(String commentsTitle) {
		this.commentsTitle = commentsTitle;
	}
	
	public String getCommentDetails() {
		return commentDetails;
	}
	public void setCommentDetails(String commentDetails) {
		this.commentDetails = commentDetails;
	}
	
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
	
}
