package com.cs429.todorpg.revised.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.ListView;

import com.cs429.todorpg.revised.DailyActivity;
import com.cs429.todorpg.revised.PlayerStatsActivity;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.controller.LogAdapter;
import com.cs429.todorpg.revised.controller.StatAdapter;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.robotium.solo.Solo;

public class StatsTest extends
		ActivityInstrumentationTestCase2<PlayerStatsActivity> {
	private SQLiteHelper db;
	private PlayerStatsActivity mActivity;
	private Solo solo;

	public StatsTest() {
		super(PlayerStatsActivity.class);
	}

	public void setUp() {
		mActivity = getActivity();
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				mActivity, "test_");
		db = new SQLiteHelper(context);
		solo = new Solo(getInstrumentation(), mActivity);

	}

	public void testStatList() throws Throwable {
		final Stat failed_iterations = new Stat("Iterations failed", 4);
		final Stat passed_itations = new Stat("Iterations passed", 1);
		final Stat screwed_students = new Stat("Screwed Students", 6);
		assertNotSame(-1, db.addStat(failed_iterations));
		assertNotSame(-1, db.addStat(passed_itations));
		assertNotSame(-1, db.addStat(screwed_students));
		ArrayList<Stat> stats = db.getStats();
		assertTrue(stats.contains(failed_iterations));
		assertTrue(stats.contains(passed_itations));
		assertTrue(stats.contains(screwed_students));
		passed_itations.setCount(2);
		screwed_students.setCount(0);
		assertNotSame(0, db.updateStat(passed_itations));
		assertNotSame(0, db.updateStat(screwed_students));
		stats = db.getStats();
		assertEquals(3, stats.size());
		assertTrue(stats.contains(passed_itations));
		assertTrue(stats.contains(screwed_students));

		final ListView stat_list = (ListView) solo.getView(R.id.stats_list);
		assertNotNull(stat_list);
		mActivity.statAdapter = new StatAdapter(mActivity, stats);
		Runnable run = new Runnable() {
			public void run() {
				mActivity.statAdapter.notifyDataSetChanged();
				stat_list.refreshDrawableState();

			}
		};
		runTestOnUiThread(run);

		// assertEquals(4, log_list.getAdapter().getCount());
		assertFalse(solo.searchText("Iterations failed"));
		assertFalse(solo.searchText("Iterations passed"));
		assertFalse(solo.searchText("Students screwed"));
	}

	public void testStatsCRUD() {
		Stat failed_iterations = new Stat("Iterations failed", 4);
		Stat passed_itations = new Stat("Iterations passed", 1);
		Stat screwed_students = new Stat("Screwed Students", 6);
		assertNotSame(-1, db.addStat(failed_iterations));
		assertNotSame(-1, db.addStat(passed_itations));
		assertNotSame(-1, db.addStat(screwed_students));
		ArrayList<Stat> stats = db.getStats();
		assertTrue(stats.contains(failed_iterations));
		assertTrue(stats.contains(passed_itations));
		assertTrue(stats.contains(screwed_students));
		passed_itations.setCount(2);
		screwed_students.setCount(0);
		assertNotSame(0, db.updateStat(passed_itations));
		assertNotSame(0, db.updateStat(screwed_students));
		stats = db.getStats();
		assertEquals(3, stats.size());
		assertTrue(stats.contains(passed_itations));
		assertTrue(stats.contains(screwed_students));

	}

}
