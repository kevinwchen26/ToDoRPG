package com.CS429.todorpg.AndroidJUnitTests;

import org.junit.Test;
import com.CS429.todorpg.MapActivity;
import com.CS429.todorpg.R;
import com.CS429.todorpg.StartMain;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.*;
import com.robotium.solo.Solo;
import android.annotation.SuppressLint;
import android.app.Instrumentation.ActivityMonitor;
import android.os.Handler;
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
/*
public class JoinQuestTests extends ActivityInstrumentationTestCase2<MapActivity> {

	private Solo solo;

	public JoinQuestTests(Class<MapActivity> activityClass) {
		super(activityClass);
	}

	public JoinQuestTests() {
		super(MapActivity.class);
	}

	private MapActivity activity;

	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@SuppressLint("NewApi")
	public void testJoinTestPass() throws Exception {
		solo.assertCurrentActivity("wrong activity", MapActivity.class);
		final GoogleMap map = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map)).getMap();
		assertNotNull(map);
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() { // adds first test marker

			@Override
			public void run() {
				map.clear();
				int num = (int) (Math.random() * 10000+1);
				Marker marker = map.addMarker(new MarkerOptions().title("Quest " + num).snippet("My Location.").position(MapActivity.getLocation(activity)));
				activity.onMarkerClick(marker);
			}
		});
		assertTrue(solo.searchText("Quest Joined"));

	}

	public void testJoinTestFail() throws Exception {
		solo.assertCurrentActivity("wrong activity", MapActivity.class);
		final GoogleMap map = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map)).getMap();
		assertNotNull(map);
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() { // adds first test marker

			@Override
			public void run() {
				map.clear();
				Marker marker = map.addMarker(new MarkerOptions().title("Quest 14").snippet("My Location.").position(MapActivity.getLocation(activity)));
				activity.onMarkerClick(marker);
			}

		});
		assertTrue(solo.searchText("You are already a member of this quest"));

	}
}
*/
