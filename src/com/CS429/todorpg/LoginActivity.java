package com.CS429.todorpg;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void Login(View view) {
		// TODO save data and go to main page
		EditText email_box = (EditText) findViewById(R.id.email_box);
		EditText pass_box = (EditText) findViewById(R.id.password_box);
		String email = email_box.getText().toString();
		String pass = pass_box.getText().toString();
		if (checkCredentials(email, pass)) {
			SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("email", email);
			editor.putString("pass", pass);
			editor.commit();
		} else {
			Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Email/Pass incorrect");
			alert.setMessage("Your email or password is incorrect");
			alert.setCancelable(true);
			email_box.setText("");
			pass_box.setText("");
			return;
		}
	}

	public boolean checkCredentials(String email, String pass) {
		// TODO check credentials against credentials in database
		return true;
	}

	public void createProfile(View view) {
		Intent intent = new Intent(this, CreateProfileActivity.class);
		startActivity(intent);
	}

}
