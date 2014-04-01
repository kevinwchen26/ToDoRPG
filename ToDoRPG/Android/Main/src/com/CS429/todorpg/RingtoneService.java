package com.CS429.todorpg;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class RingtoneService extends Service{

//	private static HashMap<Integer, AlarmPair<AlarmManager, PendingIntent>> AlarmTable;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){
		Log.d("[AlarmTest]", "Service is invoked");
//		if(AlarmTable == null)
//			AlarmTable = new HashMap<Integer, AlarmPair<AlarmManager, PendingIntent>>();
		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		
		AlarmPair<AlarmManager, PendingIntent> alarmpair = setAlarm(intent);
		String title = intent.getStringExtra("title");
/*		
		//overwriting handling..canceling existing one first
		if(AlarmTable.containsKey(title.hashCode())){
			Log.d("[AlarmTest]", "found duplicate one");
			cancelAlarm(title);
		}
		AlarmTable.put(title.hashCode(), alarmpair);
		
		Log.d("[AlarmTest]", "set alarm length is: " + AlarmTable.size());
		Set<Integer> set = AlarmTable.keySet();
		Object[] tmp = set.toArray();
*/		
/*		
		for(int i = 0; i < tmp.length; ++i)
			Log.d("[AlarmTest]", "title: " + tmp[i].toString());
*/		
		return super.onStartCommand(intent, flags, startId);
	}


	/**
	 * 
	 */
	private AlarmPair<AlarmManager, PendingIntent> setAlarm(Intent intent){
		
		String title = intent.getStringExtra("title");
		String type = intent.getStringExtra("type");
		String notitype = intent.getStringExtra("noti");
		int month = Integer.parseInt(intent.getStringExtra("month"));
		int day = Integer.parseInt(intent.getStringExtra("day"));
		
		Intent Alarmintent = new Intent(getBaseContext(), AlarmNotification.class);
		Alarmintent.putExtra("title", title);
		Alarmintent.putExtra("type", type);
		Alarmintent.putExtra("noti", notitype);
		Alarmintent.putExtra("ringtone", intent.getStringExtra("ringtone"));
		Alarmintent.putExtra("month", month);
		Alarmintent.putExtra("day", day);
		
		//add due date into Alarm intent for later stop the alarm at due date
		if(type.equals("everyday")){
			Calendar due_date = Calendar.getInstance();
			due_date.setTimeInMillis(System.currentTimeMillis());
			due_date.add(Calendar.MINUTE, 5);
			Alarmintent.putExtra("due date", due_date.getTimeInMillis());
		}
		else if(type.equals("twoday")){
			Calendar due_date = Calendar.getInstance();
			due_date.setTimeInMillis(System.currentTimeMillis());
			due_date.add(Calendar.MINUTE, 25);
			Alarmintent.putExtra("due date", due_date.getTimeInMillis());
		}
		
		PendingIntent pintent = PendingIntent.getActivity(getBaseContext(), title.hashCode(), Alarmintent, 0);
		AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		if(type.equals("everyday")){
			Log.d("[Alarm]", "type " + type + " is now setting alarm");
			Log.d("[Alarm]", "notitype " + notitype + " is now setting alarm");
			//temporary alarm set - every 1 minute - only 5 times
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.MINUTE, 1);
			//temporarily used setRepeating.. for real use, better to use InexactsetRepeating..
			manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60, pintent);
		}
		else if(type.equals("twoday")){
			Log.d("[Alarm]", "type " + type + " is now setting alarm");
			Log.d("[Alarm]", "notitype " + notitype + " is now setting alarm");
			//temporary alarm set - every 5 minute - only 5 times
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.MINUTE, 5);
			//temporarily used setRepeating.. for real use, better to use InexactsetRepeating..
			manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 5, pintent);
		}
		else if(type.equals("once")){
			//temporary alarm set - 5 sec due
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
//			calendar.add(Calendar.MINUTE, 1);
			calendar.add(Calendar.SECOND, 5);
			manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pintent);
		}

		return new AlarmPair<AlarmManager, PendingIntent>(manager, pintent);
	}
	
	/**
	 * 
	 * @param title
	 */
//	public synchronized void cancelAlarm(String title){
/*		
		AlarmPair<AlarmManager, PendingIntent> pair = AlarmTable.get(title.hashCode());
		if(pair == null){
			Log.d("[Alarm]", "not found: cancel unavailable");
			return;
		}
		pair.getFirst().cancel(pair.getSecond());
		AlarmTable.remove(title.hashCode());
*/
//		Intent Alarmintent = new Intent(getBaseContext(), AlarmNotification.class);
//		PendingIntent pintent = PendingIntent.getActivity(getBaseContext(), title.hashCode(), Alarmintent, 0);
//		AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//	}
	
}
