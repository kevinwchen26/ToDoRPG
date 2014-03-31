package com.CS429.todorpg;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

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
	}
	
	
	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(
		WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params);
	}
	
	public void onRadioButtonClicked(View view){
		
		switch(view.getId()){
		
		case R.id.alarm_everyday:
			chosen = everyday;
			break;
		
		case R.id.alarm_everytwoday:
			chosen = twoday;
			break;
			
		case R.id.alarm_once:
			chosen = once;
			break;
		
		case R.id.alarm_ringtone:
			notitype = ringtone;
			break;
		
		case R.id.alarm_vibrate:
			notitype = vibrate;
			break;
		
		case R.id.alarm_noring:
			notitype = noring;
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
