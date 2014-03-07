package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Utils.EncryptPassword;
import com.CS429.todorpg.Utils.JSONParser;

public class Login extends Activity {
	private ProgressDialog pDialog;
	EditText login_id, login_pw;
	TextView no_id_message, wrong_pw_message, id_empty_message,
			pw_empty_message;
	MyCharacter MyCharacter = new MyCharacter();
	JSONParser jsonParser = new JSONParser();
	JSONObject detail;
	Intent intent;

	// Persistent Data
	SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		prefs = getSharedPreferences(StaticClass.MY_PREFERENCES,
				Context.MODE_PRIVATE);
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

								// Store the USER ID and LOG IN STATUS into
								// persistent storage
								Editor editor = prefs.edit();
								editor.putString(StaticClass.PREF_USERNAME,
										log_info[0]); // user_name
								editor.putBoolean(
										StaticClass.PREF_IS_LOGGED_IN, true);
								if (!editor.commit()) {
									Log.d("PREF", "USER_NAME NOT STORED");
								}

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
				if (check == 5) {
					MyCharacter.execute();
				}
				break;
			case R.id.login_cancel_btn:
				finish();
				break;
			}
		}

	};

	class MyCharacter extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Loading Character Information. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg) {
			int success;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user_name",
						StaticClass.MY_ID));
				JSONObject json = jsonParser.makeHttpRequest(
						StaticClass.url_get_character_info, "GET", params);

				Log.d("Character Info", json.toString());

				success = json.getInt(StaticClass.TAG_SUCCESS);
				if (success == 1) {
					StaticClass.CHARACTER_CREATED = true;
					JSONArray info = json.getJSONArray(StaticClass.TAG_INFO);

					detail = info.getJSONObject(0);
					Log.d("Detail", detail.toString());
					// Static class update
					StaticClass.CLASS_INFO = new Character(
							detail.getString("character_name"),
							Integer.parseInt(detail.getString("str")),
							Integer.parseInt(detail.getString("con")),
							Integer.parseInt(detail.getString("dex")),
							Integer.parseInt(detail.getString("_int")),
							Integer.parseInt(detail.getString("wis")),
							Integer.parseInt(detail.getString("cha")),
							Integer.parseInt(detail.getString("level")),
							detail.getString("class"));

					int STR = Integer.parseInt(detail.getString("str"));
					int CON = Integer.parseInt(detail.getString("con"));
					int DEX = Integer.parseInt(detail.getString("dex"));
					int INT = Integer.parseInt(detail.getString("_int"));
					int WIS = Integer.parseInt(detail.getString("wis"));
					int CHA = Integer.parseInt(detail.getString("cha"));
					int LEVEL = Integer.parseInt(detail.getString("level"));
					String CLASS = detail.getString("class");

					// Shared Preferences Save
					Editor editor = prefs.edit();
					editor.putString(StaticClass.PREF_CHARACTER_NAME,
							detail.getString("character_name"));
					editor.putInt(StaticClass.PREF_CHARACTER_STR, STR);
					editor.putInt(StaticClass.PREF_CHARACTER_CON, CON);
					editor.putInt(StaticClass.PREF_CHARACTER_DEX, DEX);
					editor.putInt(StaticClass.PREF_CHARACTER_INT, INT);
					editor.putInt(StaticClass.PREF_CHARACTER_WIS, WIS);
					editor.putInt(StaticClass.PREF_CHARACTER_CHA, CHA);
					editor.putInt(StaticClass.PREF_CHARACTER_LEVEL, LEVEL);
					editor.putString(StaticClass.PREF_CHARACTER_CLASS, CLASS);
					editor.putBoolean(StaticClass.PREF_CHARACTER_EXISTS, true);

					if (!editor.commit()) {
						// Shared Preferences Save
						Log.d("PREF", "CHARACTER NOT STORED");
					}
				} else {
					Editor editor = prefs.edit();
					editor.putBoolean(StaticClass.PREF_CHARACTER_EXISTS, false);
					StaticClass.CHARACTER_CREATED = false;
					if (!editor.commit()) {
						// Shared Preferences Save
						Log.d("PREF", "CHARACTER MISSING MESSAGE NOT STORED");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String result) {
			pDialog.dismiss();
			MyCharacter.cancel(true);
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