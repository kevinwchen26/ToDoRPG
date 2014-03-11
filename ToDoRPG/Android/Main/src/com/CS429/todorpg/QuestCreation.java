package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.CS429.todorpg.Utils.JSONParser;

public class QuestCreation extends Activity {
	private ProgressDialog pDialog;
	EditText title, duration, description, newMilestone;
	ListView milestones;
	Spinner location_spinner;
	ArrayList<String> listOfMilestones = new ArrayList<String>();
	JSONParser jsonParser = new JSONParser();
	CreateQuest createQuest = new CreateQuest();
	SharedPreferences prefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_creation);
		ActivitySizeHandler();
		FindViewByID();
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
	
	private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = milestones.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
	
	private void setMilestones(String newMilestone) {
		
		listOfMilestones.add(newMilestone);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listOfMilestones);
		//Assign adapter to list view
		milestones.setAdapter(adapter);
		setListViewHeightBasedOnChildren(milestones);
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
	
	public Boolean validate() {
		String questTitle = title.getText().toString();
		String questDuration = duration.getText().toString();
		String questDescription = description.getText().toString();
		String questMilestones = collapseMilestones();
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
		
		if(questMilestones.length() == 0) {
			Toast.makeText(QuestCreation.this, "Please add a few milestones.", Toast.LENGTH_SHORT).show();
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
				String milestone = newMilestone.getText().toString();
				setMilestones(milestone);
				break;
			case R.id.creation_quest_submit:
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
	
	class CreateQuest extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
		}
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
			// Get user ID
			String userName = "";
			if (prefs.contains(StaticClass.PREF_USERNAME)) {
				userName = prefs.getString(StaticClass.PREF_USERNAME, "NOT_LOGGED_IN_CHECK_CODE");
			}
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("quest_title", questTitle));
			params.add(new BasicNameValuePair("quest_description", questDescription));
			params.add(new BasicNameValuePair("quest_difficulty", Integer.toString(listOfMilestones.size())));
			params.add(new BasicNameValuePair("creator_name", userName));
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
