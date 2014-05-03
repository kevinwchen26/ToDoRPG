package com.cs429.todorpg.revised.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.ToDoCharacter;

/**
 * Constants, used for the Database
 * 
 * @author kchen26, lchen59, edited by hlim10
 * 
 */
public class Constants {
	/*
	 * DB INFO
	 */
	private static String change;
	static String DATABASE_NAME = "TODO_DB"; // Name of DB
	static int DATABASE_VERSION = 2; // DB version

	/*
	 * Table Names
	 */
	static String TABLE_DAILIES = "dailies";
	static String TABLE_DAILIESWEEK = "dailiesweek";
	static String TABLE_CHARACTER = "character";
	static String TABLE_REWARDS = "rewards";
	static String TABLE_TODO = "todo";
	static String TABLE_EQUIPARMOR = "equiparmor";
	static String TABLE_EQUIPWEAPON = "equipweapon";
	static String TABLE_EQUIPSECONDARY = "equipsecondary";
	static String TABLE_EQUIPHELMET = "equiphelmet";
	static String TABLE_EQUIPSHIELD = "equipshield";
	static String TABLE_INVENTORY = "inventory";
	static String TABLE_HABITS = "habits";
	static String TABLE_LOG = "log";
	static String TABLE_STAT = "stat";
	static String TABLE_LIBRARY = "library";

	/*
	 * Table Creation Queries;
	 */
	static final String CHARACTER_TABLE_CREATE = "create table character(_id integer primary key autoincrement, name text not null unique, gold int not null, HP int not null, level int not null, currExp int not null, nextExp int not null);";
	static final String REWARDS_TABLE_CREATE = "create table rewards(_id integer primary key autoincrement, info text not null unique, extra text not null, cost int not null)";
	static final String DAILIES_TABLE_CREATE = "create table dailies(_id integer primary key autoincrement, my_daily text not null unique, extra text not null, difficulty int not null, finished int not null, weekid int not null)";
	static final String DAILIESWEEK_TABLE_CREATE = "create table dailiesweek(_id integer primary key autoincrement, mon int not null, tues int not null, wed int not null, thurs int not null, fri int not null, sat int not null, sun int not null)";
	static final String EQUIPARMOR_TABLE_CREATE = "create table equiparmor(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String EQUIPWEAPON_TABLE_CREATE = "create table equipweapon(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String EQUIPSECONDARY_TABLE_CREATE = "create table equipsecondary(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String EQUIPHELMET_TABLE_CREATE = "create table equiphelmet(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String EQUIPSHIELD_TABLE_CREATE = "create table equipshield(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String INVENTORY_TABLE_CREATE = "create table inventory(_id integer primary key autoincrement, type int not null, name text not null, resid int not null, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String LIBRARY_TABLE_CREATE = "create table library(_id integer primary key autoincrement, type int not null, name text not null unique, resid int not null, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null, cost int not null)";
	static final String TODO_TABLE_CREATE = "create table todo(_id integer primary key autoincrement, my_todo text not null unique, extra text not null, due_month int not null, due_date int not null, due_hour int not null, due_min int not null, difficulty int not null, finished int not null)";
	static final String HABITS_TABLE_CREATE = "create table habits(_id integer primary key autoincrement, title text not null unique, extra text not null, characteristic text not null, difficulty int not null, progress int not null)";
	static final String LOG_TABLE_CREATE = "create table log(_id integer primary key autoincrement, content text not null unique, date text not null)";
	static final String STAT_TABLE_CREATE = "create table stat(_id integer primary key autoincrement, name text not null unique, count int not null)";

	/**
	 * updates the character status of in the Database
	 * 
	 * @param db
	 * @param difficulty
	 * @param context
	 * @param sign
	 */
	public static void UpdateCharacterStatus(SQLiteHelper db, int difficulty,
			Context context, int sign) {
		ToDoCharacter character = db.getCharacter();
		switch (difficulty) {
		case 0:
			if (sign == 1) {
				change = "Earned [EXP: 10], [GOLD: 10]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() + 10, character.getHP(),
						character.getLevel(), character.getCurrExp() + 10,
						character.getNextExp() - 10);
			} else {
				change = "Lost [EXP: 10], [GOLD: 10]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() - 10, character.getHP(),
						character.getLevel(), character.getCurrExp() - 10,
						character.getNextExp() + 10);
			}
			break;
		case 1:
			if (sign == 1) {
				change = "Earned [EXP: 20], [GOLD: 20]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() + 20, character.getHP(),
						character.getLevel(), character.getCurrExp() + 20,
						character.getNextExp() - 20);
			} else {
				change = "Lost [EXP: 20], [GOLD: 20]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() - 20, character.getHP(),
						character.getLevel(), character.getCurrExp() - 20,
						character.getNextExp() + 20);
			}
			break;
		case 2:
			if (sign == 1) {
				change = "Earned [EXP: 30], [GOLD: 30]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() + 30, character.getHP(),
						character.getLevel(), character.getCurrExp() + 30,
						character.getNextExp() - 30);
			} else {
				change = "Lost [EXP: 30], [GOLD: 30]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() - 30, character.getHP(),
						character.getLevel(), character.getCurrExp() - 30,
						character.getNextExp() + 30);
			}
			break;
		}
		if (character.getCurrExp() >= character.getLevel() * 100) {
			change = "LEVEL UP";
			character.setLevel(character.getLevel() + 1);
			character.setCurrExp(0);
			character.setHP(character.getHP() + 20);
		} else if (character.getLevel() == 1 && character.getCurrExp() < 0) {
			character.setCurrExp(0);
		} else if (character.getCurrExp() <= 0 && character.getLevel() > 1) {
			change = "LEVEL DOWN";
			character.setLevel(character.getLevel() - 1);
			character.setHP(character.getHP() - 20);
			character.setCurrExp(character.getLevel() * 100);
			if (character.getHP() < 100)
				character.setHP(100);

		}
		if (character.getGold() < 0)
			character.setGold(0);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toast, null);
		Constants.ToastMessage(context, view, change);
		db.updateCharacter(character);
	}

	/**
	 * Creates a message for the Toast
	 * 
	 * @param context
	 * @param view
	 * @param change
	 */
	public static void ToastMessage(Context context, View view, String change) {
		TextView text = (TextView) view.findViewById(R.id.textView2);
		text.setText(change);
		Toast toast = new Toast(context);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}
}
