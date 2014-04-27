
package com.cs429.todorpg.revised;

import java.util.ArrayList;

import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends BaseActivity {
	enum GameState{ ready, gameOver }
	GameState state = GameState.ready;
	int width, height, playerMaxHP, enemyMaxHP;
	RelativeLayout battleScreen, battleNavigator, enemyInfo, actionMenu, playerInfo, enemySide, playerSide;
	TextView enemyName, enemyHP, playerName, playerHP, battleAnnouncement;
	ImageView enemyImage, playerImage, playerEffect, enemyEffect, playerStatus, enemyStatus;
	AnimationDrawable playerWalk, playerAttack, enemyAttack;
	Button attack, change_weapon;
	Intent intent;
	ArrayList<Character> party;
	ToDoCharacter player, enemy;
	Inventory inventory;
	SQLiteHelper sql = new SQLiteHelper(this);
	AlertDialog.Builder builder;
	AlertDialog battleEnd;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpLayout();
		setContentView(R.layout.battle_activity);
		FindViewById();
		setUpActivity();
		

	}


	private void setUpActivity() {
	    setPlayers();
		setUpBattleScreen();
		setUpBattleNavigator();
		setUpBattleMenu();
		setUpBattleInfo();
		update();
		
	}

	private void setUpBattleScreen() {
		// Set up battle screen. 
		battleScreen.setLayoutParams(new RelativeLayout.LayoutParams(width, 3*height/4));
		battleScreen.setBackgroundResource(R.drawable.battle_background);

		// Set up upper half of screen
		RelativeLayout.LayoutParams playerSideParams = new RelativeLayout.LayoutParams(width/2, 3*height/4);
		playerSideParams.addRule(RelativeLayout.RIGHT_OF, enemySide.getId());

		enemySide.setLayoutParams(new RelativeLayout.LayoutParams(width/2, 3*height/4));			    
		playerSide.setLayoutParams(playerSideParams);

		//Set the player images
	    //enemyImage.setImageResource(R.drawable.test_sprite);
	    RelativeLayout.LayoutParams enemyEffectParams = new RelativeLayout.LayoutParams(enemyEffect.getLayoutParams());
	    enemyEffectParams.addRule(RelativeLayout.RIGHT_OF, enemyImage.getId());
	    enemyEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM, enemyImage.getId());
	    enemyEffect.setLayoutParams(enemyEffectParams);


	    //playerImage.setBackgroundResource(R.drawable.warrior_walk);
	    RelativeLayout.LayoutParams playerEffectParams = new RelativeLayout.LayoutParams(playerEffect.getLayoutParams());
	    playerEffectParams.addRule(RelativeLayout.LEFT_OF, playerImage.getId());
	    playerEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM, playerImage.getId());
	    playerEffect.setLayoutParams(playerEffectParams);
	    
	    
	    RelativeLayout.LayoutParams playerStatusParams = new RelativeLayout.LayoutParams(playerStatus.getLayoutParams());
	    playerStatusParams.addRule(RelativeLayout.ALIGN_LEFT, playerImage.getId());
	    playerStatusParams.addRule(RelativeLayout.ABOVE, playerImage.getId());
	    playerStatus.setLayoutParams(playerStatusParams);
	    
	    RelativeLayout.LayoutParams enemyStatusParams = new RelativeLayout.LayoutParams(enemyStatus.getLayoutParams());
	    enemyStatusParams.addRule(RelativeLayout.ALIGN_LEFT, enemyImage.getId());
	    enemyStatusParams.addRule(RelativeLayout.ABOVE, enemyImage.getId());

	    enemyStatus.setLayoutParams(enemyStatusParams);

	    RelativeLayout.LayoutParams announcementParams =  new RelativeLayout.LayoutParams(battleAnnouncement.getLayoutParams());
	    announcementParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
	    battleAnnouncement.setLayoutParams(announcementParams);
	    
	    
	    /*******************temporarry inventory*************************/
	    inventory = new Inventory();
	    ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
		ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
	    inventory.setArmor(new Armor("Leather Armor", R.drawable.broad_armor_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setShield(new Shield("Leather Shield", R.drawable.shield_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setWeapon(new Weapon("Iron Sword", R.drawable.weapon_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		
		inventory.addInventory(new Weapon("Rogue Weapon 0", R.drawable.weapon_rogue_0, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.addInventory(new Weapon("Rogue Weapon 1", R.drawable.weapon_rogue_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.addInventory(new Weapon("Rogue Weapon 2", R.drawable.weapon_rogue_2, 1, 1, 1, negs, 1, 1, 1, poss));
	    playerImage.setImageBitmap(inventory.getBitmap());
	    enemyImage.setImageBitmap(inventory.getBitmap());
	    
	    

	}

	private void setUpBattleNavigator() {
		// Set up Battle navigator
		RelativeLayout.LayoutParams battleNav = new RelativeLayout.LayoutParams(width, height/4);
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


	}
	
	private void setUpBattleMenu() {
		int buttonHeight = (height/2)/5;
		// Set up lower half of screen
		RelativeLayout.LayoutParams actionMenuParams = new RelativeLayout.LayoutParams(width/2, height/2);
		actionMenuParams.addRule(RelativeLayout.RIGHT_OF, enemyInfo.getId());
	    actionMenu.setLayoutParams(actionMenuParams);
	    
	    RelativeLayout.LayoutParams attackParams = new RelativeLayout.LayoutParams(attack.getLayoutParams());
	    attackParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
	    attack.setLayoutParams(attackParams);
	    
	    
	    RelativeLayout.LayoutParams changeWeaponsParam = new RelativeLayout.LayoutParams(attack.getLayoutParams());
	    changeWeaponsParam.addRule(RelativeLayout.CENTER_VERTICAL, 1);
	    changeWeaponsParam.addRule(RelativeLayout.RIGHT_OF, R.id.attack_button);
	    change_weapon.setLayoutParams(changeWeaponsParam);

	    attack.setOnClickListener(ButtonListener);
	    change_weapon.setOnClickListener(ButtonListener);
	}
	

	private void setPlayers() {
		player = sql.getCharacter();
		enemy = sql.getCharacter();
		playerMaxHP = player.getHP();
		
		enemyMaxHP = enemy.getHP();
		enemyMaxHP = enemy.getHP();		

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


	private void Animate(AnimationDrawable anim, final ImageView effect, int animation) {
		effect.setBackgroundResource(animation);
		anim = (AnimationDrawable) effect.getBackground();
		anim.start();
		waitForEffectAnimationDone(anim, effect);
	}


	//Updates the screen
	private void update() {
		enemyName.setText(enemy.getName() + " Lv." + Integer.toString(enemy.getLevel()));
	    enemyHP.setText("HP" + enemy.getHP() + "/" + enemyMaxHP);


	    playerName.setText(player.getName() + " Lv." + Integer.toString(player.getLevel()));
	    playerHP.setText("HP " + player.getHP() + "/" + playerMaxHP);

	    // Need to add check game conditions. 
	    Handler h = new Handler();
	    h.postDelayed(new Runnable() {
	    	@Override
			public void run() {
	    		checkGameConditions();
	    	    if(state != GameState.gameOver);
	    	    	//changeTurn();
			}}, 1000);
	}

	//Change turns
	private void changeTurn() {
		if(true) {
			Handler h = new Handler();
		    h.postDelayed(new Runnable() {
		    	@Override
				public void run() {
				}}, 1000);
		}
		//Boss just finished turn
		else {
			Handler h = new Handler();
		    h.postDelayed(new Runnable() {
		    	@Override
				public void run() {

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
		if(enemy.getHP() < 1) {
			makeGameOverMessages("VICTORY!");
			battleEnd = builder.create();
			battleEnd.show();
			state = GameState.gameOver;

		}

	}

	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.attack_button:
				//player.attack(enemy);
				setBattleMessage(player.getName() + " attacks " + enemy.getName());
				/*
				playerEffect.setBackgroundResource(R.drawable.player_attack);
				playerAttack = (AnimationDrawable) playerEffect.getBackground();
				playerAttack.start();
				waitForEffectAnimationDone(playerAttack, playerEffect);
				*/
				Animate(playerAttack, playerEffect, R.drawable.player_attack);
				update();
				break;
			case R.id.change_weapon:
				
				break;
			}
		}

	};


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
		enemyName = (TextView) findViewById(R.id.battle_enemy_info_name);
		enemyHP = (TextView) findViewById(R.id.battle_enemy_info_hp);
		playerName = (TextView) findViewById(R.id.battle_player_info_name);
		playerHP = (TextView) findViewById(R.id.battle_player_info_hp);
		battleAnnouncement = (TextView) findViewById(R.id.battle_announcement);
		attack = (Button)findViewById(R.id.attack_button);
		change_weapon = (Button)findViewById(R.id.change_weapon);
		playerStatus = (ImageView)findViewById(R.id.battle_player_status);
		enemyStatus = (ImageView)findViewById(R.id.battle_enemy_status);
		
	}


	private void setUpLayout() {

		 //Remove title bar
	    //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
				//player.setHP(player.getMaxHP());

				//boss.setHP(boss.getMaxHP());


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
