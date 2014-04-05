package com.cs429.todorpg.revised.utils;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	public SQLiteHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null,
				Constants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(Constants.CHARACTER_TABLE_CREATE);
		db.execSQL(Constants.REWARDS_TABLE_CREATE);
		db.execSQL(Constants.DAILIES_TABLE_CREATE);
		db.execSQL(Constants.VICES_TABLE_CREATE);
		db.execSQL(Constants.ITEMS_TABLE_CREATE);
		db.execSQL(Constants.TODO_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/*
	 * Gets all the items a character owns 0 =_id 1=name 2=stat 3=effect 4=pic
	 */
	public ArrayList<Item> getItems() {
		Cursor cursor = this.getReadableDatabase().query(Constants.TABLE_ITEMS,
				null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			cursor.moveToFirst();
			ArrayList<Item> items = new ArrayList<Item>();

			do {
				String name = cursor.getString(1);
				String stat = cursor.getString(2);
				int effect = cursor.getInt(3);
				String pic = cursor.getString(4);
				items.add(new Item(name, stat, effect, pic));
			} while (cursor.moveToNext());
			return items;
		}
	}

	/*
	 * Gets character info 0 = _id 1 = name 2 = gold
	 */
	public Character getCharacter() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_CHARACTER, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			cursor.moveToFirst();
			String name = cursor.getString(1);
			int gold = cursor.getInt(2);
			return new Character(name, gold);
		}
	}

	/*
	 * returns all the rewards a character has set 0 = _id; 1 =name; 2=cost;
	 */
	public ArrayList<Reward> getRewards() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_REWARDS, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Reward> rewards = new ArrayList<Reward>();
			cursor.moveToFirst();
			do {
				String name = cursor.getString(1);
				int cost = cursor.getInt(2);
				rewards.add(new Reward(name, cost));
				cursor.moveToNext();
			} while (cursor.moveToNext());
			return rewards;
		}
	}

	/*
	 * returns all the dailies the user has set 0 = _id 1 = name 2 =reward
	 */
	public ArrayList<Dailies> getDailies() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_DAILIES, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Dailies> dailies = new ArrayList<Dailies>();
			cursor.moveToFirst();
			do {
				String name = cursor.getString(1);
				int reward = cursor.getInt(2);
				dailies.add(new Dailies(name, reward));
			} while (cursor.moveToNext());
			return dailies;
		}
	}

	/*
	 * returns all todo items a character has set 0 = _id 1 = name 2 = reward
	 */
	public ArrayList<ToDoItem> getToDoList() {
		Cursor cursor = this.getReadableDatabase().query(Constants.Table_TODO,
				null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<ToDoItem> todos = new ArrayList<ToDoItem>();
			cursor.moveToFirst();
			do {
				String name = cursor.getString(1);
				int reward = cursor.getInt(2);
				todos.add(new ToDoItem(name, reward));
			} while (cursor.moveToNext());
			return todos;

		}
	}

	/*
	 * returns all the vices a character has 0 = _id 1= name 2= stat 3 =effect
	 */
	public ArrayList<Vice> getVices() {
		Cursor cursor = this.getReadableDatabase().query(Constants.TABLE_VICES,
				null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Vice> vices = new ArrayList<Vice>();
			do {
				String name = cursor.getString(1);
				String stat = cursor.getString(2);
				int effect = cursor.getInt(3);
				vices.add(new Vice(name, stat, effect));
			} while (cursor.moveToNext());
			return vices;
		}
	}

	/*
	 * inserts users character into database
	 * 
	 * @name - characters name
	 * 
	 * @gold - users gold
	 */
	public long addCharacter(String name, int gold) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("gold", gold);
		return this.getReadableDatabase().insert(Constants.TABLE_CHARACTER,
				null, values);
	}

	/*
	 * Inserts item into database
	 * 
	 * @item - The item given to character
	 */
	public long addItem(Item item) {
		String name = item.getName();
		String stat = item.getStat();
		String pic = item.getPic();
		int effect = item.getEffect();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("stat", stat);
		values.put("pic", pic);
		values.put("effect", effect);
		return this.getReadableDatabase().insert(Constants.TABLE_ITEMS, null,
				values);
	}

	/*
	 * inserts daily into database
	 * 
	 * @daily - the Dailies to add to database
	 */
	public long addDaily(Dailies daily) {
		String name = daily.getName();
		int reward = daily.getReward();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("reward", reward);
		return this.getReadableDatabase().insert(Constants.TABLE_DAILIES, null,
				values);
	}

	/*
	 * inserts vice into database
	 * 
	 * @vice - the Vice to insert
	 */
	public long addVices(Vice vice) {
		String name = vice.getName();
		String stat = vice.getStat();
		int effect = vice.getEffect();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("stat", stat);
		values.put("effect", effect);
		return this.getReadableDatabase().insert(Constants.TABLE_VICES, null,
				values);
	}
}