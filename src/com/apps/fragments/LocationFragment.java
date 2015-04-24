package com.apps.fragments;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
import com.apps.bhojnama.LocationBasedRestaurantActivity;
import com.apps.bhojnama.R;
import com.apps.datamodel.LocationAreaInfo;
import com.apps.datamodel.LocationCityInfo;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;

public class LocationFragment extends Fragment implements OnItemSelectedListener, OnClickListener {

	private View rootView;
	private Spinner spinner, spinner1;
	private Button btnFindRestaurant;
	private static int LOAD_CITY = 1;
	private static int LOAD_BRANCH = 2;
	private ArrayList<LocationCityInfo> arrayListLocationCity;
	private ArrayList<LocationAreaInfo> arrayListArea;
	private int positionCity = 0;
	private int positionArea = 0;
	public LocationFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_location, container, false);
		initView();
		setListener();
		populateSpinnerList(ConstantValue.BASE_URL_LOCATION , LOAD_CITY);
		return rootView;
	}
	
	/**Spinner click listener*/
	private void setListener() {
        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);
        btnFindRestaurant.setOnClickListener(this);
	}

	private void initView() {
		spinner = (Spinner)rootView.findViewById(R.id.choose_city);
		spinner1 = (Spinner)rootView.findViewById(R.id.choose_area);
		btnFindRestaurant = (Button)rootView.findViewById(R.id.btnFindRestaurant);
	}


	private void populateSpinnerList(String URL, final int list_type) {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
    	
    	String url = URL;
    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() {
    	        @Override
    	        public void onResponse(String response) {
    	        	try {
    	        		if (list_type == LOAD_CITY) {
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
	
	protected void loadRestaurantInfo(String response) {
		try {
			arrayListLocationCity = new ArrayList<LocationCityInfo>();
			arrayListLocationCity = JsonParser.parseLocationCity(response);

			ArrayList<String> cities = new ArrayList<String>();
			for (int i = 0; i < arrayListLocationCity.size(); i++) {
				cities.add(arrayListLocationCity.get(i).getCityName());
			}

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cities);
			spinner.setAdapter(dataAdapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void loadBranch(String response) {
		//final Spinner spinner = (Spinner)rootView.findViewById(R.id.choose_branch);
    	try {
    		arrayListArea = new ArrayList<LocationAreaInfo>();
    		arrayListArea = JsonParser.parseLocationArea(response);
			//JsonParser.parseHottestData(response);
			ArrayList<String> categories = new ArrayList<String>();
			for (int i=0; i < arrayListArea.size();i++){
				categories.add(arrayListArea.get(i).getAreaName());
			}
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
			spinner1.setAdapter(dataAdapter);
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Spinner spinner = (Spinner) parent;
	    if(spinner.getId() == R.id.choose_city) {
	    	int gridInteger = (int) spinner.getItemIdAtPosition(position);
			if(gridInteger == spinner.getSelectedItemPosition()){
				positionCity = arrayListLocationCity.get(position).getCityId();
				populateSpinnerList(ConstantValue.BASE_URL_LOCATION + arrayListLocationCity.get(position).getCityId(), LOAD_BRANCH);
			}
	    } else if(spinner.getId() == R.id.choose_area) {
	        int gridInteger = (int) spinner1.getItemIdAtPosition(position);
			if(gridInteger == spinner1.getSelectedItemPosition()){
				positionArea = arrayListArea.get(gridInteger).getAreaId();
				//populateSpinnerList(ConstantValue.BASE_URL_LOCATION + arrayListLocationCity.get(position).getCityId(), LOAD_BRANCH);
			}
	    }
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnFindRestaurant:
			Intent intent = new Intent(getActivity(), LocationBasedRestaurantActivity.class);
			intent.putExtra("cityId", positionCity);
			intent.putExtra("areaId", positionArea);
			
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
}
