package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.itemsystem.*;
import com.cs429.todorpg.revised.model.ToDoCharacter;

public class MainActivity extends BaseActivity {

	Button rewardsButton;
	Intent intent;
	SQLiteHelper sql;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setHeader(R.id.header);
		sql = new SQLiteHelper(this);
		ToDoCharacter c = sql.getCharacter();
		if (c == null) {
			
		}
		
		CreateLibrary();
	}
	
	private void CreateLibrary() {
		EquipCost tempequipcost = sql.getLibrary("Nirvana");
		if (tempequipcost == null) {
			
			ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
			ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
			
			//create weapons first
			Equipment tempequip = new Weapon("Broad Sword", R.drawable.weapon_warrior_1, 20, 10, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 30);
			
			tempequip = new Weapon("Mythril Sword", R.drawable.weapon_warrior_4, 40, 20, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 60);
			
			tempequip = new Weapon("Gigas Sword", R.drawable.weapon_warrior_5, 100, 5, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 200);
			
			tempequip = new Weapon("Excalibur", R.drawable.weapon_warrior_6, 150, 30, 10, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 500);
			
			tempequip = new Weapon("Dagger", R.drawable.weapon_rogue_0, 10, 10, 75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 40);
			
			tempequip = new Weapon("Dancing Dagger", R.drawable.weapon_rogue_2, 20, 20, 75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 70);
			
			tempequip = new Weapon("Nunchuck", R.drawable.weapon_rogue_4, 80, 0, 75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 200);
			
			tempequip = new Weapon("ManEater", R.drawable.weapon_rogue_6, 150, 30, 75, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 500);
			
			negs.clear();
			negs.add(new NegativeEffects("Poison", 20));
			tempequip = new Weapon("Staff", R.drawable.weapon_wizard_0, 5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 40);
			
			negs.clear();
			negs.add(new NegativeEffects("Blind", 75));
			tempequip = new Weapon("Darkness Staff", R.drawable.weapon_wizard_3, 5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 80);
			
			negs.clear();
			negs.add(new NegativeEffects("Curse", 75));
			tempequip = new Weapon("Curse Staff", R.drawable.weapon_wizard_2, 5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 80);
			
			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			tempequip = new Weapon("Poison Staff", R.drawable.weapon_wizard_1, 5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 80);
			
			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			negs.add(new NegativeEffects("Blind", 75));
			tempequip = new Weapon("Wizard's Staff", R.drawable.weapon_wizard_4, 5, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 150);
			
			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			negs.add(new NegativeEffects("Blind", 75));
			negs.add(new NegativeEffects("Curse", 75));
			tempequip = new Weapon("Apocalypse", R.drawable.weapon_wizard_5, 20, 0, 0, negs, 0, 0, 0, poss);
			sql.addLibrary(tempequip, 450);
			
			negs.clear();
			negs.add(new NegativeEffects("Poison", 75));
			negs.add(new NegativeEffects("Blind", 75));
			negs.add(new NegativeEffects("Curse", 75));
			tempequip = new Weapon("Buster Sword", R.drawable.weapon_special_1, 200, 50, 75, negs, 5, 5, 5, poss);
			sql.addLibrary(tempequip, 5000);
			
			//armor
			negs.clear();
			tempequip = new Armor("Iron Armor", R.drawable.broad_armor_healer_1, 0, 0, 0, negs, 10, 0, 0, poss);
			sql.addLibrary(tempequip, 200);
			
			tempequip = new Armor("Good Armor", R.drawable.broad_armor_healer_3, 0, 0, 0, negs, 20, 0, 0, poss);
			sql.addLibrary(tempequip, 300);
			
			tempequip = new Armor("Mythril Vest", R.drawable.broad_armor_healer_5, 0, 0, 0, negs, 50, 0, 0, poss);
			sql.addLibrary(tempequip, 500);
			
			//helmet
			tempequip = new Helmet("Hat", R.drawable.head_warrior_1, 0, 0, 0, negs, 5, 0, 30, poss);
			sql.addLibrary(tempequip, 200);
			
			tempequip = new Helmet("Helm", R.drawable.head_warrior_3, 0, 0, 0, negs, 5, 0, 50, poss);
			sql.addLibrary(tempequip, 300);
			
			tempequip = new Helmet("Warrior's Helm", R.drawable.head_warrior_5, 0, 0, 0, negs, 5, 0, 80, poss);
			sql.addLibrary(tempequip, 500);
			
			//shield
			tempequip = new Shield("Standard Shield", R.drawable.shield_warrior_1, 0, 0, 0, negs, 5, 10, 0, poss);
			sql.addLibrary(tempequip, 200);
			
			tempequip = new Shield("Warrior Shield", R.drawable.shield_warrior_2, 0, 0, 0, negs, 5, 20, 0, poss);
			sql.addLibrary(tempequip, 300);
			
			poss.add(new PositiveEffects("nullBlind"));
			tempequip = new Shield("Clear Shield", R.drawable.shield_warrior_3, 0, 0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 500);
			
			poss.clear();
			poss.add(new PositiveEffects("nullPoison"));
			tempequip = new Shield("Clean Shield", R.drawable.shield_warrior_4, 0, 0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 500);
			
			
			poss.clear();
			poss.add(new PositiveEffects("nullCurse"));
			tempequip = new Shield("Heaven's Shield", R.drawable.shield_warrior_5, 0, 0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 500);
			
			poss.clear();
			poss.add(new PositiveEffects("nullPoison"));
			poss.add(new PositiveEffects("nullBlind"));
			poss.add(new PositiveEffects("nullCurse"));
			tempequip = new Shield("Nirvana", R.drawable.shield_special_1, 0, 0, 0, negs, 5, 30, 0, poss);
			sql.addLibrary(tempequip, 2000);
		}

	}
	
}
