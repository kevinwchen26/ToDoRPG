package com.CS429.todorpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class AlarmActivity extends Activity {

	private static final String everyday = "everyday";
	private static final String twoday = "twoday";
	private static final String once = "once";
	
	private String chosen = "";
	
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
		
		default:
			chosen = "none";
			break;
		}
	}
	
	public void onButtonClicked(View view){
		
		switch(view.getId()){
		
		case R.id.alarm_okay:
			Intent intent = new Intent();
			intent.putExtra("alarm", chosen);
			setResult(RESULT_OK, intent);
			finish();
			break;
		
		default:
			break;
		}
	}
}
