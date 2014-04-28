package com.cs429.todorpg.revised.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;

import com.cs429.todorpg.revised.EventLogActivity;
import com.cs429.todorpg.revised.PlayerStatsActivity;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class LogTest extends ActivityInstrumentationTestCase2<EventLogActivity> {

	private SQLiteHelper db;

	public LogTest() {
		super(EventLogActivity.class);
	}



}
