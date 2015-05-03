package com.apps.fragments;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.bhojnama.R;
import com.apps.datamodel.BranchInfo;
import com.apps.datamodel.RestaurantInfo;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;

public class BooktableFragment extends Fragment implements OnItemSelectedListener, OnClickListener {

	ArrayList<RestaurantInfo> restaurantArrayList;
	private Spinner spinner;
	private Spinner spinnerBranch;
	private Button call;
	ArrayList<BranchInfo> branchArrayList;
	private static int LOAD_RESTAURANT = 1;
	private static int LOAD_BRANCH = 2;
	ProgressDialog progress;
	public BooktableFragment() {

	}
	
	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_book_table, container, false);
		
		spinner = (Spinner) rootView.findViewById(R.id.choose_res);
		spinner.setOnItemSelectedListener(this);
		
		spinnerBranch = (Spinner) rootView.findViewById(R.id.choose_branch);
		spinnerBranch.setOnItemSelectedListener(this);
		
		call= (Button) rootView.findViewById(R.id.call);
		call.setOnClickListener(this);
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait....");
        progress.show();
        
		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		backListener();
		populateSpinnerList(ConstantValue.BASE_URL_BOOK_TABLLE , LOAD_RESTAURANT);
		return rootView;
	}
	
	
	private void populateSpinnerList(String URL, final int list_type) {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
    	
    	String url = URL;

    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() {
    	        @Override
    	        public void onResponse(String response) {
    	    		

    	        	try {
    	        		if (list_type == LOAD_RESTAURANT) {
        	        		loadRestaurantInfo(response);
        	        	} else if (list_type == LOAD_BRANCH) {
        	        		loadBranch(response);
        	        	}
    	        	} catch (Exception e) {
    	        		
    	        	}
    	        	

    	           
    	        }
    	    }, 
    	    new Response.ErrorListener() 
    	    {
    	         @Override
    	         public void onErrorResponse(VolleyError error) {
    	             // error.
    	         }
    	    }
    	);
    	
    	int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	dr.setRetryPolicy(policy);
    	queue.add(dr);
	}
	private void loadBranch(String response) {
		//final Spinner spinner = (Spinner)rootView.findViewById(R.id.choose_branch);
  
    	try {
    		branchArrayList = new ArrayList<BranchInfo>();
    		branchArrayList = JsonParser.parseBranchList(response);
			//JsonParser.parseHottestData(response);
			ArrayList<String> categories = new ArrayList<String>();
			
			for (int i=0; i < branchArrayList.size();i++){
				categories.add(branchArrayList.get(i).getArea());
			}
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
			spinnerBranch.setAdapter(dataAdapter);
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
	}
	
	protected void loadRestaurantInfo(String response) {

      
    	try {
    		restaurantArrayList = new ArrayList<RestaurantInfo>();
    		restaurantArrayList = JsonParser.parseRestaurantList(response);
			ArrayList<String> categories = new ArrayList<String>();
			
			for (int i=0; i < restaurantArrayList.size();i++){
				categories.add(restaurantArrayList.get(i).getRes_name());
			}
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
			spinner.setAdapter(dataAdapter);
    	} catch (JSONException e) {
			e.printStackTrace();
		}
    	progress.dismiss();
	}
	
	private void backListener() {
		rootView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				getActivity().setTitle("Restaurant details");
				return false;
			}
		});
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		int gridInteger = (int) spinner.getItemIdAtPosition(position);
		if(gridInteger == spinner.getSelectedItemPosition()){
			populateSpinnerList(ConstantValue.BASE_BRANCH_API + restaurantArrayList.get(position).getRes_id(), LOAD_BRANCH);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	@Override
	public void onClick(View arg0) {
		for (int i = 0; i < branchArrayList.size(); i++) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + branchArrayList.get(i).getPhoneNo()));
			startActivity(intent);	
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle("Book a table");
	}

}
