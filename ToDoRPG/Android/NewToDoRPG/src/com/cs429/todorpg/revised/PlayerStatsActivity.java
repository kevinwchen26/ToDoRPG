package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import com.cs429.todorpg.revised.controller.StatAdapter;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
/**
 * Player Stat Activity
 * @author kchen26, hlim10
 *
 */
public class PlayerStatsActivity extends BaseActivity {

	private SQLiteHelper db;
	public StatAdapter statAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_player_stats);
		setHeader(R.id.header);
		db = new SQLiteHelper(getBaseContext());
		SetAdapter();
	}
	/**
	 * Set Player Stat adapter
	 */
	private void SetAdapter() {
		ArrayList<Stat> stats = db.getStats();
		ListView stat_list = (ListView) findViewById(R.id.stats_list);
		statAdapter = new StatAdapter(this, stats);
		stat_list.setAdapter(statAdapter);
	}
}
