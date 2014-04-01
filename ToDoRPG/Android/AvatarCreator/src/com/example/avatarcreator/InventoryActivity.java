package com.example.avatarcreator;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class InventoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
		
		// Set image
		//Bitmap bitmap = Bitmap.createBitmap(AvatarCreation.AVATAR_WIDTH, AvatarCreation.AVATAR_HEIGHT, Bitmap.Config.ARGB_4444);
		//bitmap.eraseColor(Color.parseColor("green"));
		
		ImageView charImage = (ImageView) findViewById(R.id.character_image);
		charImage.setImageBitmap(UserInfo.avatar.getBitmap());
		
		// Scale image size to match screen (requires minimum API level 13)
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;
		
		charImage.getLayoutParams().height = screenWidth / 2;
		//charImage.getLayoutParams().width = screenHeight / 2;
		
		
		
		// Set up equipment list view
		ArrayList<CharacterItem> equipList = UserInfo.avatar.getInventory().getEquipment();
		ListView listView = (ListView) findViewById(R.id.equipment_list);
        EquipmentListAdapter adapter = new EquipmentListAdapter(InventoryActivity.this, R.layout.equipment_list_row, UserInfo.avatar.getInventory().getEquipment());
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            ArrayList<CharacterItem> equipment;
            @Override
            public void onItemClick(AdapterView<?> parent,
                    View view, int position, long id) {
                // Show popup options for what to do with equipment 
            	
            	
            	
            	
            	
            }
            public OnItemClickListener init(ArrayList<CharacterItem> equipList) {
                this.equipment = equipList;
                return this;
            }
            
        }.init(equipList));
		
		
		// Set up inventory grid view
		GridView gridview = (GridView) findViewById(R.id.inventory_grid_view);
	    gridview.setAdapter(new ImageAdapter(this, UserInfo.avatar.getInventory()));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	// Show popup options for what to do with inventory items
	            //Toast.makeText(Inventory.this, "" + position, Toast.LENGTH_SHORT).show();
	            showInventoryDialog(v);
	            //v.setBackgroundColor(Color.RED);
	            
	            
	            
	            
	            
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
		return true;
	}
	
	public void showInventoryDialog (View v) {
		PopupMenu popupMenu = new PopupMenu(InventoryActivity.this, v);
		popupMenu.getMenuInflater().inflate(R.menu.inventory, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			   
			   @Override
			   public boolean onMenuItemClick(MenuItem item) {
			    Toast.makeText(InventoryActivity.this,
			      item.toString(),
			      Toast.LENGTH_LONG).show();
			    return true;
			   }
			  });
			    
		popupMenu.show();
		
	}

}
