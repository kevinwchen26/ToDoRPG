package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.cs429.todorpg.revised.controller.DailyAdapter;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class AlarmNotification extends Activity {
	private ListView finished_list, missed_list;
	private SQLiteHelper db;
	ArrayList<Daily> finished_arr, missed_arr;
	private DailyAdapter finished_adapter, missed_adapter;
	private Vibrator v;
	// private Ringtone r;
	private static long[] pattern = { 1000, 200, 1000, 2000, 1200 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_notification);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		findViewById();
		init();
		db = new SQLiteHelper(this);
		getData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		v.cancel();
	}

	private void init() {
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(pattern, 0);
	}

	private void findViewById() {
		finished_list = (ListView)findViewById(R.id.finished_list);
		missed_list = (ListView)findViewById(R.id.missed_list);
	}
	private void getData() {
		finished_arr = db.getDailies(1);
		missed_arr = db.getDailies(2);
		finished_adapter = new DailyAdapter(AlarmNotification.this, finished_arr);
		finished_list.setAdapter(finished_adapter);
		missed_adapter = new DailyAdapter(AlarmNotification.this, missed_arr);
		missed_list.setAdapter(missed_adapter);
	}

	Button.OnClickListener mListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			default:
				break;
			}
		}
	};

}
