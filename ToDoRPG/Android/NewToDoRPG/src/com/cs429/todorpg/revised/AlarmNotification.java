package com.cs429.todorpg.revised;

import java.util.Calendar;

import com.cs429.todorpg.revised.R;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AlarmNotification extends Activity {

	 private Vibrator v;
//	 private Ringtone r;
	 private static long[] pattern = {1000, 200, 1000, 2000, 1200};
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_notification);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		findViewById();
		init();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		v.cancel();
	}
	
	private void init(){
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(pattern, 0);
	}
	
	private void findViewById(){
	}
	
	Button.OnClickListener mListener = new Button.OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			
			default:
				break;
			}
		}
	};
	
}
