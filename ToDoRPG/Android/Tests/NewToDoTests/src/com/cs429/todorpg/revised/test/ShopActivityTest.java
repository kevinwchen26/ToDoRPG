package com.cs429.todorpg.revised.test;

import com.cs429.todorpg.revised.ShopActivity;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Shop Activity Test
 * 
 * @author Leon Chen
 * 
 */
public class ShopActivityTest extends
ActivityInstrumentationTestCase2<ShopActivity> {

	private Solo solo;
	private ShopActivity mActivity;

	private ToDoCharacter originalChar;

	private SQLiteHelper db;

	/**
	 * creates the test
	 */
	public ShopActivityTest() {
		super(ShopActivity.class);
	}

	/**
	 * sets up the test
	 */
	protected void setUp() throws Exception {

		db = new SQLiteHelper(getActivity().getBaseContext());

		mActivity = getActivity();
		solo = new Solo(getInstrumentation(), mActivity);

		originalChar = db.getCharacter();
		ToDoCharacter newChar = originalChar;
		if (newChar == null)
			newChar = new ToDoCharacter("Tester", 0, 0, 0, 0, 0);
		if (newChar.getGold() != 100) {
			newChar.setGold(100);
		}
		db.updateCharacter(newChar);

		super.setUp();
	}

	/**
	 * finishes the test
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * checks to see if the library exists
	 * 
	 * @throws Exception
	 */
	public void test00CheckLibrary() throws Exception {
		solo.clickOnText("Broad Sword");
		solo.clickOnText("Info");
		assertTrue(solo.searchText("Dmg:20"));
		assertTrue(solo.searchText("Crit:10"));
		assertTrue(solo.searchText("Cost:30"));
		solo.clickOnButton("Ok");
	}

	/**
	 * checks to see if purchase can be made
	 * 
	 * @throws Exception
	 */
	public void test01CheckPurchase() throws Exception {
		solo.clickOnText("Broad Sword");
		solo.clickOnText("Purchase");
		assertTrue(solo.searchText("Purchased"));
	}

	/**
	 * checks for double purchase
	 * 
	 * @throws Exception
	 */
	public void test02CheckAlreadyOwn() throws Exception {
		solo.clickOnText("Broad Sword");
		solo.clickOnText("Purchase");

		assertTrue(solo.searchText("Already Own"));
	}

	/**
	 * checks for inadequate funds
	 * 
	 * @throws Exception
	 */
	public void test03TooExpensive() throws Exception {
		solo.clickOnText("Buster Sword");
		solo.clickOnText("Purchase");

		assertTrue(solo.searchText("Too Expensive"));
	}

}
