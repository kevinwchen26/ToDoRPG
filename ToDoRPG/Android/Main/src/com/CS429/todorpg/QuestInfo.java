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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AbsListView.OnScrollListener;
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
	ArrayList<Quest> current_quest;
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
		current_quest = new ArrayList<Quest>();
		fq.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quest_info, menu);
		return true;
	}
	
	/* Scroll Handler */
	private void ListScrollHandler() {
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
				int count = current_quest.size();
				if(count == totalItemCount || adapter.filtered_size == totalItemCount) return;

				if(loadMore) {
//					Toast.makeText(getApplicationContext(), "WAIT ", Toast.LENGTH_SHORT).show();
					adapter.count += visibleItemCount;
					adapter.notifyDataSetChanged();
				} 
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
		});
	}
	
	/* Item Search Handler */
	private void SearchHandler() {
		EditText search = (EditText) findViewById(R.id.footer_search);
		search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
				
			}
			@Override
			public void afterTextChanged(Editable s) {
				QuestArrayAdapter.isFiltered = true;	
				adapter.getFilter().filter(s.toString());
			}
		});
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
						current_quest.add(new Quest(questRows[i].getInt("quest_id"), questRows[i].getString("quest_title"), 
								questRows[i].getString("creator_name"),questRows[i].getString("quest_due"),
								questRows[i].getString("quest_member"), questRows[i].getString("quest_status"),
								questRows[i].getString("quest_location_long"), questRows[i].getString("quest_milestone"),
								questRows[i].getString("quest_description"),questRows[i].getString("progress_status"),questRows[i].getString("done_status")));
					}

				} else {

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("SIZE OF ARRAYLIST", current_quest.size()+"");
			return null;
		}

		protected void onPostExecute(String result) {
			pDialog.dismiss();

			listView = (ListView) findViewById(R.id.questList);

			if (questRows != null && questRows.length > 0) {
				adapter = new QuestArrayAdapter(QuestInfo.this, current_quest);
				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
							final View view, final int position, long id) {
							intent = new Intent(QuestInfo.this, QuestDetail.class);
							Quest selected;
							if(QuestArrayAdapter.isFiltered)
								selected = QuestArrayAdapter.filterResultsData.get(position);
							else selected = current_quest.get(position);
							Log.d("ID", position+"");
							intent.putExtra("quest_id", selected.getQuestId());
							intent.putExtra("option", check_option);
							intent.putExtra("quest_title", selected.getTitle());
							intent.putExtra("creator_name", selected.getLeader());
							intent.putExtra("quest_member", selected.getMember());
							intent.putExtra("quest_due", selected.getDueDate());
							intent.putExtra("quest_status", selected.getStatus());
							intent.putExtra("quest_location", selected.getLocation());
							intent.putExtra("quest_milestone", selected.getMilestone());
							intent.putExtra("quest_description", selected.getDescription());
							intent.putExtra("progress_status", selected.getProgressStstus());
							intent.putExtra("done_status", selected.getDoneStatus());
							Log.d("GRRRR", selected.getStatus());
							startActivity(intent);
							finish();
						
					}
				});
				

			} else {
				StaticClass.sendAlertMessage(QuestInfo.this, "No Quests Found",
						StaticClass.TAG_NO_QUEST_WARNING).show();
			}
			
			SearchHandler();
			ListScrollHandler();
		}
	}
}

		
/*
 *  	TODO : Moved previous code here
						 
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
