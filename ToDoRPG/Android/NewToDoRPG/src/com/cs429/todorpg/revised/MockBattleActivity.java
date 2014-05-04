package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.battlelogic.AttackResult;
import com.cs429.todorpg.battlelogic.BattleLogic;
import com.cs429.todorpg.battlelogic.BtPackage;
import com.cs429.todorpg.revised.controller.BTMessageHandler;
import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;
import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class MockBattleActivity extends BaseActivity {
	enum GameState {
		ready, gameOver
	}

	GameState state = GameState.ready;
	int width, height, playerMaxHP, enemyMaxHP;
	RelativeLayout battleScreen, battleNavigator, enemyInfo, actionMenu,
	playerInfo, enemySide, playerSide;
	TextView enemyName, enemyHP, playerName, playerHP, battleAnnouncement;
	ImageView enemyImage, playerImage, playerEffect, enemyEffect;
	AnimationDrawable playerWalk, playerAttack, enemyAttack;
	Button attack, change_weapon;
	Intent intent;
	ArrayList<Character> party;
	ToDoCharacter player, enemy;
	public ToDoCharacter getPlayer() {
		return player;
	}

	public void setPlayer(ToDoCharacter player) {
		this.player = player;
	}

	public ToDoCharacter getEnemy() {
		return enemy;
	}

	public void setEnemy(ToDoCharacter enemy) {
		this.enemy = enemy;
	}

	Avatar playerAvatar, enemyAvatar;
	Inventory inventory;
	SQLiteHelper sql = new SQLiteHelper(this);
	AlertDialog.Builder builder;
	AlertDialog battleEnd;

	boolean myTurn = true;
	
	public MockBattleActivity getMockContext() {
		return MockBattleActivity.this;
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	/**
	 * Create function when activity starts
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpLayout();
		setContentView(R.layout.battle_activity);
		FindViewById();

		setUpActivity();
		initMock();
	}

	/**
	 * Destory function when activity calls finish()
	 */
	@Override
	public void onDestroy() {
		Log.e("[LifeCycle]", "BattleMainActivity: ++ onDestroy ++");
		super.onDestroy();
	}

	/**
	 * Load inventory
	 */

	/**
	 * Toast string
	 * 
	 * @param s
	 */
	public void battleToast(String s) {
		Toast.makeText(MockBattleActivity.this, s, Toast.LENGTH_SHORT).show();
	}

	/**
	 * set up helper
	 */
	private void setUpActivity() {
		setPlayers();
		setUpBattleScreen();
		setUpBattleNavigator();
		setUpBattleMenu();
		setUpBattleInfo();
		update();

	}

	/**
	 * sets up upper half of screen, battle sprites and battle damage
	 */
	private void setUpBattleScreen() {
		// Set up battle screen.
		battleScreen.setLayoutParams(new RelativeLayout.LayoutParams(width,
				3 * height / 4));
		battleScreen.setBackgroundResource(R.drawable.battle_background);

		// Set up upper half of screen
		RelativeLayout.LayoutParams playerSideParams = new RelativeLayout.LayoutParams(
				width / 2, 3 * height / 4);
		playerSideParams.addRule(RelativeLayout.RIGHT_OF, enemySide.getId());

		enemySide.setLayoutParams(new RelativeLayout.LayoutParams(width / 2,
				3 * height / 4));
		playerSide.setLayoutParams(playerSideParams);

		// Set the player images
		RelativeLayout.LayoutParams enemyEffectParams = new RelativeLayout.LayoutParams(
				enemyEffect.getLayoutParams());
		enemyEffectParams.addRule(RelativeLayout.RIGHT_OF, enemyImage.getId());
		enemyEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM,
				enemyImage.getId());
		enemyEffect.setLayoutParams(enemyEffectParams);

		RelativeLayout.LayoutParams playerEffectParams = new RelativeLayout.LayoutParams(
				playerEffect.getLayoutParams());
		playerEffectParams.addRule(RelativeLayout.LEFT_OF, playerImage.getId());
		playerEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM,
				playerImage.getId());
		playerEffect.setLayoutParams(playerEffectParams);

		RelativeLayout.LayoutParams announcementParams = new RelativeLayout.LayoutParams(
				battleAnnouncement.getLayoutParams());
		announcementParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		battleAnnouncement.setLayoutParams(announcementParams);

		/******************* temporarry inventory *************************/
		inventory = new Inventory();
		ArrayList<NegativeEffects> negs = new ArrayList<NegativeEffects>();
		ArrayList<PositiveEffects> poss = new ArrayList<PositiveEffects>();
		inventory
		.setArmor(new Armor("Leather Armor",
				R.drawable.broad_armor_warrior_1, 1, 1, 1, negs, 1, 1,
				1, poss));
		inventory.setHelmet(new Helmet("Leather Helmet",
				R.drawable.head_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setShield(new Shield("Leather Shield",
				R.drawable.shield_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setWeapon(new Weapon("Iron Sword",
				R.drawable.weapon_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));

		inventory.addInventory(new Weapon("Rogue Weapon 0",
				R.drawable.weapon_rogue_0, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.addInventory(new Weapon("Rogue Weapon 1",
				R.drawable.weapon_rogue_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.addInventory(new Weapon("Rogue Weapon 2",
				R.drawable.weapon_rogue_2, 1, 1, 1, negs, 1, 1, 1, poss));
		playerImage.setImageBitmap(inventory.getBitmap());
		enemyImage.setImageBitmap(inventory.getBitmap());

	}

	/**
	 * set up battle menu and info
	 */
	private void setUpBattleNavigator() {
		// Set up Battle navigator
		RelativeLayout.LayoutParams battleNav = new RelativeLayout.LayoutParams(
				width, height / 4);
		battleNav.addRule(RelativeLayout.BELOW, battleScreen.getId());
		battleNavigator.setLayoutParams(battleNav);
	}

	/**
	 * setup player and enemy name and hp panel
	 */
	private void setUpBattleInfo() {
		RelativeLayout.LayoutParams playerInfoParams = new RelativeLayout.LayoutParams(
				width / 4, height / 2);
		playerInfoParams.addRule(RelativeLayout.RIGHT_OF, actionMenu.getId());

		enemyInfo.setLayoutParams(new RelativeLayout.LayoutParams(width / 4,
				height / 2));
		playerInfo.setLayoutParams(playerInfoParams);

		RelativeLayout.LayoutParams enemyHPParams = new RelativeLayout.LayoutParams(
				enemyHP.getLayoutParams());
		enemyHPParams.addRule(RelativeLayout.BELOW, enemyName.getId());
		enemyHP.setLayoutParams(enemyHPParams);

		RelativeLayout.LayoutParams playerHPParams = new RelativeLayout.LayoutParams(
				playerHP.getLayoutParams());
		playerHPParams.addRule(RelativeLayout.BELOW, playerName.getId());
		playerHP.setLayoutParams(playerHPParams);
	}

	/**
	 * set up battle menu, attack button
	 */
	private void setUpBattleMenu() {
		int buttonHeight = (height / 2) / 5;
		// Set up lower half of screen
		RelativeLayout.LayoutParams actionMenuParams = new RelativeLayout.LayoutParams(
				width / 2, height / 2);
		actionMenuParams.addRule(RelativeLayout.RIGHT_OF, enemyInfo.getId());
		actionMenu.setLayoutParams(actionMenuParams);

		RelativeLayout.LayoutParams attackParams = new RelativeLayout.LayoutParams(
				attack.getLayoutParams());
		attackParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
		attack.setLayoutParams(attackParams);

		RelativeLayout.LayoutParams changeWeaponsParam = new RelativeLayout.LayoutParams(
				attack.getLayoutParams());
		changeWeaponsParam.addRule(RelativeLayout.CENTER_VERTICAL, 1);
		changeWeaponsParam.addRule(RelativeLayout.RIGHT_OF, R.id.attack_button);
		change_weapon.setLayoutParams(changeWeaponsParam);

		attack.setOnClickListener(ButtonListener);
		change_weapon.setOnClickListener(ButtonListener);
	}

	/**
	 * pull players from DB
	 */
	private void setPlayers() {
		player = sql.getCharacter();
		enemy = sql.getCharacter();
		playerMaxHP = player.getHP();
		enemyMaxHP = enemy.getHP();
	}

	/**
	 * animation function, waits for anmiation to run to the end before clearing
	 * view
	 * 
	 * @param anim
	 * @param img
	 */
	private void waitForEffectAnimationDone(AnimationDrawable anim,
			final ImageView img) {
		final AnimationDrawable a = anim;
		int timeBetweenChecks = 300;
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)) {
					waitForEffectAnimationDone(a, img);
				} else {
					img.setBackgroundResource(R.color.transparent);
				}
			}
		}, timeBetweenChecks);
	}

	/**
	 * animation helper
	 * 
	 * @param anim
	 * @param effect
	 * @param animation
	 */
	private void Animate(AnimationDrawable anim, final ImageView effect,
			int animation) {
		effect.setBackgroundResource(animation);
		anim = (AnimationDrawable) effect.getBackground();
		anim.start();
		waitForEffectAnimationDone(anim, effect);
	}

	/**
	 * Updates the HP and checks for game ending conditions
	 */
	private void update() {
		if (enemy.getHP() < 0) {
			enemy.setHP(0);
		}
		if (player.getHP() < 0) {
			player.setHP(0);
		}
		enemyName.setText(enemy.getName() + " Lv."
				+ Integer.toString(enemy.getLevel()));
		enemyHP.setText("HP" + enemy.getHP() + "/" + enemyMaxHP);
		playerName.setText(player.getName() + " Lv."
				+ Integer.toString(player.getLevel()));
		playerHP.setText("HP " + player.getHP() + "/" + playerMaxHP);

		// Need to add check game conditions.
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {

				checkGameConditions();
			}
		}, 1000);
	}

	/**
	 * Return true if game is over
	 */
	private void checkGameConditions() {
		int victory = 0;
		if (player.getHP() < 1) {
			makeGameOverMessages("GAME OVER!");
			battleEnd = builder.create();
			battleEnd.show();
			state = GameState.gameOver;
			victory = 1;

		}
		if (enemy.getHP() < 1) {
			makeGameOverMessages("VICTORY!");
			battleEnd = builder.create();
			battleEnd.show();
			state = GameState.gameOver;
			victory = 0;

		}

	}

	/**
	 * Attack button listener
	 */
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.attack_button:
				
				if (myTurn) {
					myTurn = false;
					// Calculate effects of this attack
					AttackResult attackResult = BattleLogic
							.calculateAttackResult(playerAvatar.getInventory(),
									enemyAvatar.getInventory());

					// First check if the attack registers
					if (attackResult.isHit) {
						Log.d("TEST", "is hit");
						// Loop through the hits
						for (boolean b : attackResult.critChanceList) {
							int finalDamage = attackResult.damagePerHit;
							if (b) { // Is a critical Hit
								finalDamage *= BattleLogic.CRITICAL_DAMAGE_MODIFIER;
								setBattleMessage("You have critically hit "
										+ enemy.getName() + " causing "
										+ finalDamage + " damage!");
							} else {
								setBattleMessage(" You attacked "
										+ enemy.getName() + " causing "
										+ finalDamage + " damage!");
							}

							Animate(playerAttack, playerEffect,
									R.drawable.player_attack);
							enemy.setHP(enemy.getHP() - finalDamage);
							Log.d("TEST", "enemy HP: " + enemy.getHP());
							update();
							
						}
					} else {
						Log.d("TEST", "missed");
						setBattleMessage(" Your attack missed!");
					}

					Log.d("BATTLE_ACTIVITY", "Attack");

				} else {
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder(MockBattleActivity.this);
					ab.setTitle("Battle");
					ab.setMessage("It is not Your Turn!!");
					ab.setPositiveButton("OK", null);
					ab.show();
				}
				break;
			case R.id.change_weapon:

				break;
			}
		}
	};

	/**
	 * finds views from layout
	 */
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
		attack = (Button) findViewById(R.id.attack_button);
		change_weapon = (Button) findViewById(R.id.change_weapon);

	}

	/**
	 * Makes activity full screen and landscape
	 */
	private void setUpLayout() {
		// Remove title bar
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Sets to landscape
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// Get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;

		height = size.y;
	}

	/**
	 * helper for setting up battle message
	 * 
	 * @param msg
	 */
	private void setBattleMessage(String msg) {
		battleAnnouncement.setText(msg);
		battleAnnouncement.setVisibility(View.VISIBLE);
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				battleAnnouncement.setVisibility(View.INVISIBLE);
			}
		}, 1000);
	}

	/**
	 * builder for confirm pop up
	 * 
	 * @param msg
	 */
	public void makeGameOverMessages(String msg) {
		builder = new AlertDialog.Builder(this);

		builder.setMessage(msg);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				intent = new Intent(MockBattleActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	public void initMock() {
		
		ArrayList<PositiveEffects> posEffects = new ArrayList<PositiveEffects>();
		ArrayList<NegativeEffects> negEffects = new ArrayList<NegativeEffects>();
		
		Avatar myAv = new Avatar();
		Inventory myInventory = new Inventory();
		
		
		myInventory.setWeapon(new Weapon("Your Sword", R.drawable.weapon_warrior_1, 10, 0, 1, negEffects, 0, 0, 200, posEffects));
		ToDoCharacter myChar = new ToDoCharacter("you", 10, 100, 1, 0, 5000);
		myAv.setInventory(myInventory);
		myAv.setToDoCharacter(myChar);
		inventory = myInventory;
		
		player = myChar;
		myAv.setInventory(inventory);
		myAv.setToDoCharacter(myChar);
		setPlayerInfo(myChar);
		playerAvatar = myAv;
		playerImage.setImageBitmap(myAv.getClearBitmap());
		
		Avatar enemyAv = new Avatar();
		Inventory enemyInventory = new Inventory();
		enemyInventory.setWeapon(new Weapon("Enemy Sword", R.drawable.weapon_warrior_2, 10, 0, 1, negEffects, 0, 0, 200, posEffects));
		ToDoCharacter enemyChar = new ToDoCharacter("enemy", 10, 100, 1, 0, 5000);
		enemyAv.setInventory(enemyInventory);
		enemyAv.setToDoCharacter(enemyChar);
		
		// Initialize BattleActivity
		setEnemyImage(enemyAv);
		setEnemyInfo(enemyChar);
	}

	/*
	 * FUNCTIONS ADDED BY PAUL These functions are called by
	 * BTMessageHandler.java. therefore must be public
	 */

	public void defendAttack(AttackResult attackResult) {
		// Loop through the hits
		myTurn = true;
		if (attackResult.isHit) {
			for (boolean b : attackResult.critChanceList) {
				int finalDamage = attackResult.damagePerHit;
				if (b) { // Is a critical Hit
					finalDamage *= BattleLogic.CRITICAL_DAMAGE_MODIFIER;
					setBattleMessage(enemy.getName()
							+ " critically attacked you causing " + finalDamage
							+ " damage!");
				} else {
					setBattleMessage(enemy.getName() + " attacked you causing "
							+ finalDamage + " damage!");
				}

				Animate(enemyAttack, enemyEffect, R.drawable.enemy_attack);

				player.setHP(player.getHP() - finalDamage);
				update();
			}
		} else {
			setBattleMessage(enemy.getName() + " attacked you but missed!");
		}
	}

	public void setEnemyImage(Avatar enemyAv) {
		enemyAvatar = enemyAv;
		enemyImage.setImageBitmap(enemyAv.getClearBitmap());
	}

	public void setEnemyInfo(ToDoCharacter c) {
		enemy = c;
		enemyName.setText(c.getName());
		enemyHP.setText(Integer.toString(c.getHP()));
		enemyMaxHP = enemy.getHP();
	}

	public void setPlayerInfo(ToDoCharacter c) {
		player = c;
		playerName.setText(c.getName());
		playerHP.setText(Integer.toString(c.getHP()));
		playerMaxHP = player.getHP();
	}


}
