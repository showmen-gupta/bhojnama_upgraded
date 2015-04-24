package com.apps.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.adapter.NearbyAdapter;
import com.apps.bhojnama.NearbyMapDirectionActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.NearbyInfo;
import com.apps.datamodel.Nearbylist;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.GPSTracker;

public class NearbyFragment extends Fragment implements OnItemClickListener,OnClickListener {

	ImageView ivIcon;
	TextView tvItemName;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	public static final String GOOGLE_API_KEY = "AIzaSyCe07HODubxXpJWTCpgWTlBFkRMBwrsPj4";
	public static String URL = "";

	public NearbyFragment() {}
	private View rootView;
	private List<Nearbylist> myNearby= new ArrayList<Nearbylist>();
	ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_nearby_list, container, false);
		listview =(ListView) rootView.findViewById(R.id.nearbyList);
		URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + new GPSTracker(getActivity()).getLatitude() +"," + new GPSTracker(getActivity()).getLongitude() +"&radius=500&types=restaurant&key=AIzaSyCe07HODubxXpJWTCpgWTlBFkRMBwrsPj4";
        populateNearbyList();
        populateListView();
        //populateNearbyList();
        return rootView;
	}

	private void populateListView() {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
    	
    	String url = URL;
    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() 
    	    {
    	        @Override
    	        public void onResponse(String response) {
    	            // response
					try {
						ArrayList<NearbyInfo> nearByList = new ArrayList<NearbyInfo>();
						BhojNamaSingleton.getInstance().setArrayListNearByInfo(nearByList);
						JsonParser.parseNearbyData(response);
						populateNearbyList();
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
    	             // error.
    	         }
    	    }
    	);
    	
    	int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	dr.setRetryPolicy(policy);
    	queue.add(dr);
    	Log.i("TEXT","");
		
	}

	private void populateNearbyList() {
		listview.setOnItemClickListener(this);
		listview.setAdapter(new NearbyAdapter(getActivity(), BhojNamaSingleton.getInstance().getArrayListNearByInfo()));
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Intent intent = new Intent(getActivity(), NearbyMapDirectionActivity.class);
		intent.putExtra("list_position", position);
		intent.putExtra("view_type", "nearby");
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
