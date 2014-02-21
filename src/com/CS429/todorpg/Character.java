package com.CS429.todorpg;


public class Character {
	//Class Variables
	//Few notes: CON functions as Physical dmg variable, and WIS as Magic dmg variable
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
	
	
	//Status effects
	private boolean stun = false;
	private boolean poison = false;
	private boolean burn = false;
	private boolean freeze = false;
	private boolean silence = false;

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
	
	
	//All characters have a basic attack
	public void attack(Character enemy){
		int base = 2 * STR;
		int reduction = CON - enemy.getCON();
		int total = base + reduction;
		if(total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
	
	}
	
	public void checkStatus() {
		if(stun)
			applyStun();
		if(poison)
			applyPoison();
		if(burn)
			applyBurn();
		if(freeze)
			applyFreeze();
		if(silence)
			applySilence();
		
		
	}
	
	public void applyStun() {
		stun = true;
	}
	
	public void applyPoison() {
		poison = true;
		HP -= 15;
	}
	
	public void applyBurn() {
		burn = true;
		HP -= 5;
		MP -= 5;
	}
	
	public void applyFreeze() {
		stun = true;
		freeze = true;
		CON -= 20;
		WIS -= 20;
	}
	
	public void applySilence() {
		//disable abilites, will be on android side
		silence = true;
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
	public boolean isStun(){
		return stun;
	}
	public boolean isFrozen(){
		return freeze;
	}
	public boolean isBurn(){
		return burn;
	}
	public boolean isSilence(){
		return silence;
	}
	public boolean isPoison(){
		return poison;
	}
}

