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

/**
 * 
 * @author kchen26, hlim10
 * 
 */
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
	/**
	 * Add Event Log to Event Log Activity
	 * @param log
	 */
	public void addLog(LogItem log) {
		ListView list = (ListView) findViewById(R.id.log_list);
		logAdapter.log.add(log);
		logAdapter.notifyDataSetChanged();
		list.refreshDrawableState();
	}

}
