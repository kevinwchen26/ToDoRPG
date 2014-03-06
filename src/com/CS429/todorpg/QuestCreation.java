package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.CS429.todorpg.Utils.JSONParser;

public class QuestCreation extends Activity {
	
	EditText title, duration, description, newMilestone;
	ListView milestones;
	Spinner location_spinner;
	ArrayList<String> listOfMilestones = new ArrayList<String>();
	JSONParser jsonParser = new JSONParser();
	CreateQuest createQuest = new CreateQuest();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_creation);
		ActivitySizeHandler();
		FindViewByID();
	}

	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(
		WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params);
	}
	
	private void setMilestones(String newMilestone) {
		
		listOfMilestones.add(newMilestone);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listOfMilestones);
		//Assign adapter to list view
		milestones.setAdapter(adapter);
	}
	
	private void FindViewByID() {
		milestones = (ListView) findViewById(R.id.quest_milestones);
		newMilestone = (EditText)findViewById(R.id.creation_quest_milestone);
		duration = (EditText) findViewById(R.id.creation_quest_duration);
		description = (EditText) findViewById(R.id.creation_quest_description);
		title = (EditText) findViewById(R.id.creation_quest_title);
		location_spinner = (Spinner)findViewById(R.id.creation_quest_location);
		findViewById(R.id.creation_milestone_btn).setOnClickListener(ButtonListener);
		findViewById(R.id.creation_quest_submit).setOnClickListener(ButtonListener);

	
	}
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.creation_milestone_btn:
				String milestone = newMilestone.getText().toString();
				setMilestones(milestone);
				break;
			case R.id.creation_quest_submit:
				Log.d("Quest Creation", "Post Start");
				createQuest.execute();
				finish();

				Log.d("Quest Creation", "Post finished");
				break;

			}
		}
	};
	
	class CreateQuest extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {
			String questTitle = title.getText().toString();
			String questDuration = duration.getText().toString();
			String questDescription = description.getText().toString();
			String questMilestones = collapseMilestones();
			String questLocation = location_spinner.getSelectedItem().toString();
			String questLocationLat = null;
			String questLocationLong = null;
			Log.d("Location Spinner", questLocation);
			if(questLocation.equals("Yes")){
			//MapActivity map = new MapActivity();
			//	LatLng latlong = map.getLocation();
			//	questLocationLat = Double.toString(latlong.latitude);
			//	questLocationLat = Double.toString(latlong.longitude);
			}
			//TODO
			String currentlyLoggedIn = "";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("quest_title", questTitle));
			params.add(new BasicNameValuePair("quest_description", questDescription));
			params.add(new BasicNameValuePair("quest_difficulty", Integer.toString(listOfMilestones.size())));
			params.add(new BasicNameValuePair("creator_name", "Test"));
			params.add(new BasicNameValuePair("quest_location_lat", questLocationLat));
			params.add(new BasicNameValuePair("quest_location_long", questLocationLong));
			params.add(new BasicNameValuePair("quest_duration", questDuration));
			params.add(new BasicNameValuePair("quest_milestone", questMilestones));

			
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
	
	
	
	private String collapseMilestones() {
		String retval = "";
		for(String str : listOfMilestones)
			retval += str + "_";
		return retval;
			
	}

	

}
