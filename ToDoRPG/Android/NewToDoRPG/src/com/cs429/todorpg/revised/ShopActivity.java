package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.ItemListAdapter;
import com.cs429.todorpg.revised.controller.ShopListAdapter;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.RpgItem;
import com.cs429.todorpg.revised.itemsystem.Shop;
import com.cs429.todorpg.revised.itemsystem.Weapon;

public class ShopActivity extends BaseActivity {
	ShopListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.shop_activity);
		setHeader(R.id.header);
		
		
		/* 
		   INITIALIZE CODE, TEMPORARY. THIS SHOULD BE DONE
		   WHEN THE APPLICATION STARTS
		   TODO: Migrate this code to application initialization
		   section 
		*/
		// Set up Shop Items List
		Shop shop = new Shop();
		
		ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
		ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
		
		shop.addItem(new Weapon("Rogue Weapon 0", R.drawable.weapon_rogue_0, 1, 1, 1, negs, 1, 1, 1, poss));
		shop.addItem(new Weapon("Rogue Weapon 1", R.drawable.weapon_rogue_1, 1, 1, 1, negs, 1, 1, 1, poss));
		shop.addItem(new Weapon("Rogue Weapon 2", R.drawable.weapon_rogue_2, 1, 1, 1, negs, 1, 1, 1, poss));
		
		
		/* END INITIALIZATION CODE */
		
		ArrayList<RpgItem> inventoryList = shop.getShopItems();
		ListView listView = (ListView) findViewById(R.id.shop_list);
        adapter = new ShopListAdapter(ShopActivity.this, R.layout.shop_list_row, inventoryList);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent,
                    View view, int position, long id) {
            	showShopDialog(view, position);
            }
        });
	}
	
	/**
	 * Show options on what to do when the user presses an a shop item
	 * @param v
	 */
	public void showShopDialog (View v, final int position) {
		PopupMenu popupMenu = new PopupMenu(ShopActivity.this, v);
		popupMenu.getMenuInflater().inflate(R.menu.shop, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			   
			   @Override
			   public boolean onMenuItemClick(MenuItem item) {
				    switch(item.getItemId()) {
				   	   case R.id.shop_menu_purchase:
				   		Toast.makeText(ShopActivity.this,
					    		"Purchased",
					      Toast.LENGTH_LONG).show();
				   		// Equip item
				   		//inventory.equipItem(position, true);

				   		// Refresh list
				   		ShopActivity.this.runOnUiThread(new Runnable() {
				   			public void run() {
				   				adapter.notifyDataSetChanged();
				   			}
				   		});
				   		break;
					   
				   	   case R.id.shop_menu_info:
				   		Toast.makeText(ShopActivity.this,
					    		"Item information",
					      Toast.LENGTH_LONG).show();
				   		// Remove Item from inventory
				   		//inventory.removeInventory(position);
				   		
				   		// Refresh list
				   		ShopActivity.this.runOnUiThread(new Runnable() {
				   			public void run() {
				   				adapter.notifyDataSetChanged();
				   			}
				   		});
				   		break;   
				   }
				   return true;
			   }
		});
		popupMenu.show();
	}
}


