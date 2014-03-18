package com.CS429.todorpg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Toast.makeText(context, "Alram is set", Toast.LENGTH_SHORT).show();
		
		setRingtoneService(context, intent);
	}

	/**
	 * This sets intent to a Service for Alarm sound handling.
	 * It delivers all the event date information (due time) as wells as the Uri of default ringtone.
	 * 
	 * @param context
	 * @param intent
	 */
	private void setRingtoneService(Context context, Intent intent){
		Log.d("[AlarmTest]", "set RingtoneService");
		Intent ringService = new Intent(context, RingtoneService.class);
		
		ringService.putExtra("Ringtone", intent.getStringExtra("Ringtone"));
		ringService.putExtra("type", intent.getStringExtra("type"));
		ringService.putExtra("month", intent.getStringExtra("month"));
		ringService.putExtra("day", intent.getStringExtra("day"));
		ringService.putExtra("title", intent.getStringExtra("title"));
		
		 context.startService(ringService);
	}
	
	
}
