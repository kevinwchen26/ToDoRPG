package com.CS429.todorpg;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

public class MainPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		if (preferences.getBoolean("logged_in", false)) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

	/*
	 * onClick handler for character profile button start character profile
	 * activity
	 */

	/*
	 * onClick handler for logout button deletes user credentials for
	 * preferences and returns to login
	 */
	public void logout(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);

	}
}
