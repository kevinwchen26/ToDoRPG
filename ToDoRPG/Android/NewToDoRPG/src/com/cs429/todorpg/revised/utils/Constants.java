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
												// (_id,my_daily, extra, finished)
	static String TABLE_CHARACTER = "character"; // Table for Characters
													// : (_id,name,gold)
	static String TABLE_REWARDS = "rewards";// Table for Rewards
											// (_id,info,extra,cost)

	static String TABLE_VICES = "vices"; // Table for vices:
											// (_id,name,stat,effect)
	static String TABLE_TODO = "todo"; // Table for ToDo list items:
										// (_id,name,extra,finished)
	static String TABLE_ITEMS = "items"; // Table for
											// items;(_id,name,stat,effect,pic)
	static String TABLE_HABITS = "habits"; // Table for
									// habits;(_id,my_habit,extra,progress);

	/*
	 * Column Names
	 */
	private static String COLUMN_ID = "_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_REWARD_COST = "cost";

	/*
	 * Table Creation Queries;
	 */

	static final String CHARACTER_TABLE_CREATE = "create table character(_id integer primary key autoincrement, name text not null unique, gold int not null);";
	static final String REWARDS_TABLE_CREATE = "create table rewards(_id integer primary key autoincrement, info text not null unique, extra text not null, cost int not null)";
	static final String DAILIES_TABLE_CREATE = "create table dailies(_id integer primary key autoincrement, my_daily text not null unique, extra text not null, finished int not null)";
	static final String VICES_TABLE_CREATE = "create table vices(_id integer primary key autoincrement,name text not null unique, stat text not null, effect int not null)";
	static final String ITEMS_TABLE_CREATE = "create table items(_id integer primary key autoincrement,name text not null unique, stat text not null, effect int not null, pic text not null)";
	static final String TODO_TABLE_CREATE = "create table todo(_id integer primary key autoincrement, name text not null unique, extra text not null, finished int not null)";
	static final String HABITS_TABLE_CREATE = "create table habits(_id integer primary key autoincrement, my_habit text not null unique, extra text not null, progress int not null)";
}
