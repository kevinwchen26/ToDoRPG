package com.CS429.todorpg;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	private ProgressDialog pDialog;
	LoginProgress login_progress = new LoginProgress();
	EditText login_id, login_pw;
	TextView no_id_message, wrong_pw_message, id_empty_message,
			pw_empty_message;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ActivitySizeHandler();
		new GetAllAccountInfo().execute();
		FindViewById();
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

	/* Connect ID */
	private void FindViewById() {
		login_id = (EditText) findViewById(R.id.login_username);
		login_pw = (EditText) findViewById(R.id.login_password);
		no_id_message = (TextView) findViewById(R.id.no_id_found_message);
		wrong_pw_message = (TextView) findViewById(R.id.wrong_pw_message);
		id_empty_message = (TextView) findViewById(R.id.login_name_empty_message);
		pw_empty_message = (TextView) findViewById(R.id.login_pw_empty_message);
		findViewById(R.id.login_btn).setOnClickListener(ButtonListener);
		findViewById(R.id.login_cancel_btn).setOnClickListener(ButtonListener);
	}

	/* Button Handler */
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			HideWarningMessage();
			switch (view.getId()) {
			case R.id.login_btn:
				String user_name = login_id.getText().toString();
				String password = login_pw.getText().toString();
				ArrayList<String> ID_List = new ArrayList<String>();
				if (GetAllAccountInfo.data_check) {
					for (int i = 0; i < GetAllAccountInfo.account.length(); i++) {
						JSONObject object;
						try {
							object = GetAllAccountInfo.account.getJSONObject(i);
							ID_List.add(object
									.getString(StaticClass.TAG_USER_NAME)
									+ "||"
									+ object.getString(StaticClass.TAG_PASSWORD));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				int check = 0;
				for (String id : ID_List) {
					String[] log_info = id.split("[||]+");
					try {
						String encrypted_password = null;
						encrypted_password = EncryptPassword.Encrypt(password);
						// Log.d("encrypted password", encrypted_password);

						if (user_name.isEmpty()) {
							check = 1;
							ShowWarningMessage(check);
						}
						if (password.isEmpty()) {
							check = 2;
							ShowWarningMessage(check);
						}
						if (check == 1 || check == 2)
							break;

						if (user_name.equals(log_info[0])) {
							Log.d("I'm here", "ID FOUND!");
							if (encrypted_password.equals(log_info[1])) {
								Log.d("I'm here too", "PW found");
								StaticClass.MY_ID = log_info[0];
								check = 5;
								break;
							} else {
								check = 4;
								break;
							}
						} else {
							check = 3;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				ShowWarningMessage(check);
				if(check == 5) login_progress.execute();
				/*
				 * for(String id : ID_List) { Log.d("ID", id); }
				 */
				break;
			case R.id.login_cancel_btn:
				finish();
				break;
			}
		}

	};
	class LoginProgress extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Loading your information now...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		protected void onPostExecute(String result) {
			Toast.makeText(Login.this, StaticClass.LOGIN_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();
			login_progress.cancel(true);
			Intent intent = new Intent();
			setResult(StaticClass.LOGIN_SUCCESS, intent);
			finish();
		}
		
	}

	@SuppressLint("NewApi")
	private void ShowWarningMessage(int check) {
		if (check == 1) {
			id_empty_message.setTextColor(Color.RED);
			id_empty_message.setVisibility(View.VISIBLE);
		} else if (check == 2) {
			pw_empty_message.setTextColor(Color.RED);
			pw_empty_message.setVisibility(View.VISIBLE);
		} else if (check == 3) {
			no_id_message.setTextColor(Color.RED);
			no_id_message.setVisibility(View.VISIBLE);
		} else if (check == 4) {
			wrong_pw_message.setTextColor(Color.RED);
			wrong_pw_message.setVisibility(View.VISIBLE);
		} else {
			HideWarningMessage();
		}
	}

	private void HideWarningMessage() {
		no_id_message.setVisibility(View.GONE);
		pw_empty_message.setVisibility(View.GONE);
		id_empty_message.setVisibility(View.GONE);
		wrong_pw_message.setVisibility(View.GONE);
	}

}
