package com.apps.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.sax.StartElementListener;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.apps.bhojnama.FoodShotDetailActitvity;
import com.apps.bhojnama.LogInActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnama.SubmitNearByReviewActivity;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestInfo;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;
import com.google.android.gms.maps.model.LatLng;

public class HottestAdapter extends BaseAdapter {

	private ArrayList<HottestInfo> hottestInfo;
	private Context context;
	private LayoutInflater mInflater;
	private LatLng latLon;

	public HottestAdapter(Context context, ArrayList<HottestInfo> hottestInfo,
			LatLng currentLatLng) {
		this.hottestInfo = hottestInfo;
		this.context = context;
		this.latLon = currentLatLng;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.hottestInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return this.hottestInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_list_hottest, null);

			holder = new ViewHolder();
			holder.txtViewRestaurantName = (TextView) convertView
					.findViewById(R.id.txt_view_restaurant_name);
			holder.txtViewAddress = (TextView) convertView
					.findViewById(R.id.txt_view_address);
			holder.txtViewDistance = (TextView) convertView
					.findViewById(R.id.txtViewDistance);
			holder.txtViewLike = (TextView) convertView.findViewById(R.id.txtViewLike);
			holder.review_count = (TextView) convertView.findViewById(R.id.review_count);
			holder.like_btn = (Button) convertView
					.findViewById(R.id.like_button);

			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.txtViewsCount = (TextView) convertView.findViewById(R.id.views);
			holder.logo = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);

			/*
			 * BhojNamaSingleton.getInstance()
			 * .getHottestInfoList().get(position).getCity() + ", " +
			 * BhojNamaSingleton
			 * .getInstance().getHottestInfoList().get(position).getArea()
			 */

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.like_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPref sharedPref = new SharedPref(context);
				if (sharedPref.getLoginStatus().equalsIgnoreCase("1")) {
					// Toast.makeText(context, "Like accepted",
					// Toast.LENGTH_SHORT).show();
					//holder.txtViewsCount.setText("" + (Integer.parseInt(holder.txtViewsCount.getText().toString()) + 1));
					//holder.like_btn.setBackgroundResource(R.drawable.like_activated_1);
					callLikeApi(holder.like_btn, holder.txtViewsCount,  position);
				} else {
					Toast.makeText(context,
							"Please Login First To Like This Restaurant",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		String img_url = "http://api.bhojnama.com/";
		holder.txtViewRestaurantName.setText(BhojNamaSingleton.getInstance()
				.getHottestInfoList().get(position).getRestaurantName());
		holder.txtViewAddress.setText(BhojNamaSingleton.getInstance()
				.getHottestInfoList().get(position).getOpeningHour());
		holder.txtViewsCount.setText(Integer.toString(BhojNamaSingleton
				.getInstance().getHottestInfoList().get(position).getLikes()));
		holder.review_count.setText(Integer.toString(BhojNamaSingleton
				.getInstance().getHottestInfoList().get(position)
				.getReview_count()));
		new AQuery(context).id(holder.logo).image(
				img_url
						+ BhojNamaSingleton.getInstance().getHottestInfoList()
								.get(position).getLogo(), true, true, 0,
				R.drawable.appicon);
		holder.address.setText(BhojNamaSingleton.getInstance()
				.getHottestInfoList().get(position).getArea()
				+ ", "
				+ BhojNamaSingleton.getInstance().getHottestInfoList()
						.get(position).getCity());
		LatLng resLatLng = new LatLng(BhojNamaSingleton.getInstance()
				.getHottestInfoList().get(position).getLat(), BhojNamaSingleton
				.getInstance().getHottestInfoList().get(position).getLon());
		holder.txtViewDistance.setText(String.format("%.01f",
				getMiles(latLon, resLatLng))
				+ " KM ");
		// holder.logo.setI
		return convertView;
	}

	static class ViewHolder {
		TextView txtViewRestaurantName;
		TextView txtViewAddress;
		TextView txtViewDescription;
		TextView txtViewDistance;
		TextView txtViewStartDate;
		TextView txtViewStartDay;
		TextView txtViewsCount;
		TextView address;
		TextView txtViewEndDate;
		TextView txtViewEndDay;
		TextView review_count;
		TextView txtViewStartTime;
		TextView txtViewEndTime;
		TextView txtViewLike;
		Button like_btn;
		TextView txtViewTo;
		ImageView logo;
		ImageView imgViewFavoriate;
	}

	public double getMiles(LatLng latLngCurrent, LatLng latB) {

		Location locationA = new Location("point A");
		locationA.setLatitude(latLngCurrent.latitude);
		locationA.setLongitude(latLngCurrent.longitude);

		Location locationB = new Location("point B");
		// LatLng latB = new LatLng(a, b);
		locationB.setLatitude(latB.latitude);
		locationB.setLongitude(latB.longitude);

		float distance = locationA.distanceTo(locationB);

		return distance * 0.000621371192;
	}
	
	ProgressDialog progress;

	private void callLikeApi(final Button Like, final TextView likeCount, int position) {
	
		RequestQueue queue = Volley.newRequestQueue(context);
		final SharedPref sharedPref = new SharedPref(context);
		Log.e("user_id", ""+sharedPref.getUserID());
		Log.e("token",""+sharedPref.getUserToken());
		
		int branch_id= BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getBranchId();
		Toast.makeText(context, "Branch ID:"+branch_id, Toast.LENGTH_LONG).show();
		String like_url= ConstantValue.BASE_URL_LIKE+ branch_id+"/like";
		Log.e("URL LIKE", like_url);
	
		StringRequest myReq = new StringRequest(Method.POST, like_url, createMyReqSuccessListener(Like,likeCount), createMyReqErrorListener()) {
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				
				params.put("Content-Type", "application/json; charset=utf-8");
				params.put("user_id", sharedPref.getUserID());
				params.put("token", sharedPref.getUserToken());

				

				return params;
			};
		};
		
        
        int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	myReq.setRetryPolicy(policy);
        queue.add(myReq);
        
        progress = new ProgressDialog(context);
        progress.setMessage("Please wait....");
        progress.show();
		
	}
	
	private ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Toast.makeText(context, "You can not Like it Twice", Toast.LENGTH_LONG).show();
            	progress.dismiss();
            }
        };
	}

	private Listener<String> createMyReqSuccessListener(final Button Like,final TextView Like_count) {
		return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	progress.dismiss();
            	Log.e("Login Test", "*****" + response);
            	try {
					int status= JsonParser.parseLikedata(response);
					//Toast.makeText(context, "Status: " + status, Toast.LENGTH_LONG).show();
					
					if(status==1){
						
						Toast.makeText(context, "Thanks For the Like (y)", Toast.LENGTH_LONG).show();
						Like.setBackgroundResource(R.drawable.like_activated_1);
						Like_count.setText("" + (Integer.parseInt(Like_count.getText().toString()) + 1));
						
					}
					if(status==0){
						Toast.makeText(context, "You can not Like it Twice", Toast.LENGTH_LONG).show();
					}
					
				
				} catch (Exception e) {
					e.printStackTrace();
				}
            	
            }

        };
	}

}
