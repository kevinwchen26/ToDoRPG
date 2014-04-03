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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.CS429.todorpg.Utils.JSONParser;

import com.CS429.todorpg.Utils.Constants;
import com.CS429.todorpg.Utils.JSONParser;

public class QuestDetail extends Activity {
	Intent intent;
	TextView my_title, my_leader, my_member, my_due_date, my_location,
			my_milestone, my_description;
	Button my_status;
	int check_option, max_member;
	String updatedStatus, todo_list, member, due_date, title, status, location, description, progress_status, done_status;
	static String leader;
	int quest_id;
	JSONParser jsonParser = new JSONParser();
	ArrayList<MyToDoList> todo_list_data;
	ArrayList<String> member_list;
	ToDoListAdapter adapter;
	ListView listView;
	String[] my_list;
	ArrayList<String> progress_array;
	ArrayList<String> done_array;
	int current_position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_detail);
		progress_array = new ArrayList<String>();
		done_array = new ArrayList<String>();
		intent = getIntent();
		title = intent.getStringExtra("quest_title");
		check_option = intent.getIntExtra("option", -1);
		quest_id = intent.getIntExtra("quest_id", -1);
		leader = intent.getStringExtra("creator_name");
		todo_list = intent.getStringExtra("quest_milestone");
		member = intent.getStringExtra("quest_member");
		description = intent.getStringExtra("quest_description");
		progress_status = intent.getStringExtra("progress_status");
		done_status = intent.getStringExtra("done_status");
		max_member = Integer.parseInt(intent.getStringExtra("quest_max_member"));
		FindViewById();
		todo_list_data = new ArrayList<MyToDoList>();
		SplitStatus();

		setMessage();

	}
	private void SplitStatus() {
		String[] progress_list = progress_status.split("[" + Constants.delimiter + "]+");
		for(String list: progress_list) {
			System.out.println(list);
			progress_array.add(list);
		}
		String[] done_list = done_status.split("[" + Constants.delimiter + "]+");
		for(String list: done_list) {
			done_array.add(list);
		}
		
	}
	private void GetMemberList() {
		member_list = new ArrayList<String>();
		if(member == null) {
			return;
		}
		String[] split = member.split("["+ Constants.delimiter +"]+");
		for(String member : split) {
			member_list.add(member);
		}
	}

	private void FindViewById() {
		my_title = (TextView) findViewById(R.id.my_title);
		my_leader = (TextView) findViewById(R.id.my_leader);
		my_member = (TextView) findViewById(R.id.my_current_member);
		my_due_date = (TextView) findViewById(R.id.my_due_date);
		my_location = (TextView) findViewById(R.id.my_location);
		my_status = (Button) findViewById(R.id.my_status);
		my_status.setOnClickListener(ButtonClick);
		// my_milestone = (TextView) findViewById(R.id.my_milestone);
		my_description = (TextView) findViewById(R.id.my_description);
		listView = (ListView) findViewById(R.id.todo_list_listview);
	}

	private void setMessage() {
		my_title.setText(intent.getStringExtra("quest_title"));
		my_leader.setText(intent.getStringExtra("creator_name"));
		my_due_date.setText(intent.getStringExtra("quest_due_date"));
		// my_difficulty.setText(intent.getStringExtra("quest_difficulty"));
		// my_duration.setText(intent.getStringExtra("quest_duration"));
		my_location.setText(intent.getStringExtra("quest_location_lat"));
		// my_milestone.setText(intent.getStringExtra("quest_milestone"));

		DueDateHandler();
		HandleToDoList();
		GetMemberList();
		String new_member = "";
		for(int i = 0; i < member_list.size(); i++) {
			System.out.println(member_list.get(i));
			new_member = new_member.concat("[" + member_list.get(i) + "] ");
		}
		if(new_member.equals("[] ")) {
			my_member.setText("NONE");
		} else {
			my_member.setText(new_member);
		}
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

	private void DueDateHandler() {
		// TODO LIST: NEED TO BE DONE IN CREATE QUEST
	}

	private void HandleToDoList() {
		my_list = todo_list.split("[" + Constants.delimiter + "]+");
		for (String list : my_list) {
			todo_list_data.add(new MyToDoList(list));
			
		}
		System.out.println(todo_list_data.size());
		adapter = new ToDoListAdapter(QuestDetail.this, todo_list_data);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,
					final View view, final int position, long id) {
				/*if(!leader.equals(StaticClass.MY_ID)) {
					Toast.makeText(QuestDetail.this, leader + " " + StaticClass.MY_ID, Toast.LENGTH_SHORT).show();
					Toast.makeText(QuestDetail.this, StaticClass.TAG_NO_PERMISSION, Toast.LENGTH_SHORT).show();
					return;
				}*/
				intent = new Intent(QuestDetail.this, ToDoListStatusSetup.class);
				intent.putExtra("quest_id", quest_id);
				intent.putExtra("creator_name", leader);
				intent.putExtra("progress_status", progress_array.get(position));
				intent.putExtra("done_status", done_array.get(position));
				Log.d("position", Integer.toString(position));
				current_position = position;
				startActivityForResult(intent, 0);
				
//				Toast.makeText(QuestDetail.this, "HEllo World " + my_list[position], Toast.LENGTH_SHORT).show();
				
			}
		});
		
		

		/*
		 * listView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, final View
		 * view, final int position, long id) { MyToDoList current_list =
		 * (MyToDoList) parent.getItemAtPosition(position);
		 * Toast.makeText(getApplicationContext(), "Clicked on Row: " +
		 * current_list.getList(), Toast.LENGTH_LONG).show();
		 * 
		 * } });
		 */
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 153) {
			progress_array.set(current_position, "true");
			done_array.set(current_position, "true");
		}
		if (resultCode == 154) 	{
			progress_array.set(current_position, "true");
			done_array.set(current_position, "false");
		}
		if (resultCode == 155) {
			progress_array.set(current_position, "false");
			done_array.set(current_position, "true");
		}
		if (resultCode == 156) {
			progress_array.set(current_position, "false");
			done_array.set(current_position, "false");
		}
		StatusUpdate();
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}

	private void StatusUpdate() {
		progress_status = "";
		done_status = "";
		for(String list : progress_array) {
			progress_status = progress_status + list + Constants.delimiter;
		}
		for(String list : done_array) {
			done_status = done_status + list + Constants.delimiter;
		}
		UpdateStatus uq = new UpdateStatus();
		uq.execute();
	}
	class UpdateStatus extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg) {
			
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("progress_status", progress_status));
			params.add(new BasicNameValuePair("done_status", done_status));
			params.add(new BasicNameValuePair("quest_id", Integer.toString(quest_id)));
