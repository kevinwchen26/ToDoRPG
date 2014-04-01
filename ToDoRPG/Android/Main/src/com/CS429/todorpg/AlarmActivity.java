package com.CS429.todorpg;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

public class AlarmActivity extends Activity {

	private static final String everyday = "everyday";
	private static final String twoday = "twoday";
	private static final String once = "once";
	private static final String noring = "noring";
	private static final String ringtone = "ringtone";
	private static final String vibrate = "vibrate";
	
	private String chosen = "";
	private String notitype = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		ActivitySizeHandler();
		findViewById();
	}
	
	
	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(
		WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params);
	}
	
	
	private RadioButton everydayBtn;
	private RadioButton twodayBtn;
	private RadioButton onceBtn;
	
	private RadioButton ringtoneBtn;
	private RadioButton vibrateBtn;
	private RadioButton noringBtn;
	
	private void findViewById(){
		everydayBtn = (RadioButton)findViewById(R.id.alarm_everyday);
		twodayBtn = (RadioButton)findViewById(R.id.alarm_everytwoday);;
		onceBtn = (RadioButton)findViewById(R.id.alarm_once);;
		
		ringtoneBtn = (RadioButton)findViewById(R.id.alarm_ringtone);;
		vibrateBtn = (RadioButton)findViewById(R.id.alarm_vibrate);;
		noringBtn = (RadioButton)findViewById(R.id.alarm_noring);;
	}
	
	
	public void onRadioButtonClicked(View view){
		
		switch(view.getId()){
		
		case R.id.alarm_everyday:
			chosen = everyday;
			everydayBtn.setChecked(true);
			twodayBtn.setChecked(false);
			onceBtn.setChecked(false);
			break;
		
		case R.id.alarm_everytwoday:
			chosen = twoday;
			everydayBtn.setChecked(false);
			twodayBtn.setChecked(true);
			onceBtn.setChecked(false);
			break;
			
		case R.id.alarm_once:
			chosen = once;
			everydayBtn.setChecked(false);
			twodayBtn.setChecked(false);
			onceBtn.setChecked(true);
			break;
		
		case R.id.alarm_ringtone:
			notitype = ringtone;
			ringtoneBtn.setChecked(true);
			vibrateBtn.setChecked(false);
			noringBtn.setChecked(false);
			break;
		
		case R.id.alarm_vibrate:
			notitype = vibrate;
			ringtoneBtn.setChecked(false);
			vibrateBtn.setChecked(true);
			noringBtn.setChecked(false);
			break;
		
		case R.id.alarm_noring:
			notitype = noring;
			ringtoneBtn.setChecked(false);
			vibrateBtn.setChecked(false);
			noringBtn.setChecked(true);
			break;
			
		default:
			chosen = "none";
			notitype = "none";
			break;
		}
	}
	
	public void onButtonClicked(View view){
		
		switch(view.getId()){
		
		case R.id.alarm_okay:
			Intent intent = new Intent();
			intent.putExtra("alarm", chosen);
			intent.putExtra("noti", notitype);
			setResult(RESULT_OK, intent);
			finish();
			break;
		
		default:
			break;
		}
	}
}
