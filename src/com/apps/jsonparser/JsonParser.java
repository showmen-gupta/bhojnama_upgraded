package com.apps.jsonparser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.BranchInfo;
import com.apps.datamodel.FoodShotsCommentsInfo;
import com.apps.datamodel.FoodShotsInfo;
import com.apps.datamodel.HottestFoodItemInfo;
import com.apps.datamodel.HottestInfo;
import com.apps.datamodel.LocationAreaInfo;
import com.apps.datamodel.LocationCityInfo;
import com.apps.datamodel.NearbyInfo;
import com.apps.datamodel.NearbyResInfo;
import com.apps.datamodel.RestaurantInfo;
import com.apps.datamodel.ReviewInfo;
import com.apps.datamodel.UserInfo;


public class JsonParser {
	
	public static void parseNearbyData(String response) throws JSONException {
		String logindata;
		
		JSONObject jDataObj = new JSONObject(response);
		logindata = jDataObj.getString("status");
		
		JSONArray jsonResultArray = jDataObj.getJSONArray("results");
		int resultSize = jsonResultArray.length();
		for (int i = 0; i < resultSize; i++) {
			jsonResultArray.getJSONObject(i).getString("name");
			NearbyInfo nearByInfo = new NearbyInfo();
			nearByInfo.setRestaurantName(jsonResultArray.getJSONObject(i).getString("name"));
			nearByInfo.setRestaurantAddress(jsonResultArray.getJSONObject(i).getString("vicinity"));
			nearByInfo.setLat(jsonResultArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
			nearByInfo.setLon(jsonResultArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
			BhojNamaSingleton.getInstance().getArrayListNearByInfo().add(nearByInfo);
		}
		
		//BhojNamaSingleton.getInstance().setArrayListNearByInfo(listNearByInfo);
		Log.e("****"," ===== "+ logindata + " size= " + jsonResultArray.length()); 
		
	}
	
	public static void parseHottestData(String response) throws JSONException {
		String logindata;
		
		JSONObject jDataObj = new JSONObject(response);
		logindata = jDataObj.getString("data");
		
		//JSONArray jsonResultArray = jDataObj.getJSONArray("restaurant");
		JSONArray jsonResultArray = jDataObj.getJSONObject("data").getJSONArray("restaurant");
		int resultSize = jsonResultArray.length();
		
		for (int i = 0; i < resultSize; i++) {
			ArrayList<HottestFoodItemInfo> listHottestItem = new ArrayList<HottestFoodItemInfo>();
			HottestInfo hottestInfo = new HottestInfo();
			//if(jsonResultArray.getJSONObject(i).getInt("hottest")==1){
				hottestInfo.setRestaurantId(jsonResultArray.getJSONObject(i).getInt("id"));
				hottestInfo.setRestaurantName(jsonResultArray.getJSONObject(i).getString("name"));
				hottestInfo.setRestaurantAbout(jsonResultArray.getJSONObject(i).getString("about"));
				
				hottestInfo.setLogo(jsonResultArray.getJSONObject(i).getString("logo"));
				
				hottestInfo.setLikes(jsonResultArray.getJSONObject(i).getInt("likes"));
				
				hottestInfo.setReview_count(jsonResultArray.getJSONObject(i).getInt("review_count"));
				
				JSONArray branchArray = jsonResultArray.getJSONObject(i).getJSONArray("branches");
				
				JSONArray itemArray = jsonResultArray.getJSONObject(i).getJSONArray("items");
				
				int itemSize = itemArray.length();
				for (int j2 = 0; j2 < itemSize; j2++) {
					
					HottestFoodItemInfo hotFoodItem = new HottestFoodItemInfo();
					Log.e("Restaurant Price", "-----" + itemArray.getJSONObject(j2).getString("price"));
					hotFoodItem.setFoodItemId(itemArray.getJSONObject(j2).getInt("id"));
					hotFoodItem.setFoodTitle(itemArray.getJSONObject(j2).getString("title"));
					hotFoodItem.setDescription(itemArray.getJSONObject(j2).getString("description").trim());
					hotFoodItem.setPrice(itemArray.getJSONObject(j2).getString("price"));
					listHottestItem.add(hotFoodItem);
				}
				
				int brachSize = jsonResultArray.getJSONObject(i).getJSONArray("branches").length();
				for (int j = 0; j < brachSize; j++) {
					Log.e("Restaurant Branch HOT", "-----" + branchArray.getJSONObject(j).getString("city"));
					hottestInfo.setBranchId(branchArray.getJSONObject(j).getInt("id"));
					hottestInfo.setIsOpen(branchArray.getJSONObject(j).getInt("is_open"));
					hottestInfo.setOpeningHour(branchArray.getJSONObject(j).getString("opening_hour"));
					hottestInfo.setCity(branchArray.getJSONObject(j).getJSONObject("city").getString("name"));
					hottestInfo.setArea(branchArray.getJSONObject(j).getJSONObject("area").getString("name"));
					hottestInfo.setLat(branchArray.getJSONObject(j).getDouble("latitude"));
					hottestInfo.setLon(branchArray.getJSONObject(j).getDouble("longitude"));
				}
				
				hottestInfo.setHottestFoodItemList(listHottestItem);
				//listHottestInfo.add(hottestInfo);
				BhojNamaSingleton.getInstance().getHottestInfoList().add(hottestInfo);
			//}
		}
		
		//BhojNamaSingleton.getInstance().getHottestInfoList().add;
		//BhojNamaSingleton.getInstance().setArrayListNearByInfo(listNearByInfo);
		
	}
	
	public static void parseLocBasedRestaurant(String response) throws JSONException {
		String logindata;
		
		JSONObject jDataObj = new JSONObject(response);
		logindata = jDataObj.getString("data");
		
		//JSONArray jsonResultArray = jDataObj.getJSONArray("restaurant");
		JSONArray jsonResultArray = jDataObj.getJSONObject("data").getJSONArray("restaurants");
		int resultSize = jsonResultArray.length();
		
		for (int i = 0; i < resultSize; i++) {
			ArrayList<HottestFoodItemInfo> listHottestItem = new ArrayList<HottestFoodItemInfo>();
			HottestInfo hottestInfo = new HottestInfo();
			//if(jsonResultArray.getJSONObject(i).getInt("hottest")==1){
				hottestInfo.setRestaurantId(jsonResultArray.getJSONObject(i).getInt("id"));
				hottestInfo.setRestaurantName(jsonResultArray.getJSONObject(i).getString("name"));
				hottestInfo.setRestaurantAbout(jsonResultArray.getJSONObject(i).getString("about"));
				
				hottestInfo.setLogo(jsonResultArray.getJSONObject(i).getString("logo"));
				
				hottestInfo.setLikes(jsonResultArray.getJSONObject(i).getInt("likes"));
				
				hottestInfo.setReview_count(jsonResultArray.getJSONObject(i).getInt("review_count"));
				
				JSONArray branchArray = jsonResultArray.getJSONObject(i).getJSONArray("branches");
				
				JSONArray itemArray = jsonResultArray.getJSONObject(i).getJSONArray("items");
				
				int itemSize = itemArray.length();
				for (int j2 = 0; j2 < itemSize; j2++) {
					
					HottestFoodItemInfo hotFoodItem = new HottestFoodItemInfo();
					Log.e("Restaurant Price", "-----" + itemArray.getJSONObject(j2).getString("price"));
					hotFoodItem.setFoodItemId(itemArray.getJSONObject(j2).getInt("id"));
					hotFoodItem.setFoodTitle(itemArray.getJSONObject(j2).getString("title"));
					hotFoodItem.setDescription(itemArray.getJSONObject(j2).getString("description").trim());
					hotFoodItem.setPrice(itemArray.getJSONObject(j2).getString("price"));
					listHottestItem.add(hotFoodItem);
				}
				
				int brachSize = jsonResultArray.getJSONObject(i).getJSONArray("branches").length();
				for (int j = 0; j < brachSize; j++) {
					Log.e("Restaurant Branch HOT", "-----" + branchArray.getJSONObject(j).getString("city"));
					hottestInfo.setBranchId(branchArray.getJSONObject(j).getInt("id"));
					hottestInfo.setLat(branchArray.getJSONObject(j).getDouble("latitude"));
					hottestInfo.setLon(branchArray.getJSONObject(j).getDouble("longitude"));
					hottestInfo.setOpeningHour(branchArray.getJSONObject(j).getString("opening_hour"));
					hottestInfo.setIsOpen(branchArray.getJSONObject(j).getInt("is_open"));
					hottestInfo.setCity(branchArray.getJSONObject(j).getJSONObject("city").getString("name"));
					hottestInfo.setArea(branchArray.getJSONObject(j).getJSONObject("area").getString("name"));

				}
				
				hottestInfo.setHottestFoodItemList(listHottestItem);
				//listHottestInfo.add(hottestInfo);
				BhojNamaSingleton.getInstance().getHottestInfoList().add(hottestInfo);
			//}
		}
		
	}
	
	public static void parseFoodShots(String response, int currentArraySize) throws JSONException {
		Log.e("Current Size", "=== " + currentArraySize);
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONObject jsonPagignatorObject = jsonData.getJSONObject("paginator");
		
		BhojNamaSingleton.getInstance().setTotalPages(jsonPagignatorObject.getInt("totalPages"));
		
		JSONArray jsonArray = jsonDataObject.getJSONArray("foodShot");
		
		int foodShotSize = jsonArray.length();
		Log.e("FoodShot Size", "-----=== " + foodShotSize);
		
		if (foodShotSize == 0) {
			return;
		}
		
		for (int i = 0; i < foodShotSize; i++) {
			FoodShotsInfo foodShotsInfo = new FoodShotsInfo();
			foodShotsInfo.setFoodShotId(jsonArray.getJSONObject(i).getInt("id"));
			foodShotsInfo.setFoodShotName(jsonArray.getJSONObject(i).getString("name"));
			foodShotsInfo.setFoodShotDetails(jsonArray.getJSONObject(i).getString("detail"));
			
			JSONArray jsonArrayPhotos = jsonArray.getJSONObject(i).getJSONArray("photos");
			int photosArraySize = jsonArrayPhotos.length();
			for (int j2 = 0; j2 < photosArraySize; j2++) {
				foodShotsInfo.setPhotUrl(jsonArrayPhotos.getJSONObject(j2).getString("url"));
			}
			
			JSONArray jsonArrayComments = jsonArray.getJSONObject(i).getJSONArray("comments");
			int commentsArray = jsonArrayComments.length();
			
			ArrayList<FoodShotsCommentsInfo> arrayListFoodShotComments = new ArrayList<FoodShotsCommentsInfo>();
			for (int j = 0; j < commentsArray; j++) {
				FoodShotsCommentsInfo foodShotComments = new FoodShotsCommentsInfo();
				foodShotComments.setAuthorId(jsonArrayComments.getJSONObject(j).getJSONObject("author").getInt("id"));
				foodShotComments.setAuthorName(jsonArrayComments.getJSONObject(j).getJSONObject("author").getString("name"));
				foodShotComments.setCommentId(jsonArrayComments.getJSONObject(j).getInt("id"));
				foodShotComments.setCommentsTitle(jsonArrayComments.getJSONObject(j).getString("title"));
				foodShotComments.setCommentDetails(jsonArrayComments.getJSONObject(j).getString("detail"));
				foodShotComments.setPublishDate(jsonArrayComments.getJSONObject(j).getString("publishedDate"));
				arrayListFoodShotComments.add(foodShotComments);
			}
			
			foodShotsInfo.setFoodShotsComments(arrayListFoodShotComments);
			BhojNamaSingleton.getInstance().getArrayListFoodShots().add(foodShotsInfo);
		}
		
	}
	
	public static ArrayList<RestaurantInfo> parseRestaurantList(String response) throws JSONException {
		ArrayList<RestaurantInfo> restaurantArrayList = new ArrayList<RestaurantInfo>();
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONArray jsonArray = jsonDataObject.getJSONArray("restaurant");
		
		int foodShotSize = jsonArray.length();
		for (int i = 0; i < foodShotSize; i++) {
			RestaurantInfo restaurantInfo = new RestaurantInfo();
			restaurantInfo.setRes_id(jsonArray.getJSONObject(i).getInt("id"));
			restaurantInfo.setRes_name(jsonArray.getJSONObject(i).getString("name"));
			restaurantArrayList.add(restaurantInfo);
		}
		return restaurantArrayList;
		
	}
	
	public static ArrayList<BranchInfo> parseBranchList(String response) throws JSONException {
		
		ArrayList<BranchInfo> branchArrayList = new ArrayList<BranchInfo>();
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONArray jsonArray = jsonDataObject.getJSONArray("branches");
		
		int foodShotSize = jsonArray.length();
		for (int i = 0; i < foodShotSize; i++) {
			BranchInfo branchInfo = new BranchInfo();
			branchInfo.setBranch_id(jsonArray.getJSONObject(i).getInt("id"));
			branchInfo.setBranch_name(jsonArray.getJSONObject(i).getString("name"));
			branchInfo.setArea(jsonArray.getJSONObject(i).getString("area"));
			branchInfo.setPhoneNo(jsonArray.getJSONObject(i).getString("phone"));
			branchInfo.setCity(jsonArray.getJSONObject(i).getString("city"));
			branchArrayList.add(branchInfo);
		}
		return branchArrayList;
		
	}
	
	public static int parseLoginInfo(String response) throws JSONException {
		
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONObject jsonObject = jsonDataObject.getJSONObject("user");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setID(jsonObject.getInt("id"));
		userInfo.setName(jsonObject.getString("name"));
		userInfo.setUsername(jsonObject.getString("username"));
		userInfo.setEmail(jsonObject.getString("email"));
		userInfo.setUser_token(jsonObject.getString("token"));
		userInfo.setMessage(jsonDataObject.getString("message"));
		userInfo.setStatus(jsonData.getInt("status"));
		
		Log.e("Login Response", "-----=== " + response);
		BhojNamaSingleton.getInstance().setUserInfo(userInfo);
		return jsonData.getInt("status");
		
	}
	
	public static int parseRegInfo(String response) throws JSONException {
		
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONObject jsonObject = jsonDataObject.getJSONObject("user");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setName(jsonObject.getString("name"));
		userInfo.setUsername(jsonObject.getString("username"));
		userInfo.setEmail(jsonObject.getString("email"));
		userInfo.setMessage(jsonDataObject.getString("message"));
		userInfo.setStatus(jsonData.getInt("status"));
		
		Log.e("Registration Response:", "-----=== " + response);
		BhojNamaSingleton.getInstance().setUserInfo(userInfo);
		return jsonData.getInt("status");
		
	}
	
	public static int parsePostComments(String response) throws JSONException {
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		return jsonData.getInt("status");
		
	}
	
	public static ArrayList<LocationCityInfo> parseLocationCity(String response) throws JSONException {
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONArray jsonCityObject = jsonDataObject.getJSONArray("city");
		int length = jsonCityObject.length();
		
		ArrayList<LocationCityInfo> arrayListLocationCity = new ArrayList<LocationCityInfo>();
		for (int i = 0; i < length; i++) {
			LocationCityInfo locatinCityData = new LocationCityInfo();
			locatinCityData.setCityId(jsonCityObject.getJSONObject(i).getInt("id"));
			locatinCityData.setCityName(jsonCityObject.getJSONObject(i).getString("name"));
			arrayListLocationCity.add(locatinCityData);
		}
		return arrayListLocationCity;
		
	}
	
	public static ArrayList<LocationAreaInfo> parseLocationArea(String response) throws JSONException {
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONArray jsonCityObject = jsonDataObject.getJSONArray("areas");
		int length = jsonCityObject.length();
		
		ArrayList<LocationAreaInfo> arrayListLocationArea = new ArrayList<LocationAreaInfo>();
		for (int i = 0; i < length; i++) {
			LocationAreaInfo locationArea = new LocationAreaInfo();
			locationArea.setAreaId(jsonCityObject.getJSONObject(i).getInt("id"));
			locationArea.setAreaName(jsonCityObject.getJSONObject(i).getString("name"));
			arrayListLocationArea.add(locationArea);
		}
		return arrayListLocationArea;
		
	}
	
	public static void parseReview(String response, int currentArraySize) throws JSONException {
		JSONObject jsonData = new JSONObject(response);
		JSONObject jsonDataObject = jsonData.getJSONObject("data");
		JSONObject jsonPagignatorObject = jsonData.getJSONObject("paginator");
		
		BhojNamaSingleton.getInstance().setTotalPages(jsonPagignatorObject.getInt("totalPages"));
		
		JSONArray jsonArray = jsonDataObject.getJSONArray("foodShot");
		
		int foodShotSize = jsonArray.length();
		if (foodShotSize == 0) {
			return;
		}
		
		for (int i = 0; i < foodShotSize; i++) {
			ReviewInfo reviewInfo = new ReviewInfo();
			JSONArray jsonArrayComments = jsonArray.getJSONObject(i).getJSONArray("comments");
			int commentsArraySize = jsonArrayComments.length();
			for (int j = 0; j < commentsArraySize; j++) {
				reviewInfo.setId(jsonArrayComments.getJSONObject(j).getInt("id"));
				reviewInfo.setTitle(jsonArrayComments.getJSONObject(j).getString("title"));
				reviewInfo.setDetails(jsonArrayComments.getJSONObject(j).getString("detail"));
				reviewInfo.setPulishedDate(jsonArrayComments.getJSONObject(j).getString("publishedDate"));
				reviewInfo.setAuthor(jsonArrayComments.getJSONObject(j).getInt("author"));
			}
			
			BhojNamaSingleton.getInstance().getArrayListReviewInfo().add(reviewInfo);
		}
		
	}
	
	public static void parseReviewtData(String response) throws JSONException {
		String logindata;
		JSONObject jDataObj = new JSONObject(response);
		logindata = jDataObj.getString("data");
		
		//JSONArray jsonResultArray = jDataObj.getJSONArray("restaurant");
		JSONObject jsonResultRestObj = jDataObj.getJSONObject("data").getJSONObject("restaurant");
		
		
		int resultSize = jsonResultRestObj.length();
		
		//JSONArray branchArray = jsonResultArray.getJSONObject(i).getJSONArray("branches");
		JSONArray reviewArray = jsonResultRestObj.getJSONArray("review");
		
		
		//int brachSize = jsonResultRestObj.getJSONObject(i).getJSONArray("branches").length();
		
		int reivewSize= reviewArray.length();
		
		ArrayList<ReviewInfo> arrayListFoodShotComments = new ArrayList<ReviewInfo>();
		
		for (int j2 = 0; j2 < reivewSize; j2++) {
			
			ReviewInfo review = new ReviewInfo();
			review.setId(reviewArray.getJSONObject(j2).getInt("id"));
			review.setTitle(reviewArray.getJSONObject(j2).getString("title"));
			review.setDetails(reviewArray.getJSONObject(j2).getString("detail"));
			review.setAuthor(reviewArray.getJSONObject(j2).getJSONObject("author").getInt("id"));
			review.setAuthor_name(reviewArray.getJSONObject(j2).getJSONObject("author").getString("name"));
			
			arrayListFoodShotComments.add(review);
		}
		
		/*for (int j = 0; j < brachSize; j++) {
			Log.e("Restaurant Branch HOT", "-----" + branchArray.getJSONObject(j).getString("city"));
			restaurantInfo.setBranchId(branchArray.getJSONObject(j).getInt("id"));
			restaurantInfo.setIsOpen(branchArray.getJSONObject(j).getInt("is_open"));
			restaurantInfo.setOpeningHour(branchArray.getJSONObject(j).getString("opening_hour"));
			restaurantInfo.setCity(branchArray.getJSONObject(j).getString("city"));
			
			restaurantInfo.setArea(branchArray.getJSONObject(j).getString("area"));
			restaurantInfo.setLat(branchArray.getJSONObject(j).getDouble("latitude"));
			restaurantInfo.setLon(branchArray.getJSONObject(j).getDouble("longitude"));
			}*/
		
		//restaurantInfo.setArrayListReviewInfo(arrayListFoodShotComments);
		//hottestInfo.set(arrayListFoodShotComments);
		//listHottestInfo.add(hottestInfo);
		//BhojNamaSingleton.getInstance().getHottestInfoList().add(hottestInfo);
		
		BhojNamaSingleton.getInstance().setArrayListReviewInfo(arrayListFoodShotComments);	
		
		/*for (int i = 0; i < resultSize; i++) {
			HottestInfo restaurantInfo = new HottestInfo();
			
				restaurantInfo.setRestaurantId(jsonResultArray.getJSONObject(i).getInt("id"));
				restaurantInfo.setRestaurantName(jsonResultArray.getJSONObject(i).getString("name"));
				restaurantInfo.setRestaurantAbout(jsonResultArray.getJSONObject(i).getString("about"));
				
				restaurantInfo.setLogo(jsonResultArray.getJSONObject(i).getString("logo"));
				
				restaurantInfo.setLikes(jsonResultArray.getJSONObject(i).getInt("likes"));
				
				
			
		}*/
		
		
		//BhojNamaSingleton.getInstance().getHottestInfoList().add;
		//BhojNamaSingleton.getInstance().setArrayListNearByInfo(listNearByInfo);
		
	}
	
	
	public static void parseNearbyResData(String response) throws JSONException {
		String logindata;
		
		JSONObject jDataObj = new JSONObject(response);
		logindata = jDataObj.getString("data");
		
		//JSONArray jsonResultArray = jDataObj.getJSONArray("restaurant");
		JSONArray jsonResultArray = jDataObj.getJSONObject("data").getJSONArray("restaurant");
		int resultSize = jsonResultArray.length();
		
		for (int i = 0; i < resultSize; i++) {
			ArrayList<HottestFoodItemInfo> listHottestItem = new ArrayList<HottestFoodItemInfo>();
			NearbyResInfo nearInfo = new NearbyResInfo();
			//if(jsonResultArray.getJSONObject(i).getInt("hottest")==1){
			nearInfo.setRestaurantId(jsonResultArray.getJSONObject(i).getInt("id"));
			nearInfo.setRestaurantName(jsonResultArray.getJSONObject(i).getString("name"));
			nearInfo.setRestaurantAbout(jsonResultArray.getJSONObject(i).getString("about"));
			nearInfo.setLogo(jsonResultArray.getJSONObject(i).getString("logo"));
			nearInfo.setLikes(jsonResultArray.getJSONObject(i).getInt("likes"));
			nearInfo.setReview_count(jsonResultArray.getJSONObject(i).getInt("review_count"));	
			JSONArray branchArray = jsonResultArray.getJSONObject(i).getJSONArray("branches");
				
			JSONArray itemArray = jsonResultArray.getJSONObject(i).getJSONArray("items");
				
				int itemSize = itemArray.length();
				for (int j2 = 0; j2 < itemSize; j2++) {
					
					HottestFoodItemInfo hotFoodItem = new HottestFoodItemInfo();
					Log.e("Restaurant Price", "-----" + itemArray.getJSONObject(j2).getString("price"));
					hotFoodItem.setFoodItemId(itemArray.getJSONObject(j2).getInt("id"));
					hotFoodItem.setFoodTitle(itemArray.getJSONObject(j2).getString("title"));
					hotFoodItem.setDescription(itemArray.getJSONObject(j2).getString("description"));
					hotFoodItem.setPrice(itemArray.getJSONObject(j2).getString("price"));
					listHottestItem.add(hotFoodItem);
				}
				
				int brachSize = jsonResultArray.getJSONObject(i).getJSONArray("branches").length();
				for (int j = 0; j < brachSize; j++) {
					Log.e("Restaurant Branch HOT", "-----" + branchArray.getJSONObject(j).getString("city"));
					nearInfo.setBranchId(branchArray.getJSONObject(j).getInt("id"));
					nearInfo.setIsOpen(branchArray.getJSONObject(j).getInt("is_open"));
					nearInfo.setOpeningHour(branchArray.getJSONObject(j).getString("opening_hour"));
					nearInfo.setCity(branchArray.getJSONObject(j).getJSONObject("city").getString("name"));
					
					nearInfo.setArea(branchArray.getJSONObject(j).getJSONObject("area").getString("name"));
					nearInfo.setLat(branchArray.getJSONObject(j).getDouble("latitude"));
					nearInfo.setLon(branchArray.getJSONObject(j).getDouble("longitude"));
				}
				
				nearInfo.setHottestFoodItemList(listHottestItem);
				//listHottestInfo.add(hottestInfo);
				BhojNamaSingleton.getInstance().getArrayListNearByResInfo().add(nearInfo);
			//}
		}
		
		//BhojNamaSingleton.getInstance().getHottestInfoList().add;
		//BhojNamaSingleton.getInstance().setArrayListNearByInfo(listNearByInfo);
		
	}
	
	public static int parseLikedata(String response) throws JSONException{
		
		JSONObject jDataObj = new JSONObject(response);
		
		int status=jDataObj.getInt("status") ;
		
		return status;
		
		
	}
	
	

	
}
