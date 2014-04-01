package com.CS429.todorpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class ToDoListStatusSetup extends Activity {
	CheckBox progress, done;
	Button change_status, cancel;
	Intent intent;
	String progress_status, done_status, leader;
	int quest_id; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.todo_list_status_setup);
		super.onCreate(savedInstanceState);
		intent = getIntent();
		quest_id = intent.getIntExtra("quest_id", -1);
		leader = intent.getStringExtra("creator_name");
		progress_status = intent.getStringExtra("progress_status");	
		done_status = intent.getStringExtra("done_status");
		Log.d("Progress", progress_status);
		Log.d("done", done_status);
		ActivitySizeHandler();
		FindViewById();
		SetWorkStatus();
		invalidate_work_status();

	}

	private void SetWorkStatus() {
		if(progress_status.equals("true")) 	{
			progress.setChecked(true);
//			ToDoListAdapter.progress_img.setVisibility(View.VISIBLE);
//			ToDoListAdapter.progress_status = true;
		} else {
			progress.setChecked(false);
//			ToDoListAdapter.progress_img.setVisibility(View.GONE);
//			ToDoListAdapter.progress_status = false;
		}
		if(done_status.equals("true")) 	{
			done.setChecked(true);
//			ToDoListAdapter.done_img.setVisibility(View.VISIBLE);
//			ToDoListAdapter.done_status = true;
		} else {
			done.setChecked(false);
//			ToDoListAdapter.done_img.setVisibility(View.VISIBLE);
//			ToDoListAdapter.done_status = false;
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
		done = (CheckBox) findViewById(R.id.done);
		findViewById(R.id.ok_btn).setOnClickListener(ButtonHandler);
		findViewById(R.id.cancel_btn).setOnClickListener(ButtonHandler);
		progress.setOnClickListener(checkBoxOption);
		done.setOnClickListener(checkBoxOption);
	}

	CheckBox.OnClickListener checkBoxOption = new CheckBox.OnClickListener() {
		@Override
		public void onClick(View view) {
			if(!leader.equals(StaticClass.MY_ID)) {
				Toast.makeText(ToDoListStatusSetup.this, StaticClass.TAG_NO_PERMISSION, Toast.LENGTH_SHORT).show();
				return;
			}
			switch (view.getId()) {
			case R.id.progress:
				if (progress.isChecked()) {
//					ToDoListAdapter.progress_status = true;
					progress_status = "true";
				} else {
//					ToDoListAdapter.progress_status = false;
					progress_status = "false";
				}
				// ToDoListAdapter.progress_status =
				// !ToDoListAdapter.progress_status;
				break;
			case R.id.done:
				if (done.isChecked()) {
//					ToDoListAdapter.done_status = true;
					done_status = "true";
				} else {
//					ToDoListAdapter.done_status = false;
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
				int TAG_WORK_STATUS = 0;
				if(progress_status.equals("true") && done_status.equals("true")) TAG_WORK_STATUS = 153;
				if(progress_status.equals("true") && done_status.equals("false")) TAG_WORK_STATUS = 154;
				if(progress_status.equals("false") && done_status.equals("true")) TAG_WORK_STATUS = 155;
				if(progress_status.equals("false") && done_status.equals("false")) TAG_WORK_STATUS = 156;
				
//				StatusUpdate();
				setResult(TAG_WORK_STATUS, intent);
				finish();
				break;
			case R.id.cancel_btn:
				finish();
				break;
			}

		}

	};
}