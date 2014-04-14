package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cs429.todoprg.service.AlarmReceiver;
import com.cs429.todoprg.service.AlarmService;
import com.cs429.todorpg.revised.controller.DailyAdapter;
import com.cs429.todorpg.revised.model.Daily;


public class DailyActivity extends BaseActivity {
	private EditText add_daily_field;
	private CheckBox alarm_check;
	private ListView daily_list;
	private ArrayList<Daily> daily;
	private DailyAdapter adapter;
	
	private SharedPreferences pref;
	private boolean IsAlarmSet;
	private final int ALARMCODE = 77;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_activity);
		setHeader(R.id.header);
		daily = new ArrayList<Daily>();
		// new today_vice().execute();
		findViewById();
	}
	
	@Override
	public void onResume(){
		Log.e("[LifeCycle]", "DailyActivity: ++onResume++");
		super.onResume();
		pref = this.getSharedPreferences("ToDoRPG", MODE_PRIVATE);
		IsAlarmSet = pref.getBoolean("alarm", false);
		init();
	}
	
	@Override
	public void onPause(){
		Log.e("[LifeCycle]", "DailyActivity: ++onPause++");
		super.onPause();
		SharedPreferences.Editor ed = pref.edit();
		ed.putBoolean("alarm", IsAlarmSet);
		ed.commit();
	}
	
	private void findViewById() {
		add_daily_field = (EditText) findViewById(R.id.add_daily_field);
		daily_list = (ListView) findViewById(R.id.daily_listview);
		findViewById(R.id.add_daily_button).setOnClickListener(ButtonHandler);
		alarm_check = (CheckBox) findViewById(R.id.alarm_checkbox);
		alarm_check.setOnCheckedChangeListener(CheckListener);
		
	}
	
	private void init(){
		if(IsAlarmSet){
			Log.d("[DAY]", "alarm has already been invoked..??");
			alarm_check.setChecked(true);
		}
	}
	
	CheckBox.OnCheckedChangeListener CheckListener = new CheckBox.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
			//set alarm
			if(isChecked){
				IsAlarmSet = isChecked;
				Log.d("[DAY]", "Alarm invoked");
				intent.putExtra("purpose", "invoke");
			}
			//cancel alarm
			else{
				IsAlarmSet = isChecked;
				Log.d("[DAY]", "Alarm canceled");
				intent.putExtra("purpose", "cancel");
			}
			intent.putExtra("Alarmkey", ALARMCODE);
			Log.d("[Alarm]", "key is : " + ALARMCODE);
			sendBroadcast(intent);
		}
	};
	
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.add_daily_button:
					String my_daily = add_daily_field.getText().toString();
					if(my_daily.isEmpty()) {
						Toast.makeText(DailyActivity.this, "Fill in the blank", Toast.LENGTH_SHORT).show();
						return;
					}
					AddMyDaily(daily, my_daily);
					add_daily_field.setText("");
					SetAdapter();
					break;
			}
		}
		
	};
	private void AddMyDaily(ArrayList<Daily> daily, String my_daily) {
		for(int i = 0; i < daily.size(); i++) {
			if(daily.get(i).getDaily().equals(my_daily)) {
				Toast.makeText(DailyActivity.this, "\"" + my_daily +"\" is alreay in your daily list", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		daily.add(new Daily(my_daily));
		Toast.makeText(DailyActivity.this, my_daily, Toast.LENGTH_SHORT).show();
//		SetAdapter();
		adapter = new DailyAdapter(DailyActivity.this, daily);
		adapter.notifyDataSetChanged();
	}
	private void SetAdapter() {
		adapter = new DailyAdapter(DailyActivity.this, daily);
		daily_list.setAdapter(adapter);
		

	}
}

