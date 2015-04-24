package com.apps.datamodel;

public class Userreviewlist {
	private int icon;
	private String name;
	private String desc;
	private String res_name;
	private String about;
	private String rated;
	private int rating1;
	private int rating2;
	private int rating3;
	private int rating4;
	private int rating5;
	

	public Userreviewlist(int icon,String name, String desc, String res_name,String about,
			 String rated,int rating1, int rating2, int rating3,
			int rating4,int rating5) {
		super();
		this.icon= icon;
		this.name = name;
		this.desc = desc;
		this.res_name=res_name;
		this.about=about;
		this.setRated(rated);
		this.rating1 = rating1;
		this.rating2 = rating2;
		this.rating3 = rating3;
		this.rating4 = rating4;
		this.rating5 = rating5;
		
		
	}




	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getRes_name() {
		return res_name;
	}

	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}
	
	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
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
	public int getRating5() {
		return rating5;
	}

	public void setRating5(int rating5) {
		this.rating5 = rating5;
	}








}
