package com.cs429.todorpg.revised.itemsystem;

import java.util.ArrayList;

import android.util.Log;

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
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getCritical() {
		return critical;
	}

	public void setCritical(int critical) {
		this.critical = critical;
	}
	
	public int getMulti_Hit() {
		return multi_hit;
	}

	public void setMulti_Hit(int multi_hit) {
		this.multi_hit = multi_hit;
	}
	
	public int getDamage_Reduction() {
		return damage_reduction;
	}

	public void setDamage_Reduction(int damage_reduction) {
		this.damage_reduction = damage_reduction;
	}
	
	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	public int getEvasion() {
		return evasion;
	}

	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}
	
	public ArrayList<NegativeEffects> getnegEffects() {
		return negEffects;
	}

	public void setnegEffects(ArrayList<NegativeEffects> negeffects) {
		this.negEffects = new ArrayList<NegativeEffects> (negeffects);
	}
	
	public ArrayList<PositiveEffects> getposEffects() {
		return posEffects;
	}

	public void setposEffects(ArrayList<PositiveEffects> poseffects) {
		this.posEffects = new ArrayList<PositiveEffects> (poseffects);
	}
	
	public boolean equals(Object o) {
		Equipment equip = (Equipment) o;
		return (this.getName().equals(equip.getName()) && this.getResId() == equip.getResId() &&
				this.getDamage() == equip.getDamage() && this.getCritical() == equip.getCritical() &&
				this.getMulti_Hit() == equip.getMulti_Hit() && this.getnegEffects().equals(equip.getnegEffects()) && 
				this.getDamage_Reduction() == equip.getDamage_Reduction() && this.getEvasion() == equip.getEvasion() &&
				this.getAccuracy() == equip.getAccuracy() && this.getposEffects().equals(equip.getposEffects()));
	}
}