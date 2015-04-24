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
import com.apps.datamodel.FoodShotsCommentsInfo;
import com.apps.datamodel.FoodShotsInfo;

public class FoodShotCommentsAdapter extends BaseAdapter {

	private ArrayList<FoodShotsCommentsInfo> arrayListFoodComments;
	private Context context;
	private LayoutInflater mInflater;
	private int listPosition;

	public FoodShotCommentsAdapter(Context context, ArrayList<FoodShotsCommentsInfo> hottestInfo, int restaurantListPosition) {
		this.arrayListFoodComments = hottestInfo;
		this.context = context;
		this.listPosition = restaurantListPosition;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.arrayListFoodComments.size();
	}

	@Override
	public Object getItem(int position) {
		return this.arrayListFoodComments.get(position);
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
			convertView = mInflater.inflate(R.layout.row_foodshot_comments, null);

			holder = new ViewHolder();
			holder.txtViewCommentDetails = (TextView) convertView.findViewById(R.id.text_view_comment);
			holder.txtViewAuthor = (TextView) convertView.findViewById(R.id.text_view_user);
			
			holder.viewPager = (ViewPager) convertView.findViewById(R.id.viewpager);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewCommentDetails.setText(arrayListFoodComments.get(position).getCommentDetails());
		holder.txtViewAuthor.setText(arrayListFoodComments.get(position).getAuthorName());
		return convertView;
	}

	static class ViewHolder {
		TextView txtViewCommentDetails;
		TextView txtViewAuthor;
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

}
