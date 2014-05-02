package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

public class Shop {

	private ArrayList<EquipCost> shopItems;

	public Shop() {
		this.shopItems = new ArrayList<EquipCost>();
	}

	public Shop(ArrayList<EquipCost> shopItems) {
		super();
		this.shopItems = shopItems;
	}

	/*
	 * GETTERS & SETTERS
	 */
	public ArrayList<EquipCost> getShopItems() {
		return shopItems;
	}

	public void setShopItems(ArrayList<EquipCost> shopItems) {
		this.shopItems = shopItems;
	}

	// Item setting for shop

	public void addItem(EquipCost i) {
		this.shopItems.add(i);
	}

	public EquipCost removeItem(int i) {
		return this.shopItems.remove(i);
	}

	public EquipCost getItem(int i) {
		return this.shopItems.get(i);
	}
}
