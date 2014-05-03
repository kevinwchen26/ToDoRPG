package com.cs429.todorpg.revised.test;

import java.util.ArrayList;



import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.model.Reward;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.itemsystem.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

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
		assertTrue(names.contains("equiparmor"));
		assertTrue(names.contains("equipweapon"));
		assertTrue(names.contains("equipshield"));
		assertTrue(names.contains("equiphelmet"));
		assertTrue(names.contains("todo"));
		assertTrue(names.contains("rewards"));
		assertTrue(names.contains("dailies"));
		assertTrue(names.contains("habits"));
	}

	public void testCharacter() {
		ToDoCharacter kevin = new ToDoCharacter("kevin", Integer.MAX_VALUE, 1, 1, 1, 1);
		ToDoCharacter loser = new ToDoCharacter("loser", 0, 1, 1, 1, 1);

		assertNotNull(db);
		db.addCharacter(kevin);
		ToDoCharacter test = db.getCharacter();
		assertEquals(kevin, test);

		db.addCharacter(loser);
		test = db.getCharacter();
		assertEquals(loser, test);

		kevin.setHP(50);
		kevin.setLevel(2);
		kevin.setCurrExp(30);
		kevin.setNextExp(100);

		db.updateCharacter(kevin);
		test = db.getCharacter();
		assertEquals(kevin, test);


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

		ArrayList<ToDo> todos = db.getToDos(2);
		assertEquals(2, todos.size());
		assertTrue(todos.contains(cake));
		assertTrue(todos.contains(anime));

		cake.setFinish();
		assertTrue(db.updateToDo(cake));
		anime.setDueDate(5, 5, 5, 5);
		todos = db.getToDos(2);
		assertEquals(2, todos.size());
		assertTrue(todos.contains(cake));
		assertFalse(todos.contains(anime));

		assertTrue(db.updateToDo(anime));

		assertTrue(db.deleteToDo(anime));
		todos = db.getToDos(2);
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

		ArrayList<Daily> dailies = db.getDailies(3);
		assertEquals(2, dailies.size());
		assertTrue(dailies.contains(cake));
		assertTrue(dailies.contains(anime));

		anime.toggleRegularDate(5);
		cake.toggleFinish();
		assertTrue(db.updateDaily(anime));
		dailies = db.getDailies(3);
		assertEquals(2, dailies.size());
		assertTrue(dailies.contains(anime));
		assertFalse(dailies.contains(cake));

		assertTrue(db.updateDaily(cake));

		assertTrue(db.deleteDaily(anime));
		dailies = db.getDailies(3);
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
		ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
		ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
		Inventory char_warrior = new Inventory(new Armor("A1", 1, 0, 0, 0, negs, 0, 0, 0, poss), null, null, 
				new Weapon("W1", 4, 0, 0, 0, negs, 0, 0, 0, poss), new Weapon("W1", 4, 1, 1, 1, negs, 1, 1, 1, poss), 
				new ArrayList<RpgItem>());

		assertTrue(char_warrior.equals(char_warrior));

		assertNotNull(db);
		int char_warrior_id = db.addInventory(char_warrior);
		assertNotSame(-1, char_warrior_id);

		Inventory temp_char_warrior = db.getInventory();
		assertTrue(temp_char_warrior.equals(char_warrior));

		ArrayList <RpgItem> itemlist = new ArrayList<RpgItem>();
		negs.add(new NegativeEffects("Blind", 20));
		poss.add(new PositiveEffects("nullBlind"));
		itemlist.add(new Helmet("A2", 5, 1, 1, 1, negs, 1, 1, 1, poss));
		char_warrior.setHelmet(new Helmet("A2", 5, 1, 1, 1, negs, 1, 1, 1, poss));
		char_warrior.setInventoryItems(itemlist);

		assertTrue(char_warrior.equals(char_warrior));


		db.updateInventory(char_warrior);
		temp_char_warrior = db.getInventory();
		assertTrue(temp_char_warrior.equals(char_warrior));

		db.deleteInventory();
		assertNull(db.getInventory());

	}

	public void testLibrary() {
		ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
		ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
		Equipment temparmor = new Armor("A1", 1, 0, 0, 0, negs, 0, 0, 0, poss);
		Equipment tempweapon = new Weapon("W1", 4, 0, 0, 0, negs, 0, 0, 0, poss);


		assertNotNull(db);
		int idarmor = db.addLibrary(temparmor, 50);
		assertNotSame(-1, idarmor);
		int idarmor2 = db.addLibrary(temparmor, 50);
		assertEquals(-1, idarmor2);
		int idweapon = db.addLibrary(tempweapon, 50);
		assertNotSame(-1, idweapon);

		EquipCost testarmor = db.getLibrary("A1");
		assertEquals(temparmor, testarmor.getEquipment());
		assertEquals(50, testarmor.getCost());

		negs.add(new NegativeEffects("Blind", 20));
		poss.add(new PositiveEffects("nullBlind"));
		tempweapon = new Weapon("W1", 4, 0, 0, 0, negs, 0, 0, 0, poss);

		assertTrue(db.updateLibrary(tempweapon, 30));
		EquipCost testweapon = db.getLibrary("W1");
		assertEquals(tempweapon, testweapon.getEquipment());
		assertEquals(30, testweapon.getCost());

		assertTrue(db.deleteLibrary("A1"));
		assertTrue(db.deleteLibrary("W1"));
		testarmor = db.getLibrary("A1");
		assertNull(testarmor);
		testweapon = db.getLibrary("W1");
		assertNull(testweapon);

	}

}