//			System.out.println(Boolean.toString(ToDoListAdapter.progress_status) + " : " + Boolean.toString(ToDoListAdapter.done_status));
			JSONObject json = jsonParser.makeHttpRequest(
					Constants.url_update_work_status, "GET", params);
			Log.d("Work Update info", json.toString());
			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					// SUCCESSFULLY UPDATED DATABASE.
					Log.d("Quest info", "WORK STATUS UPDATED");
				} else {
					Log.d("Quest info", "WORK STATUS UPDATE FAIL");
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
	

	private void SetVisible() {
		String username=((UserInfo)getApplicationContext()).getUserName();

		System.out.println(leader + " : " + username);
		if (leader.equals(username)) {
			findViewById(R.id.delete).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.join).setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			FinishActivities();
		}
		return super.onKeyDown(keyCode, event);
	}

	Button.OnClickListener ButtonClick = new Button.OnClickListener() {

		String username= ((UserInfo)getApplicationContext()).getUserName();
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.my_status:
				UpdateActiveStatus();
				break;
			case R.id.save:
				FinishActivities();
				break;
			case R.id.join:
				GetMemberList();
				if(member_list.contains(username)) {
					Toast.makeText(QuestDetail.this, "You are alreay a member of this Quest", Toast.LENGTH_SHORT).show();
					return;
				} else if(member_list.size() >= Constants.TAG_MAX_NUM) { 
					Toast.makeText(QuestDetail.this, "This quest has max member", Toast.LENGTH_SHORT).show();
					return;
				}
				else {
					Toast.makeText(QuestDetail.this, "Join Success", Toast.LENGTH_SHORT).show();
					MemberUpdate mu = new MemberUpdate();
					mu.execute();
				}
				break;
			case R.id.delete:
				AlertDialog deleteBox = DeleteDialog();
				deleteBox.show();
				break;
			}

		}

	};
	private void UpdateActiveStatus() {
		// UPDATE quest status on DB
		String username= ((UserInfo)getApplicationContext()).getUserName();

		if (leader.equals(username)) {
			if ("ACTIVE".equals(intent.getStringExtra("quest_status"))) {
				my_status.setTextColor(getResources().getColor(
						R.color.light_grey));
				intent.putExtra("quest_status", "INACTIVE");
				updatedStatus = "INACTIVE";

				// Set Alarm and
				// notifications here.

			} else if ("INACTIVE".equals(intent
					.getStringExtra("quest_status"))) {
				my_status.setTextColor(getResources().getColor(
						R.color.red));
				intent.putExtra("quest_status", "ACTIVE");
				updatedStatus = "ACTIVE";
			}

			UpdateQuest uq = new UpdateQuest();
			uq.execute(updatedStatus, intent.getStringExtra("quest_id"));

			my_status.setText(updatedStatus);
			// questJsonList[position].put("quest_status",
			// updatedStatus);

			Toast.makeText(QuestDetail.this, "Quest status updated",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(QuestDetail.this,
					Constants.TAG_NO_PERMISSION, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private AlertDialog DeleteDialog() {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				// set message, title, and icon
				.setMessage(Constants.TAG_DELETE)
				.setPositiveButton(Constants.TAG_CHECK_REMOVE,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								DeleteData();
								dialog.dismiss();
								Toast.makeText(getApplicationContext(),
										"Deleted", Toast.LENGTH_SHORT).show();
								FinishActivities();
							}
						})
				.setNegativeButton(Constants.TAG_CANCEL,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).create();
		return myQuittingDialogBox;
	}

	private void DeleteData() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				DeleteDataHelper();
			}
		});
		thread.start();

	}

	private void DeleteDataHelper() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("quest_id", Integer
				.toString(quest_id)));
		JSONObject json = jsonParser.makeHttpRequest(
				Constants.url_delete_quest, "POST", params);

		Log.d("DELETE STATUS", "DeleteDataHelper(): " + json.toString());

		try {
			int success = json.getInt(Constants.TAG_SUCCESS);

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
		if (check_option == Constants.SINGLE_USER_INFO) {
			intent.putExtra("option", Constants.SINGLE_USER_INFO);
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
			status = arg[0];// arg[0];
			String _id = Integer.toString(quest_id);
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("quest_id", _id));
			JSONObject json = jsonParser.makeHttpRequest(
					Constants.url_update_quest, "GET", params);
			Log.d("Quest Update info", json.toString());
			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
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
	
	class MemberUpdate extends AsyncTask<String, String, String> {
		
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			String username= ((UserInfo)getApplicationContext()).getUserName();

			member = member.concat(username + Constants.delimiter);
			String _id = Integer.toString(quest_id);
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("quest_id", _id));
			params.add(new BasicNameValuePair("quest_member", member));
			JSONObject json = jsonParser.makeHttpRequest(
					Constants.url_update_quest_member, "GET", params);
			Log.d("Quest Member info", json.toString());
			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					// SUCCESSFULLY UPDATED DATABASE.
					Log.d("Quest Member info", "QUEST MEMBER UPDATED");
				} else {
					Log.d("Quest Member info", "QUEST MEMBER UPDATE FAIL");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(String result) {
			String new_member = "";
			GetMemberList();
			for(int i = 0; i < member_list.size(); i++) {
				System.out.println(member_list.get(i));
				new_member = new_member.concat("[" + member_list.get(i) + "] ");
			}
			my_member.setText(new_member);
			my_member.invalidate();
		
		}
		
	}
}
