package com.cs429.todorpg.revised;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Avatar {
	// Some constants
	public final static int AVATAR_WIDTH = 190;
	public final static int AVATAR_HEIGHT = 190;
	
	public Avatar () {
	}
	

	public Bitmap getBitmap() {
		
		Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT, Bitmap.Config.ARGB_8888);
		
		// Get Images
		Bitmap skin = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), R.drawable.skin_0ff591);
		Bitmap shirt = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), R.drawable.slim_shirt_white);
		Bitmap weapon = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), R.drawable.weapon_rogue_2);
		
		
		// Merge each image.
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(skin, 0,0, null);
		canvas.drawBitmap(shirt, 0,0, null);
		canvas.drawBitmap(weapon, 0,0, null);
		
		
		
		
		//return Bitmap.createScaledBitmap(bitmap, dpToPx(60), dpToPx(60), false);
		return bitmap;
	}
	
	private int dpToPx(int dp) {
	    float density = GameApplication.getAppContext().getResources().getDisplayMetrics().density;
	    return Math.round((float)dp * density);
	}
}
