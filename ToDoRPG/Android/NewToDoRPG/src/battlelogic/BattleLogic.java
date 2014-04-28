package battlelogic;

import java.util.ArrayList;

import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Equipment;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;

public class BattleLogic {
	public static void calculateDamage(Inventory attacker, Inventory defender) {
		Armor attackerArmor = attacker.getArmor();
		Helmet attackerHelmet = attacker.getHelmet();
		Shield attackerShield = attacker.getShield();
		Weapon attackerWeapon = attacker.getWeapon();
		
		ArrayList<Equipment> attackerEquipment = new ArrayList<Equipment>();
		attackerEquipment.add(attackerWeapon);
		attackerEquipment.add(attackerHelmet);
		attackerEquipment.add(attackerArmor);
		attackerEquipment.add(attackerShield);
		
		Armor defenderArmor = defender.getArmor();
        Helmet defenderHelmet = defender.getHelmet();
        Shield defenderShield = defender.getShield();
        Weapon defenderWeapon = defender.getWeapon();
        
        ArrayList<Equipment> defenderEquipment = new ArrayList<Equipment>();
        defenderEquipment.add(defenderWeapon);
        defenderEquipment.add(defenderHelmet);
        defenderEquipment.add(defenderArmor);
        defenderEquipment.add(defenderShield);
        
        
		
		
		
		
	}
}
