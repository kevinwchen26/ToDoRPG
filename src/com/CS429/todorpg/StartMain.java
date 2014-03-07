package com.CS429.todorpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class StartMain extends Activity {
	public static Activity startMain_activity;
	Intent intent, character_intent;
	
	RelativeLayout header, sub_header;
	TextView user_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_main);
		startMain_activity = this;
		ButtonHandler();
		
	}
	private void ButtonHandler() {
		header = (RelativeLayout) findViewById(R.id.header);
		sub_header = (RelativeLayout) findViewById(R.id.sub_header);
		user_id = (TextView) findViewById(R.id.user_id);
		findViewById(R.id.login_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.logout_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.quit_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.register_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.create_character_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.quest_creation_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.availabe_quest_info_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.character_info_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.join_quest_btn).setOnClickListener(ButtonOption);
	}

	Button.OnClickListener ButtonOption = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.register_btn:
					RegisterHandler();
					break;
				case R.id.login_btn:
					LoginHandler();
					break;
				case R.id.create_character_btn:
					CharacterCreation();
					break;
				case R.id.quest_creation_btn:
					QuestCreation();
					break;
				case R.id.availabe_quest_info_btn:
					AvailableQuests();
					break;
				case R.id.character_info_btn:
					CharacterInfo();
					break;
				case R.id.logout_btn:
					LogoutHandler();
					break;
				case R.id.join_quest_btn:
					JoinHandler();
					break;
				case R.id.quit_btn:
					finish();
			}

		}

		

	};

	private void JoinHandler(){
		if(!LoginStatus()) {
			Toast.makeText(this, StaticClass.NEED_LOGIN_MESSAGE, Toast.LENGTH_SHORT).show();
			return;
		} else {
			if(StaticClass.isNetworkConnected(startMain_activity)){
				Log.d("STATUS", "CONNECTED");
				intent = new Intent(StartMain.this, MapActivity.class);
				startActivity(intent);
			} else {
				StaticClass.GetNetworkDialog(startMain_activity).show(); 
				Log.d("STATUS", "NOT CONNECTED");
				return;
			}
			
			
		}
	}
	
	private void RegisterHandler() {
		if(StaticClass.isNetworkConnected(startMain_activity)){
			Log.d("STATUS", "CONNECTED");
			intent = new Intent(this, Register.class);
			startActivity(intent);
		} else {
			StaticClass.GetNetworkDialog(startMain_activity).show();
			Log.d("STATUS", "NOT CONNECTED");
			return;
		}
	}

	private void LoginHandler() {
		if(StaticClass.isNetworkConnected(startMain_activity)){
			Log.d("STATUS", "CONNECTED");
			intent = new Intent(this, Login.class);
			startActivityForResult(intent, 0);
		} else {
			StaticClass.GetNetworkDialog(startMain_activity).show(); 
			Log.d("STATUS", "NOT CONNECTED");
			return;
		}
	}
	public boolean LoginStatus() {
		if(StaticClass.MY_USERNAME == null) return false;
		else return true;
	}
	public void CharacterCreation() {
		// TODO Check if user created character
		if(!LoginStatus()) {
			Toast.makeText(this, StaticClass.NEED_LOGIN_MESSAGE, Toast.LENGTH_SHORT).show();
			return;
		}
		if(StaticClass.CHARACTER_CREATED) {
			Toast.makeText(this, StaticClass.HAVE_CHARACTER_MESSAGE, Toast.LENGTH_SHORT).show();
			return;
		}
		intent = new Intent(StartMain.this, CharacterCreation.class);
		startActivity(intent);
	}
	public void CharacterInfo() {
		if(!LoginStatus()) {
			Toast.makeText(this, StaticClass.NEED_LOGIN_MESSAGE, Toast.LENGTH_SHORT).show();
			return;
		}
		if(!StaticClass.CHARACTER_CREATED) {
			Toast.makeText(this, StaticClass.DONT_HAVE_CHARACTER_MESSAGE, Toast.LENGTH_SHORT).show();
			return;
		}
		intent = new Intent(StartMain.this, MyCharacterInfo.class);
		startActivity(intent);
		
	}
	public void LogoutHandler() {
		Toast.makeText(this, StaticClass.LOGOUT_MESSAGE, Toast.LENGTH_SHORT).show();
		/*TODO all personal info should be removed, right now id is the only info that user has */
		StaticClass.MY_USERNAME = null;
		StaticClass.CLASS_INFO = null;
		header.setVisibility(View.VISIBLE);
		sub_header.setVisibility(View.GONE);
	}
	public void QuestCreation() {
		if(!LoginStatus()) {
			Toast.makeText(this, StaticClass.NEED_LOGIN_MESSAGE, Toast.LENGTH_SHORT).show();
			return;
		} else {
			if(StaticClass.isNetworkConnected(startMain_activity)){
				Log.d("STATUS", "CONNECTED");
				intent = new Intent(StartMain.this, QuestCreation.class);
				startActivity(intent);
			} else {
				StaticClass.GetNetworkDialog(startMain_activity).show(); 
				Log.d("STATUS", "NOT CONNECTED");
				return;
			}			
		}
	}
	private void AvailableQuests() {
		if(!LoginStatus()) {
			Toast.makeText(this, StaticClass.NEED_LOGIN_MESSAGE, Toast.LENGTH_SHORT).show();
			return;
		} else {
			if(StaticClass.isNetworkConnected(startMain_activity)){
				Log.d("STATUS", "AvailableQuests: CONNECTED");
				intent = new Intent(StartMain.this, QuestInfo.class);
				startActivity(intent);
			} else {
				StaticClass.GetNetworkDialog(startMain_activity).show(); 
				Log.d("STATUS", "NOT CONNECTED");
				return;
			}			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == StaticClass.LOGIN_SUCCESS) {
			header.setVisibility(View.GONE);
			user_id.setText(StaticClass.WELCOME_MESSAGE + " " + StaticClass.MY_USERNAME);
			sub_header.setVisibility(View.VISIBLE);
		}
	}

}
