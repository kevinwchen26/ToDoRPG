package com.cs429.todorpg.service;

import com.cs429.todorpg.revised.AlarmNotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String purpose = intent.getStringExtra("purpose");
		Log.d("[Alarm]", purpose + " is received well...");
		Log.d("[Alarm]", "key is : " + intent.getIntExtra("Alarmkey", 0));
		
		if(purpose.equals("invoke")){
			invokeAlarm(intent.getIntExtra("Alarmkey", 0), context);
		}
		else if(purpose.equals("cancel")){
			cancelAlarm(intent.getIntExtra("Alarmkey", 0), context);
		}
	}	
	
	private void invokeAlarm(int key, Context context){
		Log.d("[Alarm]", "alarm is invoking...");
		Log.d("[Alarm]", "key is : " + key);
		
		Intent intent = new Intent(context, AlarmService.class);
		intent.putExtra("Alarmkey", key);
		context.startService(intent);
	}
	
	private void cancelAlarm(int key, Context context){
		Log.d("[Alarm]", "alarm is cancelling...");
		Log.d("[Alarm]", "key is : " + key);
		
		AlarmManager manager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmNotification.class);
		//checking existance
		PendingIntent checking = PendingIntent.getActivity(context, key, intent, PendingIntent.FLAG_NO_CREATE);
		if(checking == null)
			Log.d("[Alarm]", "corresponding alarm does not exist");
		
		PendingIntent pintent = PendingIntent.getActivity(context, key, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		manager.cancel(pintent);
		
		Intent intent2 = new Intent(context, AlarmService.class);
		context.stopService(intent2);
	}
}
