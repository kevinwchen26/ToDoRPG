package com.CS429.newtodorpg;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ToDoDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_detail, menu);
		return true;
	}

}
