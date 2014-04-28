
package com.cs429.todorpg.revised;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
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

import com.cs429.todorpg.revised.MainActivity;

public class BattleActivity extends BaseActivity {
	enum GameState{ ready, gameOver }
	GameState state = GameState.ready;
	int width, height, playerMaxHP, enemyMaxHP;
	RelativeLayout battleScreen, battleNavigator, enemyInfo, actionMenu, playerInfo, enemySide, playerSide;
	TextView enemyName, enemyHP, playerName, playerHP, battleAnnouncement;
	ImageView enemyImage, playerImage, playerEffect, enemyEffect, playerStatusPoison, playerStatusBlind, playerStatusCurse, enemyStatusPoison, enemyStatusBlind, enemyStatusCurse;
	AnimationDrawable playerWalk, playerAttack, enemyAttack;
	Button attack, change_weapon;
	Intent intent;
	ArrayList<Character> party;
	ToDoCharacter player, enemy;
	Avatar playerAvatar, enemyAvatar;
	Inventory inventory;
	SQLiteHelper sql = new SQLiteHelper(this);
	AlertDialog.Builder builder;
	AlertDialog battleEnd;
	
	private BTMessageHandler mHandler;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpLayout();
		setContentView(R.layout.battle_activity);
		FindViewById();
		
		
		setUpActivity();
		new ShareInfoTask().execute();
		
