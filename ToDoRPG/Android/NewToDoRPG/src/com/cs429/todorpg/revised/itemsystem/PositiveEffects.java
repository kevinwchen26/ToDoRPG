package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

/**
 * Positive Effects Class
 * 
 * @author Leon Chen
 * 
 */
public class PositiveEffects extends StatusEffects implements Serializable {

	private static final long serialVersionUID = 6135816826103394819L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public PositiveEffects(String name) {
		super(name);
	}
}
