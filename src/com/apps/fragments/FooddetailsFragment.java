package com.apps.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.adapter.HottestFoodItemAdapter;
import com.apps.bhojnama.NearbyMapDirectionActivity;
import com.apps.bhojnama.R;
import com.apps.bhojnamainfo.BhojNamaSingleton;

public class FooddetailsFragment extends Fragment {
	
	public FooddetailsFragment() {}
	
	private View rootView;
	
	private TextView txtFoodName, txtIngrediants, txtPrice;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_food_details, container, false);
		
		int base_position = getArguments().getInt("base_position");
		
		txtFoodName = (TextView) rootView.findViewById(R.id.item_name);
		txtFoodName.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(base_position).getHottestFoodItemList().get(getArguments().getInt("position")).getFoodTitle());
		
		txtIngrediants = (TextView) rootView.findViewById(R.id.ingrediants);
		txtIngrediants.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(base_position).getHottestFoodItemList().get(getArguments().getInt("position")).getIngredients());
		
		txtPrice = (TextView) rootView.findViewById(R.id.price);
		txtPrice.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(base_position).getHottestFoodItemList().get(getArguments().getInt("position")).getPrice());
		//txtViewRestaurantAddress = (TextView) rootView.findViewById(R.id.txt_view_address);
		//txtViewRestaurantAddress.setText(BhojNamaSingleton.getInstance().getHottestInfoList().get(getArguments().getInt("position")).getCity());

	/*	rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		rootView.setOnKeyListener(new View.OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
		            String tag = null;
					Log.e(tag, "keyCode: " + keyCode);
		            if( keyCode == KeyEvent.KEYCODE_BACK ) {
		                Log.e(tag, "onKey Back listener is working!!!");
		                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		                return true;
		            } else {
		                return false;
		            }
		        }
		    });*/

		return rootView;
	}
	
	

}
