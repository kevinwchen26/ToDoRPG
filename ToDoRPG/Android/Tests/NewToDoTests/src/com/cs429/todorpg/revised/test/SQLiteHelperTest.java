package com.cs429.todorpg.revised.test;

import java.util.ArrayList;

import com.cs429.todorpg.revised.utils.Dailies;
import com.cs429.todorpg.revised.utils.Item;
import com.cs429.todorpg.revised.utils.Character;
import com.cs429.todorpg.revised.utils.Reward;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.utils.ToDoItem;
import com.cs429.todorpg.revised.utils.Vice;

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
		assertTrue(names.contains("vices"));
		assertTrue(names.contains("items"));
		assertTrue(names.contains("todo"));
		assertTrue(names.contains("rewards"));
		assertTrue(names.contains("dailies"));
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

	public void testDailiesGetAdd() {
		Dailies hw = new Dailies("hw", 10);
		Dailies dinner = new Dailies("dinner", 10);

		assertNotNull(db);
		long id = db.addDaily(hw);
		assertNotSame(-1, id);
		id = db.addDaily(hw);
		assertEquals(-1, id);
		id = db.addDaily(dinner);
		assertNotSame(-1, id);

		ArrayList<Dailies> dailies = db.getDailies();
		assertEquals(2, dailies.size());
		assertTrue(dailies.contains(hw));
		assertTrue(dailies.contains(dinner));

	}

	public void testRewards() {
		Reward cake = new Reward("Cake", 10);
		Reward anime = new Reward("Anime", 40);

		assertNotNull(db);
		long id = db.addReward(cake);
		assertNotSame(-1, id);
		id = db.addReward(cake);
		assertEquals(-1, id);
		id = db.addReward(anime);
		assertNotSame(-1, id);

		ArrayList<Reward> rewards = db.getRewards();
		assertEquals(2, rewards.size());
		assertTrue(rewards.contains(cake));
		assertTrue(rewards.contains(anime));
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
