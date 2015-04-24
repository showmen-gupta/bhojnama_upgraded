package com.apps.jsonparser;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.apps.utility.ConstantValue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class UploadImageAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnImageUploadComplete callback;
	private int responseStatus;
	boolean isClassified = false;
	private String uploadedPhotoName = ""; 
	private String thisdata = "";

	public UploadImageAsyncTask(Activity x, OnImageUploadComplete callback2, boolean isClassified) {
		this.activity = x;
		this.callback = (OnImageUploadComplete) callback2;
		this.isClassified = isClassified;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... params) {
		Log.i("Food Shot review", "****" + params);
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(ConstantValue.BASE_URL);
	
				MultipartEntity reqEntry = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				
				FileBody bin = new FileBody(new File(params[2]), "image/jpeg");
				
				reqEntry.addPart("title", new StringBody(params[0]));
				reqEntry.addPart("detail", new StringBody(params[1]));
				reqEntry.addPart("published_on", new StringBody("2015-02-01 02:05:10"));
				
				//reqEntry.addPart("photos", bin);
				reqEntry.addPart("user_id", new StringBody("1"));
				reqEntry.addPart("status", new StringBody("1"));
				reqEntry.addPart("taken-on", new StringBody("2015-02-01 02:05:10"));

				httppost.setEntity(reqEntry);
	
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity resEntity = response.getEntity();
	
				InputStream is = resEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	
				StringBuilder sb = new StringBuilder();
				String line = null;
	
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
	
				is.close();
				thisdata = sb.toString().trim();
				
				Log.i("Photo Uplaod Response", "*****" + thisdata);
				/*JSONObject jDataObj = new JSONObject(thisdata);
				responseStatus = jDataObj.getInt("status");
				JSONObject jDataPhoto = jDataObj.getJSONObject("data");
				uploadedPhotoName = jDataPhoto.getString("photo_name");*/
			} catch (Exception ex) {
				return null;
			}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Log.e("uploaded photo name", uploadedPhotoName);
		callback.OnResponse(thisdata);
		
	}
	
}

