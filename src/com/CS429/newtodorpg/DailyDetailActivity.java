package com.CS429.newtodorpg;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DailyDetailActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_detail);
		setHeader(R.id.header);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daily_detail, menu);
		return true;
	}

}
