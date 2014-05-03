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
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class TutorialBattleActivity extends BaseActivity {
	enum GameState {
		ready, gameOver
	}

	GameState state = GameState.ready;
	int width, height, playerMaxHP, enemyMaxHP, tutorialStep = 0;
	RelativeLayout battleScreen, battleNavigator, enemyInfo, actionMenu,
			playerInfo, enemySide, playerSide;
	TextView enemyName, enemyHP, playerName, playerHP, battleAnnouncement, playerDamage, enemyDamage;
	ImageView enemyImage, playerImage, playerEffect, enemyEffect,
			playerStatusPoison, playerStatusBlind, playerStatusCurse,
			enemyStatusPoison, enemyStatusBlind, enemyStatusCurse;
	AnimationDrawable playerWalk, playerAttack, enemyAttack;
	Button attack, change_weapon;
	Intent intent;
	ArrayList<Character> party;
	ToDoCharacter player, enemy;
	Inventory inventory;
	SQLiteHelper sql = new SQLiteHelper(this);
	AlertDialog.Builder builder;
	AlertDialog battleEnd;
	boolean playerTurn = false;
	/**
	 * Creation function when activity starts
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpLayout();
		setContentView(R.layout.tutorial_battle_activity);
		FindViewById();
		setUpActivity();
		View view = findViewById(R.id.tutorial_battle_view);
		setBattleMessage("HI! Welcome to the battle tutorial.\n"
				+ "(Tap anywhere to continue)");

		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				runTutorial();
				return false;
			}

		});

	}
	/**
	 * Tutorial function
	 */
	private void runTutorial() {
		attack.setEnabled(false);
		if (tutorialStep == 0) {
			setBattleMessage("This is the field in which you will battle!");
		} else if (tutorialStep == 1) {
			setBattleMessage("On the bottom corners, you can see your player and enemy name and HP");
		} else if (tutorialStep == 2) {
			setBattleMessage("You have a chance to critical strike the enemy!");
		} else if (tutorialStep == 3) {
			setBattleMessage("You have finished the tutorial, Try and defeat the boss.");
		} else {
			attack.setEnabled(true);
		}
		tutorialStep++;
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
		// enemyImage.setImageResource(R.drawable.test_sprite);
		RelativeLayout.LayoutParams enemyEffectParams = new RelativeLayout.LayoutParams(
				enemyEffect.getLayoutParams());
		enemyEffectParams.addRule(RelativeLayout.RIGHT_OF, enemyImage.getId());
		enemyEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM,
				enemyImage.getId());
		enemyEffect.setLayoutParams(enemyEffectParams);

		// playerImage.setBackgroundResource(R.drawable.warrior_walk);
		RelativeLayout.LayoutParams playerEffectParams = new RelativeLayout.LayoutParams(
				playerEffect.getLayoutParams());
		playerEffectParams.addRule(RelativeLayout.LEFT_OF, playerImage.getId());
		playerEffectParams.addRule(RelativeLayout.ALIGN_BOTTOM,
				playerImage.getId());
		playerEffect.setLayoutParams(playerEffectParams);

		RelativeLayout.LayoutParams announcementParams = new RelativeLayout.LayoutParams(
				battleAnnouncement.getLayoutParams());
		announcementParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		announcementParams.addRule(RelativeLayout.CENTER_VERTICAL);
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
		Avatar playerAv = new Avatar();
		playerAv.setInventory(inventory);

		playerImage.setImageBitmap(playerAv.getClearBitmap());
		enemyImage.setImageBitmap(playerAv.getClearBitmap());

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
	 *  setup player and enemy name and hp panel
	 */
	private void setUpBattleInfo() {
		RelativeLayout.LayoutParams playerInfoParams = new RelativeLayout.LayoutParams(
				width / 4, height / 2);
		playerInfoParams.addRule(RelativeLayout.RIGHT_OF, actionMenu.getId());

		enemyInfo.setLayoutParams(new RelativeLayout.LayoutParams(width / 4,
				height / 2));
		playerInfo.setLayoutParams(playerInfoParams);

		// RelativeLayout.LayoutParams enemyNameParams = new
		// RelativeLayout.LayoutParams(enemyName.getLayoutParams());

		RelativeLayout.LayoutParams enemyHPParams = new RelativeLayout.LayoutParams(
				enemyHP.getLayoutParams());
		enemyHPParams.addRule(RelativeLayout.BELOW, enemyName.getId());
		enemyHP.setLayoutParams(enemyHPParams);

		// RelativeLayout.LayoutParams playerNameParams = new
		// RelativeLayout.LayoutParams(playerName.getLayoutParams());

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
		enemyMaxHP = enemy.getHP();

	}
	/**
	 * animation function, waits for anmiation to run to the end before clearing view
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

	private void Animate(AnimationDrawable anim, final ImageView effect,
			int animation) {
		effect.setBackgroundResource(animation);
		anim = (AnimationDrawable) effect.getBackground();
		anim.start();
		waitForEffectAnimationDone(anim, effect);
	}

	/**
	 * animation helper
	 * @param anim
	 * @param effect
	 * @param animation
	 */
	private void update() {
		enemyName.setText("Boss Lv." + Integer.toString(enemy.getLevel()));
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
				playerDamage.setText("");
				enemyDamage.setText("");
				if (state != GameState.gameOver)
					changeTurn();
			}
		}, 1000);
	}

	/**
	 * Changes turns
	 */
	private void changeTurn() {
		if (playerTurn) {
			playerTurn = false;
			Handler h = new Handler();
			h.postDelayed(new Runnable() {
				@Override
				public void run() {
					bossAI();
				}
			}, 1000);
		}
		// Boss just finished turn
		else {
			playerTurn = true;
			Handler h = new Handler();
			h.postDelayed(new Runnable() {
				@Override
				public void run() {

				}
			}, 1000);
			// Prompt message, 'This character's turn'

		}
	}
	/**
	 * simple boss AI
	 */
	private void bossAI() {
		setBattleMessage("Boss attacks " + player.getName() + " for 10 dmg!");
		player.setHP(player.getHP() - 10);
		Animate(enemyAttack, enemyEffect, R.drawable.enemy_attack);
		update();
	}

	/**
	 * Return true if game is over
	 */
	private void checkGameConditions() {

		if (player.getHP() < 1) {
			makeGameOverMessages("GAME OVER!");
			battleEnd = builder.create();
			battleEnd.show();
			state = GameState.gameOver;
		}
		if (enemy.getHP() < 1) {
			makeGameOverMessages("VICTORY!");
			battleEnd = builder.create();
			battleEnd.show();
			state = GameState.gameOver;

		}

	}
