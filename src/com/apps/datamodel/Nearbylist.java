package com.apps.datamodel;

public class Nearbylist {
	private int icon;
	private String name;
	private String type;
	private String address;
	private String distance;
	private int rating1;
	private int rating2;
	private int rating3;
	private int rating4;
	private int rating5;
	private double rating_total;
	private String total_review;
	private String review;
	private String total_friend;
	private String friends_here;

	public Nearbylist(int icon,String name, String type, String address,
			String distance, int rating1, int rating2, int rating3,
			int rating4,int rating5, double total, String total_review,
			String review, String total_friend, String friends_here) {
		super();
		this.icon= icon;
		this.name = name;
		this.type = type;
		this.address = address;
		this.distance = distance;
		this.rating1 = rating1;
		this.rating2 = rating2;
		this.rating3 = rating3;
		this.rating4 = rating4;
		this.rating5 = rating5;
		this.setRating_total(total);
		this.total_review = total_review;
		this.review = review;
		this.total_friend = total_friend;
		this.friends_here = friends_here;
	}

	
	public Nearbylist(String name2, String type2, String address2,
			String distance2, int starActive, int starActive2,
			int starInactive, int starInactive2, double d,
			String total_review2, String review2, String total_friend2,
			String friends_here2) {
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	

	public String getTotal_review() {
		return total_review;
	}
	public void setTotal_review(String total_review) {
		this.total_review = total_review;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getTotal_friend() {
		return total_friend;
	}
	public void setTotal_friend(String total_friend) {
		this.total_friend = total_friend;
	}
	public String getFriends_here() {
		return friends_here;
	}
	public void setFriends_here(String friends_here) {
		this.friends_here = friends_here;
	}


	public int getIcon() {
		return icon;
	}


	public void setIcon(int icon) {
		this.icon = icon;
	}


	public int getRating1() {
		return rating1;
	}


	public void setRating1(int rating1) {
		this.rating1 = rating1;
	}


	public int getRating2() {
		return rating2;
	}


	public void setRating2(int rating2) {
		this.rating2 = rating2;
	}


	public int getRating3() {
		return rating3;
	}


	public void setRating3(int rating3) {
		this.rating3 = rating3;
	}


	public int getRating4() {
		return rating4;
	}


	public void setRating4(int rating4) {
		this.rating4 = rating4;
	}


	public double getRating_total() {
		return rating_total;
	}


	public void setRating_total(double rating_total) {
		this.rating_total = rating_total;
	}


	public int getRating5() {
		return rating5;
	}


	public void setRating5(int rating5) {
		this.rating5 = rating5;
	}


}
