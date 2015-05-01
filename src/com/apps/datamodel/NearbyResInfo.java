package com.apps.datamodel;

import java.util.ArrayList;

public class NearbyResInfo {
	
	private int restaurantId;
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantRating;
	private String restaurantAbout;
	private double lat;
	private double lon;
	private String logo;
	private int hottest;
	private int likes;
	private int branches;
	private int branchId;
	private String openingHour;
	private int isOpen;
	private String city;
	private String area;
	
	private int foodItemId;
	private String foodTitle;
	private String price;
	private String description;
	private String mealType;
	private String ingredients;
	
	private int review_id;
	private String review_title;
	private String review_desc;
	private String published_date;
	private int review_author_id;
	private String review_author_name;
	
	private ArrayList<HottestFoodItemInfo> hottestFoodItemList;
	private ArrayList<ReviewInfo> arrayListReviewInfo;
	
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

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getHottest(int i) {
		return hottest;
	}

	public void setHottest(int hottest) {
		this.hottest = hottest;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getBranches() {
		return branches;
	}

	public void setBranches(int branches) {
		this.branches = branches;
	}



	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public ArrayList<HottestFoodItemInfo> getHottestFoodItemList() {
		return hottestFoodItemList;
	}

	public void setHottestFoodItemList(ArrayList<HottestFoodItemInfo> hottestFoodItemList) {
		this.hottestFoodItemList = hottestFoodItemList;
	}

	public String getRestaurantAbout() {
		return restaurantAbout;
	}

	public void setRestaurantAbout(String restaurantAbout) {
		this.restaurantAbout = restaurantAbout;
	}

	public String getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(String openingHour) {
		this.openingHour = openingHour;
	}

	public int getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(int foodItemId) {
		this.foodItemId = foodItemId;
	}

	public String getFoodTitle() {
		return foodTitle;
	}

	public void setFoodTitle(String foodTitle) {
		this.foodTitle = foodTitle;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public int getReview_id() {
		return review_id;
	}

	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}

	public String getReview_title() {
		return review_title;
	}

	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}

	public String getReview_desc() {
		return review_desc;
	}

	public void setReview_desc(String review_desc) {
		this.review_desc = review_desc;
	}

	public String getPublished_date() {
		return published_date;
	}

	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}

	public int getReview_author_id() {
		return review_author_id;
	}

	public void setReview_author_id(int review_author_id) {
		this.review_author_id = review_author_id;
	}

	public String getReview_author_name() {
		return review_author_name;
	}

	public void setReview_author_name(String review_author_name) {
		this.review_author_name = review_author_name;
	}

	public ArrayList<ReviewInfo> getArrayListReviewInfo() {
		return arrayListReviewInfo;
	}

	public void setArrayListReviewInfo(ArrayList<ReviewInfo> arrayListReviewInfo) {
		this.arrayListReviewInfo = arrayListReviewInfo;
	}


}
