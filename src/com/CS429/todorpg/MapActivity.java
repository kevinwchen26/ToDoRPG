package com.CS429.todorpg;

import com.google.android.gms.maps.*;
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

public class MapActivity extends Activity implements LocationListener{

	private LocationManager mManager;
	private double longtitude;
	private double latitude;
	private String provider;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("[MapActivity]", "++onCreate++");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		Double lat = getIntent().getDoubleExtra("lat", 0);
		Double longitude = getIntent().getDoubleExtra("long", 0);
		LatLng location = new LatLng(lat, longitude);
		CameraUpdate center = CameraUpdateFactory.newLatLng(location);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

		map.moveCamera(center);
		map.animateCamera(zoom);
		map.addMarker(new MarkerOptions().title("").snippet("").position(location));
		
		/*current location setting*/
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
	
}