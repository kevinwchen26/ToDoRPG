package com.cs429.todorpg.revised.test;

import java.util.ArrayList;

import com.cs429.todorpg.revised.utils.Character;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.utils.Vice;
import com.cs429.todorpg.revised.model.Reward;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.itemsystem.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

public class SQLiteHelperTest extends AndroidTestCase {
	private SQLiteHelper db;

	public void setUp() {
		RenamingDelegatingContext context = new RenamingDelegatingContext(
				getContext(), "test_");
		db = new SQLiteHelper(context);
	}

	public void testDBSetup() {
		assertNotNull(db);
		SQLiteDatabase database = db.getReadableDatabase();
		Cursor cursor = database
				.rawQuery(
						"SELECT name FROM sqlite_master WHERE type='table' ORDER BY name",
						null);

		ArrayList<String> names = new ArrayList<String>();
		cursor.moveToFirst();
		do {
			names.add(cursor.getString(0));
		} while (cursor.moveToNext());
		assertTrue(names.contains("character"));
		assertTrue(names.contains("vices"));
		assertTrue(names.contains("equip"));
		assertTrue(names.contains("todo"));
		assertTrue(names.contains("rewards"));
		assertTrue(names.contains("dailies"));
		assertTrue(names.contains("habits"));
	}

	public void testCharacterGetAdd() {
		Character kevin = new Character("kevin", Integer.MAX_VALUE);
		Character loser = new Character("loser", 0);

		assertNotNull(db);
		long id = db.addCharacter(kevin);
		assertNotSame(-1, id);
		id = db.addCharacter(loser);
		assertNotSame(-1, id);

		Character test = db.getCharacter();
		assertEquals(kevin, test);
		assertNotSame(loser, test);

	}
	
	public void testToDos() {
		ToDo cake = new ToDo("Cake", "Tasty Sweet", -1);
		ToDo anime = new ToDo("Anime", "Japanese Comics", -1);

		assertNotNull(db);
		int idcake = db.addToDo(cake);
		assertNotSame(-1, idcake);
		int idcake2 = db.addToDo(cake);
		assertEquals(-1, idcake2);
		int idanime = db.addToDo(anime);
		assertNotSame(-1, idanime);
		
		cake.setKey(idcake);
		anime.setKey(idanime);

		ArrayList<ToDo> todos = db.getToDos();
		assertEquals(2, todos.size());
		assertTrue(todos.contains(cake));
		assertTrue(todos.contains(anime));
		
		cake.setFinish();
		assertTrue(db.updateToDo(cake));
		anime.setDueDate(5, 5, 5, 5);
		todos = db.getToDos();
		assertEquals(2, todos.size());
		assertTrue(todos.contains(cake));
		assertFalse(todos.contains(anime));
		
		assertTrue(db.updateToDo(anime));
		
		assertTrue(db.deleteToDo(anime));
		todos = db.getToDos();
		assertEquals(1, todos.size());
		assertTrue(todos.contains(cake));

	}

	public void testDailies() {
		Daily cake = new Daily("Cake", "Tasty Sweet", -1);
		Daily anime = new Daily("Anime", "Japanese Comics", -1);

		assertNotNull(db);
		int idcake = db.addDaily(cake);
		assertNotSame(-1, idcake);
		int idcake2 = db.addDaily(cake);
		assertEquals(-1, idcake2);
		int idanime = db.addDaily(anime);
		assertNotSame(-1, idanime);
		
		cake.setKey(idcake);
		//Log.d("Weekkey", "" + cake.getWeekKey());
		anime.setKey(idanime);

		ArrayList<Daily> dailies = db.getDailies();
		assertEquals(2, dailies.size());
		assertTrue(dailies.contains(cake));
		assertTrue(dailies.contains(anime));
		
		anime.toggleRegularDate(5);
		cake.toggleFinish();
		assertTrue(db.updateDaily(anime));
		dailies = db.getDailies();
		assertEquals(2, dailies.size());
		assertTrue(dailies.contains(anime));
		assertFalse(dailies.contains(cake));
		
		assertTrue(db.updateDaily(cake));
		
		assertTrue(db.deleteDaily(anime));
		dailies = db.getDailies();
		assertEquals(1, dailies.size());
		assertTrue(dailies.contains(cake));

	}
	
