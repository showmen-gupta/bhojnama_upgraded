package com.apps.bhojnamainfo;

import java.util.ArrayList;

import com.apps.datamodel.FoodShotsInfo;
import com.apps.datamodel.HottestFoodItemInfo;
import com.apps.datamodel.HottestInfo;
import com.apps.datamodel.NearbyInfo;
import com.apps.datamodel.ReviewInfo;
import com.apps.datamodel.UserInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BhojNamaSingleton {

	//private String baseUrl = "http://114.134.91.91/sidra_pull/api/json";
	private ArrayList<NearbyInfo> arrayListNearByInfo;
	private ArrayList<HottestInfo> hottestInfoList;
	private ArrayList<FoodShotsInfo> arrayListFoodShots;
	private ArrayList<UserInfo> arrayListUserInfo;
	private ArrayList<ReviewInfo> arrayListReviewInfo;
	private ArrayList<HottestFoodItemInfo> arrayListFoodInfo;
	
	
	private UserInfo userInfo;
	private int totalPages;
	public static BhojNamaSingleton instance;

	public static BhojNamaSingleton getInstance() {
		if (instance == null) {
			instance = new BhojNamaSingleton();
		}
		
		return instance;
	}

	public static void destroyInstance() {
		instance = null;
	}

	public void hideKeyboard(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public void openInternetSettingsActivity(final Context context) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Internet Problem");
		alert.setMessage("No internet connection. Please connect to a network first.");
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			}
		});

		alert.show();
	}
	
	public void openErrorDialog(String err_msg, Context context) {
		err_msg = Html.fromHtml(err_msg).toString();
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(err_msg);
		alert.setCancelable(true);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
			}
		});
		alert.show();
	}

	public ArrayList<NearbyInfo> getArrayListNearByInfo() {
		return arrayListNearByInfo;
	}

	public void setArrayListNearByInfo(ArrayList<NearbyInfo> arrayListNearByInfo) {
		this.arrayListNearByInfo = arrayListNearByInfo;
	}

	public ArrayList<HottestInfo> getHottestInfoList() {
		return hottestInfoList;
	}
	


	public void setHottestInfoList(ArrayList<HottestInfo> hottestInfoList) {
		this.hottestInfoList = hottestInfoList;
	}

	public ArrayList<FoodShotsInfo> getArrayListFoodShots() {
		return arrayListFoodShots;
	}

	public void setArrayListFoodShots(ArrayList<FoodShotsInfo> arrayListFoodShots) {
		this.arrayListFoodShots = arrayListFoodShots;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public ArrayList<ReviewInfo> getArrayListReviewInfo() {
		return arrayListReviewInfo;
	}

	public void setArrayListReviewInfo(ArrayList<ReviewInfo> arrayListReviewInfo) {
		this.arrayListReviewInfo = arrayListReviewInfo;
	}

	public ArrayList<HottestFoodItemInfo> getArrayListFoodInfo() {
		return arrayListFoodInfo;
	}

	public void setArrayListFoodInfo(ArrayList<HottestFoodItemInfo> arrayListFoodInfo) {
		this.arrayListFoodInfo = arrayListFoodInfo;
	}
	
}
