package com.CS429.todorpg;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class QuestCreation extends Activity {
	
	EditText title;
	ListView milestones;
	PopupWindow popUp;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_creation);
		FindViewByID();
		setMilestones();
		
	}
	
	
	private void setMilestones() {
		String[] values = new String[]{"HI", "test" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		//Assign adapter to list view
		milestones.setAdapter(adapter);
		milestones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//Listview clicked item index
				int itemPosition = position;
				
				//Listview clicked item value
				String itemValue = (String) milestones.getItemAtPosition(position);
				
				//show alert
				 Toast.makeText(getApplicationContext(),
	                      "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
	                      .show();
			} 
			
			
		});
	}
	
	private void FindViewByID() {
		milestones = (ListView) findViewById(R.id.quest_milestones);
		title = (EditText) findViewById(R.id.creation_quest_title);
		findViewById(R.id.creation_milestone_btn).setOnClickListener(ButtonListener);
		findViewById(R.id.creation_location_btn).setOnClickListener(ButtonListener);

	
	}
	
	private void openMilestonePopUp(){
		
	}
	
	
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.creation_milestone_btn:
				openMilestonePopUp();
				break;
			case R.id.creation_location_btn:
				break;
			}
		}

	};
	
}
