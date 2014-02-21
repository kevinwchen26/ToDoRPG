package com.CS429.todorpg;

public class Mage extends Character{

	public Mage(String Name) {
		// TODO Auto-generated constructor stub
		super(Name);
		this.setWIS(this.getWIS() + 3);
		this.setINT(this.getINT() + 3);
		this.setMP(this.getMP() + (this.getINT() + this.getWIS()));
	}
	
	public Mage(String name, int HP, int MP, int Level, int CON, int STR, int DEX, int INT, int WIS, int CHA, int currentEXP, int NextLevelExp)
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
		this.setSTR(this.getSTR() + 1);
		this.setCON(this.getCON() + 2);
		this.setHP(this.getHP() + 20);
		this.setMP(this.getMP() + (this.getINT() + this.getWIS()));
		this.setDEX(this.getDEX() + 1);
		this.setCHA(this.getCHA() + 2);
		this.setINT(this.getINT() + 4);
		this.setWIS(this.getWIS() + 4);
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
