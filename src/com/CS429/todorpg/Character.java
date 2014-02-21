package com.CS429.todorpg;

public class Character {
	//Class Variables
	private String Name;
	private int HP;
	private int MP;
	private int LEVEL;
	private int CON;
	private int STR;
	private int DEX;
	private int INT;
	private int WIS;
	private int CHA;
	private int currentEXP;
	private int nextLevelEXP;
	
	/**
	 * Constructor for a character
	 * @param Name
	 */
	public Character(String Name) {
		this.setName(Name);
		this.setHP(100);
		this.setMP(50);
		this.setCON(10);
		this.setSTR(10);
		this.setDEX(10);
		this.setINT(10);
		this.setWIS(10);
		this.setCHA(10);
		this.setLEVEL(1);
		this.currentEXP = 0;
		this.nextLevelEXP = 100;
			
	}
	//STUB Methods - these will be implemented in their respective classes
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
	public void LevelStats() {
		//STUB Method
	}
	
	
	/**
	 * Add EXP to the currentEXP
	 * @param EXP
	 */
	public void gainEXP(int EXP) {
		this.currentEXP += EXP;
		this.checkEXP();
	}
	
	/**
	 * Increase the character level and call method to level stats
	 */
	public void levelUP() {
		this.setLEVEL(this.getLEVEL() + 1);
		int overEXP = this.currentEXP - this.nextLevelEXP;
		this.currentEXP = overEXP;
		this.nextLevelEXP =  this.nextLevelEXP * 2;
		this.LevelStats();
		this.checkEXP();
		
	}
	
	/**
	 * Check if the character can level up
	 */
	public void checkEXP(){
		if(this.currentEXP >= this.nextLevelEXP)
			this.levelUP();
	}
	
	//Getter and Setters
	public int getLEVEL() {
		return LEVEL;
	}
	public void setLEVEL(int lEVEL) {
		LEVEL = lEVEL;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getCON() {
		return CON;
	}
	public void setCON(int cON) {
		CON = cON;
	}
	public int getSTR() {
		return STR;
	}
	public void setSTR(int sTR) {
		STR = sTR;
	}
	public int getDEX() {
		return DEX;
	}
	public void setDEX(int dEX) {
		DEX = dEX;
	}
	public int getINT() {
		return INT;
	}
	public void setINT(int iNT) {
		INT = iNT;
	}
	public int getWIS() {
		return WIS;
	}
	public void setWIS(int wIS) {
		WIS = wIS;
	}
	public int getCHA() {
		return CHA;
	}
	public void setCHA(int cHA) {
		CHA = cHA;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getMP() {
		return MP;
	}
	public void setMP(int mP) {
		MP = mP;
	}
	public int getcurrentEXP(){
		return currentEXP;
	}
	public void setCurrentEXP(int exp){
		this.currentEXP = exp;
	}
	public int getnextLevelEXP(){
		return nextLevelEXP;
	}
	public void setNextLevelEXP(int exp){
		this.nextLevelEXP = exp;
	}
}
