package com.cs429.todorpg.revised.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;

import com.cs429.todorpg.revised.DailyActivity;
import com.cs429.todorpg.revised.PlayerStatsActivity;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class StatsTest extends
		ActivityInstrumentationTestCase2<PlayerStatsActivity> {
	private SQLiteHelper db;
	private PlayerStatsActivity mActivity;

	public StatsTest() {
		super(PlayerStatsActivity.class);
	}
	
	public void setUp() {
		mActivity=getActivity();
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				mActivity, "test_");
		db = new SQLiteHelper(context);
	}
	
	public void testStatsCRUD(){
		
	}

}
