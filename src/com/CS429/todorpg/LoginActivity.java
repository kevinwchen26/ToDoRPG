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

	public void login(View view) {
		EditText email_box = (EditText) findViewById(R.id.email_box);
		EditText pass_box = (EditText) findViewById(R.id.password_box);
		String email = email_box.getText().toString();
		String pass = pass_box.getText().toString();
		/*
		 * check if credentials match any on database
		 */
		if (checkCredentials(email, pass)) {
			Intent intent = new Intent(this, MainPageActivity.class);
			startActivity(intent);
		} else {
			/*
			 * fail message
			 */
			Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Email/Pass incorrect");
			alert.setMessage("Your email or password is incorrect");
			alert.setCancelable(true);
			email_box.setText("");
			pass_box.setText("");
			return;
		}
	}

	/*
	 * checks inputed credentials against any on the database
	 */
	public boolean checkCredentials(String email, String pass) {
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		String saved_email = preferences.getString("email", "");
		String saved_pass = preferences.getString("pass", "");
		if (saved_email.compareTo(email) == 0
				&& saved_pass.compareTo(pass) == 0) {
			return true;
		}
		return false;
	}

	/*
	 * onClick handler for new profile button on login page brings users to
	 * profile creation form
	 */
	public void createProfile(View view) {
		Intent intent = new Intent(this, CreateProfileActivity.class);
		startActivity(intent);
	}

}
