package com.example.avatarcreator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_creation);
		
        // Assign image resources
        Bitmap helmImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.light_helmet);
        Bitmap faceImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.dark);
        avatar = new Avatar(helmImage, faceImage);
        
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
			Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT, Bitmap.Config.ARGB_4444);
			bitmap.eraseColor(Color.parseColor("green"));
			
			
			//create a canvas for drawing all those small images
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(avatar.getFaceImage(), 15,100, null);
			canvas.drawBitmap(avatar.getHelmImage(), 15,0, null);
			
			
			// The result avatar is shown
			intent = new Intent(AvatarCreation.this, ShowMergedResult.class);
			intent.putExtra("merged_image", bitmap);
			startActivity(intent);
			break;
		case R.id.inventory_button:
			intent = new Intent(AvatarCreation.this, Inventory.class);
			startActivity(intent);
			break;
		}
		//create a bitmap of a size which can hold the complete image after merging
		
	}
}