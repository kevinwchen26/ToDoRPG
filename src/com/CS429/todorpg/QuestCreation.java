package com.CS429.todorpg;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_creation);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);
		FindViewByID();
		setMilestones();
		setMilestoneButton();
		
	}
	
	private void setMilestoneButton() {
		LayoutInflater inflater = (LayoutInflater) QuestCreation.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.milestone_popup, (ViewGroup) findViewById(R.id.popup_element));
		Button milestoneButton = (Button) layout.findViewById(R.id.add_milestone_btn);
		
		milestoneButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "HI", 0).show();
				popUp.dismiss();
			}
			
		});
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
		try{
			// Get instance of layout infalter
			LayoutInflater inflater = (LayoutInflater) QuestCreation.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.milestone_popup, (ViewGroup) findViewById(R.id.popup_element));

			popUp = new PopupWindow(layout, 300, 370, true);
			popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.creation_milestone_btn:
				Toast.makeText(getApplicationContext(), "HI", 0).show();
				openMilestonePopUp();
				break;
			case R.id.creation_location_btn:
				break;

			}
		}

	};
	
}
