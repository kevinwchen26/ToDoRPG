package com.cs429.todorpg.revised.utils;

import java.util.ArrayList;

import com.cs429.todorpg.revised.model.Reward;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.model.Daily;
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
		db.execSQL(Constants.EQUIPARMOR_TABLE_CREATE);
		db.execSQL(Constants.EQUIPHELMET_TABLE_CREATE);
		db.execSQL(Constants.EQUIPSHIELD_TABLE_CREATE);
		db.execSQL(Constants.EQUIPWEAPON_TABLE_CREATE);
		db.execSQL(Constants.EQUIPSECONDARY_TABLE_CREATE);
		db.execSQL(Constants.INVENTORY_TABLE_CREATE);
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
			ToDoCharacter tempchar = new ToDoCharacter(name, gold);
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
		values.put("title", habit.getHabit());
		values.put("extra", habit.getExtra());
		values.put("characteristic", habit.getCharacteristic());
		values.put("difficulty", habit.getDifficulty());
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
	
	/**
	 * getDailies() - returns a list of dailies for the character
	 * @return Arraylist of all dailies
	 */
	public Inventory getInventory() {
		Cursor cursor = this.getReadableDatabase().query(
				Constants.TABLE_EQUIPARMOR, null, null, null, null, null, null);
		Armor temparmor = (Armor) getEquip(cursor, "armor");
		
		cursor = this.getReadableDatabase().query(
				Constants.TABLE_EQUIPHELMET, null, null, null, null, null, null);
		Helmet temphelmet = (Helmet) getEquip(cursor, "helmet");
		
		cursor = this.getReadableDatabase().query(
				Constants.TABLE_EQUIPSHIELD, null, null, null, null, null, null);
		Shield tempshield = (Shield) getEquip(cursor, "shield");
		
		cursor = this.getReadableDatabase().query(
				Constants.TABLE_EQUIPWEAPON, null, null, null, null, null, null);
		Weapon tempweapon = (Weapon) getEquip(cursor, "weapon");
		
		cursor = this.getReadableDatabase().query(
				Constants.TABLE_EQUIPSECONDARY, null, null, null, null, null, null);
		Weapon tempsecondary = (Weapon) getEquip(cursor, "weapon");
		
		ArrayList <RpgItem> unused = this.getUnused();
		
		if(temparmor == null && temphelmet == null && tempshield == null && tempweapon == null && tempsecondary == null && unused.isEmpty())
			return null;
		
		Inventory tempinventory = new Inventory(temparmor, temphelmet, tempshield, tempweapon, tempsecondary, unused);
		return tempinventory;
	}

	/**
	 * addDailies() - adds a daily for the character
	 * @param daily
	 * @return the int for DB position of the daily
	 */
	public int addInventory(Inventory inventory) {
		this.deleteInventory();
		
		int temp1 = this.addEquip(inventory.getArmor(), false);
		int temp2 = this.addEquip(inventory.getHelmet(), false);
		int temp3 = this.addEquip(inventory.getShield(), false);
		int temp4 = this.addEquip(inventory.getWeapon(), false);
		int temp5 = this.addEquip(inventory.getSecondary(), true);
		
		this.addUnused(inventory.getInventoryItems());
		
		if(temp1 == -1 || temp2 == -1 || temp3 == -1 || temp4 == -1 || temp5 == -1)
			return -1;
		else
			return 0;
		
	}
	
	/**
	 * deleteDaily() - deletes the Daily from the database
	 * @param daily
	 * @return true if daily has been successfully deleted, else false
	 */
	public void deleteInventory() {
		this.deleteUnused();
		this.getReadableDatabase().delete(Constants.TABLE_EQUIPARMOR, null, null);
		this.getReadableDatabase().delete(Constants.TABLE_EQUIPHELMET, null, null);
		this.getReadableDatabase().delete(Constants.TABLE_EQUIPSHIELD, null, null);
		this.getReadableDatabase().delete(Constants.TABLE_EQUIPWEAPON, null, null);
		this.getReadableDatabase().delete(Constants.TABLE_EQUIPSECONDARY, null, null);
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
		ArrayList<RpgItem> inventory = new ArrayList<RpgItem>();
		if (cursor.getCount() == 0)
			return inventory;
		else {
			cursor.moveToFirst();
			do {
				int primary_key = cursor.getInt(0);
				int type = cursor.getInt(1);
				String name = cursor.getString(2);
				int resid = cursor.getInt(3);
				int damage = cursor.getInt(4);
				int critical = cursor.getInt(5);
				int multihit = cursor.getInt(6);
				String negEffects = cursor.getString(7);
				int damagereduction = cursor.getInt(8);
				int evasion = cursor.getInt(9);
				int accuracy = cursor.getInt(10);
				String posEffects = cursor.getString(11);
				ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
				while(!negEffects.equals(""))
				{
					int tempspace = negEffects.indexOf(' ');
					int tempcomma = negEffects.indexOf(',');
					String tempeffect = negEffects.substring(0, tempspace);
					int temppercent = Integer.parseInt(negEffects.substring(tempspace + 1, tempcomma));
					negs.add(new NegativeEffects(tempeffect, temppercent));
					negEffects = negEffects.substring(tempcomma + 1);
				}
				
				ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
				while(!posEffects.equals(""))
				{
					int tempcomma = posEffects.indexOf(',');
					String tempeffect = posEffects.substring(0, tempcomma);
					poss.add(new PositiveEffects(tempeffect));
					posEffects = posEffects.substring(tempcomma + 1);
				}
						
				RpgItem tempitem = null;
				if(type == 1)
					tempitem = new Armor(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
							accuracy, poss);
				if(type == 2)
					tempitem = new Helmet(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
							accuracy, poss);
				if(type == 3)
					tempitem = new Shield(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
							accuracy, poss);
				if(type == 4)
					tempitem = new Weapon(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
							accuracy, poss);
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
		deleteUnused();
		for(int counter = 0; counter < inventoryItems.size(); counter ++)
		{
			Equipment item = (Equipment) inventoryItems.get(counter);
			int type = 0;
			if(item instanceof Armor)
				type = 1;
			if(item instanceof Helmet)
				type = 2;
			if(item instanceof Shield)
				type = 3;
			if(item instanceof Weapon)
				type = 4;
			ArrayList <NegativeEffects> negs = item.getnegEffects();
			String negatives = "";
			for(int x = 0; x < negs.size(); x ++)
			{
				NegativeEffects tempneg = negs.get(x);
				negatives = negatives + tempneg.getName() + " " + tempneg.getAffect() + ",";
			}
			ArrayList <PositiveEffects> poss = item.getposEffects();
			String positives = "";
			for(int x = 0; x < poss.size(); x ++)
			{
				PositiveEffects temppos = poss.get(x);
				positives = positives + temppos.getName() + ",";
			}
			ContentValues values = new ContentValues();
			values.put("type", type);
			values.put("name", item.getName());
			values.put("resid", item.getResId());
			values.put("damage", item.getDamage());
			values.put("critical", item.getCritical());
			values.put("multihit", item.getMulti_Hit());
			values.put("negEffects", negatives);
			values.put("damagereduction", item.getDamage_Reduction());
			values.put("evasion", item.getEvasion());
			values.put("accuracy", item.getAccuracy());
			values.put("posEffects", positives);
			
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
		this.addUnused(inventory);
	}
	
	/**
	 * getDailiesWeek() - returns a list of dailies for the character
	 * @return Arraylist of all dailiesweek
	 */
	private Equipment getEquip(Cursor cursor, String equip) {
		if (cursor.getCount() == 0)
			return null;
		else {
			cursor.moveToFirst();
			int primary_key = cursor.getInt(0);
			String name = cursor.getString(1);
			int resid = cursor.getInt(2);
			int damage = cursor.getInt(3);
			int critical = cursor.getInt(4);
			int multihit = cursor.getInt(5);
			String negEffects = cursor.getString(6);
			int damagereduction = cursor.getInt(7);
			int evasion = cursor.getInt(8);
			int accuracy = cursor.getInt(9);
			String posEffects = cursor.getString(10);
			ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
			while(!negEffects.equals(""))
			{
				int tempspace = negEffects.indexOf(' ');
				int tempcomma = negEffects.indexOf(',');
				String tempeffect = negEffects.substring(0, tempspace);
				int temppercent = Integer.parseInt(negEffects.substring(tempspace + 1, tempcomma));
				negs.add(new NegativeEffects(tempeffect, temppercent));
				negEffects = negEffects.substring(tempcomma + 1);
			}
			
			ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
			while(!posEffects.equals(""))
			{
				int tempcomma = posEffects.indexOf(',');
				String tempeffect = posEffects.substring(0, tempcomma);
				poss.add(new PositiveEffects(tempeffect));
				posEffects = posEffects.substring(tempcomma + 1);
			}
					
			Equipment tempitem = null;
			if(equip.equals("armor"))
				tempitem = new Armor(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
						accuracy, poss);
			else if(equip.equals("helmet"))
				tempitem = new Helmet(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
						accuracy, poss);
			else if(equip.equals("shield"))
				tempitem = new Shield(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
						accuracy, poss);
			else
				tempitem = new Weapon(name, resid, damage, critical, multihit, negs, damagereduction, evasion, 
						accuracy, poss);
			return tempitem;
		}
	}

	/**
	 * addDailiesWeek() - adds a dailyweek for the character
	 * @param seven bools
	 * @return the int for DB position of the dailyweek
	 */
	private int addEquip(Equipment item, boolean secondary) {
		if (item == null)
			return 0;
		ArrayList <NegativeEffects> negs = item.getnegEffects();
		String negatives = "";
		for(int x = 0; x < negs.size(); x ++)
		{
			NegativeEffects tempneg = negs.get(x);
			negatives = negatives + tempneg.getName() + " " + tempneg.getAffect() + ",";
		}
		ArrayList <PositiveEffects> poss = item.getposEffects();
		String positives = "";
		for(int x = 0; x < poss.size(); x ++)
		{
			PositiveEffects temppos = poss.get(x);
			positives = positives + temppos.getName() + ",";
		}
		ContentValues values = new ContentValues();
		values.put("name", item.getName());
		values.put("resid", item.getResId());
		values.put("damage", item.getDamage());
		values.put("critical", item.getCritical());
		values.put("multihit", item.getMulti_Hit());
		values.put("negEffects", negatives);
		values.put("damagereduction", item.getDamage_Reduction());
		values.put("evasion", item.getEvasion());
		values.put("accuracy", item.getAccuracy());
		values.put("posEffects", positives);
		
		
		if(item instanceof Armor)
			return (int) (this.getReadableDatabase().insert(Constants.TABLE_EQUIPARMOR, null, values));
		else if(item instanceof Helmet)
			return (int) (this.getReadableDatabase().insert(Constants.TABLE_EQUIPHELMET, null, values));
		else if(item instanceof Shield)
			return (int) (this.getReadableDatabase().insert(Constants.TABLE_EQUIPSHIELD, null, values));
		else if(!secondary)
			return (int) (this.getReadableDatabase().insert(Constants.TABLE_EQUIPWEAPON, null, values));
		else 
			return (int) (this.getReadableDatabase().insert(Constants.TABLE_EQUIPSECONDARY, null, values));
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
