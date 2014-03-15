package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
	String updatedStatus, leader;
	int quest_id;
	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_detail);
		intent = getIntent();
		check_option = intent.getIntExtra("option", -1);
		quest_id = intent.getIntExtra("quest_id", -1);
		leader = intent.getStringExtra("creator_name");
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
		SetVisible();
		findViewById(R.id.save).setOnClickListener(ButtonClick);
		findViewById(R.id.delete).setOnClickListener(ButtonClick);
		findViewById(R.id.join).setOnClickListener(ButtonClick);
	}
	private void SetVisible() {
		System.out.println(my_leader + " " + StaticClass.MY_ID);
		if(leader.equals(StaticClass.MY_ID)) {
			findViewById(R.id.delete).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.join).setVisibility(View.VISIBLE);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	       FinishActivities();
	    }
	    return super.onKeyDown(keyCode, event);
	}

	Button.OnClickListener ButtonClick = new Button.OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.my_status:
					// UPDATE quest status on DB
					if(leader.equals(StaticClass.MY_ID)) {
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
					} else {
						Toast.makeText(QuestDetail.this, StaticClass.TAG_NO_PERMISSION,
								Toast.LENGTH_SHORT).show();
					}
					
	
					break;
				case R.id.save:
					FinishActivities();
					break;
				case R.id.join:
					break;
				case R.id.delete:
					AlertDialog deleteBox = DeleteDialog();
					deleteBox.show();
					break;
			}

		}

	};
	private AlertDialog DeleteDialog() {
	    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this) 
	        //set message, title, and icon
	        .setMessage(StaticClass.TAG_DELETE) 
	        .setPositiveButton(StaticClass.TAG_CHECK_REMOVE, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	DeleteData();
	                dialog.dismiss();
	                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
	                FinishActivities();
	            }
	        })
	        .setNegativeButton(StaticClass.TAG_CANCEL, new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int whichButton) { 
	                dialog.dismiss();
	            }
	        })
	        .create();
	        return myQuittingDialogBox;
	}
	private void DeleteData() {
		Thread thread = 
				new Thread(new Runnable() {
					public void run() {
						DeleteDataHelper();
					}
				});
		thread.start();

	}
	private void DeleteDataHelper(){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("quest_id", Integer.toString(quest_id)));
		JSONObject json = jsonParser.makeHttpRequest(StaticClass.url_delete_quest, "POST", params);

		Log.d("DELETE STATUS", "DeleteDataHelper(): " + json.toString());
		
		try {
			int success = json.getInt(StaticClass.TAG_SUCCESS);

			if (success == 1) {
				System.out.println("delete success");
			} else {
				System.out.println("delete fail");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	private void FinishActivities() {
		intent = new Intent(QuestDetail.this, QuestInfo.class);
		Log.d("check_option", check_option + "");
		if (check_option == StaticClass.SINGLE_USER_INFO) {
			intent.putExtra("option", StaticClass.SINGLE_USER_INFO);
		}
		startActivity(intent);
		finish();
	}

	class UpdateQuest extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg) {
			Log.d("LINE 133 current_status", arg[0]);
			Log.d("LINE 134 current_id", quest_id + "");
			// Get user ID, use it to pull quests from database
			String status = arg[0];// arg[0];
			String _id = Integer.toString(quest_id);
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("quest_id", _id));
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
