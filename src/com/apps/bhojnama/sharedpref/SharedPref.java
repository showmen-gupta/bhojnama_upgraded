package com.apps.bhojnama.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {
	
	Context mContext;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor spEditor;
	
	private final String USER_ID = "user_id";
	private final String LOG_IN_STATUS = "log_in_status";
	private final String USER_TOKEN="user_token";
	private final String USER_NAME="user_name";
	private final String FIRST_NAME="first_name";
	private final String EMAIL="email";
	
	public SharedPref(Context mContext) {
		super();
		this.mContext = mContext;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	public String getUserID() {
		return sharedPreferences.getString(USER_ID, "");  
	}
	
	public void setUserID(String userID) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(USER_ID, userID);
		spEditor.commit();
	}
	
	public String getLoginStatus() {
		return sharedPreferences.getString(LOG_IN_STATUS, "0");
	}
	
	public void setLoginStatus(String status) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(LOG_IN_STATUS, status);
		spEditor.commit();
	}
	
	public String getUserToken() {
		return sharedPreferences.getString(USER_TOKEN, "");  
	}
	
	public void setUserToken(String userToken) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(USER_TOKEN, userToken);
		spEditor.commit();
	}
	
	public String getUserName() {
		return sharedPreferences.getString(USER_NAME, "");  
	}
	
	public void setUserName(String userName) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(USER_NAME, userName);
		spEditor.commit();
	}
	
	public String getFirstName() {
		return sharedPreferences.getString(FIRST_NAME, "");  
	}
	
	public void setFirstName(String firstName) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(FIRST_NAME, firstName);
		spEditor.commit();
	}
	
	public String getEmail() {
		return sharedPreferences.getString(EMAIL, "");  
	}
	
	public void setEmail(String email) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(EMAIL, email);
		spEditor.commit();
	}

	
	
}