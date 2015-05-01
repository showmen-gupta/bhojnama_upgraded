package com.apps.fragments;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.adapter.FoodShotsAdapter;
import com.apps.bhojnama.FoodShotDetailActitvity;
import com.apps.bhojnama.PostFoodShotsActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.FoodShotsInfo;
import com.apps.jsonparser.JsonParser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class FoodShotsFragment extends Fragment implements OnClickListener, OnItemClickListener {

	private ImageView ivIcon;
	private TextView tvItemName;
	public static GridView foodShotList;
	private ProgressBar progBarHottestList;
	private TextView txtViewFooter;
	private View footerView;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	public Button btnMakeShots;
	
	private boolean isloading = false;
	private boolean isFirstTime = true;
	private int currentPageLimit = 1;
	public static FoodShotsAdapter foodShotsAdapter;

	public FoodShotsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_food_shots, container, false);
		
		initView(view);
		setListener();
		loadData(50, 1);
		return view;
	}

	private void setListViewListener() {
		/*foodShotList.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				currentPageLimit += 1;
				loadData(3, currentPageLimit);
			}

		});*/
	}

	private void initView(View view) {
		progBarHottestList = (ProgressBar) view.findViewById(R.id.progBarHottestList);
		foodShotList = (GridView) view.findViewById(R.id.list_view_food_shot);
		//foodShotList.setMode(Mode.PULL_FROM_END);
		
		btnMakeShots = (Button) view.findViewById(R.id.btn_make_food_shots);
		footerView =  ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
		txtViewFooter = (TextView) footerView.findViewById(R.id.textView1);
		txtViewFooter.setVisibility(View.GONE);
		
		ArrayList<FoodShotsInfo> arrayListFoodShots = new ArrayList<FoodShotsInfo>();
		BhojNamaSingleton.getInstance().setArrayListFoodShots(arrayListFoodShots);
		foodShotsAdapter = new FoodShotsAdapter(getActivity(), BhojNamaSingleton.getInstance().getArrayListFoodShots(), 1);
		setListViewListener();
	}

	private void setListener() {
		foodShotList.setOnItemClickListener(this);
		//foodShotList.setOnClickListener(this);
		btnMakeShots.setOnClickListener(this);
	}

	private void loadData(final int limit_size, final int page_size) {
		
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		String url = "http://api.bhojnama.com/api/food-shot?limit=" + limit_size + "&page=" + page_size;
		StringRequest dr = new StringRequest(Request.Method.GET, url,
		new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					Log.i("Response Food Shot", "" + response);
					JsonParser.parseFoodShots(response, page_size);
					if ( page_size > BhojNamaSingleton.getInstance().getTotalPages()) {
						//foodShotList.onRefreshComplete();
						return;
					}
					
					if (currentPageLimit == 1) {
						foodShotList.setAdapter(foodShotsAdapter);
						//foodShotList.onRefreshComplete();
					} else {
						foodShotsAdapter.notifyDataSetChanged();
						//foodShotList.onRefreshComplete();
					}
					progBarHottestList.setVisibility(View.GONE);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// error.
				progBarHottestList.setVisibility(View.GONE);
			}
		});

		int socketTimeout = 30000;// 30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		dr.setRetryPolicy(policy);
		queue.add(dr);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_make_food_shots) {
			SharedPref sharedPref = new SharedPref(getActivity());
			if(sharedPref.getLoginStatus().equalsIgnoreCase("1")) {
				getActivity().startActivity(new Intent(getActivity(), PostFoodShotsActivity.class));
			
			} else {
				Fragment fragment = null;
				Bundle args = new Bundle();
				args.putInt("position", 0);
				args.putInt("fragment_no", 5);
				fragment = new LogInFragment();
				fragment.setArguments(args);
				android.app.FragmentManager frgManager = getFragmentManager();
				android.app.FragmentTransaction ft = frgManager.beginTransaction();
				ft.replace(R.id.content_frame, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
			
			
			
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.i("POSITION", "****" + arg2);
		Intent intent = new Intent(getActivity(), FoodShotDetailActitvity.class);
		intent.putExtra("position", arg2 + 1);
		startActivity(intent);
	}

}
