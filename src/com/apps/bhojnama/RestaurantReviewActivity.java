package com.apps.bhojnama;

import org.json.JSONException;

import android.app.Activity;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.apps.adapter.HottestFoodItemAdapter;
import com.apps.adapter.ReviewListAllAdapter;
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.fragments.BooktableFragment;
import com.apps.fragments.SignUpFragment;
import com.apps.jsonparser.JsonParser;

public class RestaurantReviewActivity extends Activity implements OnClickListener, OnItemClickListener{

//	ImageView ivIcon;
//	TextView tvItemName;
//
//	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
//	public static final String ITEM_NAME = "itemName";

	public RestaurantReviewActivity() {}
	
	private View rootView;
	private View headerView;
	private ListView reviewList;
	private ImageView imgViewLogo;
	private Button btnGetMeThere, btnBookTable, btnSubmitReview;
	private TextView txtRestaurantName, txtRestaurantAddress, txtViewDetails,txtViewsCount;
	int position;
	int list_position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_restaurant_review_details);
		initView();
		loadData();
		setListener();
	}

	private void loadData() {
		position = getIntent().getExtras().getInt("position");
		list_position = getIntent().getExtras().getInt("list_position");
		reviewListApiCall();
		
		txtRestaurantName.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(list_position).getRestaurantName());
		txtRestaurantAddress.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(list_position).getOpeningHour());
		txtViewsCount.setText(Integer.toString(BhojNamaSingleton.getInstance().getHottestInfoList().get(list_position).getLikes()));
		
		String img_url="http://api.bhojnama.com/";
		new AQuery(this).id(imgViewLogo).image(img_url+BhojNamaSingleton.getInstance().getHottestInfoList().get(list_position).getLogo(), true, true, 0, R.drawable.appicon);
		
	}

	private void initView() {
		headerView = this.getLayoutInflater().inflate(R.layout.header_review_list, null);
		txtRestaurantName = (TextView) headerView.findViewById(R.id.txt_view_restaurant_name);
		txtRestaurantAddress = (TextView) headerView.findViewById(R.id.txt_view_address);
		txtViewsCount= (TextView) headerView.findViewById(R.id.textView04);
		reviewList = (ListView)  findViewById(R.id.review_list);
		reviewList.addHeaderView(headerView);
		imgViewLogo = (ImageView)  findViewById(R.id.img_logo);
		btnGetMeThere = (Button)  findViewById(R.id.btn_get_me_there);
		btnBookTable = (Button)  findViewById(R.id.btn_book_table);
		btnSubmitReview = (Button)  findViewById(R.id.btn_submit_review);
		
	}

	private void setListener() {
		btnSubmitReview.setOnClickListener(this);
	}

	private void populateReviewListView() {
		reviewList.setOnItemClickListener(this);
		reviewList.setAdapter(new ReviewListAllAdapter(this, BhojNamaSingleton.getInstance().getArrayListReviewInfo(), 1));
	
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_make_food_shots) {
			
		} else if (v.getId() == R.id.btn_get_me_there) {
			Intent intent = new Intent(this, NearbyMapDirectionActivity.class);
			intent.putExtra("list_position", position);
			intent.putExtra("view_type", "hottest");
			startActivity(intent);
			
		} else if (v.getId() == R.id.btn_submit_review) {
			Intent intent = new Intent(this, SubmitReviewActivity.class);
			intent.putExtra("position", BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getRestaurantId());
			intent.putExtra("list_position", position);
			startActivity(intent);
			
			
			
		} else if (v.getId() == R.id.btn_book_table) {
			Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();
			Fragment fragment = null;
			Bundle args = new Bundle();
			args.putInt("position", position);
			fragment = new BooktableFragment();
			fragment.setArguments(args);
			FragmentManager frgManager = getFragmentManager();
			android.app.FragmentTransaction ft = frgManager.beginTransaction();
			ft.add(R.id.content_frame, fragment);
			ft.addToBackStack(null);
			ft.commit();
			this.setTitle("Book a table");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

	@Override
	public void onResume() {
		super.onResume();
		 this.getActionBar().setTitle("Restaurant Details");
		//getActivity().setTitle("Restaurant Details11");
	}
	
	
	
	private void reviewListApiCall() {
		RequestQueue queue = Volley.newRequestQueue(this);
		
		int id = position;
    	
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
						populateReviewListView();
						//listview.setAdapter(hottestAdapter);
						//listview.onRefreshComplete();
						//progBarHottestList.setVisibility(View.GONE);
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

}
