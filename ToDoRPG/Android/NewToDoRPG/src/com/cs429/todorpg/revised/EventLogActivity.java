package com.cs429.todorpg.revised;

import java.util.ArrayList;

import com.cs429.todorpg.revised.controller.LogAdapter;
import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ListView;

public class EventLogActivity extends BaseActivity {

	private SQLiteHelper db;
	public LogAdapter logAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_event_log);
		setHeader(R.id.header);
		db = new SQLiteHelper(getBaseContext());
		SetAdapter();
	}

	private void SetAdapter() {
		ArrayList<LogItem> log = db.getLog();
		ListView log_list = (ListView) findViewById(R.id.log_list);
		logAdapter = new LogAdapter(this, log);
		log_list.setAdapter(logAdapter);
	}
	
	public void addLog(LogItem log){
		ListView list = (ListView)findViewById(R.id.log_list);
		logAdapter.log.add(log);
		logAdapter.notifyDataSetChanged();
		list.refreshDrawableState();
	}
	
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.event_log, menu); return true; }
	 */
}
