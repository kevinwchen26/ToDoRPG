package com.CS429.todorpg;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.widget.Toast;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapActivity extends Activity implements OnMarkerClickListener {

	private double longtitude;
	private double latitude;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// Get a handle to the Map Fragment
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		LatLng myLocation = getLocation(this);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));

		try {
			for (MarkerOptions option : getQuests()) {
				map.addMarker(option);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		map.addMarker(new MarkerOptions().title("Test").snippet("My Location.").position(getLocation(this)));

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
	public static LatLng getLocation(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// check if network is enabled
		boolean Networkenabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		// none of providers is available
		if (!Networkenabled) {
			return new LatLng(0, 0);

		} else {// at least one of them is available
			String locationProvider = LocationManager.NETWORK_PROVIDER;
			Location location = locationManager.getLastKnownLocation(locationProvider);
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			return new LatLng(latitude, longitude);
		}
	}

	/**
	 * 
	 */
	private void getNerestQuest(){
		JSONArray quests = NearestQuest.getInstance().getQuests();
		
		
		
	}
	
	@Override
	public boolean onMarkerClick(Marker marker) {
		String title = marker.getTitle();
		String[] words = title.split(" ");
		int quest_id = Integer.parseInt(words[1]);
		return false;
	}

	public ArrayList<MarkerOptions> getQuests() throws JSONException {
		JSONArray quests = NearestQuest.getInstance().getQuests();

		ArrayList<MarkerOptions> options = new ArrayList<MarkerOptions>();
		//iterate all data in quests jsonarray
		for(int i = 0; i < quests.length(); ++i){
			JSONObject object = quests.getJSONObject(i);
			MarkerOptions option = new MarkerOptions();
			LatLng position = new LatLng(object.getDouble("quest_location_lat"),
					object.getDouble("quest_location_long"));
			option.snippet("quest_description");
			option.title("quest_title");
			option.position(position);
			options.add(option);
		}
		
		return options;
/*		
		ArrayList<MarkerOptions> options = new ArrayList<MarkerOptions>();
		MarkerOptions option = new MarkerOptions();
		LatLng position = new LatLng(0, 0);
		option.snippet("Body");
		option.title("title");
		option.position(position);
		return options;
*/
	}
}