package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.CS429.todorpg.Utils.JSONParser;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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
	private NearestQuest questInBackground;
	private SharedPreferences prefs;
	private Context context;
	public ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		context = this;
		questInBackground = new NearestQuest();
		questInBackground.execute();

		// Get a handle to the Map Fragment
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		LatLng myLocation = getLocation(this);
		map.setOnMarkerClickListener(this);
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));

		try {
			for (MarkerOptions option : getQuests()) {
				map.addMarker(option);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.addMarker(new MarkerOptions().title("Quest 14").snippet("My Location.").position(getLocation(this)));

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

	@Override
	public boolean onMarkerClick(final Marker marker) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					String title = marker.getTitle();
					String[] words = title.split(" ");
					prefs = getSharedPreferences(StaticClass.MY_PREFERENCES, Context.MODE_PRIVATE);
					prefs.edit().putString("quest_id", words[1]).commit();
					new PutRelationship().execute();
					// try {
					// Thread.sleep(1000);
					// AlertDialog.Builder builder = new
					// AlertDialog.Builder(context);
					//
					// if (json == null)
					// builder.setMessage("Failed to Join Quest").show();
					// builder.setMessage(json.getString("success")).show();
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to join this quest?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

		return false;
	}

	public ArrayList<MarkerOptions> getQuests() throws JSONException, InterruptedException {

		//check if quests is null
		JSONArray quests = questInBackground.getQuests();
		
		ArrayList<MarkerOptions> options = new ArrayList<MarkerOptions>();
		if(quests == null){//waitied but still null means it doesn't have any quests
			Log.d("JSONQUEST", "still null??");
			return options;
		}
		
		Log.d("JSONQUEST", "length is; " + quests.length());
		// iterate all data in quests jsonarray
		 for (int i = 0; i < quests.length(); ++i) {
			 JSONObject object = quests.getJSONObject(i);
			 MarkerOptions option = new MarkerOptions();
			 
			//check if lat long are valid
			 String tmp1 = object.getString("quest_location_lat");
			 String tmp2 = object.getString("quest_location_long");
			 if((tmp1 == null || tmp1.isEmpty()) || (tmp2 == null || tmp2.isEmpty()))
				 continue;
			 
			 LatLng position = new LatLng(object.getDouble("quest_location_lat"),
					 object.getDouble("quest_location_long"));
			 option.snippet(object.getString("quest_description"));
			 option.title(object.getString("quest_title"));
			 option.position(position);
			 options.add(option);
			 Log.d("JSONQUEST", object.toString());
		 }
		Log.d("JSONQUEST", "done??");
		return options;
	}

	class PutRelationship extends AsyncTask<String, String, String> {

		private JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MapActivity.this);
			pDialog.setMessage("Joining Quest...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String quest_id = prefs.getString("quest_id", "-1");
			String profile_id = prefs.getString("profile_id", "-1");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("quest_id", quest_id));
			params.add(new BasicNameValuePair("profile_id", profile_id));

			JSONObject json = new JSONParser().makeHttpRequest(StaticClass.url_update_party, "POST", params);

			try {
				return json.getString("success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Failed to add quest";
		}

		protected void onPostExecute(String result) {
			try {
					Toast.makeText(MapActivity.this, result, Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pDialog.dismiss();
			finish();
		}
	}
}