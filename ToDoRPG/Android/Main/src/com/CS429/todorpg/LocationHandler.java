package com.CS429.todorpg;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationHandler {

	
	private static double longitude;
	private static double latitude;
	private static boolean ready;
	
	private static Context context;
	private static String locationprovider;
	
	private static LocationListener mListener;
	private static LocationManager mManager;
	private static ProgressDialog pDialog;
	private static AlertDialog aDialog;
	
	
	
	public static void setHandler(Context AppContext){
		context = AppContext;
		ready = false;
		init();
	}
	
	public static boolean getLocation(){
		//one more check to make sure location service is enabled
		if(!mManager.isProviderEnabled(locationprovider)){
			Toast.makeText(context, "location source needs to be on", Toast.LENGTH_SHORT).show();
			return false;
		}
		Log.d("[Location]", "location method invoked");
		pDialog.show();
		mManager.requestLocationUpdates(locationprovider, 0, 0, mListener);	
		Log.d("[Location]", "getLocation: " + longitude + ", " + latitude);
		return true;
	}
	
	public static double getLatitude(){
		return latitude;
	}
	
	public static double getLongitude(){
		return longitude;
	}
	
	public static boolean isLocationReady(){
		return ready;
	}
	
	private static void init(){
		longitude = latitude = -1;
		setListener();
		setDialog();
		locationprovider = setManager();
	}
	
	private static void setListener(){
		Log.d("[Location]", "location listener set");
		mListener = new LocationListener(){
			@Override
			public void onLocationChanged(Location location) {
				longitude = location.getLongitude();
				latitude = location.getLatitude();
				Log.d("[Location]", "location changed..." + latitude + ", " + longitude);
				pDialog.dismiss();
				ready = true;
				mManager.removeUpdates(mListener);
				convertToAddress(latitude, longitude);
			}

			@Override
			public void onProviderDisabled(String provider) {
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				
			}
		};
	}
	
	private static void setDialog(){
		Log.d("[Location]", "progress dialog set");
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Receiving location");
		pDialog.setCancelable(false);
		pDialog.setIndeterminate(false);
		pDialog.setCanceledOnTouchOutside(false);
		
		Log.d("[Location]", "alert dialog set");
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("You need to turn on location service for this. Will you keep going?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					context.startActivity(intent);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		aDialog = builder.create();
	}
	
	private static String setManager(){
		Log.d("[Location]", "location manager set");
		mManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
	
		String provider = mManager.getBestProvider(new Criteria(), false);
		boolean ProviderEnabled = mManager.isProviderEnabled(provider);
		if(!ProviderEnabled)
			aDialog.show();
/*		
		boolean NetworkEnabled = mManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean GPSEnabled = mManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		String provider = null;
		
		//no service is available..
		if(!NetworkEnabled && !GPSEnabled){
			aDialog.show();
		}
		else if(NetworkEnabled){
			Log.d("[Location]", "Network is enabled");
			provider = LocationManager.NETWORK_PROVIDER;
		}
		else if(GPSEnabled){
			Log.d("[Location]", "GPS is enabled");
			provider = LocationManager.GPS_PROVIDER;
		}
*/		
		//error handling
		if(provider == null){
			Log.d("[Location]", "Invalid provider setting error");
		}
		return provider;
	}
	
	/**
	 * 
	 * @param Lat
	 * @param Long
	 * @return
	 */
	private static String convertToAddress(double Lat, double Long){
		
		Geocoder geo = new Geocoder(context);
		try {
			List<Address> addresses = geo.getFromLocation(Lat, Long, 2);
			for(int i = 0; i < addresses.size(); ++i)
				Log.d("[Location]", i + ": " + addresses.get(i).getAddressLine(1).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}