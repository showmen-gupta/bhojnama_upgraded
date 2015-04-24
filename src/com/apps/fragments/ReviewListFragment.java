package com.apps.fragments;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
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
import com.apps.adapter.ReviewListAdapter;
import com.apps.bhojnama.PostFoodShotsActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.FoodShotsInfo;
import com.apps.datamodel.ReviewInfo;

import com.apps.jsonparser.JsonParser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ReviewListFragment extends Fragment implements OnClickListener {

	//ImageView imageView;
	private Button submit;
	
	public static PullToRefreshListView reviewList;
	private ProgressBar progBarHottestList;
	private TextView txtViewFooter;
	private View footerView;
	
	private boolean isloading = false;
	private boolean isFirstTime = true;
	private int currentPageLimit = 1;
	
	private ReviewListAdapter reviewAdapter;
	
	public ReviewListFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_review, container, false);
		submit = (Button) view.findViewById(R.id.button1);
		initView(view);
//		setListener();
//		loadData(3, 1);
	
		return view;
		
	}
	
	private void setListViewListener() {
		reviewList.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				currentPageLimit += 1;
				loadData(3, currentPageLimit);
				
			}

		});
	}
	
	private void initView(View view) {
		progBarHottestList = (ProgressBar) view.findViewById(R.id.review_list);
		reviewList = (PullToRefreshListView) view.findViewById(R.id.list_view_food_shot);
		reviewList.setMode(Mode.PULL_FROM_END);
		submit = (Button) view.findViewById(R.id.button1);
		footerView =  ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
		txtViewFooter = (TextView) footerView.findViewById(R.id.textView1);
		txtViewFooter.setVisibility(View.GONE);
		
		ArrayList<ReviewInfo> arrayListReviewList = new ArrayList<ReviewInfo>();
		BhojNamaSingleton.getInstance().setArrayListReviewInfo(arrayListReviewList);
		reviewAdapter = new ReviewListAdapter(getActivity(), BhojNamaSingleton.getInstance().getArrayListReviewInfo(), 1);
		setListViewListener();
		
	}
	
	private void loadData(final int limit_size, final int page_size) {
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}




}
