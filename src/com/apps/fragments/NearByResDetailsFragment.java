package com.apps.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.apps.adapter.HottestFoodItemAdapter;
import com.apps.adapter.NearByResFoodItemAdapter;
import com.apps.bhojnama.LogInActivity;
import com.apps.bhojnama.NearbyMapDirectionActivity;
import com.apps.bhojnama.NearbyRestaurantReviewActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnama.RestaurantReviewActivity;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;

public class NearByResDetailsFragment extends Fragment implements OnClickListener, OnItemClickListener{

//	ImageView ivIcon;
//	TextView tvItemName;
//
//	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
//	public static final String ITEM_NAME = "itemName";

	public NearByResDetailsFragment() {}
	
	private int position;
	private View rootView;
	private View headerView;
	private ListView foodList;
	private ImageView imgViewLogo;
	private Button btnGetMeThere, btnBookTable, btnSubmitReview;
	private TextView txtRestaurantName, txtRestaurantAddress, txtViewDetails,txtViewsCount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
		initView();
		loadData();
		
		setListener();
		//backListener();
		populatesFoodListView();
		
		return rootView;
		
	}

	private void backListener() {
		rootView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return false;
			}
		});
	}

	private void loadData() {
		//Get the restaurant array position from previous activity
		position = getArguments().getInt("position");
		
		txtRestaurantName.setText(BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getRestaurantName());
		txtRestaurantAddress.setText(BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getOpeningHour());
		txtViewsCount.setText(Integer.toString(BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getLikes()));
		
		String img_url="http://api.bhojnama.com/";
		new AQuery(getActivity()).id(imgViewLogo).image(img_url+BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getLogo(), true, true, 0, R.drawable.appicon);
		
	}

	private void initView() {
		
		headerView = getActivity().getLayoutInflater().inflate(R.layout.header_hottest_details, null);
		txtRestaurantName = (TextView) headerView.findViewById(R.id.txt_view_restaurant_name);
		txtRestaurantAddress = (TextView) headerView.findViewById(R.id.txt_view_address);
		txtViewsCount= (TextView) headerView.findViewById(R.id.textView4);
		foodList = (ListView) rootView.findViewById(R.id.food_list);
		foodList.addHeaderView(headerView);
		
		imgViewLogo = (ImageView) rootView.findViewById(R.id.img_logo);
//		ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
		btnGetMeThere = (Button) rootView.findViewById(R.id.btn_get_me_there);
		btnBookTable = (Button) rootView.findViewById(R.id.btn_book_table);
		btnSubmitReview = (Button) rootView.findViewById(R.id.btn_submit_review);
		//txtRestaurantName = (TextView) rootView.findViewById(R.id.txt_view_restaurant_name);
		//txtRestaurantAddress = (TextView) rootView.findViewById(R.id.txt_view_address);
		
	}

	private void setListener() {
		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		btnBookTable.setOnClickListener(NearByResDetailsFragment.this);
		btnSubmitReview.setOnClickListener(NearByResDetailsFragment.this);
		btnGetMeThere.setOnClickListener(NearByResDetailsFragment.this);
		
	}

	private void populatesFoodListView() {
		foodList.setOnItemClickListener(this);
		foodList.setAdapter(new NearByResFoodItemAdapter(getActivity(), BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getHottestFoodItemList(), getArguments().getInt("position")));
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_make_food_shots) {
			
		} else if (v.getId() == R.id.btn_get_me_there) {
			Intent intent = new Intent(getActivity(), NearbyMapDirectionActivity.class);
			intent.putExtra("list_position", getArguments().getInt("position"));
			intent.putExtra("view_type", "hottest");
			startActivity(intent);
			
		} else if (v.getId() == R.id.btn_submit_review) {
			
			Intent intent = new Intent(getActivity(), NearbyRestaurantReviewActivity.class);
			intent.putExtra("position", BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getRestaurantId());
			intent.putExtra("list_position", getArguments().getInt("position"));
			startActivity(intent);
			
			
		} else if (v.getId() == R.id.btn_book_table) {
			Toast.makeText(getActivity(), "Position: " + getArguments().getInt("position"), Toast.LENGTH_SHORT).show();
			Fragment fragment = null;
			Bundle args = new Bundle();
			args.putInt("position", getArguments().getInt("position"));
			fragment = new BooktableFragment();
			fragment.setArguments(args);
			FragmentManager frgManager = getFragmentManager();
			android.app.FragmentTransaction ft = frgManager.beginTransaction();
			ft.add(R.id.content_frame, fragment);
			ft.addToBackStack(null);
			ft.commit();
			getActivity().setTitle("Book a table");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	/*	  Fragment fragment = null;
		  Bundle args = new Bundle();
		  
		  args.putInt("base_position", getArguments().getInt("position"));
		  args.putInt("position", position - 1);
		  
		  fragment = new FooddetailsFragment();
		  fragment.setArguments(args);
		  FragmentManager frgManager = getFragmentManager();
		  android.app.FragmentTransaction ft = frgManager.beginTransaction();
		  ft.add(R.id.content_frame, fragment);
		  ft.addToBackStack(null);
		  ft.commit();
		  getActivity().setTitle("Food Item Deatils");*/
		
	}

	@Override
	public void onResume() {
		super.onResume();
		 getActivity().getActionBar().setTitle("Restaurant Details");
		//getActivity().setTitle("Restaurant Details11");
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		getActivity().getActionBar().setTitle("Hottest Restaurant");
	}

}
