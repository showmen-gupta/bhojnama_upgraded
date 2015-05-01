package com.apps.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
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
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestInfo;
import com.apps.datamodel.hottestlist;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.GPSTracker;
import com.google.android.gms.maps.model.LatLng;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HottestFragment extends Fragment implements OnItemClickListener{
	
	public HottestFragment(){}
	private View rootView;
	private ProgressBar progBarHottestList;
	private PullToRefreshListView listview;
	private int currentPageLimit = 1;
	private List<hottestlist> myHottest= new ArrayList<hottestlist>();
	private HottestAdapter hottestAdapter;
	private Button showmore;
	@Override
	public void onAttach(Activity activity) {
		Log.i("Hootest attach", "ok....");
		super.onAttach(activity);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("Hootest activity created", "ok....");
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onDetach() {
		Log.i("Hootest Detach", "ok....");
		super.onDetach();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hottest, container, false);
        Log.i("Hootest onCreate", "ok....");
        initView(rootView);
        loadData();
        setListViewListener();
        return rootView;
    }

	private void loadData() {
		ArrayList<HottestInfo> hottestRestaurentList = new ArrayList<HottestInfo>();
		BhojNamaSingleton.getInstance().setHottestInfoList(hottestRestaurentList);
		GPSTracker gps = new GPSTracker(getActivity());
		LatLng clatLng = new LatLng(gps.getLatitude(), gps.getLongitude());
	    hottestAdapter = new HottestAdapter(getActivity(), BhojNamaSingleton.getInstance().getHottestInfoList(), clatLng);
	    populateHottestList(10, 1);
	}

	private void initView(View view) {
		showmore= (Button) rootView.findViewById(R.id.show_more);
		
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf"); 
		showmore.setTypeface(font);
		
		
		
		listview =(PullToRefreshListView) rootView.findViewById(R.id.hottest_list);
	    listview.setMode(Mode.PULL_FROM_END);
	    listview.setOnItemClickListener(this);
		progBarHottestList = (ProgressBar) view.findViewById(R.id.progBarHottestList);
	}

	private void setListViewListener() {
		
		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				currentPageLimit += 1;
				populateHottestList(10, currentPageLimit);
				
			}

		});
		
		
	}

	private void populateHottestList(int limit, int pageLimit) {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
    	
    	String url = "http://api.bhojnama.com/api/hottest-restaurant?limit=" + limit + "&page=" + pageLimit;
    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() 
    	    {
    	        @Override
    	        public void onResponse(String response) {
    	            //response
    	        	Log.e("Response Hottest", "***" + response);
    	        	try {
						JsonParser.parseHottestData(response);
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
    	             // error.
    	         }
    	    }
    	);
    	
    	int socketTimeout = 30000; //30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	dr.setRetryPolicy(policy);
    	queue.add(dr);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
	  Fragment fragment = null;
	  Bundle args = new Bundle();
	  args.putInt("position", position - 1);
	  fragment = new RestaurantDetailsFragment();
	  fragment.setArguments(args);
	  FragmentManager frgManager = getFragmentManager();
	  android.app.FragmentTransaction ft = frgManager.beginTransaction();
	  //ft.hide(getParentFragment());
	  ft.add(R.id.content_frame, fragment);
	  ft.addToBackStack(null);
	  ft.commit();
	  //frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	  //getActivity().setTitle("Restaurant Deatils");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i("Hootest Resume", "ok....");
		getActivity().getActionBar().setTitle("Hottest Restaurant");
	}
	
}