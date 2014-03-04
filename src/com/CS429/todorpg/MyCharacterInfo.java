package com.CS429.todorpg;

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
		
		Log.d("CHARACTER_NAME", StaticClass.CLASS_INFO.getName());
		Log.d("STR", Integer.toString(StaticClass.CLASS_INFO.getSTR()));
		Log.d("CON", Integer.toString(StaticClass.CLASS_INFO.getCON()));
		Log.d("DEX", Integer.toString(StaticClass.CLASS_INFO.getDEX()));
		Log.d("INT", Integer.toString(StaticClass.CLASS_INFO.getINT()));
		Log.d("WIS", Integer.toString(StaticClass.CLASS_INFO.getWIS()));
		Log.d("CHA", Integer.toString(StaticClass.CLASS_INFO.getCHA()));
		Log.d("CLASS", StaticClass.CLASS_INFO.getCLASS());
		Log.d("LEVEL", Integer.toString(StaticClass.CLASS_INFO.getLEVEL()));
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
		character_name.setText(StaticClass.CLASS_INFO.getName());
		String character = StaticClass.CLASS_INFO.getCLASS();
		if(character.equals(StaticClass.WARRIOR)) {
			character_image.setImageResource(R.drawable.warrior);
		} else if(character.equals(StaticClass.ASSASSIN)) {
			character_image.setImageResource(R.drawable.assassin);
		} else if(character.equals(StaticClass.MAGE)) {
			character_image.setImageResource(R.drawable.mage);
		} else if(character.equals(StaticClass.ARCHER)) {
			character_image.setImageResource(R.drawable.archer);
		} else if(character.equals(StaticClass.SUMMONER)) {
			character_image.setImageResource(R.drawable.summoner);
		}
		character_level.setText(Integer.toString(StaticClass.CLASS_INFO.getLEVEL()));
		character_str.setText(Integer.toString(StaticClass.CLASS_INFO.getSTR()));
		character_con.setText(Integer.toString(StaticClass.CLASS_INFO.getCON()));
		character_dex.setText(Integer.toString(StaticClass.CLASS_INFO.getDEX()));
		character_int.setText(Integer.toString(StaticClass.CLASS_INFO.getINT()));
		character_wis.setText(Integer.toString(StaticClass.CLASS_INFO.getWIS()));
		character_cha.setText(Integer.toString(StaticClass.CLASS_INFO.getCHA()));
		
	}
}
