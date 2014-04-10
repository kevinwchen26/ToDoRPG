package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.ExpandListAdapter;
import com.cs429.todorpg.revised.controller.ExpandListGroup;
import com.cs429.todorpg.revised.controller.ItemListAdapter;
import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.RpgItem;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;

public class InventoryActivity extends BaseActivity {
	
	// Character Image
	private ImageView charImage;
	
	// Gridview
	//private GridView gridview;
	//private ImageAdapter gridviewImageAdapter;
	
	// Equipment
	ItemListAdapter adapter;
	Inventory inventory;
	// Items
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_activity);
		setHeader(R.id.header);
		
		
		/* 
		   INITIALIZE CODE, TEMPORARY. THIS SHOULD BE DONE
		   WHEN THE APPLICATION STARTS
		   TODO: Migrate this code to application initialization
		   section 
		*/
		Avatar avatar = new Avatar();
		inventory = new Inventory();
		inventory.setArmor(new Armor("Leather Armor", R.drawable.broad_armor_warrior_1));
		inventory.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_1));
		inventory.setShield(new Shield("Leather Shield", R.drawable.shield_warrior_1));
		inventory.setWeapon(new Weapon("Iron Sword", R.drawable.weapon_warrior_1));
		
		inventory.addInventory(new Weapon("Rogue Weapon 0", R.drawable.weapon_rogue_0));
		inventory.addInventory(new Weapon("Rogue Weapon 1", R.drawable.weapon_rogue_1));
		inventory.addInventory(new Weapon("Rogue Weapon 2", R.drawable.weapon_rogue_2));
		
		// This image goes in action bar.
		ImageView image = (ImageView) findViewById(R.id.character_activity);
		image.setImageBitmap(avatar.getBitmap(inventory.getBitmap()));
		
		/* END INITIALIZATION CODE */
		
		// Helmet
		ImageView helmet_image = (ImageView) findViewById(R.id.inventory_helmet);
		helmet_image.setImageBitmap(getEquipmentImage(inventory.getHelmet().getResId(), "#FFCCFF", -38, 0));
		
		// Weapon
		ImageView weapon_image = (ImageView) findViewById(R.id.inventory_weapon);
		weapon_image.setImageBitmap(getEquipmentImage(inventory.getWeapon().getResId(), "#FFCCFF", 15, -20));
		
		// Character
		ImageView display_image = (ImageView) findViewById(R.id.inventory_character_display);
		display_image.setImageBitmap(avatar.getBitmap(inventory.getBitmap()));
		
		// Shield
		ImageView shield_image = (ImageView) findViewById(R.id.inventory_shield);
		shield_image.setImageBitmap(getEquipmentImage(inventory.getShield().getResId(), "#FFCCFF", -55, -40));
		
		// Armor
		ImageView armor_image = (ImageView) findViewById(R.id.inventory_armor);
		armor_image.setImageBitmap(getEquipmentImage(inventory.getArmor().getResId(), "#FFCCFF", -38, -60));
		
		// Set up Possession Items List
		ArrayList<RpgItem> inventoryList = inventory.getInventoryItems();
		ListView listView = (ListView) findViewById(R.id.inventory_equipment_list);
        adapter = new ItemListAdapter(InventoryActivity.this, R.layout.inventory_list_row, inventoryList);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            ArrayList<RpgItem> equipment;
            ItemListAdapter adapt;
            
            @Override
            public void onItemClick(AdapterView<?> parent,
                    View view, int position, long id) {
            	showInventoryDialog(view, position);
                //equipment.remove(position);
            	// First remove this item from the list.
            	// adapt.remove(equipment.get(position));
            	
            	// Remove item from the underlying list. (May not be necessary)
         
            	// Remove item from current equipment and place it in inventory list
            	// in Avatar object.
            	// RpgItem currEquip = equipment.get(position);
            	
            	
            	// Refresh the Inventory gridview so it shows up there.
            	
                //adapt.notifyDataSetChanged();
            	
            	// Reset the avatar image file.
            	//charImage.setImageBitmap(UserInfo.avatar.getBitmap());
            	
            	
            	
            }
            public OnItemClickListener init(ArrayList<RpgItem> equipList, ItemListAdapter adapter) {
                this.equipment = equipList;
                this.adapt = adapter;
                return this;
            }
            
        }.init(inventoryList, adapter));
	}
	
	public Bitmap getEquipmentImage(int id, String colorString, int xOffset, int yOffset) {
		Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
		bitmap.eraseColor(Color.parseColor(colorString));
		// "#FFCCFF"
		Canvas canvas = new Canvas(bitmap);
		Bitmap equipmentImage = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), id);
		canvas.drawBitmap(equipmentImage, xOffset, yOffset, null);
		return bitmap;
	}
	
	/**
	 * Show options on what to do when the user presses an inventory item
	 * @param v
	 */
	public void showInventoryDialog (View v, final int position) {
		PopupMenu popupMenu = new PopupMenu(InventoryActivity.this, v);
		popupMenu.getMenuInflater().inflate(R.menu.inventory, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			   
			   @Override
			   public boolean onMenuItemClick(MenuItem item) {
				    switch(item.getItemId()) {
				   	   case R.id.inventory_menu_equip:
				   		Toast.makeText(InventoryActivity.this,
					    		"Equipped",
					      Toast.LENGTH_LONG).show();
				   		// Equip item
				   		
				   		// Refresh list 
				   		

				   		break;
					   
				   	   case R.id.inventory_menu_discard:
				   		Toast.makeText(InventoryActivity.this,
					    		"Item discarded",
					      Toast.LENGTH_LONG).show();
				   		// Remove Item from inventory
				   		inventory.removeInventory(position);
				   		
				   		// Refresh list
				   		adapter.notifyDataSetChanged();
				   		break;   
				   }
				   return true;
			   }
		});
		popupMenu.show();
	}


}
