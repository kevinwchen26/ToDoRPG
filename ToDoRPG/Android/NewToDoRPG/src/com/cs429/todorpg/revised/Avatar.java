package com.cs429.todorpg.revised;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

public class Avatar {
	// Some constants
	public final static int AVATAR_WIDTH = 190;
	public final static int AVATAR_HEIGHT = 190;
	
	Bitmap skin;
	
	public Avatar () {
		this.skin = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), R.drawable.skin_c06534);
	}
	
	public Avatar(int skinId) {
		this.skin = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), skinId);
	}

	public Bitmap getBitmap(Bitmap equipmentImage) {
		Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT, Bitmap.Config.ARGB_8888);
		
		// Merge each image.
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(Color.parseColor("#FFCCFF"));
		canvas.drawBitmap(skin, 0,0, null);
		canvas.drawBitmap(equipmentImage, 0,0, null);
		return bitmap;
	}
}
