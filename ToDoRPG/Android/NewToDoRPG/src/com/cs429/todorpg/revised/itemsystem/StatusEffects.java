package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

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
	 * getter and setter
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object o) {
		StatusEffects effect = (StatusEffects) o;
		return (this.getName().equals(effect.getName()));
	}
}