		mHandler = BTMessageHandler.getInstance(BattleActivity.this);
		mHandler.changeContext(BattleActivity.this);
	}

	/*
	 * CALL BACKS FOR BT HANDLER
	 */
	
	
	
	
	/*
	 * SET
	 */
	public void jesus() {
		Toast.makeText(BattleActivity.this, "jesus", Toast.LENGTH_SHORT).show();
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
		
	}
	
	public void battleToast(String s){
		Toast.makeText(BattleActivity.this, s, Toast.LENGTH_SHORT).show();
	}

	private void setUpActivity() {
	    setPlayers();
		setUpBattleScreen();
		setUpBattleNavigator();
		setUpBattleMenu();
		setUpBattleInfo();
	    setUpStatusEffects();
	    applyStatusEffects();
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
	
	private void setUpStatusEffects(){

	    RelativeLayout.LayoutParams playerStatusPoisonParams = new RelativeLayout.LayoutParams(playerStatusPoison.getLayoutParams());
	    playerStatusPoisonParams.addRule(RelativeLayout.ALIGN_LEFT, playerImage.getId());
	    playerStatusPoisonParams.addRule(RelativeLayout.ABOVE, playerImage.getId());
	    playerStatusPoison.setLayoutParams(playerStatusPoisonParams);
	    
	    
	    RelativeLayout.LayoutParams playerStatusBlindParams = new RelativeLayout.LayoutParams(playerStatusBlind.getLayoutParams());
	    playerStatusBlindParams.addRule(RelativeLayout.LEFT_OF, playerStatusPoison.getId());
	    playerStatusBlind.setLayoutParams(playerStatusBlindParams);
	    
	    RelativeLayout.LayoutParams playerStatusCurseParams = new RelativeLayout.LayoutParams(playerStatusCurse.getLayoutParams());
	    playerStatusCurseParams.addRule(RelativeLayout.RIGHT_OF, playerStatusPoison.getId());
	    playerStatusCurse.setLayoutParams(playerStatusCurseParams);
	    
	    RelativeLayout.LayoutParams enemyStatusPoisonParams = new RelativeLayout.LayoutParams(enemyStatusPoison.getLayoutParams());
	    enemyStatusPoisonParams.addRule(RelativeLayout.ALIGN_LEFT, enemyImage.getId());
	    enemyStatusPoisonParams.addRule(RelativeLayout.ABOVE, enemyImage.getId());
	    enemyStatusPoison.setLayoutParams(enemyStatusPoisonParams);
	    
	    
	    RelativeLayout.LayoutParams enemyStatusBlindParams = new RelativeLayout.LayoutParams(enemyStatusBlind.getLayoutParams());
	    enemyStatusBlindParams.addRule(RelativeLayout.LEFT_OF, enemyStatusPoison.getId());
	    enemyStatusBlind.setLayoutParams(enemyStatusBlindParams);
	    
	    RelativeLayout.LayoutParams enemyStatusCurseParams = new RelativeLayout.LayoutParams(enemyStatusCurse.getLayoutParams());
	    enemyStatusCurseParams.addRule(RelativeLayout.RIGHT_OF, enemyStatusPoison.getId());
	    enemyStatusCurse.setLayoutParams(enemyStatusCurseParams);   
	}

	private void applyStatusEffects() {
		enemyStatusCurse.setBackgroundResource(R.drawable.curse);
		enemyStatusPoison.setBackgroundResource(R.drawable.poison);
		enemyStatusBlind.setBackgroundResource(R.drawable.blind);
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
		if (enemy.getHP() < 0) {
			enemy.setHP(0);
		}
		if (player.getHP() < 0) {
			player.setHP(0);
		}
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
		int victory=0;
		if(player.getHP() < 1) {
			makeGameOverMessages("GAME OVER!");
			battleEnd = builder.create();
			battleEnd.show();	
			state = GameState.gameOver;
			victory=1;
		}
		if(enemy.getHP() < 1) {
			makeGameOverMessages("VICTORY!");
			battleEnd = builder.create();
			battleEnd.show();
			state = GameState.gameOver;
			victory=0;
		}
		updateStatsLog(player,enemy,victory);

	}

	/*
	 * ATTACK BUTTON
	 */
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.attack_button:
				
				if(mHandler.getMyTurn() && mHandler.isReadyToStart()){
					// Calculate effects of this attack
					AttackResult attackResult = BattleLogic.calculateAttackResult(playerAvatar.getInventory(), enemyAvatar.getInventory());
					
					// First check if the attack registers
					if (attackResult.isHit) {
						// Loop through the hits
						for (boolean b : attackResult.critChanceList) {
							int finalDamage = attackResult.damagePerHit;
							if (b) { // Is a critical Hit
								finalDamage *= BattleLogic.CRITICAL_DAMAGE_MODIFIER;
								setBattleMessage("You have critically hit " + enemy.getName() + " causing " + finalDamage + " damage!" );
							}
							else {
								setBattleMessage(" You attacked " + enemy.getName() + " causing " + finalDamage + " damage!" );
							}
							
							Animate(playerAttack, playerEffect, R.drawable.player_attack);
							enemy.setHP(enemy.getHP() - finalDamage);
							update();
						}
					}
					else {
						setBattleMessage(" Your attack missed!" );
					}
					
					Log.d("BATTLE_ACTIVITY", "Attack");
					mHandler.obtainMessage(BTMessageHandler.MESSAGE_BATTLE_SEND, (new BtPackage(BtPackage.ATTACK_PACKAGE, attackResult))).sendToTarget();
					
				}
				else{
					AlertDialog.Builder ab = null;
					ab = new AlertDialog.Builder(BattleActivity.this);
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
		playerStatusPoison = (ImageView)findViewById(R.id.battle_player_status_poison);
		enemyStatusPoison = (ImageView)findViewById(R.id.battle_enemy_status_poison);
		playerStatusBlind = (ImageView)findViewById(R.id.battle_player_status_blind);
		enemyStatusBlind = (ImageView)findViewById(R.id.battle_enemy_status_blind);
		playerStatusCurse = (ImageView)findViewById(R.id.battle_player_status_curse);
		enemyStatusCurse = (ImageView)findViewById(R.id.battle_enemy_status_curse);
		
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

		builder.setMessage(msg);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				intent = new Intent(BattleActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				//player.setHP(player.getMaxHP());
				//boss.setHP(boss.getMaxHP());
			}
		});
