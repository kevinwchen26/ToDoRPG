package com.cs429.todorpg.revised.utils;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "TODO_DB"; // Name of DB
	private static String TABLE_DAILIES = "dailies"; // Table for Daily Quests
	private static String TABLE_VICES = "vices"; // Table for vices
	private static String Table_TODO = "todo"; // Table for ToDo list items
	private static String TABLE_ITEMS = "items"; // Table for items;
	private static String TABLE_CHARACTER = "character"; // Table for Characters
	private static String TABLE_REWARDS = "rewards";// Table for Rewards
	private static int DATABASE_VERSION;

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public ArrayList<Item> getItems() {
		return null;
	}

	public Character getCharacter() {
		return null;
	}

	public ArrayList<Reward> getRewards() {
		return null;
	}

	public ArrayList<Dailies> getDailies() {
		return null;
	}

	public ArrayList<ToDoItem> getToDoList() {
		return null;
	}
}
