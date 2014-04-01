package com.example.avatarcreator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * 
 * @author paulkim6, kwchen3
 * 
 * Class that holds relevant information of The Avatar
 * 
 *
 */
public class Avatar {
	// Some constants
	public final static int AVATAR_WIDTH = 140;
	public final static int AVATAR_HEIGHT = 280;
	
	private Bitmap bodyImage;
	private Inventory inventory;
	
	public Avatar () {
		this.bodyImage = null;
	}
	
	public Avatar (int bodyId, Inventory inventory) {
		this.bodyImage = BitmapFactory.decodeResource(UserInfo.getContext().getResources(), bodyId);
		this.inventory = inventory;
	}
	
	public Bitmap getBodyImage() {
		return bodyImage;
	}
	public void setBodyImage(Bitmap bodyImage) {
		this.bodyImage = bodyImage;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Bitmap getBitmap() {
		if (bodyImage == null) // Sanity check
			return null;
		
		Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT, Bitmap.Config.ARGB_4444);
		
		// Merge each image.
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(bodyImage, 15,100, null);
		
		// Get equipped item images
		Bitmap helmImage = inventory.getHelmet().getBitmap();
		
		// Merge image layers of equipped items.
		if (helmImage != null)
			canvas.drawBitmap(helmImage, 15,0, null);
		
		return bitmap;
	}
}
