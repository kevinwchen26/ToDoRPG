package com.CS429.todorpg;

public class Warrior extends Character {

	public Warrior(String Name) {
		// TODO Auto-generated constructor stub
		super(Name);
		this.setSTR(this.getSTR() + 3);
		this.setCON(this.getCON() + 3);
		this.setHP(this.getCON() * 20 + this.getHP());
		
	}
	
	public Warrior(String name, int HP, int MP, int Level, int CON, int STR, int DEX, int INT, int WIS, int CHA, int currentEXP, int NextLevelExp)
	{
		super(name);
		this.setHP(HP);
		this.setMP(MP);
		this.setLEVEL(Level);
		this.setCON(CON);
		this.setSTR(STR);
		this.setDEX(DEX);
		this.setINT(INT);
		this.setWIS(WIS);
		this.setCHA(CHA);
		this.setCurrentEXP(currentEXP);
		this.setNextLevelEXP(NextLevelExp);
		
	}
	
	public void LevelStats(){
		System.out.println("I am warrior");
	}
	
	public void Skill_1(Character enemy) {
		//STUB METHOD
	}
	public void Skill_2(Character enemy) {
		//STUB METHOD
	}
	public void Skill_3(Character enemy) {
		//STUB METHOD
	}
	public void Skill_4(Character enemy) {
		//STUB METHOD
	}

}
