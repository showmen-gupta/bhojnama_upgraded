package com.apps.fragments;


import com.apps.bhojnama.R;
import com.apps.bhojnama.R.id;
import com.apps.bhojnama.R.layout;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutFragment extends Fragment {

	ImageView ivIcon;
	TextView tvItemName,desc;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	public AboutFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_about, container,
				false);

		ivIcon = (ImageView) view.findViewById(R.id.frag1_icon);
		tvItemName = (TextView) view.findViewById(R.id.frag1_text);

		tvItemName.setText(getArguments().getString(ITEM_NAME));
		ivIcon.setImageDrawable(view.getResources().getDrawable(
				getArguments().getInt(IMAGE_RESOURCE_ID)));
		desc= (TextView) view.findViewById(R.id.textView1);
		
		Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/OpenSans-Light.ttf");
		desc.setTypeface(type);
		
		return view;
	}

}
