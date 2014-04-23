package com.cs429.todorpg.revised.utils;

public class Constants {
	/*
	 * DB INFO
	 */
	static String DATABASE_NAME = "TODO_DB"; // Name of DB
	static int DATABASE_VERSION = 2;

	/*
	 * Table Names
	 */

	static String TABLE_DAILIES = "dailies"; // Table for Daily Quests :
												// (_id,my_daily, extra, difficulty finished, weekid)
	static String TABLE_DAILIESWEEK = "dailiesweek"; // Table for Daily Week:
												// (_id,mon,tues,wed,thurs,fri,sat)
	static String TABLE_CHARACTER = "character"; // Table for Characters
													// : (_id,name,gold)
	static String TABLE_REWARDS = "rewards";// Table for Rewards
											// (_id,info,extra,cost)
	static String TABLE_TODO = "todo"; // Table for ToDo list items:
										// (_id,name,extra,due_month,due_date,due_hour,due_min,difficulty,finished)
	static String TABLE_EQUIPARMOR = "equiparmor"; // Table for
									// equipped items;(_id,name,resid)
	static String TABLE_EQUIPWEAPON = "equipweapon"; // Table for
	// equipped items;(_id,name,resid)
	static String TABLE_EQUIPHELMET = "equiphelmet"; // Table for
	// equipped items;(_id,name,resid)
	static String TABLE_EQUIPSHIELD = "equipshield"; // Table for
	// equipped items;(_id,name,resid)
	static String TABLE_INVENTORY = "inventory"; // Table for
											// items;(_id,name,resid)
	static String TABLE_HABITS = "habits"; // Table for
									// habits;(_id,title,extra,characteristic,difficulty,progress);
	/*
	 * Column Names
	 */
	private static String COLUMN_ID = "_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_REWARD_COST = "cost";

	/*
	 * Table Creation Queries;
	 */

	static final String CHARACTER_TABLE_CREATE = "create table character(_id integer primary key autoincrement, name text not null unique, gold int not null, HP int not null, level int not null, currExp int not null, nextExp int not null);";
	static final String REWARDS_TABLE_CREATE = "create table rewards(_id integer primary key autoincrement, info text not null unique, extra text not null, cost int not null)";
	static final String DAILIES_TABLE_CREATE = "create table dailies(_id integer primary key autoincrement, my_daily text not null unique, extra text not null, difficulty int not null, finished int not null, weekid int not null)";
	static final String DAILIESWEEK_TABLE_CREATE = "create table dailiesweek(_id integer primary key autoincrement, mon int not null, tues int not null, wed int not null, thurs int not null, fri int not null, sat int not null, sun int not null)";
	static final String EQUIPARMOR_TABLE_CREATE = "create table equiparmor(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String EQUIPWEAPON_TABLE_CREATE = "create table equipweapon(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String EQUIPHELMET_TABLE_CREATE = "create table equiphelmet(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String EQUIPSHIELD_TABLE_CREATE = "create table equipshield(_id integer primary key autoincrement, name text, resid INTEGER, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";	
	static final String INVENTORY_TABLE_CREATE = "create table inventory(_id integer primary key autoincrement, type int not null, name text not null, resid int not null, damage INTEGER, critical INTEGER, multihit INTEGER, negEffects text not null, damagereduction INTEGER, evasion INTEGER, accuracy INTEGER, posEffects text not null)";
	static final String TODO_TABLE_CREATE = "create table todo(_id integer primary key autoincrement, my_todo text not null unique, extra text not null, due_month int not null, due_date int not null, due_hour int not null, due_min int not null, difficulty int not null, finished int not null)";
	static final String HABITS_TABLE_CREATE = "create table habits(_id integer primary key autoincrement, title text not null unique, extra text not null, characteristic text not null, difficulty int not null, progress int not null)";
}
