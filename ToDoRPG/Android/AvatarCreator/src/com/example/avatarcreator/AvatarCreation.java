package com.example.avatarcreator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author paulkim6, kwchen3
 * 
 * Class that holds relevant information of Avatar
 * Based some segments of code off of this URL: 
 * http://www.chansek.com/2012/06/merge-multiple-images-into-one-image-in.html
 *
 */
public class AvatarCreation extends Activity implements OnClickListener {
	
	ArrayList<Bitmap> smallImages;
	Avatar avatar;
	
	// Definitions for avatar size
	public final static int AVATAR_WIDTH = 140;
	public final static int AVATAR_HEIGHT = 280;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_creation);
        
        /*
         * THIS CODE NEEDS TO RUN RIGHT AFTER USER LOGIN 
         * 
         */
        
        // Create inventory
        Helmet helmet = new Helmet(1, "light helmet", R.drawable.light_helmet);
        Inventory inventory = new Inventory();
        inventory.initHelmet(helmet);
        
        // Populate inventory
        inventory.add(new Helmet(1, "wiz hat", R.drawable.wiz_hat));
        inventory.add(new Helmet(1, "light helmet", R.drawable.light_helmet));
        inventory.add(new Helmet(1, "wiz hat", R.drawable.wiz_hat));
        inventory.add(new Helmet(1, "light helmet", R.drawable.light_helmet));
        inventory.add(new Helmet(1, "wiz hat", R.drawable.wiz_hat));
        inventory.add(new Helmet(1, "light helmet", R.drawable.light_helmet));
        
        
        UserInfo.avatar = new Avatar(R.drawable.dark, inventory);
        
		Button mergeButton = (Button) findViewById(R.id.merge_button);
		mergeButton.setOnClickListener(this);
		
		Button inventoryButton = (Button) findViewById(R.id.inventory_button);
		inventoryButton.setOnClickListener(this);
    }
    
	/*
	 * This method actually implements the code for merging the small images
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	
	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		
		case R.id.merge_button:
			/*
				Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT, Bitmap.Config.ARGB_4444);
				bitmap.eraseColor(Color.parseColor("green"));
				
				
				//create a canvas for drawing all those small images
				Canvas canvas = new Canvas(bitmap);
				canvas.drawBitmap(avatar.getBodyImage(), 15,100, null);
				canvas.drawBitmap(avatar.getHelmImage(), 15,0, null);
			
			*/
			
			// The result avatar is shown
			intent = new Intent(AvatarCreation.this, ShowMergedResult.class);
			intent.putExtra("merged_image", UserInfo.avatar.getBitmap());
			startActivity(intent);
			break;
			
		case R.id.inventory_button:
			intent = new Intent(AvatarCreation.this, InventoryActivity.class);
			startActivity(intent);
			break;
		}
		//create a bitmap of a size which can hold the complete image after merging
		
	}
}