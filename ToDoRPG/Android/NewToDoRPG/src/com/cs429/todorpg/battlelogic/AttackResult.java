package com.cs429.todorpg.battlelogic;

import java.io.Serializable;
import java.util.ArrayList;

public class AttackResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4982970380548348215L;
	
	public boolean isHit;
	public int damagePerHit;
	public int numHits;
	public ArrayList<Boolean> critChanceList;
	
	public AttackResult() {
		critChanceList = new ArrayList<Boolean>();
	}
	public AttackResult(boolean isHit, int damagePerHit, int numHits, ArrayList<Boolean> critChanceList) {
		this.isHit = isHit;
		this.damagePerHit = damagePerHit;
		this.numHits = numHits;
		this.critChanceList = critChanceList;
	}


}
