package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

/**
 * Status Effects Class. Used for Equipment
 * 
 * @author Leon Chen
 * 
 */
public abstract class StatusEffects implements Serializable {
	private static final long serialVersionUID = 707598741464385006L;
	private String name;

	/**
	 * Constructors
	 * 
	 * @param name
	 */
	public StatusEffects(String name) {
		this.name = name;
	}

	/**
	 * getter for Name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * equals function
	 */
	public boolean equals(Object o) {
		StatusEffects effect = (StatusEffects) o;
		return (this.getName().equals(effect.getName()));
	}
}
