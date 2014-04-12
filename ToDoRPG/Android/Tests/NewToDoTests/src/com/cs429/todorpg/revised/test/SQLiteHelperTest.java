package com.cs429.todorpg.revised.test;

import java.util.ArrayList;

import com.cs429.todorpg.revised.utils.Item;
import com.cs429.todorpg.revised.utils.Character;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.utils.ToDoItem;
import com.cs429.todorpg.revised.utils.Vice;
import com.cs429.todorpg.revised.model.Reward;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.model.Daily;

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
		assertTrue(names.contains("items"));
		assertTrue(names.contains("todo"));
		assertTrue(names.contains("rewards"));
		assertTrue(names.contains("dailies"));
		assertTrue(names.contains("habits"));
	}

	public void testItemGetAdd() {
		Item sword = new Item("sword", "STR", +1, "sword.png");
		Item dagger = new Item("dagger", "DEX", +1, "dagger.png");
		Item staff = new Item("staff", "INT", +1, "staff.png");

		assertNotNull(db);
		long id = db.addItem(sword);
		assertNotSame(-1, id);
		id = db.addItem(sword);
		assertEquals(-1, id);
		id = db.addItem(dagger);
		assertNotSame(-1, id);
		id = db.addItem(staff);
		assertNotSame(-1, id);

		ArrayList<Item> items = db.getItems();
		assertEquals(3, items.size());
		assertTrue(items.contains(sword));
		assertTrue(items.contains(dagger));
		assertTrue(items.contains(staff));

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
		anime.setKey(idanime);

		ArrayList<Daily> dailies = db.getDailies();
		assertEquals(2, dailies.size());
		assertTrue(dailies.contains(cake));
		assertTrue(dailies.contains(anime));
		
		cake.toggleFinish();
		assertTrue(db.updateDaily(cake));
		dailies = db.getDailies();
		assertEquals(2, dailies.size());
		assertTrue(dailies.contains(cake));
		assertTrue(dailies.contains(anime));
		
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
		assertTrue(db.updateHabit(cake));
		habits = db.getHabits();
		assertEquals(2, habits.size());
		assertTrue(habits.contains(cake));
		assertTrue(habits.contains(anime));
		
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

	public void testToDoItemsGetAdd() {
		ToDoItem hw = new ToDoItem("HW", 10);
		ToDoItem mp = new ToDoItem("MP", 100);

		assertNotNull(db);
		long id = db.addToDoItem(hw);
		assertNotSame(-1, id);
		id = db.addToDoItem(hw);
		assertEquals(-1, id);
		id = db.addToDoItem(mp);
		assertNotSame(-1, id);

		ArrayList<ToDoItem> todos = db.getToDoList();
		assertEquals(2, todos.size());
		assertTrue(todos.contains(hw));
		assertTrue(todos.contains(mp));
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
