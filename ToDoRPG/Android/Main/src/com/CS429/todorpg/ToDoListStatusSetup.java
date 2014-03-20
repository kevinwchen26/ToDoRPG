package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.CS429.todorpg.Utils.JSONParser;

public class ToDoListStatusSetup extends Activity {
	CheckBox progress, done;
	Button change_status, cancel;
	Intent intent;
	String progress_status, done_status;
	int quest_id; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.todo_list_status_setup);
		super.onCreate(savedInstanceState);
		intent = getIntent();
		quest_id = intent.getIntExtra("quest_id", -1);
		progress_status = intent.getStringExtra("progress_status");
		done_status = intent.getStringExtra("done_status");
		ActivitySizeHandler();
		FindViewById();
		SetWorkStatus();
		invalidate_work_status();

	}
	private void SetWorkStatus() {
		if(progress_status.equals("true")) 	{
			progress.setChecked(true);
			ToDoListAdapter.progress_status = true;
		} else {
			progress.setChecked(false);
			ToDoListAdapter.progress_status = false;
		}
		if(done_status.equals("true")) 	{
			done.setChecked(true);
			ToDoListAdapter.done_status = true;
		} else {
			done.setChecked(false);
			ToDoListAdapter.done_status = false;
		}
		invalidate_work_status();
	}
	private void invalidate_work_status() {
		progress.invalidate();
		done.invalidate();
	}

	/* Handle Size of Screen */
	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params);
	}

	private void FindViewById() {
		progress = (CheckBox) findViewById(R.id.progress);
		progress.setOnClickListener(checkBoxOption);
		done = (CheckBox) findViewById(R.id.done);
		done.setOnClickListener(checkBoxOption);
		findViewById(R.id.ok_btn).setOnClickListener(ButtonHandler);
		findViewById(R.id.cancel_btn).setOnClickListener(ButtonHandler);
		
	}

	CheckBox.OnClickListener checkBoxOption = new CheckBox.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.progress:
				if (progress.isChecked()) {
					ToDoListAdapter.progress_status = true;
					progress_status = "true";
				} else {
					ToDoListAdapter.progress_status = false;
					progress_status = "false";
				}
				// ToDoListAdapter.progress_status =
				// !ToDoListAdapter.progress_status;
				break;
			case R.id.done:
				if (done.isChecked()) {
					ToDoListAdapter.done_status = true;
					done_status = "true";
				} else {
					ToDoListAdapter.done_status = false;
					done_status = "false";
				}
				break;
			}

		}

	};
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.ok_btn:
				StatusUpdate();
				setResult(StaticClass.TAG_WORK_STATUS, intent);
				finish();
				break;
			case R.id.cancel_btn:
				finish();
				break;
			}

		}

	};

	private void StatusUpdate() {

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
			System.out.println(Boolean.toString(ToDoListAdapter.progress_status) + " : " + Boolean.toString(ToDoListAdapter.done_status));
			JSONObject json = jsonParser.makeHttpRequest(
					StaticClass.url_update_work_status, "GET", params);
			Log.d("Work Update info", json.toString());
			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);
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
}