package com.apps.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Fragment;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.bhojnama.MainActivity;
import com.apps.bhojnama.PostFoodShotsActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnama.RestaurantReviewActivity;
import com.apps.bhojnama.SignUpActivity;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.facebooksys.FacebookModule;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;

public class LogInFragment   extends Fragment implements OnClickListener {
	private int fragment_no = 0;
	private View view;
	private ImageView ivIcon;
	private ImageView fbLogin;
	private TextView tvItemName;
	private EditText edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegSubmit;

	private FacebookModule fb;
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	boolean Islogin;
	
	private SharedPref sharedPref;
	public LogInFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fb = new FacebookModule(getActivity(), savedInstanceState);
		view = inflater.inflate(R.layout.fragment_signup,container, false);
		
		intiView();
		setListener();
		
		return view;
	}
	private void intiView() {
		fragment_no = getArguments().getInt("fragment_no");
		
		sharedPref = new SharedPref(getActivity());
		fbLogin = (ImageView) view.findViewById(R.id.image_view_btn_fb_login);
		edtTextEmail = (EditText) view.findViewById(R.id.edit_text_user);
		edtTextPassword = (EditText) view.findViewById(R.id.edit_text_password);
		btnLogin = (Button) view.findViewById(R.id.btn_login);
		btnRegSubmit = (Button) view.findViewById(R.id.btnRegSubmit);
	}
	
	private void setListener() {
		fbLogin.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnRegSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.image_view_btn_fb_login) {
			 fb.facebookLogin();
			  
		} else if (v.getId() == R.id.btn_login) {
			String email=edtTextEmail.getText().toString();
			String password= edtTextPassword.getText().toString();
			if(email.matches("") && password.matches("")){
				Toast.makeText(getActivity(), "Please Enter Your Email or Password", Toast.LENGTH_SHORT).show();
			}
			else{
				
				 login();	
			}
			
	
			
			 
		} else if (v.getId() == R.id.btnRegSubmit) {
			Intent intent = new Intent(getActivity(), SignUpActivity.class);
			startActivity(intent);
		}
	}
	
	ProgressDialog progress;
	private void login() {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest myReq = new StringRequest(Method.POST, ConstantValue.BASE_LOGIN_API, createMyReqSuccessListener(), createMyReqErrorListener()) {

			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", edtTextEmail.getText().toString().trim());
				params.put("password", edtTextPassword.getText().toString().trim());
				return params;
			};
		};
        
        int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	myReq.setRetryPolicy(policy);
        queue.add(myReq);
        
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait....");
        progress.show();
	}
		
	private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	progress.dismiss();
            	Log.e("Login Test", "*****" + response);
            	try {
            		int status = JsonParser.parseLoginInfo(response);
					if (status == 1) {
						MainActivity.isLogin = true;
						sharedPref.setLoginStatus("1");
						sharedPref.setUserID(Integer.toString(BhojNamaSingleton.getInstance().getUserInfo().getID()));
						sharedPref.setUserToken(BhojNamaSingleton.getInstance().getUserInfo().getUser_token());
						if(fragment_no == 5) {
							
							Intent intent = new Intent(getActivity(), PostFoodShotsActivity.class);
							intent.putExtra("position", 0);
							intent.putExtra("list_position", getArguments().getInt("position"));
							startActivity(intent);
							
							Fragment fragment = null;
							Bundle args = new Bundle();
							args.putInt("position", 0);
							  
							fragment = new FoodShotsFragment();
							fragment.setArguments(args);
							android.app.FragmentManager frgManager = getFragmentManager();
							  
							android.app.FragmentTransaction ft = frgManager.beginTransaction();
							ft.replace(R.id.content_frame, fragment);
							//ft.addToBackStack(null);
							//ft.remove(fragment);
							ft.commit();
							
							//getActivity().onBackPressed();
							
						} else if (fragment_no == 4) {
							Intent intent = new Intent(getActivity(), RestaurantReviewActivity.class);
							//intent.putExtra("position", BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getRestaurantId());
							intent.putExtra("list_position", getArguments().getInt("position"));
							startActivity(intent);
						}
						
						
						Toast.makeText(getActivity(), "You login process complete", Toast.LENGTH_SHORT).show();
						SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("username", BhojNamaSingleton.getInstance().getUserInfo().getUsername());
						Log.e("user_name", ""+BhojNamaSingleton.getInstance().getUserInfo().getUsername());
						//prefsCommit();
					
					} else {
						Toast.makeText(getActivity(), "Username or password is not correct", Toast.LENGTH_SHORT).show();
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
            	Toast.makeText(getActivity(), "Wrong email or password", Toast.LENGTH_SHORT).show();
            	progress.dismiss();
            }
        };
    }
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().setTitle("Sign Up");
	}
	
	private OnBackStackChangedListener getListener() {
	    OnBackStackChangedListener result = new OnBackStackChangedListener() {
	        public void onBackStackChanged() {
	        	getActivity().setTitle("Sign Up");
	            /*FragmentManager manager = getSupportFragmentManager();
	            if (manager != null) {
	                int backStackEntryCount = manager.getBackStackEntryCount();
	                if (backStackEntryCount == 0) {
	                    finish();
	                }
	                Fragment fragment = manager.getFragments()
	                                           .get(backStackEntryCount - 1);
	                fragment.onResume();
	            }*/
	        }
	    };
	    return result;
	}
	
}
