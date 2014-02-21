package com.CS429.todorpg;

public class Log extends Character {

	public Log(String Name) {
		super(Name);
		this.setDEX(this.getDEX() + 3);
		this.setWIS(this.getWIS() + 3);
	}
	
	public Log(String name, int HP, int MP, int Level, int CON, int STR, int DEX, int INT, int WIS, int CHA, int currentEXP, int NextLevelExp)
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
		this.setSTR(this.getSTR() + 2);
		this.setCON(this.getCON() + 2);
		this.setHP(this.getHP() + 50);
		this.setMP(this.getMP() + 10);
		this.setDEX(this.getDEX() + 2);
		this.setCHA(this.getCHA() + 2);
		this.setINT(this.getINT() + 2);
		this.setWIS(this.getWIS() + 2);
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