package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.cs429.todorpg.revised.controller.ExpandListAdapter;
import com.cs429.todorpg.revised.controller.ExpandListChild;
import com.cs429.todorpg.revised.controller.ExpandListGroup;
import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;

public class InventoryActivity extends BaseActivity {
	
	// Character Image
	private ImageView charImage;
	
	// Gridview
	//private GridView gridview;
	//private ImageAdapter gridviewImageAdapter;
	
	// Equipment
	//EquipmentListAdapter adapter;
	
	// Items
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_activity);
		setHeader(R.id.header);
		
		
		// INITIALIZE CODE, TEMPORARY. THIS SHOULD BE DONE
		// WHEN THE APPLICATION STARTS
		// TODO: Migrate this code to application initialization
		// section
		Avatar avatar = new Avatar();
		Inventory inventory = new Inventory();
		inventory.setArmor(new Armor("Leather Armor", R.drawable.broad_armor_warrior_1));
		inventory.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_1));
		inventory.setShield(new Shield("Leather Shield", R.drawable.shield_warrior_1));
		inventory.setWeapon(new Weapon("Iron Sword", R.drawable.weapon_warrior_1));
		
		// This image goes in action bar.
		ImageView image = (ImageView) findViewById(R.id.character_activity);
		image.setImageBitmap(avatar.getBitmap(inventory.getBitmap()));
		
		// END INITIALIZATION CODE
		ImageView display_image = (ImageView) findViewById(R.id.inventory_character_display);
		display_image.setImageBitmap(avatar.getBitmap(inventory.getBitmap()));
		
		
		// Set up Possession Items List
		ExpandList = (ExpandableListView) findViewById(R.id.inventory_item_list);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(InventoryActivity.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);	
	}
	
	public ArrayList<ExpandListGroup> SetStandardGroups() {
    	ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
    	ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru1 = new ExpandListGroup();
        gru1.setName("Comedy");
        ExpandListChild ch1_1 = new ExpandListChild();
        ch1_1.setName("A movie");
        ch1_1.setTag(null);
        list2.add(ch1_1);
        ExpandListChild ch1_2 = new ExpandListChild();
        ch1_2.setName("An other movie");
        ch1_2.setTag(null);
        list2.add(ch1_2);
        ExpandListChild ch1_3 = new ExpandListChild();
        ch1_3.setName("And an other movie");
        ch1_3.setTag(null);
        list2.add(ch1_3);
        gru1.setItems(list2);
        list2 = new ArrayList<ExpandListChild>();
        
        ExpandListGroup gru2 = new ExpandListGroup();
        gru2.setName("Action");
        ExpandListChild ch2_1 = new ExpandListChild();
        ch2_1.setName("A movie");
        ch2_1.setTag(null);
        list2.add(ch2_1);
        ExpandListChild ch2_2 = new ExpandListChild();
        ch2_2.setName("An other movie");
        ch2_2.setTag(null);
        list2.add(ch2_2);
        ExpandListChild ch2_3 = new ExpandListChild();
        ch2_3.setName("And an other movie");
        ch2_3.setTag(null);
        list2.add(ch2_3);
        gru2.setItems(list2);
        list.add(gru1);
        list.add(gru2);
        
        return list;
    }

}
