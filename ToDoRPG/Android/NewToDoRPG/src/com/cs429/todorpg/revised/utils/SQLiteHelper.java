package com.cs429.todorpg.revised.utils;

import java.util.ArrayList;

import com.cs429.todorpg.revised.model.Reward;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.model.ToDo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Leon Chen
 *
 */
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
		db.execSQL(Constants.HABITS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
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
	 * inserts users character into database
	 * 
	 * @name - characters name
	 * 
	 * @gold - users gold
	 */
	public long addCharacter(Character character) {
		String name = character.getName();
		int gold = character.getGold();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("gold", gold);
		return this.getReadableDatabase().insert(Constants.TABLE_CHARACTER,
				null, values);
	}
	
	/**
	 * getToDos() - returns a list of ToDos for the character
	 * @return Arraylist of all ToDos
	 */
	public ArrayList<ToDo> getToDos() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_TODO, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<ToDo> todos = new ArrayList<ToDo>();
			cursor.moveToFirst();
			do {
				int primary_key = cursor.getInt(0);
				String my_todo = cursor.getString(1);
				String extra = cursor.getString(2);
				int finished = cursor.getInt(3);
				ToDo temp = new ToDo(my_todo, extra, primary_key);
				if (finished == 1)
					temp.setFinish();
				todos.add(temp);
			} while (cursor.moveToNext());
			return todos;
		}
	}

	/**
	 * addToDos() - adds a todo for the character
	 * @param todo
	 * @return the int for DB position of the todo
	 */
	public int addToDo(ToDo todo) {
		String my_todo = todo.getToDo();
		String extra = todo.getExtra();
		boolean bfinished = todo.getStatus();
		int finished;
		if(bfinished)
			finished = 1;
		else
			finished = 0;
		ContentValues values = new ContentValues();
		values.put("my_todo", my_todo);
		values.put("extra", extra);
		values.put("finished", finished);
		return (int) (this.getReadableDatabase().insert(Constants.TABLE_TODO, null,
				values));
	}
	
	/**
	 * deleteToDo() - deletes the ToDo from the database
	 * @param todo
	 * @return true if todo has been successfully deleted, else false
	 */
	public boolean deleteToDo(ToDo todo) {
		return this.getReadableDatabase().delete(Constants.TABLE_TODO, 
				"_id='" + todo.getKey() + "'", null) > 0;
	}
	
	/**
	 * updateToDo() - updates the ToDo in the database
	 * @param todo
	 * @return true if successfully updated, false otherwise
	 */
	public boolean updateToDo(ToDo todo) {
		ContentValues values = new ContentValues();
		values.put("my_todo", todo.getToDo());
		values.put("extra", todo.getExtra());
		boolean bfinished = todo.getStatus();
		int finished;
		if (bfinished)
			finished = 1;
		else 
			finished = 0;
		values.put("finished", finished);
		return this.getReadableDatabase().update(Constants.TABLE_TODO, values, "_id='" + todo.getKey() + "'", null) > 0;
	}
	
	
	/**
	 * getDailies() - returns a list of dailies for the character
	 * @return Arraylist of all dailies
	 */
	public ArrayList<Daily> getDailies() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_DAILIES, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Daily> dailies = new ArrayList<Daily>();
			cursor.moveToFirst();
			do {
				int primary_key = cursor.getInt(0);
				String my_daily = cursor.getString(1);
				String extra = cursor.getString(2);
				int finished = cursor.getInt(3);
				Daily temp = new Daily(my_daily, extra, primary_key);
				if (finished == 1)
					temp.toggleFinish();
				dailies.add(temp);
			} while (cursor.moveToNext());
			return dailies;
		}
	}

	/**
	 * addDailies() - adds a daily for the character
	 * @param daily
	 * @return the int for DB position of the daily
	 */
	public int addDaily(Daily daily) {
		String my_daily = daily.getDaily();
		String extra = daily.getExtra();
		boolean bfinished = daily.getBooleanStatus();
		int finished;
		if(bfinished)
			finished = 1;
		else
			finished = 0;
		ContentValues values = new ContentValues();
		values.put("my_daily", my_daily);
		values.put("extra", extra);
		values.put("finished", finished);
		return (int) (this.getReadableDatabase().insert(Constants.TABLE_DAILIES, null,
				values));
	}
	
	/**
	 * deleteDaily() - deletes the Daily from the database
	 * @param daily
	 * @return true if daily has been successfully deleted, else false
	 */
	public boolean deleteDaily(Daily daily) {
		return this.getReadableDatabase().delete(Constants.TABLE_DAILIES, 
				"_id='" + daily.getKey() + "'", null) > 0;
	}
	
	/**
	 * updateHabit() - updates the Daily in the database
	 * @param daily
	 * @return true if successfully updated, false otherwise
	 */
	public boolean updateDaily(Daily daily) {
		ContentValues values = new ContentValues();
		values.put("my_daily", daily.getDaily());
		values.put("extra", daily.getExtra());
		boolean bfinished = daily.getBooleanStatus();
		int finished;
		if (bfinished)
			finished = 1;
		else 
			finished = 0;
		values.put("finished", finished);
		return this.getReadableDatabase().update(Constants.TABLE_DAILIES, values, "_id='" + daily.getKey() + "'", null) > 0;
	}
	

	/**
	 * getHabits() - returns a list of habits for the character
	 * @return Arraylist of all habits
	 */
	public ArrayList<Habit> getHabits() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_HABITS, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Habit> habits = new ArrayList<Habit>();
			cursor.moveToFirst();
			do {
				int primary_key = cursor.getInt(0);
				String my_habit = cursor.getString(1);
				String extra = cursor.getString(2);
				int progress = cursor.getInt(3);
				Habit temp = new Habit(my_habit, extra, primary_key);
				temp.setProgress(progress);
				habits.add(temp);
			} while (cursor.moveToNext());
			return habits;
		}
	}

	/**
	 * addHabit() - adds a habit for the character
	 * @param habit
	 * @return the int for DB position of the habit
	 */
	public int addHabit(Habit habit) {
		String my_habit = habit.getHabit();
		String extra = habit.getExtra();
		int progress = habit.getProgress();
		ContentValues values = new ContentValues();
		values.put("my_habit", my_habit);
		values.put("extra", extra);
		values.put("progress", progress);
		return (int) (this.getReadableDatabase().insert(Constants.TABLE_HABITS, null,
				values));
	}
	
	/**
	 * deleteHabit() - deletes the Habit from the database
	 * @param habit
	 * @return true if habit has been successfully deleted, else false
	 */
	public boolean deleteHabit(Habit habit) {
		return this.getReadableDatabase().delete(Constants.TABLE_HABITS, 
				"_id='" + habit.getKey() + "'", null) > 0;
	}
	
	/**
	 * updateHabit() - updates the Habit in the database
	 * @param habit
	 * @return true if successfully updated, false otherwise
	 */
	public boolean updateHabit(Habit habit) {
		ContentValues values = new ContentValues();
		values.put("my_habit", habit.getHabit());
		values.put("extra", habit.getExtra());
		values.put("progress", habit.getProgress());
		return this.getReadableDatabase().update(Constants.TABLE_HABITS, values, "_id='" + habit.getKey() + "'", null) > 0;
	}
	
	/**
	 * getRewards() - returns a list of all rewards for the character
	 * @return Arraylist of all rewards for character
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
				int primary_key = cursor.getInt(0);
				String info = cursor.getString(1);
				String extra = cursor.getString(2);
				int cost = cursor.getInt(3);
				rewards.add(new Reward(primary_key, info, extra, cost));
			} while (cursor.moveToNext());
			return rewards;
		}
	}

	/**
	 * addReward() - adds a reward to the database
	 * @param reward
	 * @return int position of reward in the DB
	 */
	public int addReward(Reward reward) {
		String info = reward.getInfo();
		String extra = reward.getExtra();
		int cost = reward.getCost();
		ContentValues values = new ContentValues();
		values.put("info", info);
		values.put("extra", extra);
		values.put("cost", cost);
		return (int) (this.getReadableDatabase().insert(Constants.TABLE_REWARDS, null,
				values));
	}
	
	/**
	 * deleteReward() - deletes the specific reward from the DB
	 * @param reward
	 * @return true if successful, else otherwise
	 */
	public boolean deleteReward(Reward reward) {
		return this.getReadableDatabase().delete(Constants.TABLE_REWARDS, 
				"_id='" + reward.getPrimary_key() + "'", null) > 0;
	}
	
	/**
	 * updateReward() - updates the reward in the DB
	 * @param reward
	 * @return true if successful, else otherwise
	 */
	public boolean updateReward(Reward reward) {
		ContentValues values = new ContentValues();
		values.put("info", reward.getInfo());
		values.put("extra", reward.getExtra());
		values.put("cost", reward.getCost());
		return this.getReadableDatabase().update(Constants.TABLE_REWARDS, values, "_id='" + reward.getPrimary_key() + "'", null) > 0;
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
	 * returns all the vices a character has 0 = _id 1= name 2= stat 3 =effect
	 */
	public ArrayList<Vice> getVices() {
		Cursor cursor = this.getReadableDatabase().query(Constants.TABLE_VICES,
				null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Vice> vices = new ArrayList<Vice>();
			cursor.moveToFirst();
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
