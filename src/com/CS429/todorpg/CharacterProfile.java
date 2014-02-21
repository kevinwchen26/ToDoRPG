package com.CS429.todorpg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;


public class CharacterProfile extends Activity {
	Intent intent;
	ImageView character_image, skill_1_image, skill_2_image, skill_3_image, skill_4_image;
	TextView str_stat, con_stat, dex_stat, int_stat, wis_stat, cha_stat,
		skill_1_explanation, skill_2_explanation, skill_3_explanation, skill_4_explanation;
	LinearLayout stat_1_3, stat_4_6,  character_skill_1, character_skill_2, character_skill_3, character_skill_4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_profile);
		FindViewById();
		ActivitySizeHandler();
		ShowProfile();
	}
	
	/*Screen size handler*/
	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();  
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setAttributes(params); 
	}
	/*connect xml id*/
	private void FindViewById() {
		stat_1_3 = (LinearLayout)findViewById(R.id.stat_1_3);
		stat_4_6 = (LinearLayout)findViewById(R.id.stat_4_6);
		character_skill_1 = (LinearLayout)findViewById(R.id.character_skill_1);
		character_skill_2 = (LinearLayout)findViewById(R.id.character_skill_2);
		character_skill_3 = (LinearLayout)findViewById(R.id.character_skill_3);
		character_skill_4 = (LinearLayout)findViewById(R.id.character_skill_4);
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
	/*Show character info*/
	private void ShowProfile() {
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		String character = (String) bundle.get("character");
		Log.d("Character name: ", character);
		if(character.equals("Warrior")) {
			character_image.setImageResource(R.drawable.warrior);
			character_image.setVisibility(View.VISIBLE);
		} else if(character.equals("Log")) {
			character_image.setImageResource(R.drawable.log);
			character_image.setVisibility(View.VISIBLE);
		} else if(character.equals("Mage")) {
			character_image.setImageResource(R.drawable.mage);
			character_image.setVisibility(View.VISIBLE);
		} else if(character.equals("Archer")) {
			character_image.setImageResource(R.drawable.archer);
			character_image.setVisibility(View.VISIBLE);
		} else if(character.equals("Summoner")) {
			character_image.setImageResource(R.drawable.summoner);
			character_image.setVisibility(View.VISIBLE);
		}
		InitializeStat(character);
		Skill_explanation(character);
	}
	/*Set stat of initial character*/
	private void InitializeStat(String character_selection_string) {
		Character default_stat = new Character(10, 10, 10, 10, 10, 10);
		str_stat.setTextColor(Color.WHITE); con_stat.setTextColor(Color.WHITE);
		dex_stat.setTextColor(Color.WHITE); int_stat.setTextColor(Color.WHITE);
		wis_stat.setTextColor(Color.WHITE); cha_stat.setTextColor(Color.WHITE);
		if(character_selection_string.equals("Warrior")) {
			default_stat.setSTR(default_stat.getSTR() + 3);
			default_stat.setCON(default_stat.getCON() + 3);
			str_stat.setTextColor(Color.RED); con_stat.setTextColor(Color.RED);
		} else if(character_selection_string.equals("Log")) {
			default_stat.setDEX(default_stat.getDEX() + 3);
			default_stat.setWIS(default_stat.getWIS() + 3);
			dex_stat.setTextColor(Color.RED); wis_stat.setTextColor(Color.RED);
		} else if(character_selection_string.equals("Mage")) {
			default_stat.setINT(default_stat.getINT() + 3);
			default_stat.setWIS(default_stat.getWIS() + 3);
			int_stat.setTextColor(Color.RED); wis_stat.setTextColor(Color.RED);
		} else if(character_selection_string.equals("Archer")) {
			default_stat.setDEX(default_stat.getDEX() + 3);
			default_stat.setCHA(default_stat.getCHA() + 3);
			dex_stat.setTextColor(Color.RED); cha_stat.setTextColor(Color.RED);
		} else if(character_selection_string.equals("Summoner")) {
			default_stat.setCHA(default_stat.getCHA() + 3);
			default_stat.setWIS(default_stat.getWIS() + 3);
			cha_stat.setTextColor(Color.RED); wis_stat.setTextColor(Color.RED);
		}
		str_stat.setText(Integer.toString(default_stat.getSTR()));
		con_stat.setText(Integer.toString(default_stat.getCON()));
		dex_stat.setText(Integer.toString(default_stat.getDEX()));
		int_stat.setText(Integer.toString(default_stat.getINT()));
		wis_stat.setText(Integer.toString(default_stat.getWIS()));
		cha_stat.setText(Integer.toString(default_stat.getCHA()));
	}
	/*Show skills of character -- To be implemented later*/
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
	}
}


