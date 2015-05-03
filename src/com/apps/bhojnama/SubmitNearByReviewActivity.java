package com.apps.bhojnama;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



import android.app.Activity;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.apps.bhojnama.R;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.utility.ConstantValue;

public class SubmitNearByReviewActivity extends Activity implements OnClickListener {
	
	EditText title, description;
	Button submit;
	private int position,list_position;
	String timeStamp = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Calendar.getInstance().getTime());

	public SubmitNearByReviewActivity() {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);
		initView();
//		loadData();
		setListener();
	}
	
	private void setListener() {
		submit.setOnClickListener(this);
	}
	
	private void initView(){
	
		title= (EditText) findViewById(R.id.title);
		description= (EditText) findViewById(R.id.description);
		submit= (Button) findViewById(R.id.submit);
		position  = getIntent().getExtras().getInt("position");
		list_position=getIntent().getExtras().getInt("list_position");
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case  R.id.submit:
			postReview();
			break;
		default:
			break;
		}
		
	}
	
	ProgressDialog progress;
	
	private void postReview() {
		Log.e("res_id", ""+BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(list_position).getRestaurantId());
		Log.e("time", timeStamp);
		RequestQueue queue = Volley.newRequestQueue(SubmitNearByReviewActivity.this);
		final SharedPref sharedPref = new SharedPref(this);
		Log.e("user_id", ""+sharedPref.getUserID());
		Log.e("token",""+sharedPref.getUserToken());
		
		if(title.getText().toString().matches("") && description.getText().toString().matches("")){
			Toast.makeText(SubmitNearByReviewActivity.this, "fields can't be empty", Toast.LENGTH_SHORT).show();	
			
		}
		else{
		StringRequest myReq = new StringRequest(Method.POST, ConstantValue.BASE_URL_USER_REVIEW + BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(list_position).getRestaurantId() + "/review" , createMyReqSuccessListener(), createMyReqErrorListener()) {
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				
				params.put("Content-Type", "application/json; charset=utf-8");
				params.put("title", title.getText().toString().trim());
				params.put("detail", description.getText().toString().trim());
				params.put("published_on", timeStamp);
				params.put("user_id", sharedPref.getUserID());
				params.put("token", sharedPref.getUserToken());
				params.put("status", "1");
				
//				params.put("Content-Type", "application/json; charset=utf-8");
//				params.put("title", "Text");
//				params.put("detail", "This is another text");
//				params.put("published_on", "2015-2-15 04:02:15");
//				params.put("status", "1");
				return params;
			};
		};
		
        
        int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	myReq.setRetryPolicy(policy);
        queue.add(myReq);
        
        progress = new ProgressDialog(SubmitNearByReviewActivity.this);
        progress.setMessage("Please wait....");
        progress.show();
		}
	}
	
	private ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Toast.makeText(SubmitNearByReviewActivity.this, "Server Response Error", Toast.LENGTH_SHORT).show();
            }
        };
	}

	private Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	progress.dismiss();
            	Log.e("Login Test", "*****" + response);
            	try {
					//updateCommentArray();
					Toast.makeText(SubmitNearByReviewActivity.this, "Your review has been posted!", Toast.LENGTH_SHORT).show();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
            	
            }

        };
	}

}
