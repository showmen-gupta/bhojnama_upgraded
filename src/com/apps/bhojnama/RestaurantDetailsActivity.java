package com.apps.bhojnama;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.apps.adapter.HottestFoodItemAdapter;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.fragments.BooktableFragment;

public class RestaurantDetailsActivity extends Activity implements
		OnClickListener, OnItemClickListener {

	// ImageView ivIcon;
	// TextView tvItemName;
	//
	// public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	// public static final String ITEM_NAME = "itemName";
	private int position;
	private View rootView;
	private ListView foodList;
	private Button btnGetMeThere, btnBookTable, btnSubmitReview;
	private TextView txtViewRestuarantName, txtViewRestaurantAddress;
	private TextView txtRestaurantName, txtRestaurantAddress, txtViewDetails,
			txtViewsCount, address;
	private ImageView img;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_restaurant_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Restaurant Details");

		intiView();
		setListener();
		loadData();
		populatesFoodListView();
	}

	private void intiView() {
		View headerView = this.getLayoutInflater().inflate(
				R.layout.header_hottest_details, null);
		txtRestaurantName = (TextView) headerView
				.findViewById(R.id.txt_view_restaurant_name);
		txtRestaurantAddress = (TextView) headerView
				.findViewById(R.id.txt_view_address);
		address = (TextView) headerView.findViewById(R.id.address);
		txtViewsCount = (TextView) headerView.findViewById(R.id.textView4);
		img = (ImageView) headerView.findViewById(R.id.img_logo);
		foodList = (ListView) findViewById(R.id.food_list);
		foodList.addHeaderView(headerView);

		btnGetMeThere = (Button) headerView.findViewById(R.id.btn_get_me_there);
		btnBookTable = (Button) headerView.findViewById(R.id.btn_book_table);
		btnSubmitReview = (Button) headerView
				.findViewById(R.id.btn_submit_review);

		// txtViewRestuarantName = (TextView)
		// findViewById(R.id.txt_view_restaurant_name);
		// txtViewRestaurantAddress =
		// (TextView)findViewById(R.id.txt_view_address);
	}

	private void loadData() {
		position = getIntent().getExtras().getInt("position");
		txtRestaurantName.setText(BhojNamaSingleton.getInstance()
				.getHottestInfoList().get(position).getRestaurantName());
		txtRestaurantAddress.setText(BhojNamaSingleton.getInstance()
				.getHottestInfoList().get(position).getOpeningHour());
		address.setText(BhojNamaSingleton.getInstance().getHottestInfoList()
				.get(position).getArea()
				+ ","
				+ BhojNamaSingleton.getInstance().getHottestInfoList()
						.get(position).getCity());
		txtViewsCount.setText(Integer.toString(BhojNamaSingleton.getInstance()
				.getHottestInfoList().get(position).getLikes()));
		String img_url = "http://api.bhojnama.com/";
		new AQuery(img).image(
				img_url
						+ BhojNamaSingleton.getInstance().getHottestInfoList()
								.get(position).getLogo(), true, true, 0,
				R.drawable.appicon);

	}

	private void setListener() {
		btnBookTable.setOnClickListener(RestaurantDetailsActivity.this);
		btnSubmitReview.setOnClickListener(RestaurantDetailsActivity.this);
		btnGetMeThere.setOnClickListener(RestaurantDetailsActivity.this);

	}

	private void populatesFoodListView() {
		foodList.setOnItemClickListener(this);
		foodList.setAdapter(new HottestFoodItemAdapter(this, BhojNamaSingleton
				.getInstance().getHottestInfoList().get(position)
				.getHottestFoodItemList(), getIntent().getExtras().getInt(
				"position")));
	}

	// public void onBackPressed() {
	// Fragment fragment = null;
	// Bundle args = new Bundle();
	// fragment = new HomeFragment();
	// fragment.setArguments(args);
	// FragmentManager frgManager = getFragmentManager();
	// frgManager.beginTransaction().replace(R.id.content_frame, fragment)
	// .commit();
	//
	// }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_make_food_shots) {

		} else if (v.getId() == R.id.btn_get_me_there) {
			Intent intent = new Intent(this, NearbyMapDirectionActivity.class);
			intent.putExtra("list_position", position);
			intent.putExtra("view_type", "hottest");
			startActivity(intent);

		} else if (v.getId() == R.id.btn_submit_review) {
			Intent intent = new Intent(RestaurantDetailsActivity.this,
					RestaurantReviewActivity.class);
			intent.putExtra("position", BhojNamaSingleton.getInstance()
					.getHottestInfoList().get(position).getRestaurantId());
			intent.putExtra("list_position", position);
			startActivity(intent);

		} else if (v.getId() == R.id.btn_book_table) {

			Intent intent = new Intent(RestaurantDetailsActivity.this,
					BookATableActivity.class);
			intent.putExtra("list_position", position);
			startActivity(intent);

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		/*
		 * Fragment fragment = null; Bundle args = new Bundle();
		 * args.putInt("position", position);
		 * 
		 * fragment = new FooddetailsFragment(); fragment.setArguments(args);
		 * FragmentManager frgManager = getFragmentManager();
		 * frgManager.beginTransaction().replace(R.id.content_frame,
		 * fragment).commit(); this.setTitle("Food Item Deatils");
		 */

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
