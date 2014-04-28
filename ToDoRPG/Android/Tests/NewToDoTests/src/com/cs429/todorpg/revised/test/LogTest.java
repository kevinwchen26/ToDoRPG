package com.cs429.todorpg.revised.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.ListView;

import com.cs429.todorpg.revised.DailyActivity;
import com.cs429.todorpg.revised.EventLogActivity;
import com.cs429.todorpg.revised.MainActivity;
import com.cs429.todorpg.revised.PlayerStatsActivity;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.controller.LogAdapter;
import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.robotium.solo.Solo;

import android.widget.PopupMenu;

public class LogTest extends ActivityInstrumentationTestCase2<EventLogActivity> {

	private SQLiteHelper db;
	private EventLogActivity mActivity;
	private Solo solo;

	public LogTest() {
		super(EventLogActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getActivity().getBaseContext(), "test_");
		db = new SQLiteHelper(context);

		mActivity = getActivity();
		solo = new Solo(getInstrumentation(), mActivity);
	}

	public void testLog() throws Throwable {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

		String poop_date = df.format(c.getTime());
		final LogItem poop = new LogItem("Pooped", poop_date);

		String eat_date = df.format(c.getTime());
		final LogItem eat = new LogItem("Ate", eat_date);

		String sleep_date = df.format(c.getTime());
		final LogItem sleep = new LogItem("Slept", sleep_date);

		assertNotSame(-1, db.addLogItem(poop));
		assertNotSame(-1, db.addLogItem(sleep));
		assertNotSame(-1, db.addLogItem(eat));

		ArrayList<LogItem> log = db.getLog();
		assertNotNull(log);
		assertEquals(3, log.size());
		assertTrue(log.contains(poop));
		assertTrue(log.contains(eat));
		assertTrue(log.contains(sleep));
		// solo.clickOnView(solo.getView(R.id.character_status));
		// solo.clickOnMenuItem("Event Log");
		final ListView log_list = (ListView)solo.getView(R.id.log_list);

		mActivity.logAdapter = new LogAdapter(mActivity, log);
		Runnable run = new Runnable() {
			public void run() {
				// reload content

				mActivity.addLog(poop);
				mActivity.addLog(eat);
				mActivity.addLog(sleep);
			}
		};
		runTestOnUiThread(run);
		solo.sleep(1000);
		
		// assertEquals(4, log_list.getAdapter().getCount());
		assertTrue(solo.searchText("Pooped"));
		assertTrue(solo.searchText("Ate"));
		assertTrue(solo.searchText("Slept"));
	}

	public void testLogCRUD() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

		String poop_date = df.format(c.getTime());
		LogItem poop = new LogItem("Pooped", poop_date);

		String eat_date = df.format(c.getTime());
		LogItem eat = new LogItem("Ate", eat_date);

		String sleep_date = df.format(c.getTime());
		LogItem sleep = new LogItem("Slept", sleep_date);

		assertNotSame(-1, db.addLogItem(poop));
		assertNotSame(-1, db.addLogItem(sleep));
		assertNotSame(-1, db.addLogItem(eat));

		ArrayList<LogItem> log = db.getLog();
		assertNotNull(log);
		assertEquals(3, log.size());

		assertTrue(log.contains(poop));
		assertTrue(log.contains(eat));
		assertTrue(log.contains(sleep));
	}
}
