package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

/**
 * Negatives Effects class.
 * 
 * @author Leon Chen
 * 
 */
public class NegativeEffects extends StatusEffects implements Serializable {

	private static final long serialVersionUID = -497390897607433780L;
	private int affect;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param affect
	 */
	public NegativeEffects(String name, int affect) {
		super(name);
		this.affect = affect;
	}

	/**
	 * retrieves the affect
	 * 
	 * @return affect
	 */
	public int getAffect() {
		return affect;
	}

	/**
	 * sets the affect
	 * 
	 * @param affect
	 */
	public void setAffect(int affect) {
		this.affect = affect;
	}

	/**
	 * equal function
	 */
	public boolean equals(Object o) {
		NegativeEffects effect = (NegativeEffects) o;
		return (this.getName().equals(effect.getName()) && this.getAffect() == effect
				.getAffect());
	}

}
