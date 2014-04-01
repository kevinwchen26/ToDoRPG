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
	
	private Bitmap helmImage;
	private Bitmap bodyImage;
	
	public Avatar () {
		this.helmImage = null;
		this.bodyImage = null;
	}
	
	public Avatar (Bitmap bodyImage) {
		this.helmImage = null;
		this.bodyImage = bodyImage;
	}
	
	public void setAvatar(Inventory inventory) {
		helmImage = BitmapFactory.decodeResource(UserInfo.getContext().getResources(), inventory.getHelmet().getResourceId());
        bodyImage = BitmapFactory.decodeResource(UserInfo.getContext().getResources(), R.drawable.dark);
		
	}
	
	public Bitmap getHelmImage() {
		return helmImage;
	}
	public void setHelmImage(Bitmap headImage) {
		this.helmImage = headImage;
	}
	public Bitmap getBodyImage() {
		return bodyImage;
	}
	public void setBodyImage(Bitmap bodyImage) {
		this.bodyImage = bodyImage;
	}
	
	public Bitmap getBitmap() {
		if (bodyImage == null) // Sanity check
			return null;
		
		Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT, Bitmap.Config.ARGB_4444);
		
		// Merge each image.
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(bodyImage, 15,100, null);
		
		// Merge image layers of equipped items.
		if (helmImage != null)
			canvas.drawBitmap(helmImage, 15,0, null);
		
		return bitmap;
	}
}
