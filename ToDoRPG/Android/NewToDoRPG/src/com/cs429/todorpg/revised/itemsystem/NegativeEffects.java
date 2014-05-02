package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

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
	 * setters and getters
	 * 
	 * @return
	 */
	public int getAffect() {
		return affect;
	}

	public void setAffect(int affect) {
		this.affect = affect;
	}

	public boolean equals(Object o) {
		NegativeEffects effect = (NegativeEffects) o;
		return (this.getName().equals(effect.getName()) && this.getAffect() == effect
				.getAffect());
	}

}
