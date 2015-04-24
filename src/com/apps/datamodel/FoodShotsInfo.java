package com.apps.datamodel;

import java.util.ArrayList;

public class FoodShotsInfo {
	private int restaurantId;
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantRating;
	private String restaurantAbout;
	
	
	private int foodShotId;
	private String foodShotName;
	private String foodShotDetails;
	
	private String foodShotPhotoId;
	private String foodShotPhotoTitle;
	private String photUrl;
	private String type;
	private String photoDetails;
	
	private int totalPages;
	
	private ArrayList<FoodShotsCommentsInfo> foodShotsComments;
	
	
	public String getRestaurantRating() {
		return restaurantRating;
	}
	
	public void setRestaurantRating(String restaurantRating) {
		this.restaurantRating = restaurantRating;
	}
	 
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}
	
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantAbout() {
		return restaurantAbout;
	}

	public void setRestaurantAbout(String restaurantAbout) {
		this.restaurantAbout = restaurantAbout;
	}

	public String getFoodShotPhotoId() {
		return foodShotPhotoId;
	}

	public void setFoodShotPhotoId(String foodShotPhotoId) {
		this.foodShotPhotoId = foodShotPhotoId;
	}

	public String getFoodShotPhotoTitle() {
		return foodShotPhotoTitle;
	}

	public void setFoodShotPhotoTitle(String foodShotPhotoTitle) {
		this.foodShotPhotoTitle = foodShotPhotoTitle;
	}

	public String getPhotUrl() {
		return photUrl;
	}

	public void setPhotUrl(String photUrl) {
		this.photUrl = photUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhotoDetails() {
		return photoDetails;
	}

	public void setPhotoDetails(String photoDetails) {
		this.photoDetails = photoDetails;
	}

	public int getFoodShotId() {
		return foodShotId;
	}

	public void setFoodShotId(int foodShotId) {
		this.foodShotId = foodShotId;
	}

	public String getFoodShotName() {
		return foodShotName;
	}

	public void setFoodShotName(String foodShotName) {
		this.foodShotName = foodShotName;
	}

	public String getFoodShotDetails() {
		return foodShotDetails;
	}

	public void setFoodShotDetails(String foodShotDetails) {
		this.foodShotDetails = foodShotDetails;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public ArrayList<FoodShotsCommentsInfo> getFoodShotsComments() {
		return foodShotsComments;
	}

	public void setFoodShotsComments(ArrayList<FoodShotsCommentsInfo> foodShotsComments) {
		this.foodShotsComments = foodShotsComments;
	}
}
