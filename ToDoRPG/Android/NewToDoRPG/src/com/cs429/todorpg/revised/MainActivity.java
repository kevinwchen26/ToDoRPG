package com.cs429.todorpg.revised;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.Inventory;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.utils.ToDoCharacter;

public class MainActivity extends BaseActivity {

	Button rewardsButton;
	Intent intent;
	SQLiteHelper sql;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		setHeader(R.id.header);
		sql = new SQLiteHelper(this);
		ToDoCharacter c = sql.getCharacter();
		
		// If no character, new user. Set new inventory
		if (c == null) {
			Inventory inventory = new Inventory();
			inventory.setArmor(new Armor("Leather Armor", R.drawable.broad_armor_warrior_1));
			inventory.setHelmet(new Helmet("Leather Helmet", R.drawable.head_warrior_1));
			inventory.setShield(new Shield("Leather Shield", R.drawable.shield_warrior_1));
			inventory.setWeapon(new Weapon("Iron Sword", R.drawable.weapon_warrior_1));
			
			inventory.addInventory(new Weapon("Rogue Weapon 0", R.drawable.weapon_rogue_0));
			inventory.addInventory(new Weapon("Rogue Weapon 1", R.drawable.weapon_rogue_1));
			inventory.addInventory(new Weapon("Rogue Weapon 2", R.drawable.weapon_rogue_2));

			sql.addInventory(inventory);
			sql.addCharacter(new ToDoCharacter("Butch", 10000));
		}
		Avatar avatar = new Avatar();
		Inventory inventory = sql.getInventory();

		
		// This image goes in hp/xp bar.
		ImageView image = (ImageView) findViewById(R.id.character_activity);
		image.setImageBitmap(avatar.getBitmap(inventory.getBitmap()));
		
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		sql = new SQLiteHelper(this);
		
		Avatar avatar = new Avatar();
		Inventory inventory = sql.getInventory();

		
		// This image goes in hp/xp bar.
		ImageView image = (ImageView) findViewById(R.id.character_activity);
		image.setImageBitmap(avatar.getBitmap(inventory.getBitmap()));
	}
	
}
