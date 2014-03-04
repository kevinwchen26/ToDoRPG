package com.CS429.todorpg;

import java.util.ArrayList;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.widget.Toast;
import android.util.Log;


public class MapActivity extends Activity implements LocationListener, OnMarkerClickListener{

	private LocationManager mManager;
	private double longtitude;
	private double latitude;
	private String provider;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("[MapActivity]", "++onCreate++");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// Get a handle to the Map Fragment
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		LatLng sydney = new LatLng(-33.867, 151.206);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

		for (MarkerOptions option : getQuests()) {
			map.addMarker(option);
		}
		map.addMarker(new MarkerOptions().title("Sydney")
				.snippet("The most populous city in Australia.")
				.position(sydney));

		setUpLocation();
	}

	@Override
	protected void onResume(){
		Log.e("[MapActivity]", "++onResume++");
		super.onResume();
		//request updates 
		mManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	protected void onPause(){
		Log.e("[MapActivity]", "++onPause++");
		super.onPause();
		//stop updates when the activity is not active
		mManager.removeUpdates(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	/**
	 * location setup method
	 */
	private void setUpLocation(){
		mManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		//check if GPS is enabled
		boolean GPSenabled = mManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//check if network is enabled
		boolean Networkenabled = mManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		//none of providers is available
		if(!GPSenabled && !Networkenabled){
			
		}
		else{//at least one of them is available
			Criteria criteria = new Criteria();
			provider = mManager.getBestProvider(criteria, true);
			Location location = mManager.getLastKnownLocation(provider);
			onLocationChanged(location);
		}
	}
	
	
	/*LocationListener method here...*/
	@Override
	public void onLocationChanged(Location location) {
		this.longtitude = location.getLongitude();
		this.latitude = location.getLatitude();
		Log.d("[MAPactivity]", "Location: " + this.latitude + ", " + this.longtitude);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), provider + " is Disabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(getApplicationContext(), provider + " is Enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onMarkerClick(Marker marker) {
		// pulls up quest info page
		// TODO link to quest info page
		return false;
	}

	public ArrayList<MarkerOptions> getQuests() {
		ArrayList<MarkerOptions> options = new ArrayList<MarkerOptions>();
		MarkerOptions option = new MarkerOptions();
		LatLng position = new LatLng(0, 0);
		option.snippet("Body");
		option.title("title");
		option.position(position);
		return options;

	}
}