package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

public class Shop {

	private ArrayList<RpgItem> shopItems;

	public Shop() {
		this.shopItems = new ArrayList<RpgItem>();
	}
	
	public Shop(ArrayList<RpgItem> shopItems) {
		super();
		this.shopItems = shopItems;
	}

	/*
	 * GETTERS & SETTERS
	 */
	public ArrayList<RpgItem> getShopItems() {
		return shopItems;
	}

	public void setShopItems(ArrayList<RpgItem> shopItems) {
		this.shopItems = shopItems;
	}
	
	// Item setting for shop
	
	public void addItem(RpgItem i){
		this.shopItems.add(i);
	}
	
	public RpgItem removeItem(int i) {
		return this.shopItems.remove(i);
	}
	
	public RpgItem getItem(int i) {
		return this.shopItems.get(i);
	}
}
