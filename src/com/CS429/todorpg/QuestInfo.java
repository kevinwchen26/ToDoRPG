package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Utils.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QuestInfo extends Activity {
	
	private ProgressDialog pDialog;
	// Persistent Data
	SharedPreferences prefs;
	JSONObject [] questRows;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quest_info);
		prefs = getSharedPreferences(StaticClass.MY_PREFERENCES, Context.MODE_PRIVATE);
		FetchQuests fq = new FetchQuests();
		fq.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quest_info, menu);
		return true;
	}
	class FetchQuests extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(QuestInfo.this);
			pDialog.setMessage("Loading Character Information. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
            		
		@Override
		protected String doInBackground(String... arg) {
			// Get user ID, use it to pull quests from database
			String userName = "";
			if (prefs.contains(StaticClass.PREF_USERNAME)) {
				userName = prefs.getString(StaticClass.PREF_USERNAME, "NOT_LOGGED_IN_CHECK_CODE");
			}
			
        	JSONParser jsonParser = new JSONParser();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", userName));
            JSONObject json = jsonParser.makeHttpRequest(StaticClass.url_get_users_quest, "GET", params);
     		Log.d("Quest info", json.toString());
			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);
				if (success == 1) {
		            JSONArray rows = json.getJSONArray("rows");
		            questRows = new JSONObject [rows.length()];
		            for (int i = 0; i < rows.length(); i++) {
		            	questRows[i] = rows.getJSONObject(i);
		            }
		           
		            
		         }else{
		        	 
		         }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
             
			return null;
		}
		protected void onPostExecute(String result) {
			pDialog.dismiss();
			 
            ListView listView = (ListView) findViewById(R.id.questList);
            QuestArrayAdapter adapter = new QuestArrayAdapter(QuestInfo.this, R.layout.quest_row, questRows);
            listView.setAdapter(adapter);
            
            listView.setOnItemClickListener(new OnItemClickListener() {
                JSONObject [] questJsonList; // The appropriate quest can be accessed with 'position'
                @Override
                public void onItemClick(AdapterView<?> parent,
                        final View view, final int position, long id) {
                	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                	    @Override
                	    public void onClick(DialogInterface dialog, int which) {
                	        switch (which){
                	        case DialogInterface.BUTTON_POSITIVE:
                	            //Yes button touched, Activate this quest.
                	        	TextView questActive = (TextView) view.findViewById(R.id.isQuestActive);
                	        	questActive.setTextColor(getResources().getColor(R.color.red));
                	        	questActive.setText("ACTIVE");
                	        	
                	        	try {
                	        		// UPDATE quest status on DB
                	        		Log.d("SIZE", "Number of quests: " + Integer.toString(questJsonList.length));
									UpdateQuest uq = new UpdateQuest();
									uq.execute("ACTIVE", questJsonList[position].getString("quest_id"));
									Toast.makeText(QuestInfo.this, "Clicked YES",
	                						Toast.LENGTH_SHORT).show();
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                	        	
                	            break;

                	        case DialogInterface.BUTTON_NEGATIVE:
                	            //No button clicked
                	        	Toast.makeText(QuestInfo.this, "Clicked NO",
                						Toast.LENGTH_SHORT).show();
                	            break;
                	        }
                	    }
                	};
                	
                	AlertDialog.Builder builder = new AlertDialog.Builder(QuestInfo.this);
                	builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                	    .setNegativeButton("No", dialogClickListener).show();
                    
                }
                public OnItemClickListener init(JSONObject [] questJsonList) {
                    this.questJsonList = questJsonList;
                    return this;
                }
                
            }.init(questRows));
		}
	}
	
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
            JSONObject json = jsonParser.makeHttpRequest(StaticClass.url_update_quest, "GET", params);
     		Log.d("Quest Update info", json.toString());
			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);
				if (success == 1) {
		            // SUCCESSFULLY UPDATED DATABASE.
					Log.d("Quest info", "QUEST STATUS UPDATED");
		         }else{
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