/**
 * attack button listener
 */
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.attack_button:
				// player.attack(enemy);
				setBattleMessage(player.getName() + " attacks "
						+ enemy.getName() + " for 50 dmg!");
				/*
				 * playerEffect.setBackgroundResource(R.drawable.player_attack);
				 * playerAttack = (AnimationDrawable)
				 * playerEffect.getBackground(); playerAttack.start();
				 * waitForEffectAnimationDone(playerAttack, playerEffect);
				 */
				Animate(playerAttack, playerEffect, R.drawable.player_attack);
				enemy.setHP(enemy.getHP() - 50);
				update();
				break;
			case R.id.change_weapon:

				break;
			}
		}

	};
/**
 * finds view in layout
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
		playerStatusPoison = (ImageView) findViewById(R.id.battle_player_status_poison);
		enemyStatusPoison = (ImageView) findViewById(R.id.battle_enemy_status_poison);
		enemyStatusBlind = (ImageView) findViewById(R.id.battle_enemy_status_blind);
		playerStatusCurse = (ImageView) findViewById(R.id.battle_player_status_curse);
		playerDamage = (TextView) findViewById(R.id.battle_player_status_blind);
		enemyDamage = (TextView) findViewById(R.id.battle_enemy_status_curse);

	}
/**
 * fullscreen and landscape orientation
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
 * shows message pop up
 * @param msg
 */
	private void setBattleMessage(String msg) {
		battleAnnouncement.setText(msg);
		battleAnnouncement.setVisibility(View.VISIBLE);

	}
	/**
	 * Continue or quit dialogue
	 * @param msg
	 */
	public void makeGameOverMessages(String msg) {
		builder = new AlertDialog.Builder(this);

		builder.setMessage(msg + "Would you like to run the tutorial again?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				tutorialStep = 0;
				setBattleMessage("HI! Welcome to the battle tutorial.\n"
						+ "(Tap anywhere to continue)");

				// player.setHP(player.getMaxHP());

				// boss.setHP(boss.getMaxHP());

			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(TutorialBattleActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

}
