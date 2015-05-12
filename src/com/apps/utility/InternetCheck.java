package com.apps.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetCheck {
	public static boolean isNetworkConnected(Activity activity) {
		  ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
		   return false;
		  } else
		   return true;
		 }

}
