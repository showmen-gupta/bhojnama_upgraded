package com.apps.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.NearbyInfo;

public class NearbyAdapter extends BaseAdapter{

	private ArrayList<NearbyInfo> eventsInfoList;
	private LayoutInflater mInflater;
	private Context context;
	private Activity activity;
	private boolean isPastEvent;
	Calendar c = Calendar.getInstance();
	public int time = c.get(Calendar.MINUTE)+1;

	public NearbyAdapter(Context context, ArrayList<NearbyInfo> eventsInfoList) {
		this.context = context;
		this.eventsInfoList = eventsInfoList;
		this.activity = activity;
		this.isPastEvent = isPastEvent;
	}

	@Override
	public int getCount() {
		if(eventsInfoList != null) {
			return eventsInfoList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return eventsInfoList.get(position);
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_list_nearby, null);

			holder = new ViewHolder();
			holder.txtViewRestaurantName = (TextView) convertView.findViewById(R.id.txt_view_restaurant_name);
			holder.txtViewAddress = (TextView) convertView.findViewById(R.id.txt_view_address);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewRestaurantName.setText(BhojNamaSingleton.getInstance().getArrayListNearByInfo().get(position).getRestaurantName());
		holder.txtViewAddress.setText(BhojNamaSingleton.getInstance().getArrayListNearByInfo().get(position).getRestaurantAddress());
		return convertView;
	}

	static class ViewHolder {
		TextView txtViewRestaurantName;
		TextView txtViewAddress;
		TextView txtViewDescription;
		TextView txtViewStartDate;
		TextView txtViewStartDay;
		
		TextView txtViewEndDate;
		TextView txtViewEndDay;
		
		TextView txtViewStartTime;
		TextView txtViewEndTime;
		
		TextView txtViewTo;
		
		ImageView imgViewFavoriate;
	}

}

