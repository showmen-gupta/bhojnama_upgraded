package com.apps.bhojnama;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.apps.adapter.CustomDrawerAdapter;
import com.apps.datamodel.DrawerItem;
import com.apps.fragments.AboutFragment;
import com.apps.fragments.BooktableFragment;
import com.apps.fragments.FoodShotsFragment;
import com.apps.fragments.HelpFragment;
import com.apps.fragments.HottestFragment;
import com.apps.fragments.LocationFragment;
import com.apps.fragments.NearbyFragment;
import com.apps.fragments.SignUpFragment;
import com.apps.fragments.UserReviewFragment;
import com.facebook.Session;


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
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.apps.bhojnama", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:", "######" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			Log.e("HASH ERROE:", "######" + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			Log.e("HASH EROE:", "######" + e.getMessage());
		}
		
		setContentView(R.layout.activity_main);
		
		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem(true)); // adding a spinner to the list

		dataList.add(new DrawerItem("Hottest Restaurant", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Nearby Restaurant", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("Book A Table", R.drawable.ic_action_gamepad));
		dataList.add(new DrawerItem("Location", R.drawable.ic_action_labels));
		dataList.add(new DrawerItem("Food Shots", R.drawable.ic_action_search));
		dataList.add(new DrawerItem("User Review", R.drawable.ic_action_cloud));
		
		dataList.add(new DrawerItem("Other Option")); // adding a header to the list
		dataList.add(new DrawerItem("About", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Sign Up", R.drawable.ic_action_settings));
		dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				
				if (isLogin) {
					dataList.get(9).setItemName("Logout");
					dataList.get(9).setImgResID(R.drawable.ic_action_settings);
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
		

	}
	
//	Intent i1 = new Intent(MainActivity.this, PhoneActivity.class);
//	startActivity(i1);	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SelectItem(int possition) {
		Fragment fragment = null;
		Bundle args = new Bundle();
		
		switch (possition) {
			case 1:
				fragment = new HottestFragment();
				break;
	
			case 2:
				fragment = new NearbyFragment();
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
				fragment = new UserReviewFragment();
				break;
				
			case 7:
				/////
				break;
				
			case 8:
				fragment = new AboutFragment();
				args.putString(AboutFragment.ITEM_NAME, dataList.get(possition).getItemName());
				args.putInt(AboutFragment.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
				break;
				
			case 9:
				fragment = new SignUpFragment();
				args.putString(SignUpFragment.ITEM_NAME, dataList.get(possition).getItemName());
				args.putInt(SignUpFragment.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
				break;
				
			case 10:
				fragment = new HelpFragment();
				args.putString(HelpFragment.ITEM_NAME, dataList.get(possition).getItemName());
				args.putInt(HelpFragment.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
	
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
		//ft.add(R.id.content_frame, fragment);
		//ft.addToBackStack(null);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();
		
		//frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

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

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}

		}
	}
	
	/* @Override
    public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Exit");
	    builder.setMessage("Are You Sure?");
	
	    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                    dialog.dismiss();
	                    finish();
	            }
	        });
	
	    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
	        }
	    });
	    AlertDialog alert = builder.create();
	    alert.show();
    }
	*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(MainActivity.this, requestCode, resultCode, data);
	}
		
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
		
}
