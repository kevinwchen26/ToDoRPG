package com.cs429.todorpg.revised;

import java.util.ArrayList;

import com.cs429.todorpg.revised.controller.StatAdapter;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ListView;

public class PlayerStats extends BaseActivity {

	private SQLiteHelper db;

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

	private void SetAdapter() {
		ArrayList<Stat> stats = db.getStats();
		ListView stat_list = (ListView) findViewById(R.id.stats_list);
		StatAdapter statAdapter = new StatAdapter(this, stats);
		stat_list.setAdapter(statAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_stats, menu);
		return true;
	}

}
