package com.cs429.todorpg.revised;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;

public class StatsActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.stats_activity);
		setHeader(R.id.header);
		
		/*
		Avatar avatar = new Avatar();
		ImageView image = (ImageView) findViewById(R.id.character_activity);
		image.setImageBitmap(avatar.getBitmap());
		
		ImageView image2 = (ImageView) findViewById(R.id.character_icon);
		image2.setImageBitmap(avatar.getBitmap());
		*/
	}
}
