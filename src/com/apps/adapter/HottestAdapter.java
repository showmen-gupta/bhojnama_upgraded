package com.apps.adapter;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.apps.bhojnama.LogInActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestInfo;
import com.apps.jsonparser.JsonParser;
import com.google.android.gms.maps.model.LatLng;

public class HottestAdapter extends BaseAdapter{
	
	private ArrayList<HottestInfo> hottestInfo;
	private Context context;
	private LayoutInflater mInflater;
	private LatLng latLon;
	
	public HottestAdapter(Context context, ArrayList<HottestInfo> hottestInfo, LatLng currentLatLng) {
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
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_list_hottest, null);

			holder = new ViewHolder();
			holder.txtViewRestaurantName = (TextView) convertView.findViewById(R.id.txt_view_restaurant_name);
			holder.txtViewAddress = (TextView) convertView.findViewById(R.id.txt_view_address);
			holder.txtViewDistance = (TextView) convertView.findViewById(R.id.txtViewDistance);
			holder.txtViewLike = (TextView) convertView.findViewById(R.id.txtViewLike);
			
			holder.address=(TextView) convertView.findViewById(R.id.address);
			holder.txtViewsCount= (TextView) convertView.findViewById(R.id.views);
			holder.logo= (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
			
			/*BhojNamaSingleton.getInstance()
						.getHottestInfoList().get(position).getCity()
						+ ", "
						+ BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getArea()*/

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewLike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPref sharedPref = new SharedPref(context);
				if (sharedPref.getLoginStatus().equalsIgnoreCase("1")) {
					Toast.makeText(context, "Like accepted", Toast.LENGTH_SHORT).show();
					callLikeApi(holder.txtViewLike);
				} else {
					Intent intentLogin = new Intent(context, LogInActivity.class);
					intentLogin.putExtra("fragment_no", 1);
					intentLogin.putExtra("list_position", position);
					
					context.startActivity(intentLogin);
				}
				
			}
		});
		
		String img_url="http://api.bhojnama.com/";
		holder.txtViewRestaurantName.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getRestaurantName());
		holder.txtViewAddress.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getOpeningHour());
		holder.txtViewsCount.setText(Integer.toString(BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getLikes()));
		new AQuery(context).id(holder.logo).image(img_url + BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getLogo(), true, true, 0, R.drawable.appicon);
		holder.address.setText(BhojNamaSingleton.getInstance()
						.getHottestInfoList().get(position).getArea()
						+ ", "
						+ BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getCity());
		LatLng resLatLng = new LatLng(BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getLat(), BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getLon());
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
		TextView address;
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
