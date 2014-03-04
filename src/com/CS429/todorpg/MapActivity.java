package com.CS429.todorpg;

import java.util.ArrayList;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MapActivity extends Activity implements OnMarkerClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
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