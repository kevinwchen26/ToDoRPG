package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.CS429.todorpg.Utils.JSONParser;

public class QuestDetail extends Activity {
	Intent intent;
	TextView my_title, my_leader, my_difficulty, my_duration, my_location,
			my_milestone, my_description;
	Button my_status;
	int check_option;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_detail);
		intent = getIntent();
		check_option = intent.getIntExtra("option", -1);
		FindViewById();
		setMessage();

	}

	private void FindViewById() {
		my_title = (TextView) findViewById(R.id.my_title);
		my_leader = (TextView) findViewById(R.id.my_leader);
		my_difficulty = (TextView) findViewById(R.id.my_difficulty);
		my_duration = (TextView) findViewById(R.id.my_duration);
		my_location = (TextView) findViewById(R.id.my_location);
		my_status = (Button) findViewById(R.id.my_status);
		my_status.setOnClickListener(ButtonClick);
		my_milestone = (TextView) findViewById(R.id.my_milestone);
		my_description = (TextView) findViewById(R.id.my_description);
	}

	private void setMessage() {
		my_title.setText(intent.getStringExtra("quest_title"));
		my_leader.setText(intent.getStringExtra("creator_name"));
		my_difficulty.setText(intent.getStringExtra("quest_difficulty"));
		my_duration.setText(intent.getStringExtra("quest_duration"));
		my_location.setText(intent.getStringExtra("quest_location_lat"));
		my_milestone.setText(intent.getStringExtra("quest_milestone"));
		my_description.setText(intent.getStringExtra("quest_description"));
		my_status.setText(intent.getStringExtra("quest_status"));
		if (intent.getStringExtra("quest_status").equals("ACTIVE")) {
			my_status.setTextColor(getResources().getColor(R.color.red));
		} else {
			my_status.setTextColor(getResources().getColor(R.color.light_grey));
		}
		findViewById(R.id.save).setOnClickListener(ButtonClick);
	}

	Button.OnClickListener ButtonClick = new Button.OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.my_status:
					// UPDATE quest status on DB
					Log.d("SIZE",
							"Number of quests: "
									+ Integer.toString(intent.getIntExtra(
											"questJsonList_length", -1)));
	
					// Change UI
					String updatedStatus = "";
					if ("ACTIVE".equals(intent.getStringExtra("quest_status"))) {
						my_status.setTextColor(getResources().getColor(
								R.color.light_grey));
						intent.putExtra("quest_status", "INACTIVE");
						updatedStatus = "INACTIVE";
	
						// Set Alarm and
						// notifications here.
	
					} else if ("INACTIVE".equals(intent
							.getStringExtra("quest_status"))) {
						my_status
								.setTextColor(getResources().getColor(R.color.red));
						intent.putExtra("quest_status", "ACTIVE");
						updatedStatus = "ACTIVE";
					}
	
					UpdateQuest uq = new UpdateQuest();
					uq.execute(updatedStatus, intent.getStringExtra("quest_id"));
	
					my_status.setText(updatedStatus);
					// questJsonList[position].put("quest_status", updatedStatus);
	
					Toast.makeText(QuestDetail.this, "Quest status updated",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.save:
					intent = new Intent(QuestDetail.this, QuestInfo.class);
					Log.d("check_option", check_option+"");
					if(check_option == StaticClass.SINGLE_USER_INFO) {
						intent.putExtra("option", StaticClass.SINGLE_USER_INFO);
					} 
					startActivity(intent);
					finish();
				}
			
		}

	};

	class UpdateQuest extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg) {
			// Get user ID, use it to pull quests from database
			String status = arg[0];
			String quest_id = arg[1];
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("quest_id", quest_id));
			JSONObject json = jsonParser.makeHttpRequest(
					StaticClass.url_update_quest, "GET", params);
			Log.d("Quest Update info", json.toString());
			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);
				if (success == 1) {
					// SUCCESSFULLY UPDATED DATABASE.
					Log.d("Quest info", "QUEST STATUS UPDATED");
				} else {
					Log.d("Quest info", "QUEST STATUS UPDATE FAIL");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
		}
	}
}
