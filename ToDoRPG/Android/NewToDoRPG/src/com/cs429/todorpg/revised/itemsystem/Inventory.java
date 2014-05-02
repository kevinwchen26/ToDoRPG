package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.cs429.todorpg.revised.Avatar;
import com.cs429.todorpg.revised.GameApplication;
import com.cs429.todorpg.revised.R;

public class Inventory implements Serializable {

	private static final long serialVersionUID = -7795460230072089745L;
	private Armor armor;
	private Helmet helmet;
	private Shield shield;
	private Weapon weapon;
	private Weapon secondary;
	private ArrayList<RpgItem> inventoryItems;

	/**
	 * Constructor for null Inventory
	 */
	public Inventory() {
		this.armor = null;
		this.helmet = null;
		this.shield = null;
		this.weapon = null;
		this.secondary = null;
		this.inventoryItems = new ArrayList<RpgItem>();
	}

	/**
	 * Constructor for inventory
	 * 
	 * @param armor
	 * @param helmet
	 * @param shield
	 * @param weapon
	 * @param secondary
	 * @param equipmentItems
	 */
	public Inventory(Armor armor, Helmet helmet, Shield shield, Weapon weapon,
			Weapon secondary, ArrayList<RpgItem> equipmentItems) {
		this.armor = armor;
		this.helmet = helmet;
		this.shield = shield;
		this.weapon = weapon;
		this.secondary = secondary;
		if (equipmentItems != null) {
			this.inventoryItems = equipmentItems;
		} else {
			this.inventoryItems = new ArrayList<RpgItem>();
		}
	}

	/**
	 * various helpers for inventory
	 * 
	 * @param e
	 */
	public void addInventory(RpgItem e) {
		inventoryItems.add(e);
	}

	public void removeInventory(int index) {
		inventoryItems.remove(index);
	}

	public int numInventory() {
		return inventoryItems.size();
	}

	public ArrayList<RpgItem> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(ArrayList<RpgItem> equipmentItems) {
		this.inventoryItems = equipmentItems;
	}

	/**
	 * various getters and setters for Object
	 * 
	 * @return
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

	public Weapon getSecondary() {
		return secondary;
	}

	public void setSecondary(Weapon weapon) {
		this.secondary = weapon;
	}

	/**
	 * boolean methods
	 * 
	 * @return
	 */
	public boolean isArmorSet() {
		return this.armor != null;
	}

	public boolean isHelmetSet() {
		return this.helmet != null;
	}

	public boolean isShieldSet() {
		return this.shield != null;
	}

	public boolean isWeaponSet() {
		return this.weapon != null;
	}

	public boolean isSecondarySet() {
		return this.secondary != null;
	}

	/**
	 * Function handles item equipping
	 * 
	 * @param item
	 */
	public void equipItem(int position, boolean second) {
		RpgItem item = inventoryItems.get(position);
		if (item instanceof Helmet) {
			if (this.helmet == null) { // just equip item, you don't have
				// anything on
				this.helmet = (Helmet) item;
				inventoryItems.remove(position);
			} else { // store currently equipped item in inventory, equip new
				// item
				Helmet temp = this.helmet;
				this.helmet = (Helmet) item;
				inventoryItems.remove(position);
				inventoryItems.add(temp);
			}
		} else if (item instanceof Weapon && !second) {
			if (this.weapon == null) { // just equip item, you don't have
				// anything on
				this.weapon = (Weapon) item;
				inventoryItems.remove(position);
			} else { // store currently equipped item in inventory, equip new
				// item
				Weapon temp = this.weapon;
				this.weapon = (Weapon) item;
				inventoryItems.remove(position);
				inventoryItems.add(temp);
			}
		} else if (item instanceof Weapon && second) {
			if (this.secondary == null) { // just equip item, you don't have
				// anything on
				this.secondary = (Weapon) item;
				inventoryItems.remove(position);
			} else { // store currently equipped item in inventory, equip new
				// item
				Weapon temp = this.secondary;
				this.secondary = (Weapon) item;
				inventoryItems.remove(position);
				inventoryItems.add(temp);
			}
		} else if (item instanceof Shield) {
			if (this.shield == null) { // just equip item, you don't have
				// anything on
				this.shield = (Shield) item;
				inventoryItems.remove(position);
			} else { // store currently equipped item in inventory, equip new
				// item
				Shield temp = this.shield;
				this.shield = (Shield) item;
				inventoryItems.remove(position);
				inventoryItems.add(temp);
			}
		} else if (item instanceof Armor) {
			if (this.armor == null) { // just equip item, you don't have
				// anything on
				this.armor = (Armor) item;
				inventoryItems.remove(position);
			} else { // store currently equipped item in inventory, equip new
				// item
				Armor temp = this.armor;
				this.armor = (Armor) item;
				inventoryItems.remove(position);
				inventoryItems.add(temp);
			}
		}

	}

	/**
	 * 
	 * @return Bitmap image of character avatar EXCEPT skin.
	 */
	public Bitmap getBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(Avatar.AVATAR_WIDTH,
				Avatar.AVATAR_HEIGHT, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		// Get Images
		if (armor != null) {
			Bitmap armorImage = BitmapFactory.decodeResource(GameApplication
					.getAppContext().getResources(), armor.getResId());
			canvas.drawBitmap(armorImage, 0, 0, null);
		} else { // Default white shirt
			Bitmap armorImage = BitmapFactory.decodeResource(GameApplication
					.getAppContext().getResources(),
					R.drawable.slim_shirt_white);
			canvas.drawBitmap(armorImage, 0, 0, null);
		}

		if (helmet != null) {
			Bitmap helmetImage = BitmapFactory.decodeResource(GameApplication
					.getAppContext().getResources(), helmet.getResId());
			canvas.drawBitmap(helmetImage, 0, 0, null);
		}

		if (weapon != null) {
			Bitmap weaponImage = BitmapFactory.decodeResource(GameApplication
					.getAppContext().getResources(), weapon.getResId());
			canvas.drawBitmap(weaponImage, 0, 0, null);
		}

		if (secondary != null) {
			Bitmap weaponImage = BitmapFactory.decodeResource(GameApplication
					.getAppContext().getResources(), secondary.getResId());
			canvas.drawBitmap(weaponImage, 0, 0, null);
		}

		if (shield != null) {
			Bitmap shieldImage = BitmapFactory.decodeResource(GameApplication
					.getAppContext().getResources(), shield.getResId());
			canvas.drawBitmap(shieldImage, 0, 0, null);
		}
		return bitmap;
	}

	public boolean equals(Object o) {
		Inventory inventory = (Inventory) o;
		return (((this.getArmor() == null && inventory.getArmor() == null) || (this
				.getArmor().equals(inventory.getArmor())))
				&& ((this.getHelmet() == null && inventory.getHelmet() == null) || (this
						.getHelmet().equals(inventory.getHelmet())))
						&& ((this.getShield() == null && inventory.getShield() == null) || (this
								.getShield().equals(inventory.getShield())))
								&& ((this.getWeapon() == null && inventory.getWeapon() == null) || (this
										.getWeapon().equals(inventory.getWeapon())))
										&& ((this.getSecondary() == null && inventory.getSecondary() == null) || (this
												.getSecondary().equals(inventory.getSecondary()))) && this
												.getInventoryItems().equals(inventory.getInventoryItems()));
	}
}
