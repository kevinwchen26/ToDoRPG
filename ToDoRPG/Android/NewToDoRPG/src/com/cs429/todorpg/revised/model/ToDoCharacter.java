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
	private int HP;
	private int level;
	private int currentExp;
	private int nextExp;
	
	public ToDoCharacter(String name, int gold) {
		this.name = name;
		this.gold = gold;
		this.HP = 20;
		this.level = 1;
		this.currentExp = 0;
		this.nextExp = 50;
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
	
	public int getHP(){
		return HP;
	}
	
	public void setHP(int HP){
		this.HP = HP;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public int getCurrExp(){
		return currentExp;
	}
	
	public void setCurrExp(int currExp){
		this.currentExp = currExp;
	}
	
	public int getNextExp(){
		return nextExp;
	}
	
	public void setNextExp(int nextExp){
		this.nextExp = nextExp;
	}
	
	public boolean equals(Object o) {
		ToDoCharacter character = (ToDoCharacter) o;
		return (this.getName().equals(character.getName()) && this.getGold() == character.getGold()
				&&this.getHP() == character.getHP() && this.getLevel() == character.getLevel() && 
				this.getCurrExp() == character.getCurrExp() && this.getNextExp() == character.getNextExp());
	}
}
