package com.apps.bhojnama;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.adapter.CustomDrawerAdapter;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.datamodel.DrawerItem;
import com.apps.fragments.AboutFragment;
import com.apps.fragments.BooktableFragment;
import com.apps.fragments.FoodShotsFragment;
import com.apps.fragments.HelpFragment;
import com.apps.fragments.HottestFragment;
import com.apps.fragments.LocationFragment;
import com.apps.fragments.LogInFragment;
import com.apps.fragments.NearbyFragment;
import com.facebook.Session;
import com.google.android.gms.plus.model.people.Person.Image;

//this is the main activity class
public class MainActivity extends Activity {
	public static boolean isLogin = false;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.apps.bhojnama", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:",
						"######"
								+ Base64.encodeToString(md.digest(),
										Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			Log.e("HASH ERROE:", "######" + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			Log.e("HASH EROE:", "######" + e.getMessage());
		}*/
		setContentView(R.layout.activity_main);

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem(true)); // adding a spinner to the list

		dataList.add(new DrawerItem("Find Nearby Restaurants", R.drawable.nearby_icon));
		dataList.add(new DrawerItem("Hottest Restaurants", R.drawable.hottest_icon));
		dataList.add(new DrawerItem("Book A Table", R.drawable.book_a_table_icon));
		dataList.add(new DrawerItem("Location", R.drawable.location_menu_icon));
		dataList.add(new DrawerItem("Food Shots", R.drawable.food_shot_icon));
		// dataList.add(new DrawerItem("User Review",
		// R.drawable.ic_action_cloud));

		dataList.add(new DrawerItem("Other Option")); // adding a header to the
														// list
		dataList.add(new DrawerItem("About", R.drawable.about_icon));
		dataList.add(new DrawerItem("Login", R.drawable.settings_icon));
		dataList.add(new DrawerItem("Help", R.drawable.help_icon));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.drawer_icon_only, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
			
			
			

			public void onDrawerOpened(View drawerView) {
				SharedPref sharedPref = new SharedPref(MainActivity.this);
				if (sharedPref.getLoginStatus().equalsIgnoreCase("1")) {
					dataList.get(8).setItemName("Logout");
					dataList.get(8).setImgResID(R.drawable.settings_icon);
					adapter.notifyDataSetChanged();
				} else {
					dataList.get(8).setItemName("Login");
					dataList.get(8).setImgResID(R.drawable.settings_icon);
					adapter.notifyDataSetChanged();
				}

				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			SelectItem(1);
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = mInflater.inflate(R.layout.action_bar, null);
		
		TextView txtView = (TextView) v.findViewById(R.id.mytext);
		txtView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDrawerLayout.openDrawer(Gravity.START);
				Toast.makeText(getApplicationContext(), "It Works", Toast.LENGTH_SHORT).show();
			}
		});
		
		ImageView btnDrawerMenu = (ImageView) v.findViewById(R.id.btnDrawerMenu);
		btnDrawerMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
					//mDrawerLayout.openDrawer(Gravity.START);
					mDrawerLayout.closeDrawer(Gravity.START);
				} else {
					mDrawerLayout.openDrawer(Gravity.START);
				}
			}
		});
		
		
		
		
		
		getActionBar().setCustomView(v);
		View v1 = getActionBar().getCustomView();
		LayoutParams lp = (LayoutParams) v1.getLayoutParams();
		lp.width = LayoutParams.MATCH_PARENT;
		v.setLayoutParams(lp);
		
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Light.ttf"); 
		TextView txtyour= (TextView) findViewById(R.id.mytext);
		txtyour.setTypeface(type);
	
		

	}

	// Intent i1 = new Intent(MainActivity.this, PhoneActivity.class);
	// startActivity(i1);

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SelectItem(int possition) {
		Fragment fragment = null;
		Bundle args = new Bundle();

		switch (possition) {
		case 1:
			fragment = new NearbyFragment();
			break;

		case 2:
			fragment = new HottestFragment();
			break;

		case 3:
			fragment = new BooktableFragment();
			break;

		case 4:
			fragment = new LocationFragment();
			break;

		case 5:

			fragment = new FoodShotsFragment();
			break;

		case 6:
			// /
			break;

		case 7:
			fragment = new AboutFragment();
			args.putString(AboutFragment.ITEM_NAME, dataList.get(possition).getItemName());
			args.putInt(AboutFragment.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
			break;

		case 8:
			SharedPref sharedPref = new SharedPref(this);
			
			if (sharedPref.getLoginStatus().equalsIgnoreCase("1")) {
				sharedPref.setLoginStatus("0");
				dataList.get(8).setItemName("Login");
				dataList.get(8).setImgResID(R.drawable.settings_icon);
				adapter.notifyDataSetChanged();
				mDrawerLayout.closeDrawer(Gravity.START);
				return;
				
			} else {
				fragment = new LogInFragment();
				args.putString(LogInFragment.ITEM_NAME, dataList.get(possition).getItemName());
				args.putInt(LogInFragment.IMAGE_RESOURCE_ID,dataList.get(possition).getImgResID());
				
			}
			
			break;

		case 9:

			fragment = new HelpFragment();
			args.putString(HelpFragment.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(HelpFragment.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;

		default:
			break;
		}

		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		int count = frgManager.getBackStackEntryCount();
		for (int i = 0; i < count; ++i) {
			frgManager.popBackStackImmediate();
		}
		android.app.FragmentTransaction ft = frgManager.beginTransaction();
		// ft.add(R.id.content_frame, fragment);
		// ft.addToBackStack(null);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();

		// frgManager.beginTransaction().replace(R.id.content_frame,
		// fragment).commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}

		}
	}

	/*
	 * @Override public void onBackPressed() { AlertDialog.Builder builder = new
	 * AlertDialog.Builder(this); builder.setTitle("Exit");
	 * builder.setMessage("Are You Sure?");
	 * 
	 * builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int which) {
	 * dialog.dismiss(); finish(); } });
	 * 
	 * builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * dialog.dismiss(); } }); AlertDialog alert = builder.create();
	 * alert.show(); }
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(MainActivity.this,
				requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	@Override
	protected void onResume() {
		try {
			FoodShotsFragment.foodShotsAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			
		}
		super.onResume();
		

	}

}
