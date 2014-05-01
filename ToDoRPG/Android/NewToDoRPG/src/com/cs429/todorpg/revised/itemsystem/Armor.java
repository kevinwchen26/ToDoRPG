package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;
import java.util.ArrayList;

public class Armor extends Equipment implements Serializable{

	private static final long serialVersionUID = 3953874955655111200L;

	/**
	 * Constructor
	 * @param name
	 * @param resId
	 * @param damage
	 * @param critical
	 * @param multi_hit
	 * @param negEffects
	 * @param damage_reduction
	 * @param evasion
	 * @param accuracy
	 * @param posEffects
	 */
	public Armor(String name, int resId, int damage, int critical, int multi_hit, ArrayList<NegativeEffects> negEffects,
			int damage_reduction, int evasion, int accuracy, ArrayList<PositiveEffects> posEffects) {
		super(name, resId, damage, critical, multi_hit, negEffects, damage_reduction, evasion, 
				accuracy, posEffects);
		// TODO Auto-generated constructor stub
	}

}
