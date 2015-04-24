package com.apps.bhojnama;


import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.adapter.HottestAdapter;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestInfo;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;
import com.apps.utility.GPSTracker;
import com.google.android.gms.maps.model.LatLng;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class LocationBasedRestaurantActivity extends Activity implements OnItemClickListener{
	private int cityId = 0;
	private int areaId = 0;
	private View rootView;
	private ProgressBar progBarHottestList;
	private PullToRefreshListView listview;
	private HottestAdapter hottestAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hottest);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setTitle("Restaurant Search List");
		intiView();
		setListener();
		loadData();
	}

	private void loadData() {
		cityId = getIntent().getExtras().getInt("cityId");
		areaId = getIntent().getExtras().getInt("areaId");
		ArrayList<HottestInfo> hottestRestaurentList = new ArrayList<HottestInfo>();
		BhojNamaSingleton.getInstance().setHottestInfoList(hottestRestaurentList);
		GPSTracker gps = new GPSTracker(this);
		LatLng clatLng = new LatLng(gps.getLatitude(), gps.getLongitude());
	    hottestAdapter = new HottestAdapter(this, BhojNamaSingleton.getInstance().getHottestInfoList(), clatLng);
		populatedRestaurantList(ConstantValue.BASE_URL_LOCATION + cityId + "/" + "area" + "/" + areaId + "/restaurants");
	}

	private void setListener() {
		listview.setOnItemClickListener(this);
	}

	private void intiView() {
		listview =(PullToRefreshListView) findViewById(R.id.restaurant_list);
	    listview.setMode(Mode.PULL_FROM_END);
	    progBarHottestList = (ProgressBar) findViewById(R.id.progBar);
	}
	
	private void populatedRestaurantList(String URL) {
		RequestQueue queue = Volley.newRequestQueue(this);
    	String url = URL;
    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() {
    	        @Override
    	        public void onResponse(String response) {
    	        	try {
						JsonParser.parseLocBasedRestaurant(response);
						listview.setAdapter(hottestAdapter);
						listview.onRefreshComplete();
						progBarHottestList.setVisibility(View.GONE);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
    	        }
    	    }, 
    	    new Response.ErrorListener() 
    	    {
    	         @Override
    	         public void onErrorResponse(VolleyError error) {
    	        	 progBarHottestList.setVisibility(View.GONE);
    	         }
    	    }
    	);
    	
    	int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	dr.setRetryPolicy(policy);
    	queue.add(dr);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		/*Fragment fragment = null;
		Bundle args = new Bundle();
		args.putInt("position", position);
		  
		fragment = new RestaurantDetailsFragment();
		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		setTitle("Restaurant Deatils");*/
		Intent intent = new Intent(this, RestaurantDetailsActivity.class);
		intent.putExtra("position", position - 1);
		startActivity(intent);
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
