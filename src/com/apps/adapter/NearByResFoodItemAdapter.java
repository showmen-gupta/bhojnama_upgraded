package com.apps.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.adapter.NearbyAdapter.ViewHolder;
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.HottestFoodItemInfo;
import com.apps.datamodel.HottestInfo;
import com.apps.datamodel.hottestlist;

public class NearByResFoodItemAdapter extends BaseAdapter{
	
	private ArrayList<HottestFoodItemInfo> hottestInfo;
	private Context context;
	private LayoutInflater mInflater;
	private int listPosition;
	
	public NearByResFoodItemAdapter(Context context, ArrayList<HottestFoodItemInfo> hottestInfo,int restaurantListPosition) {
		this.hottestInfo = hottestInfo;
		this.context = context;
		this.listPosition = restaurantListPosition;
				
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
			convertView = mInflater.inflate(R.layout.row_food_list, null);

			holder = new ViewHolder();
			holder.txtViewFoodName = (TextView) convertView.findViewById(R.id.text_view_food_name);
			holder.txtViewPrice = (TextView) convertView.findViewById(R.id.text_view_price);
			holder.txtViewDescription=(TextView) convertView.findViewById(R.id.TextView02);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtViewFoodName.setText(BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(listPosition).getHottestFoodItemList().get(position).getFoodTitle());
		holder.txtViewPrice.setText(BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(listPosition).getHottestFoodItemList().get(position).getPrice()+"TK");
		holder.txtViewDescription.setText(BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(listPosition).getHottestFoodItemList().get(position).getDescription());
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtViewFoodName;
		TextView txtViewPrice;
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
