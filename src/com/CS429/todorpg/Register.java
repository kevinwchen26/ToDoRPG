package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.CS429.Utils.EncryptPassword;
import com.CS429.Utils.JSONParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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

public class Register extends Activity {
	private ProgressDialog pDialog;
	EditText reg_id, reg_pw, repeat_pw;
	Button register_btn, cancel_btn;
	TextView duplicate_message, name_empty_messaage, pw_empty_message,
			cpw_empty_message, pw_not_match_message;
	JSONParser jsonParser = new JSONParser();
	CreateAccount create_account = new CreateAccount();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		ActivitySizeHandler();
		FindViewById();
		new GetAllAccountInfo().execute();
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
		reg_id = (EditText) findViewById(R.id.register_ID);
		reg_pw = (EditText) findViewById(R.id.register_password);
		repeat_pw = (EditText) findViewById(R.id.confirm_password);
		duplicate_message = (TextView) findViewById(R.id.register_warning_message);
		name_empty_messaage = (TextView) findViewById(R.id.register_name_empty_message);
		pw_empty_message = (TextView) findViewById(R.id.register_pw_empty_message);
		cpw_empty_message = (TextView) findViewById(R.id.register_cpw_empty_message);
		pw_not_match_message = (TextView) findViewById(R.id.pw_not_match_message);
		findViewById(R.id.register_btn).setOnClickListener(ButtonListener);
		findViewById(R.id.register_cancel_btn).setOnClickListener(ButtonListener);
	}

	/* Button Setting */
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			HideWarningMessage();
			switch (view.getId()) {
			case R.id.register_btn:
				ArrayList<String> ID_List = new ArrayList<String>();
				if (GetAllAccountInfo.data_check) {
					for (int i = 0; i < GetAllAccountInfo.account.length(); i++) {
						JSONObject object;
						try {
							object = GetAllAccountInfo.account.getJSONObject(i);
							ID_List.add(object.getString(StaticClass.TAG_USER_NAME));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				boolean warning = false;
				if (ShowWarningMessage(ID_List, warning))
					break;

				if ((reg_pw.getText().toString()).equals(repeat_pw.getText()
						.toString())) {
					create_account.execute();
				}
//				Toast.makeText(Register.this, StaticClass.ACCOUNT_CREATION_MESSAGE, Toast.LENGTH_SHORT).show();
				break;
			case R.id.register_cancel_btn:
				finish();
				break;
			}
		}
	};
	/* Hide warning message when data is correctly filled */
	public void HideWarningMessage() {
		duplicate_message.setVisibility(View.GONE);
		name_empty_messaage.setVisibility(View.GONE);
		pw_empty_message.setVisibility(View.GONE);
		cpw_empty_message.setVisibility(View.GONE);
		pw_not_match_message.setVisibility(View.GONE);
	}
	/* Show warning Message */
	@SuppressLint("NewApi")
	public boolean ShowWarningMessage(ArrayList<String> ID_List, boolean warning) {
		if (reg_id.getText().toString().isEmpty()) {
			name_empty_messaage.setTextColor(Color.RED);
			name_empty_messaage.setVisibility(View.VISIBLE);
			warning = true;
		} else if (DuplicateCheck(ID_List)) {
			duplicate_message.setTextColor(Color.RED);
			duplicate_message.setVisibility(View.VISIBLE);
			Log.d("DUPLICATE ID", reg_id.getText().toString());
			warning = true;
		}
		if (reg_pw.getText().toString().isEmpty()) {
			pw_empty_message.setTextColor(Color.RED);
			pw_empty_message.setVisibility(View.VISIBLE);
			warning = true;
		}
		if (repeat_pw.getText().toString().isEmpty()) {
			cpw_empty_message.setTextColor(Color.RED);
			cpw_empty_message.setVisibility(View.VISIBLE);
			warning = true;
		}
		if (!reg_pw.getText().toString().equals(repeat_pw.getText().toString())) {
			pw_not_match_message.setTextColor(Color.RED);
			pw_not_match_message.setVisibility(View.VISIBLE);
			warning = true;
		}
		return warning;
	}

	/* Insert Login info to Database in SQL */
	class CreateAccount extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
			pDialog.setMessage("Creating Account now..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			String name = reg_id.getText().toString();
			String password = reg_pw.getText().toString();
			String encrypted_password = null;
			Log.d("ID, PW : ", "ID : " + name + ", PW : " + password);
			try {
				encrypted_password = EncryptPassword.Encrypt(password);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Log.d("Encrypted_password : ", encrypted_password);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name", name));
			params.add(new BasicNameValuePair("password", encrypted_password));

			JSONObject json = jsonParser.makeHttpRequest(
					StaticClass.url_create_account, "POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);

				if (success == 1) {
					Log.d("Account Status", "Account Created Successfully");
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			Toast.makeText(Register.this, StaticClass.ACCOUNT_CREATION_MESSAGE, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();
			create_account.cancel(true);
			finish();
		}
	}

	public boolean DuplicateCheck(ArrayList<String> ID_List) {
		String user_name = reg_id.getText().toString();
		Log.d("user name", user_name);
		for (String id : ID_List) {
			if (user_name.equalsIgnoreCase(id)) {
				return true;
			}
		}
		return false;
	}
	
}
