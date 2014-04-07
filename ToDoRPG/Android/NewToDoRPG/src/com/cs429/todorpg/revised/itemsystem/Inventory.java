package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.cs429.todorpg.revised.Avatar;
import com.cs429.todorpg.revised.GameApplication;
import com.cs429.todorpg.revised.R;

public class Inventory {
	private Armor armor;
	private Helmet helmet;
	private Shield shield;
	private Weapon weapon;
	private ArrayList<Equipment> equipmentItems;
	
	public Inventory() {
		this.armor = null;
		this.helmet = null;
		this.shield = null;
		this.weapon = null;
		this.equipmentItems = new ArrayList<Equipment>();
	}
	
	public Inventory(Armor armor, Helmet helmet, Shield shield, Weapon weapon, ArrayList<Equipment> equipmentItems) {
		this.armor = armor;
		this.helmet = helmet;
		this.shield = shield;
		this.weapon = weapon;
		if (equipmentItems != null) {
			this.equipmentItems = equipmentItems;
		}
		else {
			this.equipmentItems = new ArrayList<Equipment>();
		}
	}
	
	/*
	 * Equipment List
	 */
	
	public void addEquipment (Equipment e) {
		equipmentItems.add(e);
	}
	
	public void removeEquipment (int index) {
		equipmentItems.remove(index);
	}
	
	public int numEquipment () {
		return equipmentItems.size();
	}
	
	
	/*
	 * Adders Getters to equipped items
	 */
	
	public Armor getArmor() {
		return armor;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}

	public Helmet getHelmet() {
		return helmet;
	}

	public void setHelmet(Helmet helmet) {
		this.helmet = helmet;
	}

	public Shield getShield() {
		return shield;
	}

	public void setShield(Shield shield) {
		this.shield = shield;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	/**
	 * 
	 * @return Bitmap image of character avatar EXCEPT skin.
	 */
	public Bitmap getBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(Avatar.AVATAR_WIDTH, Avatar.AVATAR_HEIGHT, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		
		// Get Images
		if (armor != null) {
			Bitmap armorImage = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), armor.getResId());
			canvas.drawBitmap(armorImage, 0,0, null);
		}
		else { // Default white shirt
			Bitmap armorImage = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), R.drawable.slim_shirt_white);
			canvas.drawBitmap(armorImage, 0,0, null);
		}
		
		if (helmet != null) {
			Bitmap helmetImage = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), helmet.getResId());
			canvas.drawBitmap(helmetImage, 0,0, null);
		}
		
		if (weapon != null) {
			Bitmap weaponImage = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), weapon.getResId());
			canvas.drawBitmap(weaponImage, 0,0, null);
		}
		
		if (shield != null) {
			Bitmap shieldImage = BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), shield.getResId());
			canvas.drawBitmap(shieldImage, 0,0, null);
		}
		return bitmap;
	}
}
