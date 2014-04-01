package com.CS429.todorpg;

import java.util.ArrayList;

import com.CS429.todorpg.Class.Archer;
import com.CS429.todorpg.Class.Assassin;
import com.CS429.todorpg.Class.Enemy;
import com.CS429.todorpg.Class.Mage;
import com.CS429.todorpg.Class.Summoner;
import com.CS429.todorpg.Class.Warrior;
import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Utils.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
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
	enum GameState{ ready, gameOver }
	GameState state = GameState.ready;
	boolean defaultClass, playerTurn;
	int width, height;
	RelativeLayout battleScreen, battleNavigator, enemyInfo, actionMenu, playerInfo, enemySide, playerSide;
	Button attackButton, itemsButton, passButton;
	Spinner skillsSpinner;
	TextView enemyName, enemyHP, playerName, playerHP, playerMP, battleAnnouncement;
	ImageView enemyImage, playerImage, playerEffect, enemyEffect;
	AnimationDrawable playerWalk, playerAttack, enemyAttack, playerSkill1, playerSkill2, playerSkill3, playerSkill4;
	Intent intent;
	ArrayList<Character> party;
	Character player;
	Enemy boss;
	AlertDialog.Builder builder;
	AlertDialog battleEnd;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpLayout();
		interpretIntent();
		setUpBattle();
		setContentView(R.layout.battle);
		FindViewById();
		setUpActivity();
		playerTurn = false;

	}

	private void bossAI() {
		boss.attack(player);
		setBattleMessage(boss.getName() + " attacks " + player.getName());
		Animate(enemyAttack, enemyEffect, R.drawable.player_attack);
		update();
	}
	
	private void disablePlayerButtons() {
		attackButton.setEnabled(false);
		skillsSpinner.setEnabled(false);
		itemsButton.setEnabled(false);
		passButton.setEnabled(false);
	}
	private void enablePlayerButtons() {
		attackButton.setEnabled(true);
		skillsSpinner.setEnabled(true);
		itemsButton.setEnabled(true);
		passButton.setEnabled(true);
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
	    RelativeLayout.LayoutParams enemyEffectParams = new RelativeLayout.LayoutParams(enemyEffect.getLayoutParams());
	    enemyEffectParams.addRule(RelativeLayout.RIGHT_OF, enemyImage.getId());
	    enemyEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM, enemyImage.getId());
	    enemyEffect.setLayoutParams(enemyEffectParams);
	    
	    
	    
	    
	    playerImage.setBackgroundResource(R.drawable.warrior_walk);
	    RelativeLayout.LayoutParams playerEffectParams = new RelativeLayout.LayoutParams(playerEffect.getLayoutParams());
	    playerEffectParams.addRule(RelativeLayout.LEFT_OF, playerImage.getId());
	    playerEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM, playerImage.getId());
	    playerEffect.setLayoutParams(playerEffectParams);
	    
	    RelativeLayout.LayoutParams announcementParams =  new RelativeLayout.LayoutParams(battleAnnouncement.getLayoutParams());
	    announcementParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
	    battleAnnouncement.setLayoutParams(announcementParams);
	    
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
	    
		if(player.getCLASS().equals("Warrior")){
			skillArray = R.array.warrior_skills;
			playerImage.setBackgroundResource(R.drawable.warrior_walk);
			playerWalk = (AnimationDrawable) playerImage.getBackground();
			playerWalk.start();
		}
		else if(player.getCLASS().equals("Assassin")){
			skillArray = R.array.assassins_skills;
			playerImage.setBackgroundResource(R.drawable.warrior_walk);
			playerWalk = (AnimationDrawable) playerImage.getBackground();
			playerWalk.start();
		}
		else if(player.getCLASS().equals("Mage")){
			skillArray = R.array.mage_skills;
			playerImage.setBackgroundResource(R.drawable.warrior_walk);
			playerWalk = (AnimationDrawable) playerImage.getBackground();
			playerWalk.start();
		}
		else if(player.getCLASS().equals("Summoner")){
			skillArray = R.array.summoner_skills;
			playerImage.setBackgroundResource(R.drawable.warrior_walk);
			playerWalk = (AnimationDrawable) playerImage.getBackground();
			playerWalk.start();
		}
		else if(player.getCLASS().equals("Archer")){
			skillArray = R.array.archer_skills;
			playerImage.setBackgroundResource(R.drawable.warrior_walk);
			playerWalk = (AnimationDrawable) playerImage.getBackground();
			playerWalk.start();
		}
		
		ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(skillArray));
	    skillsSpinner.setAdapter(spinnerCountShoesArrayAdapter);
	}
	
	private void waitForEffectAnimationDone(AnimationDrawable anim, final ImageView img) {
		final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                	waitForEffectAnimationDone(a, img);
                } else{
                	img.setBackgroundResource(R.color.transparent);

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
				setBattleMessage(player.getName() + " attacks " + boss.getName());
				/*
				playerEffect.setBackgroundResource(R.drawable.player_attack);
				playerAttack = (AnimationDrawable) playerEffect.getBackground();
				playerAttack.start();
				waitForEffectAnimationDone(playerAttack, playerEffect);
				*/
				Animate(playerAttack, playerEffect, R.drawable.player_attack);
				update();
				break;
			case R.id.battle_items_btn:
				setBattleMessage(player.getName() + " heals 20HP!");
				player.setHP(player.getHP() + 20);
				update();
				break;
			case R.id.battle_pass_btn:
				setBattleMessage(player.getName() + " passes!");

				update();
				break;

			}
		}
	};
	
	private void Animate(AnimationDrawable anim, final ImageView effect, int animation) {
		effect.setBackgroundResource(animation);
		anim = (AnimationDrawable) effect.getBackground();
		anim.start();
		waitForEffectAnimationDone(anim, effect);
	}
	
	
	//Updates the screen
	private void update() {
		enemyName.setText(boss.getName() + " Lv." + Integer.toString(boss.getLEVEL()));
	    enemyHP.setText("HP" + boss.getHP() + "/" + boss.getMaxHP());
	    
	    
	    playerName.setText(player.getName() + " Lv." + Integer.toString(player.getLEVEL()));
	    playerHP.setText("HP " + player.getHP() + "/" + player.getMaxHP());
	    playerMP.setText("MP " + player.getMP() + "/" + player.getMaxMP());
	    
	    // Need to add check game conditions. 
	    Handler h = new Handler();
	    h.postDelayed(new Runnable() {
	    	@Override
			public void run() {
	    		checkGameConditions();
	    	    if(state != GameState.gameOver)
	    	    	changeTurn();
			}}, 1000);
	}
	
	//Change turns
	private void changeTurn() {
		if(playerTurn) {
			playerTurn = false;
			disablePlayerButtons();
			Handler h = new Handler();
		    h.postDelayed(new Runnable() {
		    	@Override
				public void run() {
					bossAI();
				}}, 1000);
		}
		//Boss just finished turn
		else {
			playerTurn = true;
			Handler h = new Handler();
		    h.postDelayed(new Runnable() {
		    	@Override
				public void run() {
		    		enablePlayerButtons();
		    		
		    	}}, 1000);
			//Prompt message, 'This character's turn'
			
		}
	}
	//Return true if game is over
	private void checkGameConditions() {
		if(player.getHP() < 1) {
			makeGameOverMessages("GAME OVER!");
			battleEnd = builder.create();
			battleEnd.show();	
			state = GameState.gameOver;
		}
		if(boss.getHP() < 1) {
			if(player.gainEXP(boss.getExp()))
				setBattleMessage("LEVEL UP!" + player.getName() + " is " + Integer.toString(player.getLEVEL()));
			makeGameOverMessages("VICTORY!");
			battleEnd = builder.create();
			battleEnd.show();
			state = GameState.gameOver;

		}

		
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
					if(player.Skill_1(boss)) {
					setBattleMessage(player.getName() + " uses " + skillsSpinner.getSelectedItem().toString());
					Animate(playerAttack, playerEffect, R.drawable.player_attack);
					update();
					}
					else { 
						setBattleMessage("Low mana!"); 
					}
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
				skillsSpinner.setSelection(0);
				
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
		enemyEffect = (ImageView) findViewById(R.id.battle_enemy_effect);
		attackButton = (Button) findViewById(R.id.battle_attack_btn);
		skillsSpinner = (Spinner) findViewById(R.id.battle_skills_spinner);
		itemsButton = (Button) findViewById(R.id.battle_items_btn);
		passButton = (Button) findViewById(R.id.battle_pass_btn);
		enemyName = (TextView) findViewById(R.id.battle_enemy_info_name);
		enemyHP = (TextView) findViewById(R.id.battle_enemy_info_hp);
		playerName = (TextView) findViewById(R.id.battle_player_info_name);
		playerHP = (TextView) findViewById(R.id.battle_player_info_hp);
		playerMP = (TextView) findViewById(R.id.battle_player_info_mp);
		battleAnnouncement = (TextView) findViewById(R.id.battle_announcement);
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
		Character leader = CharacterOperations.pullCharacter(((UserInfo)getApplicationContext()).getCharacter());
		party.add(leader);
		
		//*** Will need to change to pull all members of a party in the next iteration, for loop ^^^^^^^^ ***//
	}
	
	private void makeBoss() {
		boss = new Enemy("Boss");
		boss.setHP(100);
		boss.setMaxHP(100);
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
	
	private void setBattleMessage(String msg) {
		battleAnnouncement.setText(msg);
		battleAnnouncement.setVisibility(View.VISIBLE);
		Handler h = new Handler();
	    h.postDelayed(new Runnable() {
	    	@Override
			public void run() {
	    		battleAnnouncement.setVisibility(View.INVISIBLE);
	    		
	    	}}, 1000);

	}
	
	public void makeGameOverMessages(String msg) {
		builder = new AlertDialog.Builder(this);

		builder.setMessage(msg + " Would you like to try again?");
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				player.setHP(player.getMaxHP());
				player.setMP(player.getMaxMP());
				
				boss.setHP(boss.getMaxHP());
				boss.setMP(boss.getMaxMP());

				
			}
		});
		
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {			
				dialog.dismiss();
				finish();
				
			}
		});
		
	}
	
	
}
