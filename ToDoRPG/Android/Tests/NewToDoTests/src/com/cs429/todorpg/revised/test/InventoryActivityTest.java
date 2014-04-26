package com.cs429.todorpg.revised.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cs429.todorpg.revised.InventoryActivity;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.RpgItem;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;
import com.robotium.solo.Solo;

public class InventoryActivityTest extends
		ActivityInstrumentationTestCase2<InventoryActivity> {
	
	private Solo solo;
	public InventoryActivity activity;
	public Inventory inventory;
	private final int VIEW_TIME = 3000;
	
	public InventoryActivityTest(){
		super(InventoryActivity.class);
	}
	
	/**
	 * Setup code is mostly resident in the activity itself for now.
	 */
	@Override
	public void setUp() throws Exception{
		super.setUp();
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
		
		ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
		ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
		
		inventory = new Inventory();
		inventory.setArmor(new Armor("Leather Armor", R.drawable.broad_armor_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setShield(new Shield("Leather Shield", R.drawable.shield_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.setWeapon(new Weapon("Iron Sword", R.drawable.weapon_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		
		inventory.addInventory(new Weapon("Rogue Weapon 0", R.drawable.weapon_rogue_0, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.addInventory(new Weapon("Rogue Weapon 1", R.drawable.weapon_rogue_1, 1, 1, 1, negs, 1, 1, 1, poss));
		inventory.addInventory(new Weapon("Rogue Weapon 2", R.drawable.weapon_rogue_2, 1, 1, 1, negs, 1, 1, 1, poss));
		
		activity.avatar.inventory = inventory;
	}

	@Override 
	protected void tearDown() throws Exception{
		inventory = null;
		/*
		ListView reward_list = (ListView)solo.getView(R.id.rewards_list);
		ImageButton delete = (ImageButton)solo.getView(R.id.reward_delete_button);
		*/
		solo.finishOpenedActivities();
	}
	
	/*
	public void testAddReward() throws Exception{
		ListView reward_list = (ListView)solo.getView(R.id.rewards_list);
		EditText new_reward = (EditText)solo.getView(R.id.add_reward_field);
		solo.clearEditText(new_reward);
		solo.enterText(new_reward, "Test");
		Button add = (Button)solo.getView(R.id.add_reward_button);
		solo.clickOnView(add);
		solo.sleep(2000);
		assertEquals(1, reward_list.getCount());
	}
	*/
	
	public void testCheck() {
		assertTrue(true);
	}
	
	/*
	 * Test taking off equipped items
	 */
	public void testRemoveHelmet() {
		ImageView helmetImageView = (ImageView)solo.getView(R.id.inventory_helmet);
		solo.clickOnView(helmetImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Unequip");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isHelmetSet());
	}
	
	public void testRemoveArmor() {
		ImageView armorImageView = (ImageView)solo.getView(R.id.inventory_armor);
		solo.clickOnView(armorImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Unequip");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isArmorSet());
	}
	
	public void testRemoveShield() {
		ImageView shieldImageView = (ImageView)solo.getView(R.id.inventory_shield);
		solo.clickOnView(shieldImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Unequip");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isShieldSet());
	}
	
	public void testRemoveWeapon() {
		ImageView weaponImageView = (ImageView)solo.getView(R.id.inventory_weapon);
		solo.clickOnView(weaponImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Unequip");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isWeaponSet());
	}
	
	/*
	 * Test equipped item discarding
	 */
	public void testDiscardHelmet() {
		ImageView helmetImageView = (ImageView)solo.getView(R.id.inventory_helmet);
		solo.clickOnView(helmetImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Discard");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isHelmetSet());
	}
	
	public void testDiscardArmor() {
		ImageView armorImageView = (ImageView)solo.getView(R.id.inventory_armor);
		solo.clickOnView(armorImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Discard");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isArmorSet());
	}
	
	public void testDiscardShield() {
		ImageView shieldImageView = (ImageView)solo.getView(R.id.inventory_shield);
		solo.clickOnView(shieldImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Discard");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isShieldSet());
	}
	
	public void testDiscardWeapon() {
		ImageView weaponImageView = (ImageView)solo.getView(R.id.inventory_weapon);
		solo.clickOnView(weaponImageView);
		solo.sleep(VIEW_TIME);
		
		// Click on resultant pop-up menu
		solo.clickOnMenuItem("Discard");
		solo.sleep(VIEW_TIME);
		
		assertFalse(inventory.isWeaponSet());
	}
	
	/*
	 * Test equipping items from inventory
	 */
	
	/**
	 * Test whether items seen in listview match with internal objects
	 */
	public void testFirstItem() {
		ListView lv = (ListView)solo.getView(R.id.inventory_equipment_list);
		int reqPosition = lv.getFirstVisiblePosition() - lv.getHeaderViewsCount();
		RpgItem rItem = (RpgItem) lv.getAdapter().getItem(reqPosition);
		assertEquals(R.drawable.weapon_rogue_0, rItem.getResId());
		
	}
	/**
	 * Tests whether item equipment is working
	 */
	public void testEquipItem() {
		ListView lv = (ListView)solo.getView(R.id.inventory_equipment_list);
		int firstPosition = lv.getFirstVisiblePosition() - lv.getHeaderViewsCount();
		
		View firstItem = lv.getChildAt(firstPosition);
		
		solo.clickOnView(firstItem);
		
		
		solo.sleep(VIEW_TIME);
		solo.clickOnMenuItem("Equip");
		solo.sleep(VIEW_TIME);
		RpgItem rItem = inventory.getWeapon();
		assertEquals(rItem.getResId(), R.drawable.weapon_rogue_0);
	}
	
	/**
	 * Test item discarding.
	 */
	public void testDiscardItem() {
		ListView lv = (ListView)solo.getView(R.id.inventory_equipment_list);
		int firstPosition = lv.getFirstVisiblePosition() - lv.getHeaderViewsCount();
		View firstItem = lv.getChildAt(firstPosition);
		solo.clickOnView(firstItem);
		
		//solo.clickOnMenuItem("Discard");
		solo.clickOnMenuItem("Discard", true);
		solo.sleep(5000);
		assertEquals(inventory.numInventory(), 2);
	}
}