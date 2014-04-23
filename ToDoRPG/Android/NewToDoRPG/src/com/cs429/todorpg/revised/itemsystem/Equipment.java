package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

public abstract class Equipment extends RpgItem {
	private int damage;
	private int critical;
	private int multi_hit;
	private ArrayList<NegativeEffects> negEffects;
	private int damage_reduction;
	private int evasion;
	private int accuracy;
	private ArrayList<PositiveEffects> posEffects = new ArrayList<PositiveEffects>();
	
	public Equipment(String name, int resId, int damage, int critical, int multi_hit, ArrayList<NegativeEffects> negEffects,
			int damage_reduction, int evasion, int accuracy, ArrayList<PositiveEffects> posEffects) {
		
		super(name, resId);
		this.damage = damage;
		this.critical = critical;
		this.multi_hit = multi_hit;
		this.negEffects = new ArrayList<NegativeEffects>(negEffects);
		this.damage_reduction = damage_reduction;
		this.evasion = evasion;
		this.accuracy = accuracy;
		this.posEffects = new ArrayList <PositiveEffects>(posEffects);
	}

}