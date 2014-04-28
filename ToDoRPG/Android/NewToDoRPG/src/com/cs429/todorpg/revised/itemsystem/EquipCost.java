package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

public class EquipCost implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7606215269118767404L;
	private Equipment equip;
	private int cost;
	
	public EquipCost(Equipment tempequip, int tempcost) {
		this.equip = tempequip;
		this.cost = tempcost;
	}

	public int getCost() {
		return cost;
	}

	public Equipment getEquipment() {
		return equip;
	}
	
	public boolean equals(Object o) {
		EquipCost tempecost = (EquipCost) o;
		return (this.getEquipment().equals(tempecost.getEquipment()) && this.getCost() == tempecost.getCost());
	}
}
