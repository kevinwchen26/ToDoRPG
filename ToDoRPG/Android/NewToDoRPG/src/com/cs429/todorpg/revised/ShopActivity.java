package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.ShopListAdapter;
import com.cs429.todorpg.revised.itemsystem.EquipCost;
import com.cs429.todorpg.revised.itemsystem.Shop;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

/**
 * The Shop Activity Class where you can purchase weapons and armor
 * 
 * @author Leon Chen
 * 
 */
public class ShopActivity extends BaseActivity {
	ShopListAdapter adapter;
	Shop shop;
	TextView gold;
	ToDoCharacter my_character;
	private static SQLiteHelper db;
	ArrayList<EquipCost> allItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.shop_activity);
		setHeader(R.id.header);

		db = new SQLiteHelper(this);

		gold = (TextView) findViewById(R.id.shop_gold);
		my_character = db.getCharacter();

		gold.setText("Gold: " + my_character.getGold());

		// Set up Shop Items List
		this.shop = new Shop(); // Shop should be a persistent object via
		// database.

		ArrayList<EquipCost> allItems = db.getLibraryAll();

		for (int x = 0; x < allItems.size(); x++)
			shop.addItem(allItems.get(x));

		/* END INITIALIZATION CODE */

		ArrayList<EquipCost> inventoryList = shop.getShopItems();
		ListView listView = (ListView) findViewById(R.id.shop_list);
		adapter = new ShopListAdapter(ShopActivity.this,
				R.layout.shop_list_row, inventoryList);
		adapter.setNotifyOnChange(true);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * Click any of the list items
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showShopDialog(view, position);
			}
		});
	}

	/**
	 * Show options on what to do when the user presses an a shop item
	 * 
	 * @param v
	 */
	public void showShopDialog(View v, final int position) {
		PopupMenu popupMenu = new PopupMenu(ShopActivity.this, v);
		popupMenu.getMenuInflater().inflate(R.menu.shop, popupMenu.getMenu());
		popupMenu
		.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.shop_menu_purchase:
					// when you click purchase, checks for duplicates
					// and adequate cost
					allItems = db.getLibraryAll();
					my_character = db.getCharacter();
					if (allItems.get(position).getCost() > my_character
							.getGold()) {
						Toast.makeText(ShopActivity.this,
								"Too Expensive", Toast.LENGTH_LONG)
								.show();
					} else {
						if (app.avatar.inventory.getInventoryItems()
								.contains(
										allItems.get(position)
										.getEquipment())
										|| isEqual(app.avatar.inventory
												.getArmor(), (allItems
														.get(position).getEquipment()))
														|| isEqual(app.avatar.inventory
																.getHelmet(), (allItems
																		.get(position).getEquipment()))
																		|| isEqual(app.avatar.inventory
																				.getWeapon(), (allItems
																						.get(position).getEquipment()))
																						|| isEqual(app.avatar.inventory
																								.getShield(), (allItems
																										.get(position).getEquipment()))) {
							Toast.makeText(ShopActivity.this,
									"Already Own", Toast.LENGTH_LONG)
									.show();
						} else {
							int newGold = my_character.getGold()
									- allItems.get(position).getCost();
							my_character.setGold(newGold);
							db.updateCharacter(my_character);
							app.avatar.inventory.addInventory(allItems
									.get(position).getEquipment());
							Toast.makeText(ShopActivity.this,
									"Purchased", Toast.LENGTH_LONG)
									.show();
						}
						// resets the gold counter
						my_character = db.getCharacter();
						gold.setText("Gold: " + my_character.getGold());
					}

					// Refresh list
					ShopActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});
					break;

				case R.id.shop_menu_info:
					// Diplay item info
					showItemInfoDialog(shop.getItem(position));

					// Refresh list
					ShopActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});
					break;
				}
				return true;
			}
		});
		popupMenu.show();
	}

	/**
	 * Brings up another Activity that shows the information on the item
	 * 
	 * @param item
	 */
	public void showItemInfoDialog(EquipCost item) {
		final Dialog d = new Dialog(ShopActivity.this);
		d.setContentView(R.layout.item_info_dialog);
		d.setTitle(item.getEquipment().getName());

		TextView damage = (TextView) d.findViewById(R.id.item_info_damage);
		damage.setText("Dmg:" + item.getEquipment().getDamage());

		TextView critical = (TextView) d.findViewById(R.id.item_info_critical);
		critical.setText("Crit:" + item.getEquipment().getCritical());

		TextView multi = (TextView) d.findViewById(R.id.item_info_multi);
		multi.setText("Multi:" + item.getEquipment().getMulti_Hit());

		TextView defense = (TextView) d.findViewById(R.id.item_info_defense);
		defense.setText("Def:" + item.getEquipment().getDamage_Reduction());

		TextView evasion = (TextView) d.findViewById(R.id.item_info_evasion);
		evasion.setText("Eva:" + item.getEquipment().getEvasion());

		TextView accuracy = (TextView) d.findViewById(R.id.item_info_accuracy);
		accuracy.setText("Acc:" + item.getEquipment().getAccuracy());

		TextView effects = (TextView) d.findViewById(R.id.item_info_effects);
		String effectsS = "";

		if (!(item.getEquipment().getnegEffects().isEmpty())) {
			effectsS = effectsS
					+ item.getEquipment().getnegEffects().get(0).getName();
			for (int x = 1; x < item.getEquipment().getnegEffects().size(); x++) {
				effectsS = effectsS + ", "
						+ item.getEquipment().getnegEffects().get(x).getName();
			}
		}
		if (!(item.getEquipment().getposEffects().isEmpty())) {
			effectsS = effectsS
					+ item.getEquipment().getposEffects().get(0).getName();
			for (int x = 1; x < item.getEquipment().getposEffects().size(); x++) {
				effectsS = effectsS + ", "
						+ item.getEquipment().getposEffects().get(x).getName();
			}
		}
		effects.setText("Magic:" + effectsS);

		TextView currentgold = (TextView) d.findViewById(R.id.item_info_cost);
		currentgold.setText("Cost:" + item.getCost());

		ImageView image = (ImageView) d.findViewById(R.id.item_info_image);
		image.setImageResource(item.getEquipment().getResId());
		Button dialogButton = (Button) d
				.findViewById(R.id.item_info_dialog_button);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				d.dismiss();
			}
		});

		d.show();
	}

	private static boolean isEqual(Object o1, Object o2) {
		return o1 == o2 || (o1 != null && o1.equals(o2));
	}
}
