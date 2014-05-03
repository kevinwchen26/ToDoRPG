package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

/**
 * Class for the shop.
 * 
 * @author Leon Chen
 * 
 */
public class Shop {

	private ArrayList<EquipCost> shopItems;

	/**
	 * Constructor, creates an empty shop list
	 */
	public Shop() {
		this.shopItems = new ArrayList<EquipCost>();
	}

	/**
	 * Constructor, creates a shoplist given shopItems
	 * 
	 * @param shopItems
	 */
	public Shop(ArrayList<EquipCost> shopItems) {
		super();
		this.shopItems = shopItems;
	}

	/**
	 * gets the Shop Items
	 * 
	 * @return all shop items
	 */
	public ArrayList<EquipCost> getShopItems() {
		return shopItems;
	}

	/**
	 * sets a shop items
	 * 
	 * @param shopItems
	 */
	public void setShopItems(ArrayList<EquipCost> shopItems) {
		this.shopItems = shopItems;
	}

	/**
	 * adds an Item to the shop list
	 * 
	 * @param i
	 */
	public void addItem(EquipCost i) {
		this.shopItems.add(i);
	}

	/**
	 * removes an item from the shop list
	 * 
	 * @param i
	 *            position of item
	 * @return the item that was removed
	 */
	public EquipCost removeItem(int i) {
		return this.shopItems.remove(i);
	}

	/**
	 * gets an item from the shop list
	 * 
	 * @param i
	 *            position of item
	 * @return item that was at the position
	 */
	public EquipCost getItem(int i) {
		return this.shopItems.get(i);
	}
}
