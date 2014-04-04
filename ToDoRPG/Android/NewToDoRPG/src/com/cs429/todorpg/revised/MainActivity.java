package com.cs429.todorpg.revised;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	Button rewardsButton;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rewardsButton = (Button) findViewById(R.id.rewards_button);
		rewardsButton.setOnClickListener(ButtonListener);
		
	}
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.rewards_button:
				intent = new Intent(MainActivity.this, Rewards.class);
				startActivity(intent);
				break;
			

			}
		}
	};
	
	

}
