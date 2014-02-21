package com.CS429.todorpg;

public class Archer extends Character{

	public Archer(String Name) {
		// TODO Auto-generated constructor stub
		super(Name);
		this.setCHA(this.getCHA() + 3);
		this.setDEX(this.getDEX() + 3);
		this.setMP(this.getMP() + (this.getDEX()+ this.getCHA()));

		
	}
	
	public Archer(String name, int HP, int MP, int Level, int CON, int STR, int DEX, int INT, int WIS, int CHA, int currentEXP, int NextLevelExp)
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
		this.setCON(this.getCON() + 1);
		this.setHP(this.getHP() + 50);
		this.setMP(this.getMP() + (this.getDEX()+ this.getCHA()));
		this.setDEX(this.getDEX() + 4);
		this.setCHA(this.getCHA() + 3);
		this.setINT(this.getINT() + 1);
		this.setWIS(this.getWIS() + 1);
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
