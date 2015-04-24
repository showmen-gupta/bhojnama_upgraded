package com.apps.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.adapter.HottestAdapter;
import com.apps.adapter.ReviewListAllAdapter;
import com.apps.bhojnama.R;
import com.apps.bhojnama.R.drawable;
import com.apps.bhojnama.R.id;
import com.apps.bhojnama.R.layout;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestInfo;
import com.apps.datamodel.Userreviewlist;
import com.apps.datamodel.hottestlist;
import com.apps.jsonparser.JsonParser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class UserReviewFragment extends Fragment implements OnClickListener, OnItemClickListener {
//	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
//	public static final String ITEM_NAME = "itemName";

	public UserReviewFragment() {}
	private View rootView;
	private ProgressBar progBarHottestList;
	private PullToRefreshListView listview;
	private int currentPageLimit = 1;
	private List<hottestlist> myHottest= new ArrayList<hottestlist>();
	private ReviewListAllAdapter reviewlist;
	int position;
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
//		reviewList.setAdapter(new ReviewListAllAdapter(getActivity(), BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getArrayListReviewInfo(), getArguments().getInt("position")));
	    reviewlist = new ReviewListAllAdapter(getActivity(), BhojNamaSingleton.getInstance().getArrayListReviewInfo(), position);
	    populateHottestList(10, 1);
	    //reviewList.setAdapter(new ReviewListAllAdapter(getActivity(), BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getArrayListReviewInfo(), getArguments().getInt("position")));
	}

	private void initView(View view) {
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
		position = getArguments().getInt("position");
		int id = BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getRestaurantId();
    	
    	String url = "http://api.bhojnama.com/api/restaurant/"+ id;
    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() 
    	    {
    	        @Override
    	        public void onResponse(String response) {
    	            //response
    	        	Log.e("Response", "***" + response);
    	        	try {
						JsonParser.parseReviewtData(response);
						//listview.setAdapter(hottestAdapter);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		  Fragment fragment = null;
		  Bundle args = new Bundle();
		  args.putInt("position", position - 1);
		  
		 /* fragment = new RestaurantReviewDetailsFragment();
		  fragment.setArguments(args);
		  FragmentManager frgManager = getFragmentManager();
		  android.app.FragmentTransaction ft = frgManager.beginTransaction();
		  //ft.hide(getParentFragment());
		  ft.add(R.id.content_frame, fragment);
		  ft.addToBackStack(null);
		  ft.commit();*/
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
