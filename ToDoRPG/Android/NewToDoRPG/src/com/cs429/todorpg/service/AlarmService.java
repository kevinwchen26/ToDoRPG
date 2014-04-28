package com.cs429.todorpg.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cs429.todorpg.revised.AlarmNotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service{
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){
		Log.d("[AlarmTest]", "Service is invoked");
		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d("[Alarm]", "Service: setting up an alarm...");
		
		Calendar alarmtime = getAlarmTime();
		
		int key = intent.getIntExtra("Alarmkey", 0);
		Log.d("[Alarm]", "key is : " + key);
//		long interval = AlarmManager.INTERVAL_DAY;
		
		//temporary set 5minute interval - 10 seconds interval
		long interval = 1000 * 10;
		////////////////////////////////
		
		Intent AlarmIntent = new Intent(getBaseContext(), AlarmNotification.class);
		PendingIntent pintent = PendingIntent.getActivity(getBaseContext(), key, AlarmIntent, 0);
	
		AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmtime.getTimeInMillis(), interval, pintent);
		
		return super.onStartCommand(intent, flags, startId);
	}


	private Calendar getAlarmTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.set(Calendar.HOUR_OF_DAY, 12);
//		calendar.set(Calendar.MINUTE, 0);
		
		//temporarily set 10 seconds
		calendar.add(Calendar.SECOND, 10);
		//////////////////////////
		return calendar;
	}
}
