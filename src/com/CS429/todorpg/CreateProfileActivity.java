package com.CS429.todorpg;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CreateProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_profile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_profile, menu);
		return true;
	}

	public void createNewProfile(View view) {
		EditText email_box = (EditText) this.findViewById(R.id.new_email_box);
		String email = email_box.getText().toString();
		EditText pass_box = (EditText) this.findViewById(R.id.new_password_box);
		String pass = pass_box.getText().toString();
		EditText repeat_pass_box = (EditText) this
				.findViewById(R.id.reenter_password_box);
		String repeat_pass = repeat_pass_box.getText().toString();
		saveProfile(email, pass, repeat_pass);

	}

	private void saveProfile(String email, String pass, String repeat_pass) {
		if (pass.compareTo(repeat_pass) != 0) {
			Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Password mismatch");
			alert.setMessage("Your passwords do not match");
			alert.setCancelable(true);
			return;
		}
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("email", email);
		editor.putString("pass", pass);
		editor.commit();
	}

	public void cancel(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}
