package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.CS429.todorpg.Utils.Constants;
import com.CS429.todorpg.Utils.JSONParser;
import com.google.android.gms.maps.model.LatLng;

public class QuestCreation extends Activity {
//	private ProgressDialog pDialog;
	EditText title, description, duedate; //, newMilestone;
	private String month, day, location;
//	ListView milestones;
	Spinner location_spinner;
	Spinner maximum_spinner;
	JSONParser jsonParser = new JSONParser();
	CreateQuest createQuest = new CreateQuest();
	SharedPreferences prefs;
	String milestones_to_string;
	String alarmType, notitype;
	public static boolean milestone_written = false;
	public static ArrayList<String> milestones;
	
	private int capacity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_creation);
		ActivitySizeHandler();
		FindViewByID();
		milestones = new ArrayList<String>();
		SpinnerListener();
		//prefs = getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);	
		LocationHandler.setHandler(QuestCreation.this);
	}
	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(
		WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params);
	}
	private void SpinnerListener() {
		location_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if(location_spinner.getSelectedItem().toString().equals("Yes")){
					//prevent app crash due to location service unavailability
					if(!LocationHandler.getLocation())
						finish();
					
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		});
		
		maximum_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				capacity = Integer.parseInt(maximum_spinner.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//default set to 1
				capacity = 1;
			}
		});
	}
	
	
	private void FindViewByID() {
//		duration = (EditText) findViewById(R.id.creation_quest_duration);
//		month = (EditText)findViewById(R.id.creation_month);
//		day = (EditText)findViewById(R.id.creation_day);
		
		description = (EditText) findViewById(R.id.creation_quest_description);
		title = (EditText) findViewById(R.id.creation_quest_title);
		location_spinner = (Spinner)findViewById(R.id.creation_quest_location);
		maximum_spinner = (Spinner)findViewById(R.id.creation_quest_maximum_number);
		duedate = (EditText)findViewById(R.id.due_date_edit);
		findViewById(R.id.creation_milestone_btn).setOnClickListener(ButtonListener);
		findViewById(R.id.creation_quest_submit).setOnClickListener(ButtonListener);
		findViewById(R.id.creation_alarm).setOnClickListener(ButtonListener);
		findViewById(R.id.due_date).setOnClickListener(ButtonListener);
	}
	
	@SuppressLint("NewApi")
	public Boolean validate() {
		String questTitle = title.getText().toString();
//		String questDueMonth = month;
//		String questDueDay = day;
		String questDuedate = duedate.getText().toString();
		String questDescription = description.getText().toString();
		String questLocation = location_spinner.getSelectedItem().toString();
		Boolean validateStatus = true;
		if(questTitle.length() == 0) {
			title.setError("Please add a title.");
			Toast.makeText(QuestCreation.this, "Please add a title.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
/*		
		if(questDuration.length() == 0) {
			duration.setError("Please add a duration (hours)");
			Toast.makeText(QuestCreation.this, "Please add a duration (hours).", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
	
		if(questDueMonth.length() == 0 || questDueDay.length() == 0){
			Toast.makeText(QuestCreation.this, "please add due date", Toast.LENGTH_SHORT).show();
			validateStatus = false;
		}
*/		
		if(questDescription.length() == 0) {
			description.setError("Please add a description.");
			Toast.makeText(QuestCreation.this, "Please add a description.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		if(milestones_to_string == null) {
			Toast.makeText(QuestCreation.this, "Please add at least one milestone.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		
		if(!questLocation.equals("Yes") && !questLocation.equals("No")) {
			Toast.makeText(QuestCreation.this, "Please select location.", Toast.LENGTH_SHORT).show();
			validateStatus = false;

		}
		
		if(questDuedate == null || questDuedate.isEmpty()){
			Toast.makeText(QuestCreation.this, "please add due date", Toast.LENGTH_SHORT).show();
			validateStatus = false;
		}
		else{
			String[] date = questDuedate.split("/");
			
			if(date.length != 3){
				Toast.makeText(QuestCreation.this, "invalid due date format", Toast.LENGTH_SHORT).show();
				validateStatus = false;
			}
			else if(date[0].length() < 1 || date[0].length() > 2 || date[1].length() > 2 || date[1].length() < 1 
					|| date[2].length() != 4){
				Toast.makeText(QuestCreation.this, "invalid due date format", Toast.LENGTH_SHORT).show();
				validateStatus = false;
			}
			else if(month == null || day == null){
				month = date[0];
				day = date[1];
				Log.d("[due date]", "month: " + month + ", day: " + day);
			}
		}
		
		return 	validateStatus;
	}
	
	
	/************************need to fill out this form soon for alarm***********************************************************/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		//case for alarm setting
		if(resultCode == RESULT_OK && data != null){
			if(requestCode == 0){
//				Toast.makeText(getApplicationContext(), data.getExtras().getString("alarm"), Toast.LENGTH_SHORT).show();
				alarmType = data.getStringExtra("alarm");
				notitype = data.getStringExtra("noti");
			}
			//due date calendar view
			else if(requestCode == 1){
				String due_date = data.getStringExtra("DATE");
//				((Button)findViewById(R.id.due_date)).setText(due_date);
				duedate.setText(due_date);
				
				String[] dates = due_date.split("/");
				month = dates[0];
				day = dates[1];
				Log.d("[due date]", "month: " + month + ", day: " + day);
			}
		}
	}
	
	
	/**
	 * This method handles real alarm set...
	 * maybe I can use alarm intent or do alarm programatically...thinking...
	 */
	private void setAlarms(){
		//in case alarm hasn't set at all...
		if(alarmType == null || alarmType.equals("none")){
			Log.d("[AlarmTest]", "Alarm setting unavailable");
			return;
		}
		//default notitype is no ringtone
		if(notitype == null || notitype.equals("none")){
			notitype = "noring";
		}
		
		Log.d("[AlarmTest]", "Start Alarm setting");
		
//	    Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		Intent intent = new Intent(QuestCreation.this, RingtoneService.class);
	    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	    
	    String uri = getRingtoneForAlarm();
	    Log.d("[AlarmTest]", "double check: " + uri);
	    Log.d("[AlarmTest]", "alarm type: " + alarmType);
	    intent.putExtra("Ringtone", uri);
	    intent.putExtra("type", alarmType);
	    intent.putExtra("noti", notitype);
	    intent.putExtra("ringtone", getRingtoneForAlarm());
	    intent.putExtra("month", month);
	    intent.putExtra("day", day);
	    intent.putExtra("title", title.getText().toString());
	    
	    Calendar calendar = Calendar.getInstance();
	    Log.d("[AlarmTest]", "Time set: " + calendar.getTime());
	    calendar.add(Calendar.SECOND, 5);
	    
	    startService(intent);

	}

	/**
	 * This gets the ringtone uri to play when the device is awaken on the alarm
	 * 
	 * @return ringtone uri
	 */
	private String getRingtoneForAlarm(){
		Log.d("[AlarmTest]", "Start ringtone setting");
		
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		Log.d("[AlarmTest]", "Uri: " + uri);
		return uri.toString();
	}
	
	
	
	
	
	/****************************************************************************************************************************/
	
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.creation_milestone_btn:
				Intent intent = new Intent(QuestCreation.this, QuestMilestone.class);
				intent.putExtra("count", 1);
				startActivity(intent);
				break;
			case R.id.creation_quest_submit:
				if(milestone_written) {
					InsertData();
					Log.d("MILESTONE", milestones_to_string);
				}
				if(validate()){
					createQuest.execute();
					setAlarms();	//alarm set
					finish();
				}
				else
					Toast.makeText(QuestCreation.this, Constants.QUEST_FAIL, Toast.LENGTH_SHORT).show();
				break;

			case R.id.creation_alarm:
				Intent AlarmIntent = new Intent(QuestCreation.this, AlarmActivity.class);
				startActivityForResult(AlarmIntent, 0);
				break;
				
			case R.id.due_date:
				Intent DuedateIntent = new Intent(QuestCreation.this, CalendarView.class);
				startActivityForResult(DuedateIntent, 1);
				break;
			}
		}
	};
	
	

	/**
	 * This handler is only for location handler.
	 * once location handler is run, when it found the current location,
	 * this handler will deal with location data.
	 */
	public static final int LATLONG = 0;
	public static final int ADDRESS = 1;

	public final Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			
			case LATLONG:
				break;
				
			case ADDRESS:
				Log.d("[Location]", "address received here, " + msg.obj.toString());
				String address = msg.obj.toString();
				setDialog(address).create().show();
				break;
				
			default:
				break;
			}
		}
	};
	

	private AlertDialog.Builder setDialog(final String address){
		AlertDialog.Builder builder = new AlertDialog.Builder(QuestCreation.this);
		builder.setMessage("Your current location is found to be: \n" + address + "\n Do you want to use this address?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				location = address;
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				location_spinner.setSelection(2);
				dialog.cancel();
			}
		});
		return builder;
	}
	
	
	
	private void InsertData() {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for(; i < milestones.size() - 1; i++){
			sb.append(milestones.get(i) + Constants.delimiter);
		}
		sb.append(milestones.get(i));
		milestones_to_string = sb.toString();
		
	}
	
	class CreateQuest extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... args) {
			
			String questTitle = title.getText().toString();
	//		String questDuration = duration.getText().toString();
			
			/****ADDED ****/
			String quest_due_date = new String(month + "/" + day);	//due date in form of "month/day"
			String quest_max_member = String.valueOf(capacity);	//maximum capacity
			String quest_location = null;
			/*************/
			
			String questDescription = description.getText().toString();
//			String questMilestones = collapseMilestones();
			String questLocation = location_spinner.getSelectedItem().toString();
			Log.d("spinner1", questLocation);
			String questLocationLat = null;
			String questLocationLong = null;
			Log.d("Location Spinner", questLocation);
			if(questLocation.equals("Yes")){
				Log.d("spinner", questLocation);
				quest_location = location;
			}
			//TODO
			String currentlyLoggedIn = "";
			// Get user ID
			String userName = "";
			UserInfo user = (UserInfo)getApplicationContext();
			if (user.isLoggedIn()) {
				user.getUserName();
				//userName = prefs.getString(Constants.PREF_USERNAME, );
			}
			else{
				userName="NOT_LOGGED_IN_CHECK_CODE";
			}
			String num_false = "";
			System.out.println(milestones.size());
			for(int i = 0; i < milestones.size(); i++) {
				num_false += "false" + StaticClass.delimiter;
				
			}
			
			System.out.println(quest_due_date + " / " + quest_max_member + " /  " + quest_location);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("quest_title", questTitle));
			params.add(new BasicNameValuePair("quest_description", questDescription));
			params.add(new BasicNameValuePair("quest_difficulty", 5+""));//Integer.toString(listOfMilestones.size())));
			params.add(new BasicNameValuePair("creator_name", userName));
			params.add(new BasicNameValuePair("quest_location_lat", questLocationLat));
			params.add(new BasicNameValuePair("quest_location_long", questLocationLong));
//			params.add(new BasicNameValuePair("quest_duration", questDuration));
			params.add(new BasicNameValuePair("quest_milestone", milestones_to_string));
			params.add(new BasicNameValuePair("quest_due_date", quest_due_date));
			params.add(new BasicNameValuePair("quest_location", "Siebel")); // TODO quest_location));
			
			
			params.add(new BasicNameValuePair("quest_max_member", quest_max_member));
			params.add(new BasicNameValuePair("progress_status", num_false));
			params.add(new BasicNameValuePair("done_status", num_false));

			
			JSONObject json = jsonParser.makeHttpRequest(
					Constants.url_create_quest, "POST", params);
			
			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);

				if (success == 1) {
					Log.d("Quest Status", "Quest Created Successfully");
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
			
		}
		
		protected void onPostExecute(String file_url) {
			Toast.makeText(QuestCreation.this, Constants.QUEST_SUCCESS, Toast.LENGTH_SHORT).show();
			createQuest.cancel(true);

			finish();
		}
	}
}
