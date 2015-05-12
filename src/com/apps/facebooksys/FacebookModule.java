package com.apps.facebooksys;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apps.bhojnama.SignUpActivity;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

public class FacebookModule {
	private Activity activityContext;
	private ProgressDialog dlog;
	private SharedPref sharedPref;
	public FacebookModule(Activity activity, Bundle savedInstanceState) {
		this.activityContext = activity;
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {}
			if (session == null) {session = new Session(activityContext);}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {}
		}
	}
	
	public void facebookLogin(){
		Session s = new Session(activityContext);
		Session.setActiveSession(s);
		Session.OpenRequest request = new Session.OpenRequest(activityContext);
		request.setPermissions(Arrays.asList("basic_info","email"));
		request.setCallback( new Session.StatusCallback() {
             @Override
             public void call(Session session, SessionState state, Exception exception) {
                 if (session.isOpened()) {
                	 dlog = ProgressDialog.show(activityContext, "Log In", "Please wait...", true, false);
                     Request.newMeRequest(session, new Request.GraphUserCallback() {
                         @Override
                         public void onCompleted(GraphUser user, Response response) {
                        	 
                        	 if (dlog.isShowing()) {
                 				dlog.dismiss();
                 			 }
                        	// Toast.makeText(activityContext, "Successfully login", Toast.LENGTH_SHORT).show();
                             if (user != null) {
								try {
									Log.i("Facebook Output Final", "" + response);
									GraphObject go = response.getGraphObject();
									JSONObject jso = go.getInnerJSONObject();
									Log.i("User Name"," " + jso.getString("email"));
									Log.i("FB Id"," " + jso.getString("id"));
									Log.i("FB Profile Pic", "" + "http://graph.facebook.com/" + jso.getString("id") + "/picture?type=normal");
									String fbPic = "http://graph.facebook.com/" + jso.getString("id") + "/picture?type=small";
									Toast.makeText(activityContext, "Successfully logged in with Facebook", Toast.LENGTH_SHORT).show();
									
									String email=jso.getString("email");
									String first_name=jso.getString("first_name");
									
									Class<?> landingClass= SignUpActivity.class;
									activityContext.startActivity(new Intent(activityContext, landingClass));
									sharedPref = new SharedPref(activityContext);
									sharedPref.setEmail(email);
									sharedPref.setFirstName(first_name);
									sharedPref.setUserName(first_name);
									   
									activityContext.finish();
									
									
								} catch (JSONException e) {
									e.printStackTrace();
								}
                             } else {
								Toast.makeText(activityContext, "Error User Null", Toast.LENGTH_SHORT).show();
							}
						}
					}).executeAsync();
                 }
             }
             
		}); // end of call;

		s.openForRead(request); // now do the request above
	}
}
