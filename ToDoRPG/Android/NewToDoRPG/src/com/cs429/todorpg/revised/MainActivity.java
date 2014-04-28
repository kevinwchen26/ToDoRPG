package com.cs429.todorpg.revised;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.EquipCost;
import com.cs429.todorpg.revised.itemsystem.Equipment;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class MainActivity extends BaseActivity {

	Intent intent;
	SQLiteHelper sql;
	ToDoCharacter character;
	TextView current_level, current_hp, current_exp, completed_quests,
			current_money, total_battles, char_name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sql = new SQLiteHelper(this);
		CreateCharacter();
		CreateLibrary();
		setHeader(R.id.header);
		sql = new SQLiteHelper(getBaseContext());
		FindViewById();
		GetCharacterInfo();
		fill_list();
	}

	private void CreateCharacter() {
		character = sql.getCharacter();
		if (character == null) {
			character = new ToDoCharacter("Hero", 0, 100, 1, 0, 100);
			sql.addCharacter(character);
			addInitialItems();
			initializeStats();
			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());

			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			String formattedDate = df.format(c.getTime());
			sql.addLogItem(new LogItem("Created Character", formattedDate));
		}
		UpdateStats();
		GameApplication app = (GameApplication) getApplication();
		app.avatar.inventory = sql.getInventory();
	}
	private void UpdateStats() {
		ArrayList<Daily> completed_daily = new ArrayList<Daily>();
		completed_daily = sql.getDailies(1);
		if(completed_daily != null) {
			int daily_count = completed_daily.size();
			Log.d("SIZE", daily_count+"");
			sql.updateStat(new Stat("Dailies Completed", daily_count));
		}
		
		ArrayList<ToDo> completed_todo = new ArrayList<ToDo>();
		completed_todo = sql.getToDos(1);
		if(completed_todo != null) {
			int todo_count = completed_todo.size();
			System.out.println(todo_count);
			sql.updateStat(new Stat("ToDos Completed", todo_count));
		}
		
	}
	private void initializeStats() {
		sql.addStat(new Stat("Battles Fought", 0));
		sql.addStat(new Stat("Battles Won", 0));
		sql.addStat(new Stat("Battles Lost", 0));
		sql.addStat(new Stat("Dailies Completed", 0));
		sql.addStat(new Stat("ToDos Completed", 0));
		sql.addStat(new Stat("Items Bought", 0));
		sql.addStat(new Stat("Gold Spent", 0));
	}

	private void CreateLibrary() {
		EquipCost tempequipcost = sql.getLibrary("Nirvana");
		if (tempequipcost == null) {

			ArrayList<NegativeEffects> negs = new ArrayList<NegativeEffects>();
			ArrayList<PositiveEffects> poss = new ArrayList<PositiveEffects>();

			// create weapons first
			Equipment tempequip = new Weapon("Broad Sword",
					R.drawable.weapon_warrior_1, 20, 10, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 30);

			tempequip = new Weapon("Mythril Sword",
					R.drawable.weapon_warrior_4, 40, 20, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 60);

			tempequip = new Weapon("Gigas Sword", R.drawable.weapon_warrior_5,
					100, 5, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 200);

			tempequip = new Weapon("Excalibur", R.drawable.weapon_warrior_6,
					150, 30, 10, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 500);

			tempequip = new Weapon("Dagger", R.drawable.weapon_rogue_0, 10, 10,
					75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 40);

			tempequip = new Weapon("Dancing Dagger", R.drawable.weapon_rogue_2,
					20, 20, 75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 70);

			tempequip = new Weapon("Nunchuck", R.drawable.weapon_rogue_4, 80,
					0, 75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 200);

			tempequip = new Weapon("ManEater", R.drawable.weapon_rogue_6, 150,
					30, 75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 500);

			negs.clear();
			negs.add(new NegativeEffects("Poison", 20));
			tempequip = new Weapon("Staff", R.drawable.weapon_wizard_0, 5, 0,
					0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 40);

			negs.clear();
			negs.add(new NegativeEffects("Blind", 75));
			tempequip = new Weapon("Darkness Staff",
					R.drawable.weapon_wizard_3, 5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 80);

			negs.clear();
			negs.add(new NegativeEffects("Curse", 75));
			tempequip = new Weapon("Curse Staff", R.drawable.weapon_wizard_2,
					5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 80);

			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			tempequip = new Weapon("Poison Staff", R.drawable.weapon_wizard_1,
					5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 80);

			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			negs.add(new NegativeEffects("Blind", 75));
			tempequip = new Weapon("Wizard's Staff",
					R.drawable.weapon_wizard_4, 5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 150);

			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			negs.add(new NegativeEffects("Blind", 75));
			negs.add(new NegativeEffects("Curse", 75));
			tempequip = new Weapon("Apocalypse", R.drawable.weapon_wizard_5,
					20, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 450);

			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			negs.add(new NegativeEffects("Blind", 75));
			negs.add(new NegativeEffects("Curse", 75));
			tempequip = new Weapon("Buster Sword", R.drawable.weapon_special_1,
					200, 50, 75, negs, 5, 5, 5, poss);
			sql.addLibrary(tempequip, 5000);

			// armor
			negs.clear();
			tempequip = new Armor("Iron Armor",
					R.drawable.broad_armor_healer_1, 0, 0, 0, negs, 10, 0, 0,
					poss);
			sql.addLibrary(tempequip, 200);

			tempequip = new Armor("Good Armor",
					R.drawable.broad_armor_healer_3, 0, 0, 0, negs, 20, 0, 0,
					poss);
			sql.addLibrary(tempequip, 300);

			tempequip = new Armor("Mythril Vest",
					R.drawable.broad_armor_healer_5, 0, 0, 0, negs, 50, 0, 0,
					poss);
			sql.addLibrary(tempequip, 500);

			// helmet
			tempequip = new Helmet("Hat", R.drawable.head_warrior_1, 0, 0, 0,
					negs, 5, 0, 30, poss);
			sql.addLibrary(tempequip, 200);

			tempequip = new Helmet("Helm", R.drawable.head_warrior_3, 0, 0, 0,
					negs, 5, 0, 50, poss);
			sql.addLibrary(tempequip, 300);

			tempequip = new Helmet("Warrior's Helm", R.drawable.head_warrior_5,
					0, 0, 0, negs, 5, 0, 80, poss);
			sql.addLibrary(tempequip, 500);

			// shield
			tempequip = new Shield("Standard Shield",
					R.drawable.shield_warrior_1, 0, 0, 0, negs, 5, 10, 0, poss);
			sql.addLibrary(tempequip, 200);

			tempequip = new Shield("Warrior Shield",
					R.drawable.shield_warrior_2, 0, 0, 0, negs, 5, 20, 0, poss);
			sql.addLibrary(tempequip, 300);

			poss.add(new PositiveEffects("nullBlind"));
			tempequip = new Shield("Clear Shield", R.drawable.shield_warrior_3,
					0, 0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 500);

			poss.clear();
			poss.add(new PositiveEffects("nullPoison"));
			tempequip = new Shield("Clean Shield", R.drawable.shield_warrior_4,
					0, 0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 500);

			poss.clear();
			poss.add(new PositiveEffects("nullCurse"));
			tempequip = new Shield("Heaven's Shield",
					R.drawable.shield_warrior_5, 0, 0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 500);

			poss.clear();
			poss.add(new PositiveEffects("nullPoison"));
			poss.add(new PositiveEffects("nullBlind"));
			poss.add(new PositiveEffects("nullCurse"));
			tempequip = new Shield("Nirvana", R.drawable.shield_special_1, 0,
					0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 2000);
		}

	}

	public void addInitialItems() {
		
		
		GameApplication app = (GameApplication) getApplication();
		
		EquipCost tsword = sql.getLibrary("Broad Sword");
		EquipCost tdagger = sql.getLibrary("Dagger");
		
		Weapon fsword = (Weapon) (tsword.getEquipment());
		Weapon fdagger = (Weapon) (tdagger.getEquipment());

		app.avatar.inventory.setArmor(null);
		app.avatar.inventory.setHelmet(null);
		app.avatar.inventory.setShield(null);
		app.avatar.inventory.setWeapon(fsword);

		app.avatar.inventory.addInventory(fdagger);
		
		sql.addInventory(app.avatar.inventory);
	}

	private void FindViewById() {
		current_level = (TextView) findViewById(R.id.current_level);
		current_hp = (TextView) findViewById(R.id.current_hp);
		current_exp = (TextView) findViewById(R.id.current_exp);
		current_money = (TextView) findViewById(R.id.current_money);

	}

	private void GetCharacterInfo() {
		character = sql.getCharacter();
		if (character == null) {
			return;
		}
		char_name = (TextView) findViewById(R.id.char_name);
		char_name.setText(character.getName());
		current_level.setText(Integer.toString(character.getLevel()));
		current_hp.setText(Integer.toString(character.getHP()));
		current_money.setText(Integer.toString(character.getGold()));
		DecimalFormat df = new DecimalFormat("#.00");
		double curr_exp = character.getCurrExp()
				/ (double) (character.getLevel() * 100) * 100;
		String result = df.format(curr_exp).concat("%");
		current_exp.setText(result);

	}

	public void setName(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter Hero Name");

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input
		// as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String name = input.getText().toString();
				sql.addCharacter(new ToDoCharacter(sql.getCharacter(), name));
				GetCharacterInfo();
				char_name.invalidate();
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

	public void fill_list() {
		ArrayList<LogItem> log = sql.getLog();

	}
}
