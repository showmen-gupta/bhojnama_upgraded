package com.apps.datamodel;

public class NearbyInfo {
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantRating;
	private double lat;
	private double lon;
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
}
