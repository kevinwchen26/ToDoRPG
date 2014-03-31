package com.CS429.todorpg;

import java.util.Calendar;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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
	 private Ringtone r;
	 private static long[] pattern = {1000, 200, 1000, 2000, 1200};
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_notification);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		init();
		findViewById();
	}

	@Override
	public void onResume(){
		super.onResume();
		/*
		 * temporary set
		 * everyday - every 1 minute 5 times....
		 * twoday - every 5 minute 5 times....
		 * it is in due_date
		 */
		String notitype = getIntent().getStringExtra("noti");
		
		if(notitype.equals("vibrate")){
			Log.d("[Alarm]", "vibrating...");
			v.vibrate(pattern, 0);
		}
		else if(notitype.equals("ringtone")){
			Log.d("[Alarm]", "ringing...");
			//double check
			if(getIntent().getStringExtra("ringtone") != null)
				r.play();
			else
				Log.e("[Alarm]", "Error in getting uri..");
		}
		else{
			Log.d("[Alarm]", "nothing...");
		}
		
		long due_date = getIntent().getLongExtra("due date", -1);
		Log.d("[Alarm]", "due date is: " + due_date);
		
		Calendar calendar = Calendar.getInstance();
		if(calendar.getTimeInMillis() >= due_date){
			Log.d("[Alarm]", "now is due date.. time to cancel the alarm...");
			String title = getIntent().getStringExtra("title");
//			new RingtoneService().cancelAlarm(title);
//			RingtoneService.cancelAlarm(title, getBaseContext());
			cancelAlarm(title);
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		String notitype = getIntent().getStringExtra("noti");
		
		if(notitype.equals("vibrate")){
			Log.d("[Alarm]", "vibrating cancel");
			v.cancel();
		}
		else if(notitype.equals("ringtone")){
			Log.d("[Alarm]", "ringing cancel");
			r.stop();
		}
		else{
			Log.d("[Alarm]", "nothing..do nothing");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_notification, menu);
		return true;
	}

	
	private void init(){
		String title = getIntent().getStringExtra("title");
		((TextView)findViewById(R.id.alarm_title)).setText(title);
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		r = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse(getIntent().getStringExtra("ringtone")));
	}
	
	private void findViewById(){
		findViewById(R.id.alarm_cancel_button).setOnClickListener(mListener);
		findViewById(R.id.alarm_okay_button).setOnClickListener(mListener);
	}
	
	private void cancelAlarm(String title){
		Log.d("[Alarm]", "title: " + title + " is now cancelling");
		Intent Alarmintent = new Intent(getBaseContext(), AlarmNotification.class);
		PendingIntent pintent = PendingIntent.getActivity(getBaseContext(), title.hashCode(), Alarmintent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		manager.cancel(pintent);
	}
	
	Button.OnClickListener mListener = new Button.OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			
			case R.id.alarm_cancel_button:
				String title = getIntent().getStringExtra("title");
				cancelAlarm(title);
				finish();
				break;
			
			case R.id.alarm_okay_button:
				finish();
				break;
				
			default:
				break;
			}
		}
	};
	
}
