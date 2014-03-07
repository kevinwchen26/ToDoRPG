package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.CS429.todorpg.Utils.JSONParser;

public class QuestInfo extends Activity {

	private ProgressDialog pDialog;
	// Persistent Data
	SharedPreferences prefs;
	JSONObject[] questRows;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quest_info);
		prefs = getSharedPreferences(StaticClass.MY_PREFERENCES,
				Context.MODE_PRIVATE);
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
				userName = prefs.getString(StaticClass.PREF_USERNAME,
						"NOT_LOGGED_IN_CHECK_CODE");
			}

			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userName", userName));
			JSONObject json = jsonParser.makeHttpRequest(
					StaticClass.url_get_users_quest, "GET", params);
			Log.d("Quest info", json.toString());
			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);
				if (success == 1) {
					JSONArray rows = json.getJSONArray("rows");
					questRows = new JSONObject[rows.length()];
					for (int i = 0; i < rows.length(); i++) {
						questRows[i] = rows.getJSONObject(i);
					}

				} else {

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
			
			if (questRows != null && questRows.length > 0) {
				QuestArrayAdapter adapter = new QuestArrayAdapter(QuestInfo.this,
						R.layout.quest_row, questRows);
				listView.setAdapter(adapter);
	
				listView.setOnItemClickListener(new OnItemClickListener() {
					JSONObject[] questJsonList; // The appropriate quest can be
												// accessed with 'position'
	
					@Override
					public void onItemClick(AdapterView<?> parent, final View view,
							final int position, long id) {
						try {
							// Create Quest Dialog
	
							String questDialogTitle = questJsonList[position]
									.getString("quest_title");
							String questDescription = questJsonList[position]
									.getString("quest_description");
							final String questStatus = questJsonList[position]
									.getString("quest_status");
	
							final Dialog dialog = new Dialog(QuestInfo.this);
							dialog.setContentView(R.layout.quest_dialog);
							dialog.setTitle(questDialogTitle);
	
							TextView questDialogDescription = (TextView) dialog
									.findViewById(R.id.questDialogDescription);
							questDialogDescription.setText(questDescription);
							Button questStatusButton = (Button) dialog
									.findViewById(R.id.questStatusButton);
							Button questDeleteButton = (Button) dialog
									.findViewById(R.id.questDeleteButton);
							Button questCancelButton = (Button) dialog
									.findViewById(R.id.questCancelButton);
	
							if ("ACTIVE".equals(questStatus)) {
								questStatusButton.setText("De-Activate");
							} else if ("INACTIVE".equals(questStatus)) {
								questStatusButton.setText("Activate");
							}
	
							questStatusButton
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											try {
												// UPDATE quest status on DB
												Log.d("SIZE",
														"Number of quests: "
																+ Integer
																		.toString(questJsonList.length));
	
												// Change UI
												String updatedStatus = "";
												TextView questActive = (TextView) view
														.findViewById(R.id.isQuestActive);
												if ("ACTIVE".equals(questStatus)) {
													questActive
															.setTextColor(getResources()
																	.getColor(
																			R.color.light_grey));
													updatedStatus = "INACTIVE";
													
													// Set Alarm and notifications here.
													
													
												} else if ("INACTIVE"
														.equals(questStatus)) {
													questActive
															.setTextColor(getResources()
																	.getColor(
																			R.color.red));
													updatedStatus = "ACTIVE";
												}
	
												UpdateQuest uq = new UpdateQuest();
												uq.execute(
														updatedStatus,
														questJsonList[position]
																.getString("quest_id"));
	
												questActive.setText(updatedStatus);
												questJsonList[position].put(
														"quest_status",
														updatedStatus);
	
												Toast.makeText(QuestInfo.this,
														"Quest status updated",
														Toast.LENGTH_SHORT).show();
	
											} catch (JSONException e) {
												Toast.makeText(QuestInfo.this,
														"Failed to update quest",
														Toast.LENGTH_SHORT).show();
												e.printStackTrace();
											}
											dialog.dismiss();
										}
									});
							questDeleteButton
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											Toast.makeText(QuestInfo.this,
													"Not implemented yet",
													Toast.LENGTH_SHORT).show();
											dialog.dismiss();
										}
									});
							questCancelButton
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											dialog.dismiss();
										}
									});
	
							dialog.show();
	
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
	
					public OnItemClickListener init(JSONObject[] questJsonList) {
						this.questJsonList = questJsonList;
						return this;
					}
	
				}.init(questRows));
			}
			else {
				StaticClass.sendAlertMessage(QuestInfo.this, "No Quests Found", "You must create a quest first").show();
			}
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