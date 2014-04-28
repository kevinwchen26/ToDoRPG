package com.cs429.todorpg.revised.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;

import com.cs429.todorpg.revised.DailyActivity;
import com.cs429.todorpg.revised.PlayerStatsActivity;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class StatsTest extends
		ActivityInstrumentationTestCase2<PlayerStatsActivity> {
	private SQLiteHelper db;

	public StatsTest() {
		super(PlayerStatsActivity.class);
	}
	

}
