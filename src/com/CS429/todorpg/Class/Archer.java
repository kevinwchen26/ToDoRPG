package com.CS429.todorpg.Class;

import java.util.Random;

public class Archer extends Character {

	// Bonus passive - physical damage calculation is done with archer's DEX
	public Archer(String Name) {
		// TODO Auto-generated constructor stub
		super(Name);
		this.setCHA(this.getCHA() + 3);
		this.setDEX(this.getDEX() + 3);
		this.setMP(this.getMP() + (this.getDEX() + this.getCHA()));
		this.setMaxHP(this.getHP());
		this.setMaxMP(this.getMP());

	}

	public Archer(String name, int HP, int MP, int Level, int CON, int STR,
			int DEX, int INT, int WIS, int CHA, int currentEXP, int NextLevelExp) {
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
		this.setMaxHP(HP);
		this.setMaxMP(MP);
	}

	public void LevelStats() {
		this.setSTR(this.getSTR() + 2);
		this.setCON(this.getCON() + 1);
		this.setHP(this.getHP() + 50);
		this.setMP(this.getMP() + (this.getDEX() + this.getCHA()));
		this.setDEX(this.getDEX() + 3);
		this.setCHA(this.getCHA() + 3);
		this.setINT(this.getINT() + 1);
		this.setWIS(this.getWIS() + 1);
	}

	public void attack(Character enemy) {
		int base = 2 * this.getSTR();
		int reduction = this.getDEX() - enemy.getCON();
		int total = base + reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);

	}

	// Critical Strike
	// Chance to deal critical dmg, if it fails, it deals normal dmg, mana 50
	public void Skill_1(Character enemy) {
		// STUB METHOD
		int base = 2 * this.getSTR();
		int reduction = this.getCON() - enemy.getCON();
		int total = base + reduction;
		if (total < 0)
			total = 0;

		Random generator = new Random();
		int chance = generator.nextInt(10);

		/*
		 * if(chance < 6) total *= 2;
		 */

		enemy.setHP(enemy.getHP() - total);
		this.setMP(this.getMP() - 50);

	}

	// Poison arrow
	// Deals moderate physical dmg, and applies poison. mana 50
	public void Skill_2(Character enemy) {
		int base = 25;
		int total = base;
		int reduction = this.getDEX() - enemy.getCON();
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		enemy.applyPoison();
		this.setMP(this.getMP() - 50);

	}

	// Piercing shot
	// High physical dmg, regain mana from 20% dmg done, mana 60
	public void Skill_3(Character enemy) {
		int base = 50;
		int total = base;
		int reduction = this.getDEX() - enemy.getCON();
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		this.setMP(this.getMP() + total / 5);
		this.setMP(this.getMP() - 60);

	}

	// Seal the Deal
	// Ult: High damage, critical strike 100%. If the enemy is poison, they will
	// be stunned as well. mana 100
	public void Skill_4(Character enemy) {
		int base = 100;
		int bonus = this.getSTR() + this.getDEX();
		int total = base + bonus;
		int reduction = this.getDEX() - enemy.getCON();
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - 2 * total);
		if (enemy.isPoison())
			enemy.applyStun();
		this.setMP(this.getMP() - 100);

	}

}