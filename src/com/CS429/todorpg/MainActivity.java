package com.CS429.todorpg;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	static SQLiteDatabase database;
	DatabaseHelper helper;
	static String tableName = "MyCharacter";
	Spinner character_selection;
	ImageView character_image, skill_1_image, skill_2_image, skill_3_image, skill_4_image;
	TextView str_stat, con_stat, dex_stat, int_stat, wis_stat, cha_stat,
		skill_1_explanation, skill_2_explanation, skill_3_explanation, skill_4_explanation;
	LinearLayout stat_1_3, stat_4_6,  character_skill_1, character_skill_2, character_skill_3, character_skill_4;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FindViewById();
		SpinnerListener();
		createDatabase();
	}
	private void FindViewById() {
		stat_1_3 = (LinearLayout)findViewById(R.id.stat_1_3);
		stat_4_6 = (LinearLayout)findViewById(R.id.stat_4_6);
		character_skill_1 = (LinearLayout)findViewById(R.id.character_skill_1);
		character_skill_2 = (LinearLayout)findViewById(R.id.character_skill_2);
		character_skill_3 = (LinearLayout)findViewById(R.id.character_skill_3);
		character_skill_4 = (LinearLayout)findViewById(R.id.character_skill_4);
		character_selection = (Spinner)findViewById(R.id.character_selection);
		str_stat = (TextView)findViewById(R.id.str_stat);
		con_stat = (TextView)findViewById(R.id.con_stat);
		dex_stat = (TextView)findViewById(R.id.dex_stat);
		int_stat = (TextView)findViewById(R.id.int_stat);
		wis_stat = (TextView)findViewById(R.id.wis_stat);
		cha_stat = (TextView)findViewById(R.id.cha_stat);
		skill_1_explanation = (TextView)findViewById(R.id.skill_1_explanation);
		skill_2_explanation = (TextView)findViewById(R.id.skill_2_explanation);
		skill_3_explanation = (TextView)findViewById(R.id.skill_3_explanation);
		skill_4_explanation = (TextView)findViewById(R.id.skill_4_explanation);
		character_image = (ImageView)findViewById(R.id.character_image);
		skill_1_image = (ImageView)findViewById(R.id.skill_1_image);
		skill_2_image = (ImageView)findViewById(R.id.skill_2_image);
		skill_3_image = (ImageView)findViewById(R.id.skill_3_image);
		skill_4_image = (ImageView)findViewById(R.id.skill_4_image);
		
	}
	private void SpinnerListener() {
		character_selection.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//				Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
//				String character_selection_string;
				String character_selection_string = parent.getItemAtPosition(pos).toString().trim();
				if(character_selection_string.equals("Choose Character")) {
					SetInvisible();
				} else if(character_selection_string.equals("Warrior")) {
					character_image.setImageResource(R.drawable.warrior);
					character_image.setVisibility(View.VISIBLE);
				} else if(character_selection_string.equals("Log")) {
					character_image.setImageResource(R.drawable.log);
					character_image.setVisibility(View.VISIBLE);
				} else if(character_selection_string.equals("Mage")) {
					character_image.setImageResource(R.drawable.mage);
					character_image.setVisibility(View.VISIBLE);
				} else if(character_selection_string.equals("Archer")) {
					character_image.setImageResource(R.drawable.archer);
					character_image.setVisibility(View.VISIBLE);
				} else if(character_selection_string.equals("Summoner")) {
					character_image.setImageResource(R.drawable.summoner);
					character_image.setVisibility(View.VISIBLE);
				}
				InitializeStat(character_selection_string);
				Skill_explanation(character_selection_string);
				InsertData();
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			
		});
	}
	/* Insert data to the database */
	private void InsertData() {
		DefaultStat default_stat = null;   			// temp ---- Remove
		database.beginTransaction();
		try {
			String sql = null;
			sql = "insert into " + tableName + "(str, con, dex, _int, wis, cha) " +
						"values('"+ default_stat.getStrength() +"', '"+ default_stat.getConstitution() +"', '" 
						+ default_stat.getDexterity() +"', '"+ default_stat.getIntelligence() +"', '" 
						+ default_stat.getWisdom() +"', '" + default_stat.getCharisma()+"')";
			database.execSQL(sql);
			database.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
		}
	}
	private void InitializeStat(String character_selection_string) {
		DefaultStat default_stat = new DefaultStat(10, 10, 10, 10, 10, 10);
		str_stat.setTextColor(Color.WHITE); con_stat.setTextColor(Color.WHITE);
		dex_stat.setTextColor(Color.WHITE); int_stat.setTextColor(Color.WHITE);
		wis_stat.setTextColor(Color.WHITE); cha_stat.setTextColor(Color.WHITE);
		if(character_selection_string.equals("Warrior")) {
			default_stat.setStrength(default_stat.getStrength() + 3);
			default_stat.setConstitution(default_stat.getConstitution() + 3);
			SetVisible();
			str_stat.setTextColor(Color.RED); con_stat.setTextColor(Color.RED);
		} else if(character_selection_string.equals("Log")) {
			default_stat.setDexterity(default_stat.getDexterity() + 3);
			default_stat.setWisdom(default_stat.getWisdom() + 3);
			SetVisible();
			dex_stat.setTextColor(Color.RED); wis_stat.setTextColor(Color.RED);
		} else if(character_selection_string.equals("Mage")) {
			default_stat.setIntelligence(default_stat.getIntelligence() + 3);
			default_stat.setWisdom(default_stat.getWisdom() + 3);
			SetVisible();
			int_stat.setTextColor(Color.RED); wis_stat.setTextColor(Color.RED);
		} else if(character_selection_string.equals("Archer")) {
			default_stat.setDexterity(default_stat.getDexterity() + 3);
			default_stat.setCharisma(default_stat.getCharisma() + 3);
			dex_stat.setTextColor(Color.RED); cha_stat.setTextColor(Color.RED);
			SetVisible();
		} else if(character_selection_string.equals("Summoner")) {
			default_stat.setCharisma(default_stat.getCharisma() + 3);
			default_stat.setWisdom(default_stat.getWisdom() + 3);
			cha_stat.setTextColor(Color.RED); wis_stat.setTextColor(Color.RED);
			SetVisible();
		}
		str_stat.setText(Integer.toString(default_stat.getStrength()));
		con_stat.setText(Integer.toString(default_stat.getConstitution()));
		dex_stat.setText(Integer.toString(default_stat.getDexterity()));
		int_stat.setText(Integer.toString(default_stat.getIntelligence()));
		wis_stat.setText(Integer.toString(default_stat.getWisdom()));
		cha_stat.setText(Integer.toString(default_stat.getCharisma()));
	}
	private void Skill_explanation(String character_selection_string) {
		if(character_selection_string.equals("Warrior")) {
			WarriorSkills();
		} else if(character_selection_string.equals("Log")) {
			LogSkills();
		} else if(character_selection_string.equals("Mage")) {
			MageSkills();
		} else if(character_selection_string.equals("Archer")) {
			ArcherSkills();
		} else if(character_selection_string.equals("Summoner")) {
			SummonerSkills();
		}
	}
	private void WarriorSkills() {
		skill_1_image.setImageResource(R.drawable.warrior);
		skill_2_image.setImageResource(R.drawable.log);
		skill_3_image.setImageResource(R.drawable.mage);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(SkillExplanation.warrior_skill_1);
		skill_2_explanation.setText(SkillExplanation.warrior_skill_2);
		skill_3_explanation.setText(SkillExplanation.warrior_skill_3);
		skill_4_explanation.setText(SkillExplanation.warrior_skill_4);
		SetVisible();
	}
	private void LogSkills() {
		skill_1_image.setImageResource(R.drawable.log);
		skill_2_image.setImageResource(R.drawable.warrior);
		skill_3_image.setImageResource(R.drawable.mage);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(SkillExplanation.log_skill_1);
		skill_2_explanation.setText(SkillExplanation.log_skill_2);
		skill_3_explanation.setText(SkillExplanation.log_skill_3);
		skill_4_explanation.setText(SkillExplanation.log_skill_4);
		SetVisible();
	}
	private void MageSkills() {
		skill_1_image.setImageResource(R.drawable.archer);
		skill_2_image.setImageResource(R.drawable.log);
		skill_3_image.setImageResource(R.drawable.mage);
		skill_4_image.setImageResource(R.drawable.warrior);
		skill_1_explanation.setText(SkillExplanation.mage_skill_1);
		skill_2_explanation.setText(SkillExplanation.mage_skill_2);
		skill_3_explanation.setText(SkillExplanation.mage_skill_3);
		skill_4_explanation.setText(SkillExplanation.mage_skill_4);
		SetVisible();
	}
	private void ArcherSkills() {
		skill_1_image.setImageResource(R.drawable.mage);
		skill_2_image.setImageResource(R.drawable.log);
		skill_3_image.setImageResource(R.drawable.warrior);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(SkillExplanation.archer_skill_1);
		skill_2_explanation.setText(SkillExplanation.archer_skill_2);
		skill_3_explanation.setText(SkillExplanation.archer_skill_3);
		skill_4_explanation.setText(SkillExplanation.archer_skill_4);
		SetVisible();
	}
	private void SummonerSkills() {
		skill_1_image.setImageResource(R.drawable.warrior);
		skill_2_image.setImageResource(R.drawable.mage);
		skill_3_image.setImageResource(R.drawable.log);
		skill_4_image.setImageResource(R.drawable.archer);
		skill_1_explanation.setText(SkillExplanation.summoner_skill_1);
		skill_2_explanation.setText(SkillExplanation.summoner_skill_2);
		skill_3_explanation.setText(SkillExplanation.summoner_skill_3);
		skill_4_explanation.setText(SkillExplanation.summoner_skill_4);
		SetVisible();
	}
	
	private void SetVisible() {
		stat_1_3.setVisibility(View.VISIBLE);
		stat_4_6.setVisibility(View.VISIBLE);
		character_skill_1.setVisibility(View.VISIBLE);
		character_skill_2.setVisibility(View.VISIBLE);
		character_skill_3.setVisibility(View.VISIBLE);
		character_skill_4.setVisibility(View.VISIBLE);
	}
	private void SetInvisible() {
		character_image.setVisibility(View.INVISIBLE);
		stat_1_3.setVisibility(View.INVISIBLE);
		stat_4_6.setVisibility(View.INVISIBLE);	
		character_skill_1.setVisibility(View.INVISIBLE);
		character_skill_2.setVisibility(View.INVISIBLE);
		character_skill_3.setVisibility(View.INVISIBLE);
		character_skill_4.setVisibility(View.INVISIBLE);
	}
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	 /* create database by using helper */
	private void createDatabase() {
		String db_name = "my_character.db";
		int version = 1;
		helper = new DatabaseHelper(this, db_name, null, version);
		database = helper.getWritableDatabase();
		
	}

	/* Database Helper */
	class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			createTable(db);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
		private void createTable(SQLiteDatabase db) {
			String sql = "create table " + tableName + "(_id integer primary key autoincrement, str int, con int, dex int, _int int, wis int, cha int)";
			try {
				db.execSQL(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
