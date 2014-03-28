package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import com.CS429.todorpg.Class.Archer;
import com.CS429.todorpg.Class.Assassin;
import com.CS429.todorpg.Class.Mage;
import com.CS429.todorpg.Class.Summoner;
import com.CS429.todorpg.Class.Warrior;
import com.CS429.todorpg.Class.Character;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends Activity {
	boolean defaultClass, playerTurn;
	int width, height;
	RelativeLayout battleScreen, battleNavigator, enemyInfo, actionMenu, playerInfo, enemySide, playerSide;
	Button attackButton, itemsButton, passButton;
	Spinner skillsSpinner;
	TextView enemyName, enemyHP, playerName, playerHP, playerMP;
	ImageView enemyImage, playerImage, playerEffect;
	AnimationDrawable playerWalk, playerAttack, playerSkill1, playerSkill2, playerSkill3, playerSkill4;
	Intent intent;
	ArrayList<Character> party;
	Character player;
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
		playerTurn = true;

	}

	private void bossAI() {
		boss.attack(player);
		update();
	}
	
	private void disablePlayerButtons() {
		attackButton.setEnabled(false);
	}
	private void enablePlayerButtons() {
		attackButton.setEnabled(true);
	}
	
	private void setUpActivity() {
		setUpBattleScreen();
		setUpBattleNavigator();
		setUpBattleMenu();
		setUpBattleInfo();
		update();
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
	    enemyImage.setImageResource(R.drawable.test_sprite);
	    playerImage.setBackgroundResource(R.drawable.warrior_walk);
	    RelativeLayout.LayoutParams playerEffectParams = new RelativeLayout.LayoutParams(playerEffect.getLayoutParams());
	    playerEffectParams.addRule(RelativeLayout.LEFT_OF, playerImage.getId());
	    playerEffect.setLayoutParams(playerEffectParams);
	    
	}
	
	private void setUpBattleNavigator() {
		// Set up Battle navigator
		RelativeLayout.LayoutParams battleNav = new RelativeLayout.LayoutParams(width, height/2);
		battleNav.addRule(RelativeLayout.BELOW, battleScreen.getId());
	    battleNavigator.setLayoutParams(battleNav);

		
	}
	
	private void setUpBattleInfo() {
		RelativeLayout.LayoutParams playerInfoParams = new RelativeLayout.LayoutParams(width/4, height/2);
		playerInfoParams.addRule(RelativeLayout.RIGHT_OF, actionMenu.getId());
				
	    enemyInfo.setLayoutParams(new RelativeLayout.LayoutParams(width/4, height/2));
	    playerInfo.setLayoutParams(playerInfoParams);
	    
	    //RelativeLayout.LayoutParams enemyNameParams = new RelativeLayout.LayoutParams(enemyName.getLayoutParams());
	    
	    RelativeLayout.LayoutParams enemyHPParams = new RelativeLayout.LayoutParams(enemyHP.getLayoutParams());
	    enemyHPParams.addRule(RelativeLayout.BELOW, enemyName.getId());
	    enemyHP.setLayoutParams(enemyHPParams);
	    
	    //RelativeLayout.LayoutParams playerNameParams = new RelativeLayout.LayoutParams(playerName.getLayoutParams());
	    
	    
	    RelativeLayout.LayoutParams playerHPParams = new RelativeLayout.LayoutParams(playerHP.getLayoutParams());
	    playerHPParams.addRule(RelativeLayout.BELOW, playerName.getId());
	    playerHP.setLayoutParams(playerHPParams);
	    
	    RelativeLayout.LayoutParams playerMPParams = new RelativeLayout.LayoutParams(playerMP.getLayoutParams());
	    playerMPParams.addRule(RelativeLayout.BELOW, playerHP.getId());
	    playerMP.setLayoutParams(playerMPParams);
	    
	    
	}
	
	private void setPlayer() {
		int skillArray = -1;
	    
		if(player instanceof Warrior){
			skillArray = R.array.warrior_skills;
			playerImage.setBackgroundResource(R.drawable.warrior_walk);
			playerWalk = (AnimationDrawable) playerImage.getBackground();
			playerWalk.start();
		}
		else if(player instanceof Assassin){
			skillArray = R.array.assassins_skills;
		}
		else if(player instanceof Mage){
			skillArray = R.array.mage_skills;
		}
		else if(player instanceof Summoner){
			skillArray = R.array.summoner_skills;
		}
		else if(player instanceof Archer){
			skillArray = R.array.archer_skills;
		}
		
		ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(skillArray));
	    skillsSpinner.setAdapter(spinnerCountShoesArrayAdapter);
	}
	
	private void waitForEffectAnimationDone(AnimationDrawable anim) {
		final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                	waitForEffectAnimationDone(a);
                } else{
                    playerEffect.setBackgroundResource(R.color.transparent);;
                }
            }
        }, timeBetweenChecks);

	}
	
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.battle_attack_btn:
				player.attack(boss);
				playerEffect.setBackgroundResource(R.drawable.player_attack);
				playerAttack = (AnimationDrawable) playerEffect.getBackground();
				playerAttack.start();
				waitForEffectAnimationDone(playerAttack);
				update();
				break;
			case R.id.battle_items_btn:
				player.setHP(player.getHP() + 20);
				break;
			case R.id.battle_pass_btn:
				break;

			}
		}
	};
	
	
	
	
	//Updates the screen
	private void update() {
		enemyName.setText(boss.getName());
	    enemyHP.setText("HP" + boss.getHP() + "/" + boss.getMaxHP());
	    
	    
	    playerName.setText(player.getName());
	    playerHP.setText("HP " + player.getHP() + "/" + player.getMaxHP());
	    playerMP.setText("MP " + player.getMP() + "/" + player.getMaxMP());
	    
	    // Need to add check game conditions. 
	    // if(checkGameConditions())
	    
	    // change turns
	    changeTurn();
	    
	    
	}
	
	//Change turns
	private void changeTurn() {
		if(playerTurn) {
			playerTurn = false;
			disablePlayerButtons();
			bossAI();
		}
		//Boss just finished turn
		else {
			playerTurn = true;
			enablePlayerButtons();
			//Prompt message, 'This character's turn'
			
		}
	}
	//Return true if game is over
	private boolean checkGameConditions() {
		return false;
	}
	
	private void setUpBattleMenu() {
		int buttonHeight = (height/2)/5;
		// Set up lower half of screen
		RelativeLayout.LayoutParams actionMenuParams = new RelativeLayout.LayoutParams(width/2, height/2);
		actionMenuParams.addRule(RelativeLayout.RIGHT_OF, enemyInfo.getId());
	    actionMenu.setLayoutParams(actionMenuParams);
	    
	    RelativeLayout.LayoutParams attackButtonParam = new RelativeLayout.LayoutParams(attackButton.getLayoutParams());
	    attackButtonParam.height = buttonHeight;
	    attackButton.setLayoutParams(attackButtonParam);

	    
	    RelativeLayout.LayoutParams skillsSpinnerParam = new RelativeLayout.LayoutParams(attackButtonParam.width, buttonHeight);
	    skillsSpinnerParam.addRule(RelativeLayout.BELOW, attackButton.getId());
	    skillsSpinner.setLayoutParams(skillsSpinnerParam);
	    
	    RelativeLayout.LayoutParams itemsButtonParam = new RelativeLayout.LayoutParams(itemsButton.getLayoutParams());
	    itemsButtonParam.addRule(RelativeLayout.BELOW, skillsSpinner.getId());
	    itemsButtonParam.height = buttonHeight;
	    itemsButton.setLayoutParams(itemsButtonParam);
	    
	    RelativeLayout.LayoutParams passButtonParam = new RelativeLayout.LayoutParams(passButton.getLayoutParams());
	    passButtonParam.addRule(RelativeLayout.BELOW, itemsButton.getId());
	    passButtonParam.height = buttonHeight;
	    passButton.setLayoutParams(passButtonParam);
	    
	    attackButton.setOnClickListener(ButtonListener);
	    itemsButton.setOnClickListener(ButtonListener);
	    passButton.setOnClickListener(ButtonListener);
	    
	    skillsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long id) {
				// TODO Auto-generated method stub
				if(index == 1){
					player.Skill_1(boss);
					update();
				}
				else if(index == 2){
					player.Skill_2(boss);
					update();
				}
				else if(index == 3){
					player.Skill_3(boss);
					update();
				}
				else if(index == 4){
					player.Skill_4(boss);
					update();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    setPlayer();
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
		playerEffect = (ImageView) findViewById(R.id.battle_player_effect);
		attackButton = (Button) findViewById(R.id.battle_attack_btn);
		skillsSpinner = (Spinner) findViewById(R.id.battle_skills_spinner);
		itemsButton = (Button) findViewById(R.id.battle_items_btn);
		passButton = (Button) findViewById(R.id.battle_pass_btn);
		enemyName = (TextView) findViewById(R.id.battle_enemy_info_name);
		enemyHP = (TextView) findViewById(R.id.battle_enemy_info_hp);
		playerName = (TextView) findViewById(R.id.battle_player_info_name);
		playerHP = (TextView) findViewById(R.id.battle_player_info_hp);
		playerMP = (TextView) findViewById(R.id.battle_player_info_mp);
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
		
		//*** Pull user's character info ***//
		Character leader = CharacterOperations.pullCharacter(UserInfo.CLASS_INFO.getCLASS(),
				UserInfo.CLASS_INFO.getName(), UserInfo.CLASS_INFO.getHP(), UserInfo.CLASS_INFO.getMP(), 
				UserInfo.CLASS_INFO.getLEVEL(), UserInfo.CLASS_INFO.getCON(), UserInfo.CLASS_INFO.getSTR(), 
				UserInfo.CLASS_INFO.getDEX(), UserInfo.CLASS_INFO.getINT(), UserInfo.CLASS_INFO.getWIS(), 
				UserInfo.CLASS_INFO.getCHA(), UserInfo.CLASS_INFO.getcurrentEXP(), UserInfo.CLASS_INFO.getnextLevelEXP());
		party.add(leader);
		
		//*** Will need to change to pull all members of a party in the next iteration, for loop ^^^^^^^^ ***//
	}
	
	private void makeBoss() {
		boss = new Warrior("Boss");
		boss.setHP(1000);
		boss.setMaxHP(1000);
		//*** Add new parameters later ***//

	}
	
	private void setUpBattle(){
		party = new ArrayList<Character>();
		if(defaultClass){
			party.add(new Warrior("Warrior"));
			
		}
		else {
			getCharacters();	
		}
		makeBoss();
		player = party.get(0);
	}
	
}
