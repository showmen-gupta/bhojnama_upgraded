package com.apps.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.apps.datamodel.NearbyResInfo;
import com.apps.datamodel.Nearbylist;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.GPSTracker;
import com.google.android.gms.maps.model.LatLng;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class NearbyFragment extends Fragment implements OnItemClickListener,OnClickListener {

	ImageView ivIcon;
	TextView tvItemName;
	private ProgressBar progBar;
	private ProgressBar progBarHottestList;
	private int currentPageLimit = 1;
	private boolean isFirstTime = true;
	
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	public static final String GOOGLE_API_KEY = "AIzaSyCe07HODubxXpJWTCpgWTlBFkRMBwrsPj4";
	public static String URL = "";

	public NearbyFragment() {}
	private View rootView;
	private List<Nearbylist> myNearby= new ArrayList<Nearbylist>();
	private ArrayList<NearbyResInfo> myNearbyList = new ArrayList<NearbyResInfo>();
	PullToRefreshListView listview;
	Button showmore;
	GPSTracker gps;
	NearbyAdapter nearbyAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_nearby_list, container, false);
		listview =(PullToRefreshListView) rootView.findViewById(R.id.nearbyList);
		progBar = (ProgressBar) rootView.findViewById(R.id.progBar);
		Toast.makeText(getActivity(), "Please wait for a while,your Nearby Restaurants are Loading!!!", Toast.LENGTH_LONG).show();
		showmore= (Button) rootView.findViewById(R.id.show_more);
		progBarHottestList = (ProgressBar) rootView.findViewById(R.id.progBarHottestList);
		
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf"); 
		showmore.setTypeface(font);

		gps = new GPSTracker(getActivity());
		//URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + new GPSTracker(getActivity()).getLatitude() +"," + new GPSTracker(getActivity()).getLongitude() +"&radius=500&types=restaurant&key=AIzaSyCe07HODubxXpJWTCpgWTlBFkRMBwrsPj4";
        URL = "http://api.bhojnama.com/api/nearby?longitude=" + gps.getLongitude() +"&latitude=" + gps.getLatitude();
		///populateNearbyList();
        setListViewListener();
        listview.setMode(Mode.PULL_FROM_END);
        populateListView(10, 1);
        
        //populateNearbyList();
        return rootView;
	}
	
	private void setListViewListener() {
		
		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				currentPageLimit += 1;
				populateListView(10, currentPageLimit);
				
			}

		});
		
		
	}


	private void populateListView(int limit, int pageLimit) {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		
    	String url = URL+"&page=" + pageLimit + "&limit=" +limit ;
    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() 
    	    {
    	        @Override
    	        public void onResponse(String response) {
    	            // response
					try {
						progBar.setVisibility(View.GONE);
						//ArrayList<NearbyResInfo> nearByList = new ArrayList<NearbyResInfo>();
						//BhojNamaSingleton.getInstance().setArrayListNearByResInfo(nearByList);
						
						
						if (isFirstTime) {
							isFirstTime = false;
							BhojNamaSingleton.getInstance().setArrayListNearByResInfo(myNearbyList);
							JsonParser.parseNearbyResData(response);
							myNearbyList = BhojNamaSingleton.getInstance().getArrayListNearByResInfo();
							populateNearbyList();
							
							
						} else {
							JsonParser.parseNearbyResData(response);
							//myNearbyList.addAll(BhojNamaSingleton.getInstance().getArrayListNearByResInfo());
							//BhojNamaSingleton.getInstance().setArrayListNearByResInfo(myNearbyList);
							nearbyAdapter.notifyDataSetChanged();
							listview.onRefreshComplete();
						}
						
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
    	        	 progBar.setVisibility(View.GONE);
    	        	 Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_LONG).show();
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
		LatLng latLng = new LatLng(gps.getLatitude(), gps.getLongitude());
		listview.setOnItemClickListener(this);
		nearbyAdapter = new NearbyAdapter(getActivity(), myNearbyList, latLng);
		listview.setAdapter(nearbyAdapter);
		listview.onRefreshComplete();
		progBarHottestList.setVisibility(View.GONE);
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		/*Intent intent = new Intent(getActivity(), NearbyMapDirectionActivity.class);
		intent.putExtra("list_position", position);
		intent.putExtra("view_type", "nearby");
		startActivity(intent);*/
		Fragment fragment = null;
		  Bundle args = new Bundle();
		  args.putInt("position", position-1);
		  fragment = new NearByResDetailsFragment();
		  fragment.setArguments(args);
		  FragmentManager frgManager = getFragmentManager();
		  android.app.FragmentTransaction ft = frgManager.beginTransaction();
		  //ft.hide(getParentFragment());
		  ft.add(R.id.content_frame, fragment);
		  ft.addToBackStack(null);
		  ft.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
