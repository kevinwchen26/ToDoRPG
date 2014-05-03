package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

/**
 * Extra object that contains both an Equipment and a cost. Used for Library and
 * Shop
 * 
 * @author Leon Chen
 * 
 */
public class EquipCost implements Serializable {

	private static final long serialVersionUID = -7606215269118767404L;
	private Equipment equip;
	private int cost;

	/**
	 * Constructor
	 * 
	 * @param tempequip
	 * @param tempcost
	 */
	public EquipCost(Equipment tempequip, int tempcost) {
		this.equip = tempequip;
		this.cost = tempcost;
	}

	/**
	 * getter for Cost
	 * 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * getter for Equipment
	 * 
	 * @return equipment
	 */
	public Equipment getEquipment() {
		return equip;
	}

	/**
	 * Equals function
	 */
	public boolean equals(Object o) {
		EquipCost tempecost = (EquipCost) o;
		return (this.getEquipment().equals(tempecost.getEquipment()) && this
				.getCost() == tempecost.getCost());
	}
}
