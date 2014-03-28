package com.CS429.todorpg;

import com.CS429.todorpg.Class.Archer;
import com.CS429.todorpg.Class.Assassin;
import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Class.Mage;
import com.CS429.todorpg.Class.Summoner;
import com.CS429.todorpg.Class.Warrior;
import com.CS429.todorpg.Utils.Constants;

public class CharacterOperations {
	
	public static Character createCharacter(String Class, String Name) {
		if(Class == Constants.WARRIOR)
			return new Warrior(Name);
		else if(Class == Constants.ARCHER)
			return new Archer(Name);
		else if(Class == Constants.MAGE)
			return new Mage(Name);
		else if(Class == Constants.SUMMONER)
			return new Summoner(Name);
		else if(Class == Constants.ASSASSIN)
			return new Assassin(Name);
		else
			return null;
	
	}
	
//	public static Character pullCharacter(String Class, String Name, int HP, int MP, int Level, int CON, int STR, int DEX, int INT, int WIS, int CHA, int currentEXP, int nextLevelEXP)
//	{
//		
//		
//	}

	public static Character pullCharacter(Character character) {
		String Name = character.getName();
		int HP = character.getHP();
		int MP = character.getMP();
		int Level = character.getLEVEL();
		int CON = character.getCON();
		int STR = character.getSTR();
		int DEX = character.getDEX();
		int INT = character.getDEX();
		int WIS = character.getWIS();
		int CHA = character.getCHA();
		int currentEXP = character.getcurrentEXP();
		int nextLevelEXP = character.getnextLevelEXP();
		if(Character.class.equals(Constants.WARRIOR))
			return new Warrior(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Character.class.equals(Constants.ARCHER))
			return new Archer(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Character.class.equals(Constants.MAGE))
			return new Mage(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Character.class.equals(Constants.SUMMONER))
			return new Summoner(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Character.class.equals(Constants.ASSASSIN))
			return new Assassin(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else
			return null;
	}
	
}
