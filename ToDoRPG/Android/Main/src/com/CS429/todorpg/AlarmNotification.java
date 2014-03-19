package com.CS429.todorpg;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AlarmNotification extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_notification);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_notification, menu);
		return true;
	}

}
