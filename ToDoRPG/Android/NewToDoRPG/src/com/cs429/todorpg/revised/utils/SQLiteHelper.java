package com.cs429.todorpg.revised.utils;

import java.util.ArrayList;

import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.model.Reward;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.itemsystem.*;

import android.content.ContentValues;
import android.content.Context;
import android.content.ClipData.Item;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
		db.execSQL(Constants.DAILIESWEEK_TABLE_CREATE);
		db.execSQL(Constants.VICES_TABLE_CREATE);
		db.execSQL(Constants.EQUIP_TABLE_CREATE);
		db.execSQL(Constants.INVENTORY_TABLE_CREATE);
		db.execSQL(Constants.TODO_TABLE_CREATE);
		db.execSQL(Constants.HABITS_TABLE_CREATE);
		db.execSQL(Constants.STAT_TABLE_CREATE);
		db.execSQL(Constants.LOG_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

	/*
	 * Gets character info 0 = _id 1 = name 2 = gold
	 */
	public ToDoCharacter getCharacter() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_CHARACTER, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			cursor.moveToFirst();
			String name = cursor.getString(1);
			int gold = cursor.getInt(2);
			int HP = cursor.getInt(3);
			int level = cursor.getInt(4);
			int currexp = cursor.getInt(5);
			int nextexp = cursor.getInt(6);
			ToDoCharacter tempchar = new ToDoCharacter(name, gold, HP, level, currexp, nextexp);
			tempchar.setHP(HP);
			tempchar.setLevel(level);
			tempchar.setCurrExp(currexp);
			tempchar.setNextExp(nextexp);
			return tempchar;
		}
	}

	/*
	 * inserts users character into database
	 * 
	 * @name - characters name
	 * 
	 * @gold - users gold
	 */
	public long addCharacter(ToDoCharacter character) {
		this.deleteCharacter();
		String name = character.getName();
		int gold = character.getGold();
		int HP = character.getHP();
		int level = character.getLevel();
		int currExp = character.getCurrExp();
		int nextExp = character.getNextExp();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("gold", gold);
		values.put("HP", HP);
		values.put("level", level);
		values.put("currExp", currExp);
		values.put("nextExp", nextExp);
		return this.getReadableDatabase().insert(Constants.TABLE_CHARACTER,
				null, values);
	}

	/*
	 * inserts users character into database
	 * 
	 * @name - characters name
	 * 
	 * @gold - users gold
	 */
	private void deleteCharacter() {
		this.getReadableDatabase().delete(Constants.TABLE_CHARACTER, null, null);
	}

	/*
	 * inserts users character into database
	 * 
	 * @name - characters name
	 * 
	 * @gold - users gold
	 */
	public void updateCharacter(ToDoCharacter ch) {
		this.addCharacter(ch);
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
				int due_month = cursor.getInt(3);
				int due_date = cursor.getInt(4);
				int due_hour = cursor.getInt(5);
				int due_min = cursor.getInt(6);
				int difficulty = cursor.getInt(7);
				int finished = cursor.getInt(8);
				ToDo temp = new ToDo(my_todo, extra, primary_key);

				temp.setDueDate(due_month, due_date, due_hour, due_min);
				temp.setDifficulty(difficulty);

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
		int [] temp_due_date = todo.getDueDate();
		int due_month = temp_due_date[0];
		int due_date = temp_due_date[1];
		int due_hour = temp_due_date[2];
		int due_min = temp_due_date[3];
		int difficulty = todo.getDifficulty();
		boolean bfinished = todo.getStatus();
		int finished;
		if(bfinished)
			finished = 1;
		else
			finished = 0;
		ContentValues values = new ContentValues();
		values.put("my_todo", my_todo);
		values.put("extra", extra);
		values.put("due_month", due_month);
		values.put("due_date", due_date);
		values.put("due_hour", due_hour);
		values.put("due_min", due_min);
		values.put("difficulty", difficulty);
		values.put("finished", finished);

		Log.d("[DB]", "addToDo()");

		return (int) (this.getReadableDatabase().insert(Constants.TABLE_TODO, null,
				values));
	}

	/**
	 * deleteToDo() - deletes the ToDo from the database
	 * @param todo
	 * @return true if todo has been successfully deleted, else false
	 */
	public boolean deleteToDo(ToDo todo) {
		Log.d("[DB]", "deleteToDo()");

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
		int [] temp_due_date = todo.getDueDate();
		int due_month = temp_due_date[0];
		int due_date = temp_due_date[1];
		int due_hour = temp_due_date[2];
		int due_min = temp_due_date[3];
		values.put("my_todo", todo.getToDo());
		values.put("extra", todo.getExtra());
		values.put("due_month", due_month);
		values.put("due_date", due_date);
		values.put("due_hour", due_hour);
		values.put("due_min", due_min);
		values.put("difficulty", todo.getDifficulty());
		boolean bfinished = todo.getStatus();
		int finished;
		if (bfinished)
			finished = 1;
		else 
			finished = 0;
		values.put("finished", finished);

		Log.d("[DB]", "updateToDo()");

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
				int difficulty = cursor.getInt(3);
				int finished = cursor.getInt(4);
				int weekid = cursor.getInt(5);
				Daily temp = new Daily(my_daily, extra, primary_key);
				if (finished == 1)
					temp.toggleFinish();
				temp.setDifficulty(difficulty);
				temp.setWeekKey(weekid);
				ArrayList<Boolean> allDailiesWeek = this.getDailiesWeek(weekid);
				for(int index = 0; index < allDailiesWeek.size(); index++)
				{
					if(allDailiesWeek.get(index))
						temp.toggleRegularDate(index);
				}
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
		int difficulty = daily.getDifficulty();
		boolean bfinished = daily.getBooleanStatus();
		int finished;
		if(bfinished)
			finished = 1;
		else
			finished = 0;
		int weekid = this.addDailyWeek(daily.getRegularDate(0), daily.getRegularDate(1), daily.getRegularDate(2), 
				daily.getRegularDate(3), daily.getRegularDate(4), daily.getRegularDate(5), daily.getRegularDate(6));
		if (weekid == -1)
			return weekid;
		ContentValues values = new ContentValues();
		values.put("my_daily", my_daily);
		values.put("extra", extra);
		values.put("difficulty", difficulty);
		values.put("finished", finished);
		values.put("weekid", weekid);
		int result = (int)(this.getReadableDatabase().insert(Constants.TABLE_DAILIES, null, values));
		if (result != -1)
			daily.setWeekKey(weekid);
		else
		{
			this.deleteDailyWeek(weekid);
		}
		Log.d("Weekid", "" + weekid);
		return result;
	}

	/**
	 * deleteDaily() - deletes the Daily from the database
	 * @param daily
	 * @return true if daily has been successfully deleted, else false
	 */
	public boolean deleteDaily(Daily daily) {
		boolean weekidfound = this.deleteDailyWeek(daily.getWeekKey());
		if (!weekidfound)
			return false;
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
		values.put("difficulty", daily.getDifficulty());
		boolean bfinished = daily.getBooleanStatus();
		int finished;
		if (bfinished)
			finished = 1;
		else 
			finished = 0;
		values.put("finished", finished);
		values.put("weekid", daily.getWeekKey());
		boolean weekidfound = this.updateDailyWeek(daily.getWeekKey(), daily.getRegularDate(0), daily.getRegularDate(1), daily.getRegularDate(2), 
				daily.getRegularDate(3), daily.getRegularDate(4), daily.getRegularDate(5), daily.getRegularDate(6));
		if(!weekidfound)
			return false;
		return this.getReadableDatabase().update(Constants.TABLE_DAILIES, values, "_id='" + daily.getKey() + "'", null) > 0;
	}

	/**
	 * getDailiesWeek() - returns a list of dailies for the character
	 * @return Arraylist of all dailiesweek
	 */
	private ArrayList<Boolean> getDailiesWeek(int weekid) {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_DAILIESWEEK, null, "_id='" + weekid + "'", null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Boolean> dailiesweek = new ArrayList<Boolean>();
			cursor.moveToFirst();
			do {
				int primary_key = cursor.getInt(0);
				int mon = cursor.getInt(1);
				int tues = cursor.getInt(2);
				int wed = cursor.getInt(3);
				int thurs = cursor.getInt(4);
				int fri = cursor.getInt(5);
				int sat = cursor.getInt(6);
				int sun = cursor.getInt(7);
				boolean monb = getBool(mon);
				boolean tuesb = getBool(tues);
				boolean wedb = getBool(wed);
				boolean thursb = getBool(thurs);
				boolean frib = getBool(fri);
				boolean satb = getBool(sat);
				boolean sunb = getBool(sun);
				dailiesweek.add(monb);
				dailiesweek.add(tuesb);
				dailiesweek.add(wedb);
				dailiesweek.add(thursb);
				dailiesweek.add(frib);
				dailiesweek.add(satb);
				dailiesweek.add(sunb);
			} while (cursor.moveToNext());
			return dailiesweek;
		}
	}

	/**
	 * addDailiesWeek() - adds a dailyweek for the character
	 * @param seven bools
	 * @return the int for DB position of the dailyweek
	 */
	private int addDailyWeek(boolean monb, boolean tuesb, boolean wedb, boolean thursb, boolean frib, boolean satb, boolean sunb) {
		int mon = this.getInt(monb);
		int tues = this.getInt(tuesb);
		int wed = this.getInt(wedb);
		int thurs = this.getInt(thursb);
		int fri = this.getInt(frib);
		int sat = this.getInt(satb);
		int sun = this.getInt(sunb);
		ContentValues values = new ContentValues();
		values.put("mon", mon);
		values.put("tues", tues);
		values.put("wed", wed);
		values.put("thurs", thurs);
		values.put("fri", fri);
		values.put("sat", sat);
		values.put("sun", sun);
		return (int) (this.getReadableDatabase().insert(Constants.TABLE_DAILIESWEEK, null,
				values));
	}

	/**
	 * deleteDaily() - deletes the Daily from the database
	 * @param daily
	 * @return true if daily has been successfully deleted, else false
	 */
	private boolean deleteDailyWeek(int weekid) {
		return this.getReadableDatabase().delete(Constants.TABLE_DAILIESWEEK, 
				"_id='" + weekid + "'", null) > 0;
	}

	/**
	 * updateHabit() - updates the Daily in the database
	 * @param daily
	 * @return true if successfully updated, false otherwise
	 */
	private boolean updateDailyWeek(int weekid, boolean monb, boolean tuesb, boolean wedb, boolean thursb, boolean frib, boolean satb, boolean sunb) {
		int mon = this.getInt(monb);
		int tues = this.getInt(tuesb);
		int wed = this.getInt(wedb);
		int thurs = this.getInt(thursb);
		int fri = this.getInt(frib);
		int sat = this.getInt(satb);
		int sun = this.getInt(sunb);
		ContentValues values = new ContentValues();
		values.put("mon", mon);
		values.put("tues", tues);
		values.put("wed", wed);
		values.put("thurs", thurs);
		values.put("fri", fri);
		values.put("sat", sat);
		values.put("sun", sun);
		return this.getReadableDatabase().update(Constants.TABLE_DAILIESWEEK, values, "_id='" + weekid + "'", null) > 0;
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
				String title = cursor.getString(1);
				String extra = cursor.getString(2);
				String characteristic = cursor.getString(3);
				int difficulty = cursor.getInt(4);
				int progress = cursor.getInt(5);
				Habit temp = new Habit(title, extra, primary_key);
				temp.setProgress(progress);
				temp.setCharacteristic(characteristic);
				temp.setDifficulty(difficulty);
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
		String title = habit.getHabit();
		String extra = habit.getExtra();
		String characteristic = habit.getCharacteristic();
		int difficulty = habit.getDifficulty();
		int progress = habit.getProgress();

		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("extra", extra);
		values.put("characteristic", characteristic);
		values.put("difficulty", difficulty);
		values.put("progress", progress);

		Log.d("[DB]", "addHabit()");

		return (int) (this.getReadableDatabase().insert(Constants.TABLE_HABITS, null,
				values));
	}

	/**
	 * deleteHabit() - deletes the Habit from the database
	 * @param habit
	 * @return true if habit has been successfully deleted, else false
	 */
	public boolean deleteHabit(Habit habit) {
		Log.d("[DB]", "deleteHabit()");

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
		values.put("title", habit.getHabit());
		values.put("extra", habit.getExtra());
		values.put("characteristic", habit.getCharacteristic());
		values.put("difficulty", habit.getDifficulty());
		values.put("progress", habit.getProgress());

		Log.d("[DB]", "updatesHabit()");

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

	/**
	 * getDailies() - returns a list of dailies for the character
	 * @return Arraylist of all dailies
	 */
	public Inventory getInventory() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_EQUIP, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			cursor.moveToFirst();
			int primary_key = cursor.getInt(0);
			String armorname = cursor.getString(1);
			int armorresid = cursor.getInt(2);
			String helmetname = cursor.getString(3);
			int helmetresid = cursor.getInt(4);
			String shieldname = cursor.getString(5);
			int shieldresid = cursor.getInt(6);
			String weaponname = cursor.getString(7);
			int weaponresid = cursor.getInt(8);
			ArrayList <RpgItem> unused = this.getUnused();
			Armor temparmor = null; Helmet temphelmet = null; Shield tempshield = null; Weapon tempweapon = null;
			if(armorname != null)
				temparmor = new Armor(armorname, armorresid);
			if(helmetname != null)
				temphelmet= new Helmet(helmetname, helmetresid);
			if(shieldname != null)
				tempshield = new Shield (shieldname, shieldresid);
			if(weaponname != null)
				tempweapon = new Weapon(weaponname, weaponresid);
			Inventory tempinventory = new Inventory(temparmor, temphelmet, tempshield, tempweapon, unused);
			return tempinventory;
		}
	}

	/**
	 * addDailies() - adds a daily for the character
	 * @param daily
	 * @return the int for DB position of the daily
	 */
	public int addInventory(Inventory inventory) {
		this.deleteInventory();


		String armorname; Integer armorresid;
		if(inventory.getArmor() == null)
		{
			armorname = null;
			armorresid = null;
		}
		else
		{
			armorname = inventory.getArmor().getName();
			armorresid = inventory.getArmor().getResId();
		}
		String helmetname; Integer helmetresid;
		if(inventory.getHelmet() == null)
		{
			helmetname = null;
			helmetresid = null;
		}
		else
		{
			helmetname = inventory.getHelmet().getName();
			helmetresid = inventory.getHelmet().getResId();
		}
		String shieldname; Integer shieldresid;
		if(inventory.getShield() == null)
		{
			shieldname = null;
			shieldresid = null;
		}
		else
		{
			shieldname = inventory.getShield().getName();
			shieldresid = inventory.getShield().getResId();
		}
		String weaponname; Integer weaponresid;
		if(inventory.getWeapon() == null)
		{
			weaponname = null;
			weaponresid = null;
		}
		else
		{
			weaponname = inventory.getWeapon().getName();
			weaponresid = inventory.getWeapon().getResId();
		}
		this.addUnused(inventory.getInventoryItems());
		ContentValues values = new ContentValues();
		values.put("armorname", armorname);
		values.put("armorresid", armorresid);
		values.put("helmetname", helmetname);
		values.put("helmetresid", helmetresid);
		values.put("shieldname", shieldname);
		values.put("shieldresid", shieldresid);
		values.put("weaponname", weaponname);
		values.put("weaponresid", weaponresid);
		return (int)(this.getReadableDatabase().insert(Constants.TABLE_EQUIP, null, values));
	}

	/**
	 * deleteDaily() - deletes the Daily from the database
	 * @param daily
	 * @return true if daily has been successfully deleted, else false
	 */
	public void deleteInventory() {
		this.deleteUnused();
		this.getReadableDatabase().delete(Constants.TABLE_EQUIP, null, null);
	}

	/**
	 * updateHabit() - updates the Daily in the database
	 * @param daily
	 * @return true if successfully updated, false otherwise
	 */
	public void updateInventory(Inventory inventory) {
		this.addInventory(inventory);
	}

	/**
	 * getDailiesWeek() - returns a list of dailies for the character
	 * @return Arraylist of all dailiesweek
	 */
	private ArrayList<RpgItem> getUnused() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_INVENTORY, null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<RpgItem> inventory = new ArrayList<RpgItem>();
			cursor.moveToFirst();
			do {
				int primary_key = cursor.getInt(0);
				String name = cursor.getString(1);
				int resid = cursor.getInt(2);
				int type = cursor.getInt(3);
				RpgItem tempitem = null;
				if(type == 1)
					tempitem = new Armor(name, resid);
				if(type == 2)
					tempitem = new Helmet(name, resid);
				if(type == 3)
					tempitem = new Shield(name, resid);
				if(type == 4)
					tempitem = new Weapon(name, resid);
				inventory.add(tempitem);
			} while (cursor.moveToNext());
			return inventory;
		}
	}

	/**
	 * addDailiesWeek() - adds a dailyweek for the character
	 * @param seven bools
	 * @return the int for DB position of the dailyweek
	 */
	private void addUnused(ArrayList<RpgItem> inventoryItems) {
		for(int counter = 0; counter < inventoryItems.size(); counter ++)
		{
			RpgItem item = inventoryItems.get(counter);
			int type = 0;
			if(item instanceof Armor)
				type = 1;
			if(item instanceof Helmet)
				type = 2;
			if(item instanceof Shield)
				type = 3;
			if(item instanceof Weapon)
				type = 4;
			ContentValues values = new ContentValues();
			values.put("name", item.getName());
			values.put("resid", item.getResId());
			values.put("type", type);
			this.getReadableDatabase().insert(Constants.TABLE_INVENTORY, null, values);

		}
	}

	/**
	 * deleteDaily() - deletes the Daily from the database
	 * @param daily
	 * @return true if daily has been successfully deleted, else false
	 */
	private void deleteUnused() {
		this.getReadableDatabase().delete(Constants.TABLE_INVENTORY, null, null);
	}

	/**
	 * updateHabit() - updates the Daily in the database
	 * @param daily
	 * @return true if successfully updated, false otherwise
	 */
	private void updateUnused(ArrayList <RpgItem> inventory) {
		this.deleteUnused();
		this.addUnused(inventory);
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


	public int addStat(Stat stat){
		String name = stat.getName();
		int count = stat.getCount();
		ContentValues values = new ContentValues();
		values.put("name",name);
		values.put("count", count);
		return (int) (this.getReadableDatabase().insert(Constants.TABLE_STAT, null,
				values));
	}

	public boolean updateStat(Stat stat){
		String name = stat.getName();
		int count = stat.getCount();
		ContentValues values = new ContentValues();
		values.put("name",name);
		values.put("count", count);
		return this.getReadableDatabase().update(Constants.TABLE_REWARDS, values, "_id='" + stat.getId() + "'", null) > 0;
	}

	public ArrayList<Stat> getStats(){
		Cursor cursor = this.getReadableDatabase().query(Constants.TABLE_STAT,
				null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<Stat> stats = new ArrayList<Stat>();
			cursor.moveToFirst();
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				int count = cursor.getInt(2);
				stats.add(new Stat(id, name, count));
			} while (cursor.moveToNext());
			return stats;
		}
	}

	public int addLogItem(LogItem item){
		String text = item.getContent();
		String date = item.getDate_time();
		ContentValues values = new ContentValues();
		values.put("content", text);
		values.put("date", date);
		return (int) (this.getReadableDatabase().insert(Constants.TABLE_LOG, null,
				values));
	}

	public ArrayList<LogItem> getLog(){
		Cursor cursor = this.getReadableDatabase().query(Constants.TABLE_LOG,
				null, null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		else {
			ArrayList<LogItem> log = new ArrayList<LogItem>();
			cursor.moveToFirst();
			do {
				int id = cursor.getInt(0);
				String content = cursor.getString(1);
				String date = cursor.getString(2);
				log.add(new LogItem(id, content, date));
			} while (cursor.moveToNext());
			return log;
		}
	}

	private boolean getBool(int tempint){
		if(tempint == 1)
			return true;
		return false;
	}
	private int getInt(boolean tempbool){
		if(tempbool)
			return 1;
		return 0;
	}
}
