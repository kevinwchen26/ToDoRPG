package com.cs429.todorpg.revised.itemsystem;

public class EquipCost {
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
