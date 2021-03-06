package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Helmet class
 * 
 * @author Leon Chen
 * 
 */
public class Helmet extends Equipment implements Serializable {

	private static final long serialVersionUID = -8417234289259450993L;

	/**
	 * Constructor
	 */
	public Helmet(String name, int resId, int damage, int critical,
			int multi_hit, ArrayList<NegativeEffects> negEffects,
			int damage_reduction, int evasion, int accuracy,
			ArrayList<PositiveEffects> posEffects) {
		super(name, resId, damage, critical, multi_hit, negEffects,
				damage_reduction, evasion, accuracy, posEffects);
	}

}
