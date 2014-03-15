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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.CS429.todorpg.Utils.JSONParser;

public class QuestDetail extends Activity {
	Intent intent;
	TextView my_title, my_leader, my_member, my_due_date, my_location,
			my_milestone, my_description;
	Button my_status;
	int check_option;
	String updatedStatus, todo_list, member, due_date, title, status, location, description;
	static String leader;
	int quest_id;
	JSONParser jsonParser = new JSONParser();
	ArrayList<MyToDoList> arrData;
	ArrayList<String> member_list;
	ToDoListAdapter adapter;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_detail);
		
		intent = getIntent();
		title = intent.getStringExtra("quest_title");
		check_option = intent.getIntExtra("option", -1);
		quest_id = intent.getIntExtra("quest_id", -1);
		leader = intent.getStringExtra("creator_name");
		todo_list = intent.getStringExtra("quest_milestone");
		member = intent.getStringExtra("quest_member");
		description = intent.getStringExtra("quest_description");
		FindViewById();
		arrData = new ArrayList<MyToDoList>();

		setMessage();

	}
	private void GetMemberList() {
		member_list = new ArrayList<String>();
		if(member == null) {
			return;
		}
		String[] split = member.split("["+ StaticClass.delimiter +"]+");
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
//			new_member = ((i==0)? (new_member.concat(member_list.get(i))) : (", " + new_member.concat(member_list.get(i))));
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
		String[] my_list = todo_list.split("[" + StaticClass.delimiter + "]+");
		for (String list : my_list) {
			arrData.add(new MyToDoList(list));
		}
		System.out.println(arrData.size());
		adapter = new ToDoListAdapter(QuestDetail.this, arrData);
		listView.setAdapter(adapter);

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

	private void SetVisible() {

		System.out.println(leader + " : " + StaticClass.MY_ID);
		if (leader.equals(StaticClass.MY_ID)) {
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
				if(member_list.contains(StaticClass.MY_ID)) {
					Toast.makeText(QuestDetail.this, "You are alreay a member of this Quest", Toast.LENGTH_SHORT).show();
					return;
				} else if(member_list.size() >= StaticClass.TAG_MAX_NUM) { 
					Toast.makeText(QuestDetail.this, "This quest has max member", Toast.LENGTH_SHORT).show();
					return;
				}
				else {
					Toast.makeText(QuestDetail.this, "Join Starts", Toast.LENGTH_SHORT).show();
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
		if (leader.equals(StaticClass.MY_ID)) {
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
					StaticClass.TAG_NO_PERMISSION, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private AlertDialog DeleteDialog() {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				// set message, title, and icon
				.setMessage(StaticClass.TAG_DELETE)
				.setPositiveButton(StaticClass.TAG_CHECK_REMOVE,
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
				.setNegativeButton(StaticClass.TAG_CANCEL,
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
				StaticClass.url_delete_quest, "POST", params);

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
			status = arg[0];// arg[0];
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
	
	class MemberUpdate extends AsyncTask<String, String, String> {
		
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			member = member.concat(StaticClass.MY_ID + StaticClass.delimiter);
			String _id = Integer.toString(quest_id);
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("quest_id", _id));
			params.add(new BasicNameValuePair("quest_member", member));
			JSONObject json = jsonParser.makeHttpRequest(
					StaticClass.url_update_quest_member, "GET", params);
			Log.d("Quest Member info", json.toString());
			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);
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
