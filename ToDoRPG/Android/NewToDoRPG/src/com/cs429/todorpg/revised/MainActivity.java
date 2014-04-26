package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;
import com.cs429.todorpg.revised.model.ToDoCharacter;

public class MainActivity extends BaseActivity {

	Button rewardsButton;
	Intent intent;
	SQLiteHelper sql;
	ToDoCharacter character;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sql = new SQLiteHelper(this);
		CreateCharacter();
		setHeader(R.id.header);

	}

	private void CreateCharacter() {
		character = sql.getCharacter();
		if (character == null) {
			character = new ToDoCharacter("Hello", 0, 100, 1, 0, 100);
			sql.addCharacter(character);
			addInitialItems();

		}
	}
	public void addInitialItems() {
		ArrayList<NegativeEffects>negs = new ArrayList<NegativeEffects>();
		ArrayList<PositiveEffects>poss = new ArrayList<PositiveEffects>();
		GameApplication app =(GameApplication)getApplication();
		
		app.avatar.inventory.setArmor(new Armor("Leather Armor", R.drawable.broad_armor_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		app.avatar.inventory.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		app.avatar.inventory.setShield(new Shield("Leather Shield", R.drawable.shield_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		app.avatar.inventory.setWeapon(new Weapon("Iron Sword", R.drawable.weapon_warrior_1, 1, 1, 1, negs, 1, 1, 1, poss));
		
		app.avatar.inventory.addInventory(new Weapon("Rogue Weapon 0", R.drawable.weapon_rogue_0, 1, 1, 1, negs, 1, 1, 1, poss));
		app.avatar.inventory.addInventory(new Weapon("Rogue Weapon 1", R.drawable.weapon_rogue_1, 1, 1, 1, negs, 1, 1, 1, poss));
		app.avatar.inventory.addInventory(new Weapon("Rogue Weapon 2", R.drawable.weapon_rogue_2, 1, 1, 1, negs, 1, 1, 1, poss));		
	}

}
