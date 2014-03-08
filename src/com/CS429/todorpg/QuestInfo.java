package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.CS429.todorpg.Utils.JSONParser;

public class QuestInfo extends Activity {
	private ProgressDialog pDialog;
	// Persistent Data
	SharedPreferences prefs;
	JSONObject[] questRows;
	Intent intent;
	int check_option;
	QuestArrayAdapter adapter;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_quest_info);
		intent = getIntent();
		check_option = intent.getIntExtra("option", -1);
		Log.d("VAL", Integer.toString(check_option));
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
			pDialog.setMessage("Loading My Quest Information. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg) {
			// Get user ID, use it to pull quests from database
			String userName = "";

			JSONParser jsonParser = new JSONParser();
			if(check_option == StaticClass.SINGLE_USER_INFO && prefs.contains(StaticClass.PREF_USERNAME)) {
				userName = prefs.getString(StaticClass.PREF_USERNAME,
						"NOT_LOGGED_IN_CHECK_CODE");
			} 
			Log.d("User Name", userName);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userName", userName));
			
			JSONObject json;

			if(check_option == StaticClass.SINGLE_USER_INFO) {
				json = jsonParser.makeHttpRequest(
						StaticClass.url_get_users_quest, "GET", params);
			} else {
				json = jsonParser.makeHttpRequest(
						StaticClass.url_get_quests, "GET", params);
			}
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

			listView = (ListView) findViewById(R.id.questList);

			if (questRows != null && questRows.length > 0) {
				adapter = new QuestArrayAdapter(
						QuestInfo.this, R.layout.quest_row, questRows);
				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {
					JSONObject[] questJsonList; // The appropriate quest can be
												// accessed with 'position'

					@Override
					public void onItemClick(AdapterView<?> parent,
							final View view, final int position, long id) {
							intent = new Intent(QuestInfo.this, QuestDetail.class);
							try {
								Log.d("OPTION", check_option+"");
								intent.putExtra("option", check_option);
								intent.putExtra("position", position);
								intent.putExtra("quest_id", questJsonList[position].getString("quest_id"));
								intent.putExtra("questJsonList_length", questJsonList.length);
								intent.putExtra("quest_title", questJsonList[position].getString("quest_title"));
								intent.putExtra("quest_description", questJsonList[position].getString("quest_description"));
								intent.putExtra("quest_difficulty", questJsonList[position].getString("quest_difficulty"));
								intent.putExtra("creator_name", questJsonList[position].getString("creator_name"));
								intent.putExtra("quest_duration", questJsonList[position].getString("quest_duration"));
								intent.putExtra("quest_milestone", questJsonList[position].getString("quest_milestone"));
								intent.putExtra("quest_location_lat", questJsonList[position].getString("quest_location_lat"));
								intent.putExtra("quest_location_long", questJsonList[position].getString("quest_location_long"));
								intent.putExtra("quest_status", questJsonList[position].getString("quest_status"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(intent);
							finish();
						/*
						 * TODO I am not sure What active and deactive does, so I commented active dialog things.
						 *      If this we need this, just remove line 127 ~ 144 and uncommnet that :)
						 * 
						 * */
						/*try {
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
												if ("ACTIVE"
														.equals(questStatus)) {
													questActive
															.setTextColor(getResources()
																	.getColor(
																			R.color.light_grey));
													updatedStatus = "INACTIVE";

													// Set Alarm and
													// notifications here.

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

												questActive
														.setText(updatedStatus);
												questJsonList[position].put(
														"quest_status",
														updatedStatus);

												Toast.makeText(QuestInfo.this,
														"Quest status updated",
														Toast.LENGTH_SHORT)
														.show();

											} catch (JSONException e) {
												Toast.makeText(
														QuestInfo.this,
														"Failed to update quest",
														Toast.LENGTH_SHORT)
														.show();
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
						}*/
					}

					public OnItemClickListener init(JSONObject[] questJsonList) {
						this.questJsonList = questJsonList;
						return this;
					}

				}.init(questRows));
			} else {
				StaticClass.sendAlertMessage(QuestInfo.this, "No Quests Found",
						"You must create a quest first").show();
			}
		}
	}

	/*class UpdateQuest extends AsyncTask<String, String, String> {
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
	}*/
}