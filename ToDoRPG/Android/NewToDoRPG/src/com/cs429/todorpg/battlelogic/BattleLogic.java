package com.cs429.todorpg.battlelogic;

import java.util.ArrayList;
import java.util.Random;

import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Equipment;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;

public class BattleLogic {
	
	public static final int CRITICAL_DAMAGE_MODIFIER = 3;
	
	/**
	 * 
	 * Does not take in count Negative / Positive effects yet
	 * @param attacker
	 * @param defender
	 * @return
	 */
	public static AttackResult calculateAttackResult(Inventory attacker, Inventory defender) {
		Armor attackerArmor = attacker.getArmor();
		Helmet attackerHelmet = attacker.getHelmet();
		Shield attackerShield = attacker.getShield();
		Weapon attackerWeapon = attacker.getWeapon();
		
		ArrayList<Equipment> attackerEquipment = new ArrayList<Equipment>();
		if (attackerWeapon != null)
			attackerEquipment.add(attackerWeapon);
		if (attackerHelmet != null)
			attackerEquipment.add(attackerHelmet);
		if (attackerArmor != null)
			attackerEquipment.add(attackerArmor);
		if (attackerShield != null)
			attackerEquipment.add(attackerShield);
		
		Armor defenderArmor = defender.getArmor();
        Helmet defenderHelmet = defender.getHelmet();
        Shield defenderShield = defender.getShield();
        Weapon defenderWeapon = defender.getWeapon();
        
        ArrayList<Equipment> defenderEquipment = new ArrayList<Equipment>();
        if (defenderWeapon != null)
            defenderEquipment.add(defenderWeapon);
        if (defenderHelmet != null)
            defenderEquipment.add(defenderHelmet);
        if (defenderArmor != null)
            defenderEquipment.add(defenderArmor);
        if (defenderShield != null)
            defenderEquipment.add(defenderShield);
        
        
        int defenderEvasion = 0;
        int defenderDefense = 0;
        for (Equipment e : defenderEquipment) {
        	defenderEvasion += e.getEvasion();
        	defenderDefense += e.getDamage_Reduction();
        }
        
        int attackerAccuracy = 70;
        int attackerMultiHit = 0;
        int attackerCritical = 0;
        int attackerDamage = 0;
        for (Equipment e : attackerEquipment) {
        	attackerAccuracy += e.getAccuracy();
        	attackerMultiHit += e.getMulti_Hit();
        	attackerCritical += e.getCritical();
        	attackerDamage += e.getDamage();
        }
        
        AttackResult am = new AttackResult();
        
        
        // (1) determine if the strike is a HIT (from evasion and accuracy)
        int hitChance = attackerAccuracy - defenderEvasion;
        boolean isHit = getChanceResult(hitChance);
        if(isHit) {
        	am.isHit = isHit; 
        }
        else {
        	am.isHit = false;
        	return am;
        }
        
        // (2) determine damage of each HIT (From damage and damage reduction)
        int hitDamage = attackerDamage - defenderDefense;
        am.damagePerHit = hitDamage;
        
        // (3) determine how many times the HIT lands (From multi_hit)
        int numHits = numHits(attackerMultiHit);
        am.numHits = numHits;
        
        // (4) determine critical chance for each hit
        ArrayList<Boolean> critChanceList = new ArrayList<Boolean>();
        for (int i = 0; i < numHits; i++)
        	critChanceList.add(getChanceResult(attackerCritical));
        am.critChanceList = critChanceList;
		return am;
	}
	
	public static boolean getChanceResult(int percentage) {
		return Math.random() * 100 < percentage;
	}

	public static int numHits(int multiHit) {
		Random r = new Random();
		int min = 1;
		int max = multiHit + 2;
		int resultHits = r.nextInt(max - min) + min;
		return resultHits;
	}
}
