package com.cs429.todorpg.revised.model;

/**
 * 
 * @author lchen59, kwchen3
 *
 *
 * Every character needs a name a a gold count
 */

public class ToDoCharacter{
	private String name;
	private int gold;
	
	public ToDoCharacter(String name, int gold) {
		this.name = name;
		this.gold = gold;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		ToDoCharacter character = (ToDoCharacter) o;
		return (this.getName().equals(character.getName()) && this.getGold() == character.getGold());
	}
}
