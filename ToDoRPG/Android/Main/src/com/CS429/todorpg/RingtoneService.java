package com.CS429.todorpg;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class RingtoneService extends Service{

	private static HashMap<String, AlarmPair<AlarmManager, PendingIntent>> AlarmTable;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){
		Log.d("[AlarmTest]", "Service is invoked");
		if(AlarmTable == null)
			AlarmTable = new HashMap<String, AlarmPair<AlarmManager, PendingIntent>>();
		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		
		AlarmPair<AlarmManager, PendingIntent> alarmpair = setAlarm(intent);
		String title = intent.getStringExtra("title");
		
		//overwriting handling..canceling existing one first
		if(AlarmTable.containsKey(title)){
			Log.d("[AlarmTest]", "found duplicate one");
			cancelAlarm(title);
		}
		AlarmTable.put(title, alarmpair);
		
		Log.d("[AlarmTest]", "set alarm length is: " + AlarmTable.size());
		Set<String> set = AlarmTable.keySet();
		Object[] tmp = set.toArray();
		for(int i = 0; i < tmp.length; ++i)
			Log.d("[AlarmTest]", "title: " + tmp[i].toString());
		
		return super.onStartCommand(intent, flags, startId);
	}


	/**
	 * 
	 */
	private AlarmPair<AlarmManager, PendingIntent> setAlarm(Intent intent){
		
		String title = intent.getStringExtra("title");
		String type = intent.getStringExtra("type");
		int month = Integer.parseInt(intent.getStringExtra("month"));
		int day = Integer.parseInt(intent.getStringExtra("day"));
		PendingIntent pintent = PendingIntent.getActivity(getBaseContext(), 1, new Intent(RingtoneService.this, AlarmNotification.class), 0);
		AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
/*		
		if(type.equals("everyday")){
			
		}
		else if(type.equals("twoday")){
			
		}
		else if(type.equals("once")){
			
		}
*/		
		//temporary alarm set
		Calendar calendar = Calendar.getInstance();
		Log.d("[AlarmTest]", "temporary setting time is: " + calendar.getTime());
		calendar.add(Calendar.MINUTE, 1);
		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pintent);
		//
		
		return new AlarmPair<AlarmManager, PendingIntent>(manager, pintent);
	}
	
	/**
	 * 
	 * @param title
	 */
	private void cancelAlarm(String title){
		AlarmPair<AlarmManager, PendingIntent> pair = AlarmTable.get(title);
		pair.getFirst().cancel(pair.getSecond());
		AlarmTable.remove(title);
	}
	
}
