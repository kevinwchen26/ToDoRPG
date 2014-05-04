package com.cs429.todorpg.revised;

import java.io.Serializable;

import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.model.ToDoCharacter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

/**
 * Avatar Activity. Keeps track of the Inventory and Charater stats for all
 * Acitivities
 * 
 * @author Leon Chen
 * 
 */
public class Avatar implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -717939185743407480L;
	// Some constants
	public final static int AVATAR_WIDTH = 190;
	public final static int AVATAR_HEIGHT = 190;

	public Inventory inventory;
	public ToDoCharacter toDoCharacter;

	/**
	 * returns the Character
	 * 
	 * @return toDoCharacter
	 */
	public ToDoCharacter getToDoCharacter() {
		return toDoCharacter;
	}

	/**
	 * sets the todocharacter
	 * 
	 * @param toDoCharacter
	 */
	public void setToDoCharacter(ToDoCharacter toDoCharacter) {
		this.toDoCharacter = toDoCharacter;
	}

	/**
	 * gets the Inventory
	 * 
	 * @return inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * sets the Inventory
	 * 
	 * @param inventory
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	/**
	 * When first created, Inventory is blank
	 */
	public Avatar() {

		inventory = new Inventory();
	}

	/**
	 * draws out the avatar inventory
	 * 
	 * @return bitmat of avatar with correct equipment
	 */
	public Bitmap getBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT,
				Bitmap.Config.ARGB_8888);
		Context context = GameApplication.getAppContext();
		Bitmap skin = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.skin_c06534);
		// Merge each image.
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(Color.parseColor("#FFCCFF"));
		canvas.drawBitmap(skin, 0, 0, null);

		if (inventory == null) {
			Log.e("appdebug", "inventory is null");
		}

		canvas.drawBitmap(inventory.getBitmap(), 0, 0, null);
		return bitmap;
	}

	/**
	 * clears the bitmap
	 * 
	 * @return clean bitmap
	 */
	public Bitmap getClearBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT,
				Bitmap.Config.ARGB_8888);
		Context context = GameApplication.getAppContext();
		Bitmap skin = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.skin_c06534);
		// Merge each image.
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(skin, 0, 0, null);
		canvas.drawBitmap(inventory.getBitmap(), 0, 0, null);
		return bitmap;
	}
}
