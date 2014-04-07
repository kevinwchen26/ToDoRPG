package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

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
}
