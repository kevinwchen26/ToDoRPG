package com.cs429.todorpg.revised.itemsystem;

public abstract class StatusEffects {
	private String name;
	
	public StatusEffects(String name) {
		this.name = name;
	}
	
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
