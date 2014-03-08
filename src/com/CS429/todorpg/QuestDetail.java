package com.CS429.todorpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuestDetail extends Activity {
	Intent intent;
	TextView my_title, my_leader, my_difficulty, my_status, my_duration,
			my_location, my_milestone, my_description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_detail);
		intent = getIntent();
		FindViewById();
		setMessage();

	}

	private void FindViewById() {
		my_title = (TextView) findViewById(R.id.my_title);
		my_leader = (TextView) findViewById(R.id.my_leader);
		my_difficulty = (TextView) findViewById(R.id.my_difficulty);
		my_duration = (TextView) findViewById(R.id.my_duration);
		my_location = (TextView) findViewById(R.id.my_location);
		my_status = (TextView) findViewById(R.id.my_status);
		my_milestone = (TextView) findViewById(R.id.my_milestone);
		my_description = (TextView) findViewById(R.id.my_description);
	}

	private void setMessage() {
		my_title.setText(intent.getStringExtra("quest_title"));
		my_leader.setText(intent.getStringExtra("creator_name"));
		my_difficulty.setText(intent.getStringExtra("quest_difficulty"));
		my_duration.setText(intent.getStringExtra("quest_duration"));
		my_status.setText(intent.getStringExtra("quest_status"));
		my_location.setText(intent.getStringExtra("quest_location_lat"));
		my_milestone.setText(intent.getStringExtra("quest_milestone"));
		my_description.setText(intent.getStringExtra("quest_description"));
	}

}
