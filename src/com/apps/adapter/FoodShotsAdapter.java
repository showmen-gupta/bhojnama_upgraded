package com.apps.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.FoodShotsInfo;

public class FoodShotsAdapter extends BaseAdapter {

	private ArrayList<FoodShotsInfo> hottestInfo;
	private Context context;
	private LayoutInflater mInflater;
	private int listPosition;

	public FoodShotsAdapter(Context context, ArrayList<FoodShotsInfo> hottestInfo, int restaurantListPosition) {
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
			convertView = mInflater.inflate(R.layout.row_foodshot_list, null);

			holder = new ViewHolder();
			holder.txtViewFoodShotDetails = (TextView) convertView.findViewById(R.id.text_view_food_shot_details);
			holder.txtViewPrice = (TextView) convertView.findViewById(R.id.text_view_price);
			
			holder.viewPager = (ViewPager) convertView.findViewById(R.id.viewpager);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ViewAdapter viewAdapter = new ViewAdapter();
		
		viewAdapter.setCount(3);
		
		holder.viewPager.setAdapter(viewAdapter);
		holder.viewPager.setOffscreenPageLimit(5);
		holder.viewPager.setPageMargin(5);
		holder.viewPager.setClipChildren(false);
		
		Log.e("FOOD DETAILS", "#####" + BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotDetails());
		holder.txtViewFoodShotDetails.setText(BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotDetails());
		
		return convertView;
	}

	static class ViewHolder {
		TextView txtViewFoodShotDetails;
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
		ViewPager viewPager;
	}

	private class ViewAdapter extends PagerAdapter {

		private int count = 4;

		public void setCount(int count) {
			this.count = count;
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TextView textView = new TextView(context);
			textView.setText("Item " + position);
			textView.setBackgroundResource(R.drawable.demo_food);
			((ViewPager) container).addView(textView, 0);
			return textView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((TextView) object);
		}

	}

}
