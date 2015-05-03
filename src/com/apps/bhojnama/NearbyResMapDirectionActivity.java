package com.apps.bhojnama;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.facebooksys.FacebookModule;
import com.apps.utility.GMapV2GetRouteDirection;
import com.apps.utility.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class NearbyResMapDirectionActivity extends Activity implements OnClickListener, OnItemClickListener {

	private ImageView imgViewTaxiLogo;
	private TextView txtViewTime;
	private TextView txtViewDate;
	private TextView txtViewRegNo;
	private TextView txtViewPickupToDestination;
	private TextView txtViewDistance;
	private TextView txtViewTitle;
	private TextView txtViewEstimatedTime;

	private Button btnContact;
	private Button btnCancelOrShare;
	private Button btnBack;
	private GoogleMap gmap;
	private int tabType = 0;

	private GMapV2GetRouteDirection routeDirection;
	private Document doc;
	private PolylineOptions rectLine;
	private MapView map;
	private int position;
	
	private LatLng destinationLatLon;
	private LatLng userLatLng;
	
	private FacebookModule fb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_map);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		fb = new FacebookModule(this, savedInstanceState);
		getActionBar().setTitle("Maps for direction");
		//fb.facebookLogin();
        initViews();
        loadData();
        
        
	}
	
	private void initViews() {
		gmap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		routeDirection = new GMapV2GetRouteDirection();
	}

	private void loadData() {
		
		String markerTitle = null;
		String markerSnippet = null;
		
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().getString("view_type").equalsIgnoreCase("hottest")) {
				position = getIntent().getExtras().getInt("list_position");
				destinationLatLon = new LatLng(BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getLat(),BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getLon());
				markerTitle = BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getRestaurantName();
				markerSnippet = BhojNamaSingleton.getInstance()
						.getArrayListNearByResInfo().get(position).getCity()
						+ ", "
						+ BhojNamaSingleton.getInstance().getArrayListNearByResInfo().get(position).getArea();
			} else {
				position = getIntent().getExtras().getInt("list_position");
				destinationLatLon = new LatLng(BhojNamaSingleton.getInstance().getArrayListNearByInfo().get(position).getLat(),BhojNamaSingleton.getInstance().getArrayListNearByInfo().get(position).getLon());
				markerTitle = BhojNamaSingleton.getInstance().getArrayListNearByInfo().get(position).getRestaurantName();
				markerSnippet = BhojNamaSingleton.getInstance().getArrayListNearByInfo().get(position).getRestaurantAddress();
			}
			
		}
		
		userLatLng = new LatLng(new GPSTracker(this).getLatitude(), new GPSTracker(this).getLongitude());
		
		Marker pickUpMarker = gmap.addMarker(new MarkerOptions().title("I am here")
		.position(userLatLng)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
		.snippet("My Current Location"));

		Marker destinationMarker = gmap.addMarker(new MarkerOptions()
		.title(markerTitle)
		.position(destinationLatLon)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
		.snippet(markerSnippet));

		if (gmap != null) {
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(userLatLng).zoom(12) // Sets the zoom
					.tilt(0) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
		
			gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			new GetRouteTask(userLatLng, destinationLatLon).execute();
		}
		
	}

	private void setListener() {
		btnContact.setOnClickListener(this);
		btnCancelOrShare.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
	
	}

	private class GetRouteTask extends AsyncTask<String, Void, String> {

		private ProgressDialog Dialog;
		String response = "";

		LatLng pickUpPosition, DestinationPostion;

		public GetRouteTask(LatLng pickUpPosition, LatLng DestinationPostion) {
			this.pickUpPosition = pickUpPosition;
			this.DestinationPostion = DestinationPostion;
		}

		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(NearbyResMapDirectionActivity.this);
			Dialog.setMessage("Loading...");
			Dialog.setCancelable(false);
			Dialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Get All Route values
			doc = routeDirection.getDocument(pickUpPosition, DestinationPostion, GMapV2GetRouteDirection.MODE_DRIVING);
			response = "Success";
		
			return response;

		}

		@Override
		protected void onPostExecute(String result) {
			if( Dialog.isShowing()){
				Dialog.dismiss();
			}
			
			ArrayList<LatLng> directionPoint = routeDirection.getDirection(doc);
			rectLine = new PolylineOptions().width(8).color(Color.RED);

			centerIncidentRouteOnMap(directionPoint);

			for (int i = 0; i < directionPoint.size(); i++) {
				rectLine.add(directionPoint.get(i));
			}
			// Adding route on the map
			//gmap.clear();
			gmap.addPolyline(rectLine);
			
		}
	}

	public void centerIncidentRouteOnMap(ArrayList<LatLng> copiedPoints) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home: 
            // API 5+ solution
            onBackPressed();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
}
