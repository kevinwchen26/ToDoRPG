package com.CS429.todorpg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class StartMain extends Activity {
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_main);
		ButtonHandler();

	}

	private void ButtonHandler() {
		findViewById(R.id.login_btn).setOnClickListener(ButtonOption);
		findViewById(R.id.register_btn).setOnClickListener(ButtonOption);;
		findViewById(R.id.create_character_btn).setOnClickListener(ButtonOption);;
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
				intent = new Intent(StartMain.this, CharacterCreation.class);
				startActivity(intent);
				break;
			}

		}

	};

	private void RegisterHandler() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout LoginLayout = (LinearLayout) inflater.inflate(R.layout.register, null);

		final EditText id = (EditText) LoginLayout.findViewById(R.id.register_email);
		final EditText pw = (EditText) LoginLayout.findViewById(R.id.register_password);
		final EditText cpw = (EditText) LoginLayout.findViewById(R.id.confirm_password);
		new AlertDialog.Builder(StartMain.this)
				.setTitle("Register")
				.setView(LoginLayout)
				.setPositiveButton("Register",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								if ((pw.getText().toString()).equals(cpw.getText().toString())) {
									Toast.makeText(StartMain.this,
											"Good! \nID : " + id.getText().toString()
													+ "\nPW : "
													+ pw.getText().toString()
													+ "\nCPW"
													+ cpw.getText().toString(),
											Toast.LENGTH_LONG).show();

								} else {
									Toast.makeText(
											StartMain.this, "Wrong password" +  "\nPW : "
													+ pw.getText().toString()
													+ "\nCPW : "
													+ cpw.getText().toString(),
											Toast.LENGTH_LONG).show();
								}
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();

	}

	private void LoginHandler() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout LoginLayout = (LinearLayout) inflater.inflate(
				R.layout.login, null);

		final EditText id = (EditText) LoginLayout
				.findViewById(R.id.login_username);
		final EditText pw = (EditText) LoginLayout
				.findViewById(R.id.login_password);

		new AlertDialog.Builder(StartMain.this)
				.setTitle("Login")
				.setView(LoginLayout)
				.setPositiveButton("Login",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(
										StartMain.this,
										"ID : " + id.getText().toString()
												+ "\nPW : "
												+ pw.getText().toString(),
										Toast.LENGTH_LONG).show();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
