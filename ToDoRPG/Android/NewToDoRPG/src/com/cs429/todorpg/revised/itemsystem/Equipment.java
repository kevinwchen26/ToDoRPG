package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Equipment class. Used for weapons, armor, helmet, shield
 * 
 * @author Leon Chen
 * 
 */
public abstract class Equipment extends RpgItem implements Serializable {

	private static final long serialVersionUID = 2763463244560673526L;
	private int damage;
	private int critical;
	private int multi_hit;
	private ArrayList<NegativeEffects> negEffects;
	private int damage_reduction;
	private int evasion;
	private int accuracy;
	private ArrayList<PositiveEffects> posEffects = new ArrayList<PositiveEffects>();

	/**
	 * Constructor
	 * 
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
	public Equipment(String name, int resId, int damage, int critical,
			int multi_hit, ArrayList<NegativeEffects> negEffects,
			int damage_reduction, int evasion, int accuracy,
			ArrayList<PositiveEffects> posEffects) {

		super(name, resId);
		this.damage = damage;
		this.critical = critical;
		this.multi_hit = multi_hit;
		this.negEffects = new ArrayList<NegativeEffects>(negEffects);
		this.damage_reduction = damage_reduction;
		this.evasion = evasion;
		this.accuracy = accuracy;
		this.posEffects = new ArrayList<PositiveEffects>(posEffects);
	}

	/**
	 * getter function for damage
	 * 
	 * @return damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * setter function for damage
	 * 
	 * @param damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * getter function for critical
	 * 
	 * @return critical
	 */
	public int getCritical() {
		return critical;
	}

	/**
	 * setter function for critical
	 * 
	 * @param critical
	 */
	public void setCritical(int critical) {
		this.critical = critical;
	}

	/**
	 * getter function for multi-hit
	 * 
	 * @return multi-hit percent
	 */
	public int getMulti_Hit() {
		return multi_hit;
	}

	/**
	 * setter function for multihit
	 * 
	 * @param multi_hit
	 */
	public void setMulti_Hit(int multi_hit) {
		this.multi_hit = multi_hit;
	}

	/**
	 * getter function for damage reduction
	 * 
	 * @return damage reduction
	 */
	public int getDamage_Reduction() {
		return damage_reduction;
	}

	/**
	 * setter function for damage reduction
	 * 
	 * @param damage_reduction
	 */
	public void setDamage_Reduction(int damage_reduction) {
		this.damage_reduction = damage_reduction;
	}

	/**
	 * getter function for Accuracy
	 * 
	 * @return accuracy
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * setter function for accuracy
	 * 
	 * @param accuracy
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * getter function for evasion
	 * 
	 * @return evasion
	 */
	public int getEvasion() {
		return evasion;
	}

	/**
	 * setter function for evasion
	 * 
	 * @param evasion
	 */
	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}

	/**
	 * getter function for Negative effects
	 * 
	 * @return list of negative effects
	 */
	public ArrayList<NegativeEffects> getnegEffects() {
		return negEffects;
	}

	/**
	 * setter function for negative effects
	 * 
	 * @param negeffects
	 */
	public void setnegEffects(ArrayList<NegativeEffects> negeffects) {
		this.negEffects = new ArrayList<NegativeEffects>(negeffects);
	}

	/**
	 * getter function for positive effects
	 * 
	 * @return list of positive effects
	 */
	public ArrayList<PositiveEffects> getposEffects() {
		return posEffects;
	}

	/**
	 * setter function for positive effects
	 * 
	 * @param poseffects
	 */
	public void setposEffects(ArrayList<PositiveEffects> poseffects) {
		this.posEffects = new ArrayList<PositiveEffects>(poseffects);
	}

	/**
	 * equals function
	 */
	public boolean equals(Object o) {
		Equipment equip = (Equipment) o;
		return (this.getName().equals(equip.getName())
				&& this.getResId() == equip.getResId()
				&& this.getDamage() == equip.getDamage()
				&& this.getCritical() == equip.getCritical()
				&& this.getMulti_Hit() == equip.getMulti_Hit()
				&& this.getnegEffects().equals(equip.getnegEffects())
				&& this.getDamage_Reduction() == equip.getDamage_Reduction()
				&& this.getEvasion() == equip.getEvasion()
				&& this.getAccuracy() == equip.getAccuracy() && this
				.getposEffects().equals(equip.getposEffects()));
	}
}