	public void testHabits() {
		Habit cake = new Habit("Cake", "Tasty Sweet", -1);
		Habit anime = new Habit("Anime", "Japanese Comics", -1);

		assertNotNull(db);
		int idcake = db.addHabit(cake);
		assertNotSame(-1, idcake);
		int idcake2 = db.addHabit(cake);
		assertEquals(-1, idcake2);
		int idanime = db.addHabit(anime);
		assertNotSame(-1, idanime);
		
		cake.setKey(idcake);
		anime.setKey(idanime);

		ArrayList<Habit> habits = db.getHabits();
		assertEquals(2, habits.size());
		assertTrue(habits.contains(cake));
		assertTrue(habits.contains(anime));
		
		cake.setProgress(10);
		anime.setDifficulty(2);
		assertTrue(db.updateHabit(cake));
		habits = db.getHabits();
		assertEquals(2, habits.size());
		assertTrue(habits.contains(cake));
		assertFalse(habits.contains(anime));
		
		assertTrue(db.updateHabit(anime));
		
		assertTrue(db.deleteHabit(anime));
		habits = db.getHabits();
		assertEquals(1, habits.size());
		assertTrue(habits.contains(cake));
	}

	public void testRewards() {
		Reward cake = new Reward(-1, "Cake", "Tasty Sweet", 10);
		Reward anime = new Reward(-1, "Anime", "Japanese Comics", 40);

		assertNotNull(db);
		int idcake = db.addReward(cake);
		assertNotSame(-1, idcake);
		int idcake2 = db.addReward(cake);
		assertEquals(-1, idcake2);
		int idanime = db.addReward(anime);
		assertNotSame(-1, idanime);
		
		cake.setPrimary_key(idcake);
		anime.setPrimary_key(idanime);

		ArrayList<Reward> rewards = db.getRewards();
		assertEquals(2, rewards.size());
		assertTrue(rewards.contains(cake));
		assertTrue(rewards.contains(anime));
		
		cake = new Reward(idcake, "Cake", "Tasty 20lb Sweet", 5000);
		assertTrue(db.updateReward(cake));
		rewards = db.getRewards();
		assertEquals(2, rewards.size());
		assertTrue(rewards.contains(cake));
		assertTrue(rewards.contains(anime));
		
		assertTrue(db.deleteReward(anime));
		rewards = db.getRewards();
		assertEquals(1, rewards.size());
		assertTrue(rewards.contains(cake));
	}
	
	public void testInventory() {
		Inventory char_warrior = new Inventory(new Armor("A1", 1), new Helmet("H1",2 ), new Shield("S1", 3), new Weapon("W1", 4), new ArrayList<RpgItem>());

		assertNotNull(db);
		int char_warrior_id = db.addInventory(char_warrior);
		assertNotSame(-1, char_warrior_id);

		Inventory temp_char_warrior = db.getInventory();
		assertTrue(temp_char_warrior.equals(char_warrior));
		
		ArrayList <RpgItem> itemlist = new ArrayList<RpgItem>();
		itemlist.add(new Armor("A2", 5));
		char_warrior.setInventoryItems(itemlist);
		db.updateInventory(char_warrior);
		temp_char_warrior = db.getInventory();
		assertTrue(temp_char_warrior.equals(char_warrior));
		
		db.deleteInventory();
		assertNull(db.getInventory());

	}
	
	public void testVicesGetAdd() {
		Vice drugs = new Vice("crack", "INT", -100);
		Vice lazy = new Vice("lazy", "HP", -10);

		assertNotNull(db);
		long id = db.addVices(drugs);
		assertNotSame(-1, id);
		id = db.addVices(drugs);
		assertEquals(-1, id);
		id = db.addVices(lazy);
		assertNotSame(-1, id);

		ArrayList<Vice> vices = db.getVices();
		assertEquals(2, vices.size());
		assertTrue(vices.contains(drugs));
		assertTrue(vices.contains(lazy));
	}
}
