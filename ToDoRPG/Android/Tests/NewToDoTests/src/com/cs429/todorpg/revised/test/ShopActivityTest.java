package com.cs429.todorpg.revised.test;

import java.util.ArrayList;

import com.cs429.todorpg.revised.ShopActivity;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.ToDoActivity;
import com.cs429.todorpg.revised.itemsystem.*;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ShopActivityTest extends ActivityInstrumentationTestCase2<ShopActivity> {

	private Solo solo;
	private ShopActivity mActivity;
	
	private ToDoCharacter originalChar;
	private Inventory originalInventory;
	
	
	private EditText add_daily_field;
	private Button test_sword_button;
	private Button test_sheild_button;
	private ListView listview;
	private ImageButton edit_button;
	private ImageButton delete_button;
	
	private SQLiteHelper db;
	
	public ShopActivityTest(){
		super(ShopActivity.class);
	}

	protected void setUp() throws Exception {
		
		db = new SQLiteHelper(getActivity().getBaseContext());
		
		mActivity = getActivity();
		solo = new Solo(getInstrumentation(), mActivity);
		
		originalChar = db.getCharacter();
		ToDoCharacter newChar = originalChar;
		if(newChar == null)
			newChar = new ToDoCharacter("Tester", 0, 0, 0, 0, 0);
		if(newChar.getGold() != 100)
		{
			newChar.setGold(100);
		}
		db.updateCharacter(newChar);
		
		/**ArrayList <PositiveEffects> tempos = new ArrayList<PositiveEffects>();
		ArrayList <NegativeEffects> tempneg = new ArrayList<NegativeEffects>();
		Weapon tempweapon = new Weapon("test sword", 1, 2, 3, 4, tempneg, 5, 6, 7, tempos);
		Shield tempshield = new Shield("test shield", 8, 9, 10, 11, tempneg, 12, 13, 14, tempos);
		
		db.addLibrary(tempweapon, 5);
		db.addLibrary(tempshield, 1000);
		
		originalChar = db.getCharacter();
		ToDoCharacter newChar = originalChar;
		if(newChar == null)
			newChar = new ToDoCharacter("Hero", 0, 0, 0, 0, 0);
		if(newChar.getGold() != 100)
		{
			newChar.setGold(100);
		}
		db.updateCharacter(newChar);
		
		originalInventory = db.getInventory();
		Inventory newInventory = new Inventory();
		db.updateInventory(newInventory); **/
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void test00CheckLibrary() throws Exception {
		solo.clickOnText("Broad Sword");
		solo.clickOnText("Info");
		assertTrue(solo.searchText("Dmg:20"));
		assertTrue(solo.searchText("Crit:10"));
		assertTrue(solo.searchText("Cost:30"));
		solo.clickOnButton("Ok");
	}
	public void test01CheckPurchase() throws Exception {
		solo.clickOnText("Broad Sword");
		solo.clickOnText("Purchase");
		assertTrue(solo.searchText("Purchased"));
	}
	
	public void test02CheckAlreadyOwn() throws Exception {
		solo.clickOnText("Broad Sword");
		solo.clickOnText("Purchase");
		
		assertTrue(solo.searchText("Already Own"));
	}
	
	public void test03TooExpensive() throws Exception {
		solo.clickOnText("Buster Sword");
		solo.clickOnText("Purchase");
	
		assertTrue(solo.searchText("Too Expensive"));
	}
	
	
/*	public void testDeleteHabit() throws Exception {
		listview = (ListView) solo.getView(R.id.daily_listview);
		int original = listview.getCount();
		delete_button = (ImageButton) solo.getView(R.id.daily_delete_button);
		solo.clickOnView(delete_button);
		solo.sleep(1000);
		
		assertEquals(listview.getCount(), original - 1);
	}
	
	public void testAddHabitEditBox(){
		add_daily_field = (EditText) solo.getView(R.id.add_daily_field);
		add_daily_button = (Button) solo.getView(R.id.add_daily_button);
		listview = (ListView) solo.getView(R.id.daily_listview);
		
		solo.clearEditText(add_daily_field);
		solo.enterText(add_daily_field, "testdaily");
		solo.clickOnView(add_daily_button);
		solo.sleep(1000);
		
		edit_button = (ImageButton) solo.getView(R.id.daily_edit_button);
		solo.clickOnView(edit_button);
		solo.sleep(2000);
		
		EditText extra_note = (EditText) solo.getView(R.id.extra_notes);
		solo.clearEditText(extra_note);
		solo.enterText(extra_note, "testExtra");
		solo.sleep(1000);
		
		Button medium = (Button) solo.getView(R.id.medium);
		solo.clickOnView(medium);
		solo.sleep(1000);
		
		Button save_close = (Button) solo.getView(R.id.save_close);
		solo.clickOnView(save_close);
		solo.sleep(1000);
	
		delete_button = (ImageButton) solo.getView(R.id.daily_delete_button);
		solo.clickOnView(delete_button);
		solo.sleep(1000);
	}

	*/
}
