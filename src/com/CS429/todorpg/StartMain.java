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

public class StartMain extends Activity {
	Intent intent;
	RelativeLayout header, sub_header;
	TextView user_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_main);
		ButtonHandler();
	}
	private void ButtonHandler() {
		header = (RelativeLayout) findViewById(R.id.header);
		sub_header = (RelativeLayout) findViewById(R.id.sub_header);
		user_id = (TextView) findViewById(R.id.user_id);
		findViewById(R.id.login_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.logout_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.register_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.create_character_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.quest_creation_btn).setOnClickListener(ButtonOption);
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
				case R.id.logout_btn:
					LogoutHandler();
					break;
			}

		}

	};

	private void RegisterHandler() {
		intent = new Intent(this, Register.class);
		startActivity(intent);
	}

	private void LoginHandler() {
		intent = new Intent(this, Login.class);
		startActivityForResult(intent, 0);
	}
	
	public void CharacterCreation() {
		intent = new Intent(StartMain.this, CharacterCreation.class);
		startActivity(intent);
	}
	public void LogoutHandler() {
		Toast.makeText(this, StaticClass.LOGOUT_MESSAGE, Toast.LENGTH_SHORT).show();
		/*TODO all personal info should be removed, right now id is the only info that user has */
		StaticClass.MY_ID = null;
		header.setVisibility(View.VISIBLE);
		sub_header.setVisibility(View.GONE);
	}
	public void QuestCreation() {
		intent = new Intent(StartMain.this, QuestCreation.class);
		startActivity(intent);
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
			user_id.setText(StaticClass.WELCOME_MESSAGE + " " + StaticClass.MY_ID);
			sub_header.setVisibility(View.VISIBLE);
		}
	}

}
