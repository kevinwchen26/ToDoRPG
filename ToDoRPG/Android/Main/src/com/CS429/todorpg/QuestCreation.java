package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.CS429.todorpg.Utils.JSONParser;

public class QuestCreation extends Activity {
	private ProgressDialog pDialog;
	EditText title, duration, description; //, newMilestone;
//	ListView milestones;
	Spinner location_spinner;
	JSONParser jsonParser = new JSONParser();
	CreateQuest createQuest = new CreateQuest();
	SharedPreferences prefs;
	String milestones_to_string;
	public static boolean milestone_written = false;
	public static ArrayList<String> milestones;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_creation);
		ActivitySizeHandler();
		FindViewByID();
		milestones = new ArrayList<String>();
		SpinnerListener();
		prefs = getSharedPreferences(StaticClass.MY_PREFERENCES, Context.MODE_PRIVATE);	
	}
	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(
		WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params);
	}
	private void SpinnerListener() {
		location_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if(location_spinner.getSelectedItem().toString().equals("Yes")){
					/*Log.d("spinner", location_spinner.getSelectedItem().toString());
					Intent intent = new Intent(QuestCreation.this, MapActivity.class);
					startActivity(intent);*/
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
	}
	
	
	private void FindViewByID() {
		duration = (EditText) findViewById(R.id.creation_quest_duration);
		description = (EditText) findViewById(R.id.creation_quest_description);
		title = (EditText) findViewById(R.id.creation_quest_title);
		location_spinner = (Spinner)findViewById(R.id.creation_quest_location);
		findViewById(R.id.creation_milestone_btn).setOnClickListener(ButtonListener);
		findViewById(R.id.creation_quest_submit).setOnClickListener(ButtonListener);

	
	}
	
	public Boolean validate() {
		String questTitle = title.getText().toString();
		String questDuration = duration.getText().toString();
		String questDescription = description.getText().toString();
		String questLocation = location_spinner.getSelectedItem().toString();
		Boolean validateStatus = true;
		if(questTitle.length() == 0) {
			title.setError("Please add a title.");
			Toast.makeText(QuestCreation.this, "Please add a title.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		if(questDuration.length() == 0) {
			duration.setError("Please add a duration (hours)");
			Toast.makeText(QuestCreation.this, "Please add a duration (hours).", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		
		if(questDescription.length() == 0) {
			description.setError("Please add a description.");
			Toast.makeText(QuestCreation.this, "Please add a description.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		if(milestones_to_string == null) {
			Toast.makeText(QuestCreation.this, "Please add at least one milestone.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		
		if(!questLocation.equals("Yes") && !questLocation.equals("No")) {
			Toast.makeText(QuestCreation.this, "Please select location.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		return 	validateStatus;
	}
	
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.creation_milestone_btn:
				Intent intent = new Intent(QuestCreation.this, QuestMilestone.class);
				intent.putExtra("count", 1);
				startActivity(intent);
				break;
			case R.id.creation_quest_submit:
				if(milestone_written) {
					InsertData();
					Log.d("MILESTONE", milestones_to_string);
				}
				if(validate()){
					createQuest.execute();
					finish();
				}
				else
					Toast.makeText(QuestCreation.this, StaticClass.QUEST_FAIL, Toast.LENGTH_SHORT).show();


				break;

			}
		}
	};
	
	private void InsertData() {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for(; i < milestones.size() - 1; i++){
			sb.append(milestones.get(i) + StaticClass.delimiter);
		}
		sb.append(milestones.get(i));
		milestones_to_string = sb.toString();
		
	}
	
	class CreateQuest extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... args) {
			String questTitle = title.getText().toString();
			String questDuration = duration.getText().toString();
			String questDescription = description.getText().toString();
//			String questMilestones = collapseMilestones();
			String questLocation = location_spinner.getSelectedItem().toString();
			Log.d("spinner1", questLocation);
			String questLocationLat = null;
			String questLocationLong = null;
			Log.d("Location Spinner", questLocation);
			if(questLocation.equals("Yes")){
				Log.d("spinner", questLocation);
			}
			//TODO
			String currentlyLoggedIn = "";
			// Get user ID
			String userName = "";
			if (prefs.contains(StaticClass.PREF_USERNAME)) {
				userName = prefs.getString(StaticClass.PREF_USERNAME, "NOT_LOGGED_IN_CHECK_CODE");
			}
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("quest_title", questTitle));
			params.add(new BasicNameValuePair("quest_description", questDescription));
			params.add(new BasicNameValuePair("quest_difficulty", 5+""));//Integer.toString(listOfMilestones.size())));
			params.add(new BasicNameValuePair("creator_name", userName));
			params.add(new BasicNameValuePair("quest_location_lat", questLocationLat));
			params.add(new BasicNameValuePair("quest_location_long", questLocationLong));
			params.add(new BasicNameValuePair("quest_duration", questDuration));
			params.add(new BasicNameValuePair("quest_milestone", milestones_to_string));

			
			JSONObject json = jsonParser.makeHttpRequest(
					StaticClass.url_create_quest, "POST", params);
			
			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);

				if (success == 1) {
					Log.d("Quest Status", "Quest Created Successfully");
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
			
		}
		
		protected void onPostExecute(String file_url) {
			Toast.makeText(QuestCreation.this, StaticClass.QUEST_SUCCESS, Toast.LENGTH_SHORT).show();
			createQuest.cancel(true);

			finish();
		}
	}
}