package com.apps.bhojnama;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.customviews.CustomProgressDialog;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;

public class SignUpActivity extends Activity implements OnClickListener{
	public Dialog dialogUploadImage;
	private Uri mImageCaptureUri;
	private ImageView imgViewPick;
	private Button btnSubmit;
	private ProgressBar progBarHottestList;
	private ProgressDialog progress;
	private EditText editTextFirstName, editTextUserName, editTextEmail, editTextPassword;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	private String  selectedImagePath = null;
	
	@Override
	protected void onCreate(Bundle splash) {
		super.onCreate(splash);
		setContentView(R.layout.activity_signup);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initViews();
		setListener();
	}
	
	private void setListener() {
		btnSubmit.setOnClickListener(this);
	}

	private void initViews() {
		imgViewPick = (ImageView) findViewById(R.id.image_view_btn_pick);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		
		editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
		editTextUserName = (EditText) findViewById(R.id.editTextUserName);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		 SharedPref sharedPref = new SharedPref(SignUpActivity.this);
		
		//Log.e("email", sharedPref.getEmail());
		
		if(sharedPref.getEmail()!="" && sharedPref.getUserName()!="" && sharedPref.getFirstName()!=""){
			editTextEmail.setText(sharedPref.getEmail().toString());
			editTextFirstName.setText(sharedPref.getFirstName().toString());
			editTextUserName.setText(sharedPref.getUserName().toString());
		}
		
		
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnSubmit) {
			//initUploadImageOptionUI();
			signUp();
		} else if (v.getId() == R.id.btn_make_food_shots) {
			//postFoodShot();
		}
		
	}
	
	
	private void signUp() {
		RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
		StringRequest myReq = new StringRequest(Method.POST, ConstantValue.BASE_SIGNUP_API, createMyReqSuccessListener(), createMyReqErrorListener()) {

			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", editTextFirstName.getText().toString().trim());
				params.put("name", editTextUserName.getText().toString().trim());
				params.put("email", editTextEmail.getText().toString().trim());
				params.put("password", editTextPassword.getText().toString().trim());
				return params;
			};
		};
        
        int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	myReq.setRetryPolicy(policy);
        queue.add(myReq);
        
        progress = new CustomProgressDialog(this);
		progress.show();
	}
		
	private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	progress.dismiss();
            	Log.e("SignUp Response", "*****" + response);
            	try {
            		int status = JsonParser.parseRegInfo(response);
					if (status == 1) {
						Toast.makeText(SignUpActivity.this, "You signup process complete", Toast.LENGTH_SHORT).show();
						
					} else {
						Toast.makeText(SignUpActivity.this, "Username or password is not correct", Toast.LENGTH_SHORT).show();
						
					} 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            	
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	progress.dismiss();
            	Toast.makeText(SignUpActivity.this, "Server Problem", Toast.LENGTH_SHORT).show();
            }
        };
    }
	
	private void resetInputText() {
		editTextEmail.setText("");
		editTextFirstName.setText("");
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home: 
            // API 5+ solution
            onBackPressed();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
}
