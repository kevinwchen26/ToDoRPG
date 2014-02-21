package com.CS429.todorpg;

public class Character {
	private String Name;
	
	private int LEVEL;
	private int CON;
	private int STR;
	private int DEX;
	private int INT;
	private int WIS;
	private int CHA;
	private int currentEXP;
	private int nextLevelEXP;
	
	public Character(String Name) {
		this.Name = Name;
		this.CON = 10;
		this.STR = 10;
		this.DEX = 10;
		this.INT = 10;
		this.WIS = 10;
		this.CHA = 10;
		this.currentEXP = 0;
		this.nextLevelEXP = 100;
		
		
	}
	
	public void Skill_1() {
		//STUB METHOD
	}
	public void Skill_2() {
		//STUB METHOD
	}
	public void Skill_3() {
		//STUB METHOD
	}
	public void Skill_4() {
		//STUB METHOD
	}
	public void levelUP() {
		this.LEVEL++;
		int overEXP = this.currentEXP - this.nextLevelEXP;
		this.currentEXP = overEXP;
		this.nextLevelEXP =  this.nextLevelEXP * 2;
		this.LevelStats();
		
	}
	
	public void LevelStats() {
		System.out.println("I am generic");
	}
	
	public void checkEXP(){
		System.out.println("I am generic");
		if(this.currentEXP >= this.nextLevelEXP)
			this.levelUP();
	}
	
	
}
