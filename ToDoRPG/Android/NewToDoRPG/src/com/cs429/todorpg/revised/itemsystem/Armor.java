package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

public class Armor extends Equipment {

	public Armor(String name, int resId, int damage, int critical, int multi_hit, ArrayList<NegativeEffects> negEffects,
			int damage_reduction, int evasion, int accuracy, ArrayList<PositiveEffects> posEffects) {
		super(name, resId, damage, critical, multi_hit, negEffects, damage_reduction, evasion, 
				accuracy, posEffects);
		// TODO Auto-generated constructor stub
	}

}
