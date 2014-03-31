package com.CS429.todorpg;

import com.CS429.todorpg.Utils.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCharacterInfo extends Activity {
	TextView character_name, character_level, character_str, character_con, character_dex, character_int, character_wis, character_cha;
	ImageView character_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_character_info);
		FindViewById();
		ShowInfo();
		
//		Log.d("CHARACTER_NAME", UserInfo.CLASS_INFO.getName());
//		Log.d("STR", Integer.toString(UserInfo.CLASS_INFO.getSTR()));
//		Log.d("CON", Integer.toString(UserInfo.CLASS_INFO.getCON()));
//		Log.d("DEX", Integer.toString(UserInfo.CLASS_INFO.getDEX()));
//		Log.d("INT", Integer.toString(UserInfo.CLASS_INFO.getINT()));
//		Log.d("WIS", Integer.toString(UserInfo.CLASS_INFO.getWIS()));
//		Log.d("CHA", Integer.toString(UserInfo.CLASS_INFO.getCHA()));
//		Log.d("CLASS", UserInfo.CLASS_INFO.getCLASS());
//		Log.d("LEVEL", Integer.toString(UserInfo.CLASS_INFO.getLEVEL()));
	}
	public void FindViewById() {
		character_name = (TextView) findViewById(R.id.character_name);
		character_image = (ImageView) findViewById(R.id.character_image);
		character_level = (TextView) findViewById(R.id.character_level);
		character_str = (TextView) findViewById(R.id.character_str);
		character_con = (TextView) findViewById(R.id.character_con);
		character_dex = (TextView) findViewById(R.id.character_dex);
		character_int = (TextView) findViewById(R.id.character_int);
		character_wis = (TextView) findViewById(R.id.character_wis);
		character_cha = (TextView) findViewById(R.id.character_cha);
	}
	public void ShowInfo() {
		com.CS429.todorpg.Class.Character character = ((UserInfo)getApplicationContext()).getCharacter();
		character_name.setText(character.getName());
		String characterClass = character.getCLASS();
		if(characterClass.equals(Constants.WARRIOR)) {
			character_image.setImageResource(R.drawable.warrior);
		} else if(characterClass.equals(Constants.ASSASSIN)) {
			character_image.setImageResource(R.drawable.assassin);
		} else if(characterClass.equals(Constants.MAGE)) {
			character_image.setImageResource(R.drawable.mage);
		} else if(characterClass.equals(Constants.ARCHER)) {
			character_image.setImageResource(R.drawable.archer);
		} else if(characterClass.equals(Constants.SUMMONER)) {
			character_image.setImageResource(R.drawable.summoner);
		}
		character_level.setText(Integer.toString(character.getLEVEL()));
		character_str.setText(Integer.toString(character.getSTR()));
		character_con.setText(Integer.toString(character.getCON()));
		character_dex.setText(Integer.toString(character.getDEX()));
		character_int.setText(Integer.toString(character.getINT()));
		character_wis.setText(Integer.toString(character.getWIS()));
		character_cha.setText(Integer.toString(character.getCHA()));
		
	}
}
