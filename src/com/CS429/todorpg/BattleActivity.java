package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import com.CS429.todorpg.Class.Warrior;
import com.CS429.todorpg.Class.Character;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class BattleActivity extends Activity {
	
	boolean defaultClass;
	Intent intent;
	List<Character> party;
	Character boss;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battle);
		intent = getIntent();
		defaultClass = intent.getBooleanExtra("default", true);
		setUpBattle();
	}
	

	public void getCharacters() {
		
		//*** Will need to change to pull all members of a party in the next iteration ***//
		Character newPlayer = CharacterOperations.pullCharacter(StaticClass.CLASS_INFO.getCLASS(),
				StaticClass.CLASS_INFO.getName(), StaticClass.CLASS_INFO.getHP(), StaticClass.CLASS_INFO.getMP(), 
				StaticClass.CLASS_INFO.getLEVEL(), StaticClass.CLASS_INFO.getCON(), StaticClass.CLASS_INFO.getSTR(), 
				StaticClass.CLASS_INFO.getDEX(), StaticClass.CLASS_INFO.getINT(), StaticClass.CLASS_INFO.getWIS(), 
				StaticClass.CLASS_INFO.getCHA(), StaticClass.CLASS_INFO.getcurrentEXP(), StaticClass.CLASS_INFO.getnextLevelEXP());
		party.add(newPlayer);
	}
	
	public void makeBoss() {
		boss = new Character("Boss");
		//*** Add new parameters later ***//

	}
	
	public void setUpBattle(){
		party = new ArrayList<Character>();
		if(defaultClass){
			party.add(new Warrior("Bob"));
			
		}
		else {
			getCharacters();	
		}
		
	}
	
}
