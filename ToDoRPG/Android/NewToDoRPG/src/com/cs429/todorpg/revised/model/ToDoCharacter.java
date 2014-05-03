package com.cs429.todorpg.revised.model;

import java.io.Serializable;

/**
 * 
 * @author lchen59, kwchen3
 * 
 * 
 *         Every character needs a name a a gold count
 */

public class ToDoCharacter implements Serializable {

	private static final long serialVersionUID = -1062021211113236302L;

	private String name;
	private int gold;
	private int HP;
	private int level;
	private int currentEXP;
	private int nextEXP;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param gold
	 * @param HP
	 * @param level
	 * @param currentEXP
	 * @param nextEXP
	 */
	public ToDoCharacter(String name, int gold, int HP, int level,
			int currentEXP, int nextEXP) {
		this.name = name;
		this.gold = gold;
		this.HP = HP;
		this.level = level;
		this.currentEXP = currentEXP;
		this.nextEXP = nextEXP;
	}

	/**
	 * Secondary Constructor
	 * 
	 * @param other
	 * @param name
	 */
	public ToDoCharacter(ToDoCharacter other, String name) {
		this.name = name;
		this.gold = other.gold;
		this.HP = other.HP;
		this.level = other.level;
		this.currentEXP = other.currentEXP;
		this.nextEXP = other.nextEXP;
	}

	/**
	 * gets the gold from character
	 * 
	 * @return gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * sets gold of character
	 * 
	 * @param gold
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * gets name of character
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the character
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the HP of the character
	 * 
	 * @return HP
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * sets HP of the character
	 * 
	 * @param HP
	 */
	public void setHP(int HP) {
		this.HP = HP;
	}

	/**
	 * gets the Level of the Character
	 * 
	 * @return level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * sets the level of the character
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * gets the current Exp
	 * 
	 * @return currentExp
	 */
	public int getCurrExp() {
		return currentEXP;
	}

	/**
	 * sets the current Exp
	 * 
	 * @param currExp
	 */
	public void setCurrExp(int currExp) {
		this.currentEXP = currExp;
	}

	/**
	 * gets the next EXP of character
	 * 
	 * @return nextEXP
	 */
	public int getNextExp() {
		return nextEXP;
	}

	/**
	 * sets next exp of character
	 * 
	 * @param nextExp
	 */
	public void setNextExp(int nextExp) {
		this.nextEXP = nextExp;
	}

	/**
	 * equals function
	 */
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
