package com.cs429.todorpg.revised.utils;
/**
 * 
 * @author kchen26, hlim10
 *
 */
public class Constants {
	/*
	 * DB INFO
	 */
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
}
