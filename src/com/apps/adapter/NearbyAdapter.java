package com.apps.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.apps.adapter.HottestAdapter.ViewHolder;
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestInfo;
import com.apps.datamodel.NearbyInfo;
import com.apps.datamodel.NearbyResInfo;
import com.google.android.gms.maps.model.LatLng;

public class NearbyAdapter extends BaseAdapter{

	private ArrayList<NearbyResInfo> nearInfo;
	private Context context;
	private LayoutInflater mInflater;
	private LatLng latLon;
	
	public NearbyAdapter(Context context, ArrayList<NearbyResInfo> nearInfo, LatLng currentLatLng) {
		this.nearInfo = nearInfo;
		this.context = context;
		this.latLon = currentLatLng;
				
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.nearInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return this.nearInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_list_hottest, null);

			holder = new ViewHolder();
			holder.txtViewRestaurantName = (TextView) convertView.findViewById(R.id.txt_view_restaurant_name);
			holder.txtViewAddress = (TextView) convertView.findViewById(R.id.txt_view_address);
			holder.txtViewDistance = (TextView) convertView.findViewById(R.id.txtViewDistance);
			holder.txtViewLike = (TextView) convertView.findViewById(R.id.txtViewLike);
			
			holder.txtViewsCount= (TextView) convertView.findViewById(R.id.views);
			holder.logo= (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewLike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callLikeApi(holder.txtViewLike);
			}
		});
		
		String img_url="http://api.bhojnama.com/";
		holder.txtViewRestaurantName.setText(nearInfo.get(position).getRestaurantName());
		holder.txtViewAddress.setText(nearInfo.get(position).getOpeningHour());
		holder.txtViewsCount.setText(Integer.toString(nearInfo.get(position).getLikes()));
		new AQuery(context).id(holder.logo).image(img_url +nearInfo.get(position).getLogo(), true, true, 0, R.drawable.appicon);
		
		LatLng resLatLng = new LatLng(nearInfo.get(position).getLat(), nearInfo.get(position).getLon());
		holder.txtViewDistance.setText(String.format("%.01f", getMiles(latLon, resLatLng)) + " KM ");
		//holder.logo.setI
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
		
		TextView txtViewEndDate;
		TextView txtViewEndDay;
		
		TextView txtViewStartTime;
		TextView txtViewEndTime;
		TextView txtViewLike;
		
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
	
	private void callLikeApi(final TextView txtViewLike) {
		RequestQueue queue = Volley.newRequestQueue(context);
		
    	
    	String url = "api/branch/1/like/"+ "id" + BhojNamaSingleton.getInstance().getUserInfo().getID();
    	StringRequest dr = new StringRequest(Request.Method.GET, url, 
    	    new Response.Listener<String>() 
    	    {
    	        @Override
    	        public void onResponse(String response) {
    	            //response
    	        	Log.e("Response", "***" + response);
    	        	txtViewLike.setText("" + txtViewLike.getText() + 1);
    	        	/*try {
    	        		
						//JsonParser.parseReviewtData(response);
						//listview.setAdapter(hottestAdapter);
						//listview.onRefreshComplete();
						//progBarHottestList.setVisibility(View.GONE);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
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

