package com.CS429.todorpg;

import com.CS429.todorpg.Class.Archer;
import com.CS429.todorpg.Class.Assassin;
import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Class.Mage;
import com.CS429.todorpg.Class.Summoner;
import com.CS429.todorpg.Class.Warrior;

import android.annotation.SuppressLint;

public class CharacterOperations {
	
	public static Character createCharacter(String Class, String Name) {
		if(Class == StaticClass.WARRIOR)
			return new Warrior(Name);
		else if(Class == StaticClass.ARCHER)
			return new Archer(Name);
		else if(Class == StaticClass.MAGE)
			return new Mage(Name);
		else if(Class == StaticClass.SUMMONER)
			return new Summoner(Name);
		else if(Class == StaticClass.ASSASSIN)
			return new Assassin(Name);
		else
			return null;
	
	}
	
	public static Character pullCharacter(String Class, String Name, int HP, int MP, int Level, int CON, int STR, int DEX, int INT, int WIS, int CHA, int currentEXP, int nextLevelEXP)
	{
		if(Class == StaticClass.WARRIOR)
			return new Warrior(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == StaticClass.ARCHER)
			return new Archer(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == StaticClass.MAGE)
			return new Mage(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == StaticClass.SUMMONER)
			return new Summoner(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == StaticClass.ASSASSIN)
			return new Assassin(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else
			return null;
		
	}
	
}
