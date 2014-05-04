package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.DailyAdapter;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.service.AlarmReceiver;

/**
 * Daily Activity
 * 
 * @author hlim10, ssong25
 * 
 */
public class DailyActivity extends BaseActivity {
	private EditText add_daily_field;
	private CheckBox alarm_check;
	private ListView daily_list;
	private ArrayList<Daily> daily;
	private DailyAdapter adapter;

	private SharedPreferences pref;
	private boolean IsAlarmSet;
	private final int ALARMCODE = 77;
	private SQLiteHelper db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.daily_activity);
		setHeader(R.id.header);
		findViewById();
		db = new SQLiteHelper(getBaseContext());
		setDailyList();
		SetAdapter();
	}

	/**
	 * Resume Activity
	 */
	@Override
	public void onResume() {
		Log.e("[LifeCycle]", "DailyActivity: ++onResume++");
		super.onResume();
		pref = this.getSharedPreferences("ToDoRPG", MODE_PRIVATE);
		IsAlarmSet = pref.getBoolean("alarm", false);
		init();
	}

	/**
	 * Pause Activity
	 */
	@Override
	public void onPause() {
		Log.e("[LifeCycle]", "DailyActivity: ++onPause++");
		super.onPause();
		SharedPreferences.Editor ed = pref.edit();
		ed.putBoolean("alarm", IsAlarmSet);
		ed.commit();
	}

	/**
	 * Connect Id
	 */
	private void findViewById() {
		add_daily_field = (EditText) findViewById(R.id.add_daily_field);
		daily_list = (ListView) findViewById(R.id.daily_listview);
		findViewById(R.id.add_daily_button).setOnClickListener(ButtonHandler);
		alarm_check = (CheckBox) findViewById(R.id.alarm_checkbox);
		alarm_check.setOnCheckedChangeListener(CheckListener);

	}

	/**
	 * Initialize the Alarm
	 */
	private void init() {
		if (IsAlarmSet) {
			Log.d("[DAY]", "alarm has already been invoked..??");
			alarm_check.setChecked(true);
		}
	}

	/**
	 * Alarm Listener
	 */
	CheckBox.OnCheckedChangeListener CheckListener = new CheckBox.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
			// set alarm
			if (isChecked) {
				IsAlarmSet = isChecked;
				Log.d("[DAY]", "Alarm invoked");
				intent.putExtra("purpose", "invoke");
			}
			// cancel alarm
			else {
				IsAlarmSet = isChecked;
				Log.d("[DAY]", "Alarm canceled");
				intent.putExtra("purpose", "cancel");
			}
			intent.putExtra("Alarmkey", ALARMCODE);
			Log.d("[Alarm]", "key is : " + ALARMCODE);
			sendBroadcast(intent);
		}
	};
	/**
	 * Add Daily Activity when Click Add button
	 */
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.add_daily_button:
				String my_daily = add_daily_field.getText().toString();
				if (my_daily.isEmpty()) {
					Toast.makeText(DailyActivity.this, "Fill in the blank",
							Toast.LENGTH_SHORT).show();
					return;
				}
				AddMyDaily(daily, my_daily);
				add_daily_field.setText("");
				SetAdapter();
				break;
			}
		}

	};

	/**
	 * Check for duplicate in Daily. If no duplicate found, add daily to new
	 * ArrayList
	 */
	private void AddMyDaily(ArrayList<Daily> daily, String my_daily) {
		for (int i = 0; i < daily.size(); i++) {
			if (daily.get(i).getDaily().equals(my_daily)) {
				Toast.makeText(DailyActivity.this,
						"\"" + my_daily + "\" is alreay in your daily list",
						Toast.LENGTH_SHORT).show();
				return;
			}
		}
		Daily day = new Daily(my_daily);
		daily.add(day);
		int id = db.addDaily(day);
		day.setKey(id);

		Toast.makeText(DailyActivity.this, my_daily, Toast.LENGTH_SHORT).show();
		adapter = new DailyAdapter(DailyActivity.this, daily);
		adapter.notifyDataSetChanged();
	}

	/**
	 * Set Adapter of Daily Activity
	 */
	private void SetAdapter() {
		adapter = new DailyAdapter(DailyActivity.this, daily);
		daily_list.setAdapter(adapter);
	}

	/**
	 * Get Daily list from DB
	 */
	private void setDailyList() {
		daily = db.getDailies(3);
		if (daily == null)
			daily = new ArrayList<Daily>();
	}
}
