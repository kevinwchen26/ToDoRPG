package com.CS429.todorpg.AndroidJUnitTests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.CS429.todorpg.MapActivity;
import com.CS429.todorpg.NearestQuest;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.widget.Button;

public class testMapActivity extends AndroidTestCase {

	NearestQuest testQuest;
	
	@Before
	protected void setUp() throws Exception {
		testQuest = new NearestQuest();
		testQuest.execute();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		testQuest = null;
	}

	/**
	 * tests if quests is not null.
	 * our database has valid data so it shouldn't be null at all.
	 * @throws InterruptedException 
	 * 
	 */
	@Test
	public synchronized void testPreCondition() throws InterruptedException{
		wait(2000);
		JSONArray quests = testQuest.getQuests();
		assertTrue(quests != null);
	}

	@Test
	public synchronized void testValidity() throws JSONException, InterruptedException{
		wait(2000);
		JSONArray quests = testQuest.getQuests();
		
		assertTrue(quests.length() == 33);
		ArrayList<MarkerOptions> options = MapActivity.getQuests(testQuest);
		
		//check valid data counts
		assertTrue(options.size() == 2);
		
		//testing match with valid data
		MarkerOptions option = options.get(0);
		assertEquals(option.getTitle(), "Quest 22 a");
		assertEquals(String.valueOf(option.getPosition().latitude), "40.1139208");
		assertEquals(String.valueOf(option.getPosition().longitude), "-88.2242436");
		
		option = options.get(1);
		assertEquals(option.getTitle(), "Quest 23 a");
		assertEquals(String.valueOf(option.getPosition().latitude), "40.1133377");
		assertEquals(String.valueOf(option.getPosition().longitude), "-88.2247903");
	}
	
	
	
	
}
