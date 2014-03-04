package com.CS429.todorpg;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}