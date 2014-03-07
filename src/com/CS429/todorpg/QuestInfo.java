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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
		        	StaticClass.CHARACTER_CREATED = true;
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
                JSONObject [] questJsonList;
                @Override
                public void onItemClick(AdapterView<?> parent,
                        View view, int position, long id) {
                	/*
                    String jsonString = om.writeValueAsString(contactList[position - 1]);
                    Intent contactInfoActivity = new Intent(view.getContext(), ContactInfo.class);
                    contactInfoActivity.putExtra("jsonString", jsonString);
                    startActivity(contactInfoActivity);
                    */
                    
                }
                public OnItemClickListener init(JSONObject [] questJsonList) {
                    this.questJsonList = questJsonList;
                    return this;
                }
                
            }.init(questRows));
		}
	}
}