package com.apps.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestInfo;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_list_hottest, null);

			holder = new ViewHolder();
			holder.txtViewRestaurantName = (TextView) convertView.findViewById(R.id.txt_view_restaurant_name);
			holder.txtViewAddress = (TextView) convertView.findViewById(R.id.txt_view_address);
			holder.txtViewDistance = (TextView) convertView.findViewById(R.id.txtViewDistance);
			holder.txtViewsCount= (TextView) convertView.findViewById(R.id.views);
			holder.logo= (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String img_url="http://api.bhojnama.com/";
		holder.txtViewRestaurantName.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getRestaurantName());
		holder.txtViewAddress.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getOpeningHour());
		holder.txtViewsCount.setText(Integer.toString(BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getLikes()));
		new AQuery(context).id(holder.logo).image(img_url+BhojNamaSingleton.getInstance().getHottestInfoList().get(position).getLogo(), true, true, 0, R.drawable.appicon);
		
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
		
		TextView txtViewEndDate;
		TextView txtViewEndDay;
		
		TextView txtViewStartTime;
		TextView txtViewEndTime;
		
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

}
