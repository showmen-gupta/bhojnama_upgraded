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
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.ReviewInfo;

public class ReviewListAdapter extends BaseAdapter {

	private ArrayList<ReviewInfo> reviewInfo;
	private Context context;
	private LayoutInflater mInflater;
	private int listPosition;

	public ReviewListAdapter(Context context, ArrayList<ReviewInfo> reviewInfo, int reviewListPosition) {
		this.reviewInfo = reviewInfo;
		this.context = context;
		this.listPosition = reviewListPosition;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.reviewInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return this.reviewInfo.get(position);
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
			convertView = mInflater.inflate(R.layout.row_user_review_list, null);

			holder = new ViewHolder();
			
			holder.image= (ImageView) convertView.findViewById(R.id.icon);
			holder.txtUser = (TextView) convertView.findViewById(R.id.textView5);
			holder.txtViewAbout = (TextView) convertView.findViewById(R.id.textView3);
			holder.txtViewdesc = (TextView) convertView.findViewById(R.id.secondLine);
			holder.star1=(ImageView) convertView.findViewById(R.id.ImageView03);
			holder.star2=(ImageView) convertView.findViewById(R.id.ImageView02);
			holder.star3=(ImageView) convertView.findViewById(R.id.imageView1);
			holder.star4=(ImageView) convertView.findViewById(R.id.ImageView01);
			holder.star5=(ImageView) convertView.findViewById(R.id.imageView4);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
/*		ViewAdapter viewAdapter = new ViewAdapter();
		
		viewAdapter.setCount(3);
		
		holder.viewPager.setAdapter(viewAdapter);
		holder.viewPager.setOffscreenPageLimit(5);
		holder.viewPager.setPageMargin(5);
		holder.viewPager.setClipChildren(false);*/
		
//		Log.e("FOOD DETAILS", "#####" + BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotDetails());
//		holder.txtViewFoodShotDetails.setText(BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotDetails());
		
		return convertView;
	}

	static class ViewHolder {
		ImageView image, star1, star2, star3,star4,star5;
		TextView txtUser;
		TextView txtViewAbout;
		TextView txtViewdesc;
		
		
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
