package com.cs429.todorpg.revised.itemsystem;

public abstract class RpgItem {
	private String name;
	
	public RpgItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
