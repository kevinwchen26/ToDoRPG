package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Utils.JSONParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterCreation extends Activity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	InsertData insert = new InsertData();
	LinearLayout stat_1_3, stat_4_6, character_skill_1, character_skill_2, character_skill_3, character_skill_4, confirm_layout;
	ImageView character_image, skill_1_image, skill_2_image, skill_3_image, skill_4_image;
	TextView str_stat, con_stat, dex_stat, int_stat, wis_stat, cha_stat, skill_1_explanation, skill_2_explanation, skill_3_explanation,	skill_4_explanation;
	AlertDialog dialog;
	Spinner character_spinner;
	Button create, cancel;
	EditText character_name;
	String character_selection_string;
	Character MyCharacter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_creation);
		ActivitySizeHandler();
		FindViewById();
		SpinnerListener();
	}

	/* Screen size handler */
	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.FILL_PARENT;
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params);
	}

	/* connect xml id */
	private void FindViewById() {
		stat_1_3 = (LinearLayout) findViewById(R.id.stat_1_3);
		stat_4_6 = (LinearLayout) findViewById(R.id.stat_4_6);
		character_skill_1 = (LinearLayout) findViewById(R.id.character_skill_1);
		character_skill_2 = (LinearLayout) findViewById(R.id.character_skill_2);
		character_skill_3 = (LinearLayout) findViewById(R.id.character_skill_3);
		character_skill_4 = (LinearLayout) findViewById(R.id.character_skill_4);
		confirm_layout = (LinearLayout) findViewById(R.id.confirm_layout);
		str_stat = (TextView) findViewById(R.id.str_stat);
		con_stat = (TextView) findViewById(R.id.con_stat);
		dex_stat = (TextView) findViewById(R.id.dex_stat);
		int_stat = (TextView) findViewById(R.id.int_stat);
		wis_stat = (TextView) findViewById(R.id.wis_stat);
		cha_stat = (TextView) findViewById(R.id.cha_stat);
		skill_1_explanation = (TextView) findViewById(R.id.skill_1_explanation);
		skill_2_explanation = (TextView) findViewById(R.id.skill_2_explanation);
		skill_3_explanation = (TextView) findViewById(R.id.skill_3_explanation);
		skill_4_explanation = (TextView) findViewById(R.id.skill_4_explanation);
		character_image = (ImageView) findViewById(R.id.character_image);
		skill_1_image = (ImageView) findViewById(R.id.skill_1_image);
		skill_2_image = (ImageView) findViewById(R.id.skill_2_image);
		skill_3_image = (ImageView) findViewById(R.id.skill_3_image);
		skill_4_image = (ImageView) findViewById(R.id.skill_4_image);
		character_name = (EditText) findViewById(R.id.character_name);
		character_spinner = (Spinner) findViewById(R.id.character_spinner);
		findViewById(R.id.create_character_btn).setOnClickListener(ButtonHandler);
		findViewById(R.id.cancel_character_btn).setOnClickListener(ButtonHandler);
	}
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.create_character_btn:
					if(!character_name.getText().toString().isEmpty() && !character_selection_string.equals("Choose Character")) {
						insert.execute();
					}
					break;
				case R.id.cancel_character_btn:
					finish();
					break;
			}
			
		}
		
	};

	private void SpinnerListener() {
		character_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// Toast.makeText(parent.getContext(),
				// parent.getItemAtPosition(pos).toString(),
				// Toast.LENGTH_SHORT).show();
				// String character_selection_string;
				character_selection_string = parent.getItemAtPosition(pos).toString().trim();
				if (character_selection_string.equals("Choose Character")) {
					SetInvisible();
				} else if (character_selection_string.equals(StaticClass.WARRIOR)) {
					character_image.setImageResource(R.drawable.warrior);
					character_image.setVisibility(View.VISIBLE);
				} else if (character_selection_string.equals(StaticClass.ASSASSIN)) {
					character_image.setImageResource(R.drawable.assassin);
					character_image.setVisibility(View.VISIBLE);
				} else if (character_selection_string.equals(StaticClass.MAGE)) {
					character_image.setImageResource(R.drawable.mage);
					character_image.setVisibility(View.VISIBLE);
				} else if (character_selection_string.equals(StaticClass.ARCHER)) {
					character_image.setImageResource(R.drawable.archer);
					character_image.setVisibility(View.VISIBLE);
				} else if (character_selection_string.equals(StaticClass.SUMMONER)) {
					character_image
							.setImageResource(R.drawable.summoner);
					character_image.setVisibility(View.VISIBLE);
				}
				InitializeStat(character_selection_string);
				Skill_explanation(character_selection_string);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
	}

	private void InitializeStat(String character_selection_string) {
		MyCharacter = new Character("", 10, 10, 10, 10, 10, 10, 1, "");
		str_stat.setTextColor(Color.WHITE);
		con_stat.setTextColor(Color.WHITE);
		dex_stat.setTextColor(Color.WHITE);
		int_stat.setTextColor(Color.WHITE);
		wis_stat.setTextColor(Color.WHITE);
		cha_stat.setTextColor(Color.WHITE);
		if (character_selection_string.equals(StaticClass.WARRIOR)) {
			MyCharacter.setSTR(MyCharacter.getSTR() + 3);
			MyCharacter.setCON(MyCharacter.getCON() + 3);
			SetVisible();
			str_stat.setTextColor(Color.RED);
			con_stat.setTextColor(Color.RED);
		} else if (character_selection_string.equals(StaticClass.ASSASSIN)) {
			MyCharacter.setDEX(MyCharacter.getDEX() + 3);
			MyCharacter.setWIS(MyCharacter.getWIS() + 3);
			SetVisible();
			dex_stat.setTextColor(Color.RED);
			wis_stat.setTextColor(Color.RED);
		} else if (character_selection_string.equals(StaticClass.MAGE)) {
			MyCharacter.setINT(MyCharacter.getINT() + 3);
			MyCharacter.setWIS(MyCharacter.getWIS() + 3);
			SetVisible();
			int_stat.setTextColor(Color.RED);
			wis_stat.setTextColor(Color.RED);
		} else if (character_selection_string.equals(StaticClass.ARCHER)) {
			MyCharacter.setDEX(MyCharacter.getDEX() + 3);
			MyCharacter.setCHA(MyCharacter.getCHA() + 3);
			dex_stat.setTextColor(Color.RED);
			cha_stat.setTextColor(Color.RED);
			SetVisible();
		} else if (character_selection_string.equals(StaticClass.SUMMONER)) {
			MyCharacter.setCHA(MyCharacter.getCHA() + 3);
			MyCharacter.setWIS(MyCharacter.getWIS() + 3);
			cha_stat.setTextColor(Color.RED);
			wis_stat.setTextColor(Color.RED);
			SetVisible();
		}
		str_stat.setText(Integer.toString(MyCharacter.getSTR()));
		con_stat.setText(Integer.toString(MyCharacter.getCON()));
		dex_stat.setText(Integer.toString(MyCharacter.getDEX()));
		int_stat.setText(Integer.toString(MyCharacter.getINT()));
		wis_stat.setText(Integer.toString(MyCharacter.getWIS()));
		cha_stat.setText(Integer.toString(MyCharacter.getCHA()));
	}

	private void SetVisible() {
		stat_1_3.setVisibility(View.VISIBLE);
		stat_4_6.setVisibility(View.VISIBLE);
		character_skill_1.setVisibility(View.VISIBLE);
		character_skill_2.setVisibility(View.VISIBLE);
		character_skill_3.setVisibility(View.VISIBLE);
		character_skill_4.setVisibility(View.VISIBLE);
		confirm_layout.setVisibility(View.VISIBLE);
	}

	private void SetInvisible() {
		character_image.setVisibility(View.GONE);
		stat_1_3.setVisibility(View.GONE);
		stat_4_6.setVisibility(View.GONE);
		confirm_layout.setVisibility(View.GONE);
		character_skill_1.setVisibility(View.GONE);
		character_skill_2.setVisibility(View.GONE);
		character_skill_3.setVisibility(View.GONE);
		character_skill_4.setVisibility(View.GONE);
	}

	private void Skill_explanation(String character_selection_string) {
		if (character_selection_string.equals(StaticClass.WARRIOR)) {
			WarriorSkills();
		} else if (character_selection_string.equals(StaticClass.ASSASSIN)) {
			AssassinSkills();
		} else if (character_selection_string.equals(StaticClass.MAGE)) {
			MageSkills();
		} else if (character_selection_string.equals(StaticClass.ARCHER)) {
			ArcherSkills();
		} else if (character_selection_string.equals(StaticClass.SUMMONER)) {
			SummonerSkills();
		}
	}

	private void WarriorSkills() {
		skill_1_image.setImageResource(R.drawable.warrior);
		skill_2_image.setImageResource(R.drawable.assassin);
		skill_3_image.setImageResource(R.drawable.mage);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(StaticClass.Warrior_skill_1);
		skill_2_explanation.setText(StaticClass.Warrior_skill_2);
		skill_3_explanation.setText(StaticClass.Warrior_skill_3);
		skill_4_explanation.setText(StaticClass.Warrior_skill_4);
		SetVisible();
	}

	private void AssassinSkills() {
		skill_1_image.setImageResource(R.drawable.assassin);
		skill_2_image.setImageResource(R.drawable.warrior);
		skill_3_image.setImageResource(R.drawable.mage);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(StaticClass.Assassin_skill_1);
		skill_2_explanation.setText(StaticClass.Assassin_skill_2);
		skill_3_explanation.setText(StaticClass.Assassin_skill_3);
		skill_4_explanation.setText(StaticClass.Assassin_skill_4);
		SetVisible();
	}

	private void MageSkills() {
		skill_1_image.setImageResource(R.drawable.archer);
		skill_2_image.setImageResource(R.drawable.assassin);
		skill_3_image.setImageResource(R.drawable.mage);
		skill_4_image.setImageResource(R.drawable.warrior);
		skill_1_explanation.setText(StaticClass.Mage_skill_1);
		skill_2_explanation.setText(StaticClass.Mage_skill_2);
		skill_3_explanation.setText(StaticClass.Mage_skill_3);
		skill_4_explanation.setText(StaticClass.Mage_skill_4);
		SetVisible();
	}

	private void ArcherSkills() {
		skill_1_image.setImageResource(R.drawable.mage);
		skill_2_image.setImageResource(R.drawable.assassin);
		skill_3_image.setImageResource(R.drawable.warrior);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(StaticClass.Archer_skill_1);
		skill_2_explanation.setText(StaticClass.Archer_skill_2);
		skill_3_explanation.setText(StaticClass.Archer_skill_3);
		skill_4_explanation.setText(StaticClass.Archer_skill_4);
		SetVisible();
	}

	private void SummonerSkills() {
		skill_1_image.setImageResource(R.drawable.warrior);
		skill_2_image.setImageResource(R.drawable.mage);
		skill_3_image.setImageResource(R.drawable.assassin);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(StaticClass.Summoner_skill_1);
		skill_2_explanation.setText(StaticClass.Summoner_skill_2);
		skill_3_explanation.setText(StaticClass.Summoner_skill_3);
		skill_4_explanation.setText(StaticClass.Summoner_skill_4);
		SetVisible();
	}
	
	class InsertData extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CharacterCreation.this);
			pDialog.setMessage("Creating Character now...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... arg) {
			String name = character_name.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Log.d("user_name", StaticClass.MY_ID);
			Log.d("character_name", name);
			Log.d("str", Integer.toString(MyCharacter.getSTR()));
			Log.d("con", Integer.toString(MyCharacter.getCON()));
			Log.d("dex", Integer.toString(MyCharacter.getDEX()));
			Log.d("_int", Integer.toString(MyCharacter.getINT()));
			Log.d("wis", Integer.toString(MyCharacter.getWIS()));
			Log.d("cha", Integer.toString(MyCharacter.getCHA()));
			Log.d("CLASS", character_spinner.getSelectedItem().toString());
			params.add(new BasicNameValuePair("user_name", StaticClass.MY_ID));
			params.add(new BasicNameValuePair("character_name", name));
			params.add(new BasicNameValuePair("str", Integer.toString(MyCharacter.getSTR())));
			params.add(new BasicNameValuePair("con", Integer.toString(MyCharacter.getCON())));
			params.add(new BasicNameValuePair("dex", Integer.toString(MyCharacter.getDEX())));
			params.add(new BasicNameValuePair("_int", Integer.toString(MyCharacter.getINT())));
			params.add(new BasicNameValuePair("wis", Integer.toString(MyCharacter.getWIS())));
			params.add(new BasicNameValuePair("cha", Integer.toString(MyCharacter.getCHA())));
			params.add(new BasicNameValuePair("level", Integer.toString(StaticClass.INIT_LEVEL)));
			params.add(new BasicNameValuePair("class", character_spinner.getSelectedItem().toString()));

			JSONObject json = jsonParser.makeHttpRequest(
					StaticClass.url_create_character, "POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(StaticClass.TAG_SUCCESS);

				if (success == 1) {
					Log.d("Character Creation Status", "Character Created Successfully");
					StaticClass.CLASS_INFO = new Character(name,MyCharacter.getSTR(), MyCharacter.getCON(),MyCharacter.getDEX(), MyCharacter.getINT(), 
							MyCharacter.getWIS(),MyCharacter.getCHA(), StaticClass.INIT_LEVEL, character_spinner.getSelectedItem().toString());
									
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		protected void onPostExecute(String result) {
			Toast.makeText(CharacterCreation.this, StaticClass.CHARACTER_CREATE_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
			pDialog.dismiss();
			insert.cancel(true);
			StaticClass.CHARACTER_CREATED = true;
			finish();
			
			
		}
		
	}

}
