package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import com.CS429.todorpg.Class.Warrior;
import com.CS429.todorpg.Class.Character;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BattleActivity extends Activity {
	
	boolean defaultClass;
	int width, height;
	RelativeLayout battleScreen, battleNavigator, enemyInfo, actionMenu, playerInfo, enemySide, playerSide;
	ImageView enemyImage, playerImage;
	Intent intent;
	ArrayList<Character> party;
	Character boss;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpLayout();
		interpretIntent();
		setUpBattle();
		setContentView(R.layout.battle);
		FindViewById();
		setUpActivity();

	}
	
	private void setUpBattleScreen() {
		// Set up battle screen. 
		battleScreen.setLayoutParams(new RelativeLayout.LayoutParams(width, height/2));
		battleScreen.setBackgroundResource(R.drawable.battle_background);
		
		// Set up upper half of screen
		RelativeLayout.LayoutParams playerSideParams = new RelativeLayout.LayoutParams(width/2, height/2);
		playerSideParams.addRule(RelativeLayout.RIGHT_OF, enemySide.getId());
		    
		enemySide.setLayoutParams(new RelativeLayout.LayoutParams(width/2, height/2));			    
		playerSide.setLayoutParams(playerSideParams);
			    
		//Set the player images
	    enemyImage.setImageResource(R.drawable.warrior_enemy);
	    playerImage.setImageResource(R.drawable.warrior_player);
	}
	
	private void setUpBattleNavigator() {
		// Set up Battle navigator
		RelativeLayout.LayoutParams battleNav = new RelativeLayout.LayoutParams(width, height/2);
		battleNav.addRule(RelativeLayout.BELOW, battleScreen.getId());
	    battleNavigator.setLayoutParams(battleNav);
			    
	    // Set up lower half of screen
		RelativeLayout.LayoutParams actionMenuParams = new RelativeLayout.LayoutParams(width/2, height/2);
		actionMenuParams.addRule(RelativeLayout.RIGHT_OF, enemyInfo.getId());
				
		RelativeLayout.LayoutParams playerInfoParams = new RelativeLayout.LayoutParams(width/4, height/2);
		playerInfoParams.addRule(RelativeLayout.RIGHT_OF, actionMenu.getId());
				
	    enemyInfo.setLayoutParams(new RelativeLayout.LayoutParams(width/4, height/2));
	    playerInfo.setLayoutParams(playerInfoParams);
	    actionMenu.setLayoutParams(actionMenuParams);
	}
	private void setUpActivity() {
		setUpBattleScreen();
		setUpBattleNavigator();
	}
	
	private void FindViewById() {
		battleScreen = (RelativeLayout) findViewById(R.id.battle_screen);
		battleNavigator = (RelativeLayout) findViewById(R.id.battle_navigator);
		enemyInfo = (RelativeLayout) findViewById(R.id.battle_enemy_info);
		actionMenu = (RelativeLayout) findViewById(R.id.battle_action_menu);
		playerInfo = (RelativeLayout) findViewById(R.id.battle_character_info);
		enemySide = (RelativeLayout) findViewById(R.id.battle_enemy_side);
		playerSide = (RelativeLayout) findViewById(R.id.battle_player_side);
		enemyImage = (ImageView) findViewById(R.id.battle_enemy_image);
		playerImage = (ImageView) findViewById(R.id.battle_player_image);

	}
	
	private void interpretIntent() {
		intent = getIntent();
		defaultClass = intent.getBooleanExtra("default", true);
	}
	
	private void setUpLayout() {
		
		 //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    // Sets to landscape 
	 	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    // Get screen size
	    Display display = getWindowManager().getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    width = size.x;
	    height = size.y;
	    


	}
	

	private void getCharacters() {
		
		//*** Will need to change to pull all members of a party in the next iteration ***//
		Character newPlayer = CharacterOperations.pullCharacter(StaticClass.CLASS_INFO.getCLASS(),
				StaticClass.CLASS_INFO.getName(), StaticClass.CLASS_INFO.getHP(), StaticClass.CLASS_INFO.getMP(), 
				StaticClass.CLASS_INFO.getLEVEL(), StaticClass.CLASS_INFO.getCON(), StaticClass.CLASS_INFO.getSTR(), 
				StaticClass.CLASS_INFO.getDEX(), StaticClass.CLASS_INFO.getINT(), StaticClass.CLASS_INFO.getWIS(), 
				StaticClass.CLASS_INFO.getCHA(), StaticClass.CLASS_INFO.getcurrentEXP(), StaticClass.CLASS_INFO.getnextLevelEXP());
		party.add(newPlayer);
	}
	
	private void makeBoss() {
		boss = new Character("Boss");
		//*** Add new parameters later ***//

	}
	
	private void setUpBattle(){
		party = new ArrayList<Character>();
		if(defaultClass){
			party.add(new Warrior("Bob"));
			
		}
		else {
			getCharacters();	
		}
		
	}
	
}
