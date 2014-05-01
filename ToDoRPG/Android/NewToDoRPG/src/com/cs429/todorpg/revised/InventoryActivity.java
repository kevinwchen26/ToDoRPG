package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.ItemListAdapter;
import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.RpgItem;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class InventoryActivity extends BaseActivity {
	// Equipment Temporaryily public
	ItemListAdapter adapter;
	ImageView display_image;
	ImageView helmet_image;
	ImageView weapon_image;
	ImageView shield_image;
	ImageView armor_image;
	
	// Equipment Types Identifiers (for switch)
	private final int HELMET = 0;
	private final int WEAPON = 1;
	private final int SHIELD = 2;
	private final int ARMOR = 3;
	
	/**
	 * Requirements: Inventory must not be null.
	 */
	private static SQLiteHelper db;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.inventory_activity);
		setHeader(R.id.header);
		// This image goes in action bar.
		ImageView image = (ImageView) findViewById(R.id.character_activity);
		image.setImageBitmap(app.avatar.getBitmap());
		db = new SQLiteHelper(this);

		/* END INITIALIZATION CODE */

		helmet_image = (ImageView) findViewById(R.id.inventory_helmet);
		weapon_image = (ImageView) findViewById(R.id.inventory_weapon);
		display_image = (ImageView) findViewById(R.id.inventory_character_display);
		shield_image = (ImageView) findViewById(R.id.inventory_shield);
		armor_image = (ImageView) findViewById(R.id.inventory_armor);
		
		helmet_image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (app.avatar.inventory.isHelmetSet())
					showEquipmentDialog(v, HELMET);
			}
		});
		
		weapon_image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (app.avatar.inventory.isWeaponSet())
					showEquipmentDialog(v, WEAPON);
			}
		});
		
		shield_image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (app.avatar.inventory.isShieldSet())
					showEquipmentDialog(v, SHIELD);
			}
		});
		
		armor_image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (app.avatar.inventory.isArmorSet())
					showEquipmentDialog(v, ARMOR);
			}
		});
		
		setImageViews();
		
		// Set up Possession Items List
		ArrayList<RpgItem> inventoryList = app.avatar.inventory.getInventoryItems();
		ListView listView = (ListView) findViewById(R.id.inventory_equipment_list);
        adapter = new ItemListAdapter(InventoryActivity.this, R.layout.inventory_list_row, inventoryList);
        adapter.setNotifyOnChange(true);
        db.addInventory(app.avatar.inventory);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent,
                    View view, int position, long id) {
            	showInventoryDialog(view, position);
            }
        });
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
				   		app.avatar.inventory.equipItem(position, false);
				   		// Refresh list 
				   		
				   		InventoryActivity.this.runOnUiThread(new Runnable() {
				   			public void run() {
				   				adapter.notifyDataSetChanged();
				   				db.addInventory(app.avatar.inventory);
				   			}
				   		});
				   		
				   		setImageViews();
				   		break;
					   
				   	   case R.id.inventory_menu_discard:
				   		Toast.makeText(InventoryActivity.this,
					    		"Item discarded",
					      Toast.LENGTH_LONG).show();
				   		// Remove Item from inventory
				   		app.avatar.inventory.removeInventory(position);
				   		
				   		// Refresh list
				   		adapter.notifyDataSetChanged();
				   		db.addInventory(app.avatar.inventory);
				   		setImageViews();
				   		break;   
				   }
				   return true;
			   }
		});
		popupMenu.show();
	}
	
	public void showEquipmentDialog (View v, final int equipmentType) {
		PopupMenu popupMenu = new PopupMenu(InventoryActivity.this, v);
		popupMenu.getMenuInflater().inflate(R.menu.equipment, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			   
			   @Override
			   public boolean onMenuItemClick(MenuItem item) {
				    switch(item.getItemId()) {
				   	   case R.id.equipment_menu_unequip:
				   		Toast.makeText(InventoryActivity.this,
					    		"Unequipped",
					      Toast.LENGTH_LONG).show();
				   		// Unequip item
				   		switch(equipmentType) {
				   		case HELMET:
				   			app.avatar.inventory.addInventory(app.avatar.inventory.getHelmet());
				   			app.avatar.inventory.setHelmet(null);
				   			break;
				   		case WEAPON:
				   			app.avatar.inventory.addInventory(app.avatar.inventory.getWeapon());
				   			app.avatar.inventory.setWeapon(null);
				   			break;
				   		case SHIELD:
				   			app.avatar.inventory.addInventory(app.avatar.inventory.getShield());
				   			app.avatar.inventory.setShield(null);
				   			break;
				   		case ARMOR:
				   			app.avatar.inventory.addInventory(app.avatar.inventory.getArmor());
				   			app.avatar.inventory.setArmor(null);
				   			break;
				   		}
				   		
				   		// Refresh list and avatar 
				   		adapter.notifyDataSetChanged();
				   		db.addInventory(app.avatar.inventory);
				   		setImageViews();
				   		break;
					   
				   	   case R.id.equipment_menu_discard:
				   		Toast.makeText(InventoryActivity.this,
					    		"Item discarded",
					      Toast.LENGTH_LONG).show();
				   		// Remove Item
				   		switch(equipmentType) {
				   		case HELMET:
				   			app.avatar.inventory.setHelmet(null);
				   			break;
				   		case WEAPON:
				   			app.avatar.inventory.setWeapon(null);
				   			break;
				   		case SHIELD:
				   			app.avatar.inventory.setShield(null);
				   			break;
				   		case ARMOR:
				   			app.avatar.inventory.setArmor(null);
				   			break;
				   		}
				   		
				   		// Refresh list
				   		adapter.notifyDataSetChanged();
				   		db.addInventory(app.avatar.inventory);
				   		setImageViews();
				   		break;   
				   }
				   return true;
			   }
		});
		popupMenu.show();
	}
	
	public void setImageViews() {
		helmet_image.setImageBitmap(getEquipmentImage(app.avatar.inventory.getHelmet(), "#FFCCFF", -38, 0));
		weapon_image.setImageBitmap(getEquipmentImage(app.avatar.inventory.getWeapon(), "#FFCCFF", 15, -20));
		display_image.setImageBitmap(app.avatar.getBitmap());
		shield_image.setImageBitmap(getEquipmentImage(app.avatar.inventory.getShield(), "#FFCCFF", -55, -40));
		armor_image.setImageBitmap(getEquipmentImage(app.avatar.inventory.getArmor(), "#FFCCFF", -38, -60));
	}

	public Bitmap getEquipmentImage(RpgItem item, String colorString, int xOffset, int yOffset) {
		
		Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
		bitmap.eraseColor(Color.parseColor(colorString));
		
		Canvas canvas = new Canvas(bitmap);
		if (item != null) {
			int id = item.getResId();
			Bitmap equipmentImage = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), id);
			canvas.drawBitmap(equipmentImage, xOffset, yOffset, null);
		}
		return bitmap;
	}
}
