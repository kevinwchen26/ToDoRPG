package com.CS429.todorpg;

import java.util.Random;

public class Mage extends Character{

	//Bonus passive: basic attacks scale with int. Dmg still calculated with CON
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
	
	//Fire Strike
	//High dmg nuke, chance to apply burn, 50 mana
	public void Skill_1(Character enemy) {
		int base = 35;
		int bonus = this.getINT()/2;
		int total = base + bonus;
		int reduction = this.getWIS() - enemy.getWIS();
		total += reduction;
		if(total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		Random generator = new Random();
		int chance = generator.nextInt(4);
		
		/*
		if(chance == 0)
			enemy.applyBurn();
			*/
		this.setMP(this.getMP() - 50);

	}
	
	//SHHHHHHH
	//Apply silence, gain mana, 0 mana
	public void Skill_2(Character enemy) {
		enemy.applySilence();
		this.setMP(this.getMP() + 20);
	}
	
	//Frostbite
	//Low dmg, low mana cost, apply freezes, 35 mana
	public void Skill_3(Character enemy) {
		int base = 25;
		int bonus = this.getINT()/4;
		int total = base + bonus;
		int reduction = this.getWIS() - enemy.getWIS();
		total += reduction;
		if(total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);

		enemy.applyFreeze();
		this.setMP(this.getMP() - 35);

	}
	
	//Judgement
	//Ult: high dmg, permenantly reduces WIS, mana 100
	public void Skill_4(Character enemy) {
		int base = 150;
		int bonus = this.getINT();
		int total = base + bonus;
		int reduction = this.getWIS() - enemy.getWIS();
		total += reduction;
		if(total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);

		enemy.setWIS(enemy.getWIS() - 50);
		this.setMP(this.getMP() - 100);

		
		
	}

}