/*
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				intent = new Intent(BattleActivity.this, MainActivity.class);
				startActivity(intent);
				finish();

			}
		});
*/
	}
	private class ShareInfoTask extends AsyncTask<Void, Void, Void> {

		ProgressDialog progress;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = ProgressDialog.show(BattleActivity.this, "Initializing...", "Please wait while we load the battle", true);
			
			
			
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progress.dismiss();
		}

		@Override
		protected Void doInBackground(Void... params) {
			
			/*
			 *	GENERATE NEW AVATAR
			 * 
			 *  Need to include Inventory and ToDoCharacter Object in Avatar
			 */
			Avatar av = new Avatar();
			Inventory in = new Inventory();
		    ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
			ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
		    in.setArmor(new Armor("Leather Armor", R.drawable.broad_armor_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
			in.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_1, 1, 1, 1, negs, 1, 1, 10, poss));
			in.setShield(new Shield("Leather Shield", R.drawable.shield_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
			in.setWeapon(new Weapon("Iron Sword", R.drawable.weapon_warrior_1, 10, 1, 1, negs, 1, 1, 70, poss));
			
			in.addInventory(new Weapon("Rogue Weapon 0", R.drawable.weapon_rogue_0, 1, 1, 1, negs, 1, 1, 1, poss));
			in.addInventory(new Weapon("Rogue Weapon 1", R.drawable.weapon_rogue_1, 1, 1, 1, negs, 1, 1, 1, poss));
			in.addInventory(new Weapon("Rogue Weapon 2", R.drawable.weapon_rogue_2, 1, 1, 1, negs, 1, 1, 1, poss));
			av.setInventory(in);
			playerImage.setImageBitmap(av.getClearBitmap());
			in.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_2, 1, 1, 1, negs, 1, 1, 10, poss));
			
			ToDoCharacter myCharacter = new ToDoCharacter("Alice", 90, 120, 5, 0, 50);
			setPlayerInfo(myCharacter);
			//ToDoCharacter enemeyCharacter = new ToDoCharacter("Alonso", 90, 120, 5, 0, 50);
			av.setToDoCharacter(myCharacter);
			mHandler.obtainMessage(BTMessageHandler.MESSAGE_SHARE_CHAR_INFO, new BtPackage(BtPackage.SHARE_INFO_PACKAGE, (av))).sendToTarget();
			playerAvatar = av;

			return null;
		}
	 }
	
	/*
	 * FUNCTIONS ADDED BY PAUL
	 * These functions are called by BTMessageHandler.java. therefore must be public
	 */
	
	public void defendAttack(AttackResult attackResult) {
		// Loop through the hits
		if (attackResult.isHit) {
			for (boolean b : attackResult.critChanceList) {
				int finalDamage = attackResult.damagePerHit;
				if (b) { // Is a critical Hit
					finalDamage *= BattleLogic.CRITICAL_DAMAGE_MODIFIER;
					setBattleMessage(enemy.getName() + " critically attacked you causing " + finalDamage + " damage!" );
				}
				else {
					setBattleMessage(enemy.getName() + " attacked you causing " + finalDamage + " damage!" );
				}
				
				Animate(enemyAttack, enemyEffect, R.drawable.enemy_attack);
				
				player.setHP(player.getHP() - finalDamage);
				update();	
			}
		}
		else {
			setBattleMessage(enemy.getName() + " attacked you but missed!" );
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
	}
	
	public void setPlayerInfo(ToDoCharacter c) {
		player = c;
		playerName.setText(c.getName());
		playerHP.setText(Integer.toString(c.getHP()));
	}
	
	public byte [] getByteRep(Object o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out;
		byte [] msgBytes = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
			msgBytes = bos.toByteArray();
			out.close();
			bos.close(); 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msgBytes;
	}
	

	private void updateStatsLog(ToDoCharacter player, ToDoCharacter enemy, int victory) {
		SQLiteHelper db = new SQLiteHelper(this);
		ArrayList<Stat>stats = db.getStats();
		for(Stat stat: stats){
			if(stat.getName().equals("Battles Fought")){
				stat.setCount(stat.getCount()+1);
				db.updateStat(stat);
			}
		}
		if(victory ==1){
			for(Stat stat: stats){
				if(stat.getName().equals("Battles Won")){
					stat.setCount(stat.getCount()+1);
					db.updateStat(stat);
				}
				Calendar c = Calendar.getInstance();
				System.out.println("Current time => " + c.getTime());

				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String formattedDate = df.format(c.getTime());
				String text = "Won battle against "+enemy.getName();
				db.addLogItem(new LogItem(text,formattedDate));
			}
		}
		else{
			for(Stat stat: stats){
				if(stat.getName().equals("Battles Lost")){
					stat.setCount(stat.getCount()+1);
					db.updateStat(stat);
				}
				Calendar c = Calendar.getInstance();
				System.out.println("Current time => " + c.getTime());

				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String formattedDate = df.format(c.getTime());
				String text = "Lost battle against "+enemy.getName();
				db.addLogItem(new LogItem(text,formattedDate));
			}
		}

		
	}
	

}
