package com.cs429.todorpg.revised.model;

import java.io.Serializable;

/**
 * 
 * @author lchen59, kwchen3
 * 
 * 
 *         Every character needs a name a a gold count
 */

public class ToDoCharacter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1062021211113236302L;

	private String name;
	private int gold;
	private int HP;
	private int level;
	private int currentEXP;
	private int nextEXP;

	public ToDoCharacter(String name, int gold, int HP, int level,
			int currentEXP, int nextEXP) {
		this.name = name;
		this.gold = gold;
		this.HP = HP;
		this.level = level;
		this.currentEXP = currentEXP;
		this.nextEXP = nextEXP;
	}

	public ToDoCharacter(ToDoCharacter other, String name) {
		this.name = name;
		this.gold = other.gold;
		this.HP = other.HP;
		this.level = other.level;
		this.currentEXP = other.currentEXP;
		this.nextEXP = other.nextEXP;
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

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		this.HP = HP;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCurrExp() {
		return currentEXP;
	}

	public void setCurrExp(int currExp) {
		this.currentEXP = currExp;
	}

	public int getNextExp() {
		return nextEXP;
	}

	public void setNextExp(int nextExp) {
		this.nextEXP = nextExp;
	}

	public boolean equals(Object o) {
		ToDoCharacter character = (ToDoCharacter) o;
		return (this.getName().equals(character.getName())
				&& this.getGold() == character.getGold()
				&& this.getHP() == character.getHP()
				&& this.getLevel() == character.getLevel()
				&& this.getCurrExp() == character.getCurrExp() && this
					.getNextExp() == character.getNextExp());
	}
}
