package com.cs429.todorpg.revised;
import android.os.Bundle;
import android.view.WindowManager;
import java.util.ArrayList;

import android.app.Dialog;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.ShopListAdapter;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.RpgItem;
import com.cs429.todorpg.revised.itemsystem.Shop;
import com.cs429.todorpg.revised.itemsystem.Weapon;

public class ShopActivity extends BaseActivity {
	ShopListAdapter adapter;
	Shop shop;
	
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
		this.shop = new Shop(); // Shop should be a persistent object via database.
		
		
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
				   		   // Diplay item info
				   		   showItemInfoDialog(shop.getItem(position));
				   		
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
	
	
	public void showItemInfoDialog(RpgItem item) {
		final Dialog d = new Dialog(ShopActivity.this);
		d.setContentView(R.layout.item_info_dialog);
		d.setTitle(item.getName());
		
		TextView text = (TextView) d.findViewById(R.id.item_info_text);
		
		// Can display Item stats here
		text.setText("Item stats");
		
		
		ImageView image = (ImageView) d.findViewById(R.id.item_info_image);
		image.setImageResource(item.getResId());
		Button dialogButton = (Button) d.findViewById(R.id.item_info_dialog_button);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				d.dismiss();
			}
		});

		d.show();
	}
}


