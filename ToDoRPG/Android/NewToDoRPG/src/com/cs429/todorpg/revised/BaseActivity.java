package com.cs429.todorpg.revised;

import java.text.DecimalFormat;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

/**
 * Action Bar
 * 
 * @author hlim10, ssong25
 * 
 */
public abstract class BaseActivity extends Activity {
	Intent intent;
	ActionBar actionbar;
	static TextView hp, exp;
	PopupMenu popup;
	private static SQLiteHelper db;
	GameApplication app = (GameApplication) getApplication();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (GameApplication) getApplication();
		actionbar = getActionBar();

		if (actionbar != null)
			setActionbar();
		db = new SQLiteHelper(this);
	}

	@Override
	protected void onResume() {
		this.overridePendingTransition(0, 0);
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.baseaction, menu);
		MenuItem item = menu.getItem(0);
		BitmapDrawable drawable = new BitmapDrawable(getApplicationContext()
				.getResources(), app.avatar.getBitmap());
		item.setIcon(drawable);
		return true;
	}

	/**
	 * Action bar listener
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent homeintent = new Intent(BaseActivity.this,
					MainActivity.class);
			startActivity(homeintent);
			finish();
			return true;

		case R.id.character_status:
			popup = new PopupMenu(BaseActivity.this,
					(View) findViewById(R.id.character_status));
			popup.getMenuInflater().inflate(R.menu.character_menu,
					popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					if (item.getTitle().equals("Inventory")) {
						Intent intent = new Intent(BaseActivity.this,
								InventoryActivity.class);
						startActivity(intent);
						finish();
					} else if (item.getTitle().equals("Player Stats")) {
						Intent intent = new Intent(BaseActivity.this,
								PlayerStatsActivity.class);
						startActivity(intent);
						finish();

					} else if (item.getTitle().equals("Event Log")) {
						Intent intent = new Intent(BaseActivity.this,
								EventLogActivity.class);
						startActivity(intent);
						finish();

					}
					return true;
				}
			});
			popup.show();
			return true;

		case R.id.battle:

			popup = new PopupMenu(BaseActivity.this,
					(View) findViewById(R.id.quests));
			popup.getMenuInflater()
			.inflate(R.menu.battle_main, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					if (item.getTitle().equals("Battle Menu")) {
						intent = new Intent(BaseActivity.this,
								BattleMainActivity.class);
					} else if (item.getTitle().equals("Tutorial")) {
						intent = new Intent(BaseActivity.this,
								TutorialBattleActivity.class);
					}
					startActivity(intent);
					finish();
					return true;
				}
			});
			popup.show();
			return true;

		case R.id.quests:
			popup = new PopupMenu(BaseActivity.this,
					(View) findViewById(R.id.quests));
			popup.getMenuInflater().inflate(R.menu.quest_menu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					if (item.getTitle().equals("Habits")) {
						intent = new Intent(BaseActivity.this,
								HabitActivity.class);
					} else if (item.getTitle().equals("Dailies")) {
						intent = new Intent(BaseActivity.this,
								DailyActivity.class);
					} else if (item.getTitle().equals("To-Dos")) {
						intent = new Intent(BaseActivity.this,
								ToDoActivity.class);
					}
					startActivity(intent);
					finish();
					return true;
				}
			});
			popup.show();
			return true;

		case R.id.rewards:
			popup = new PopupMenu(BaseActivity.this,
					(View) findViewById(R.id.quests));
			popup.getMenuInflater()
			.inflate(R.menu.reward_shop, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					if (item.getTitle().equals("Rewards")) {
						intent = new Intent(BaseActivity.this,
								RewardActivity.class);
					} else if (item.getTitle().equals("Shop")) {
						intent = new Intent(BaseActivity.this,
								ShopActivity.class);
					}
					startActivity(intent);
					finish();
					return true;
				}
			});
			popup.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Set Action bar
	 */
	private void setActionbar() {
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		BitmapDrawable drawable = new BitmapDrawable(getApplicationContext()
				.getResources(), app.avatar.getBitmap());
		actionbar.setIcon(drawable);
		actionbar.setDisplayShowTitleEnabled(false);

	}

	/**
	 * Shows current Character HP and EXP
	 * 
	 */
	protected void setHeader(int resId) {
		hp = (TextView) findViewById(R.id.character_hp);
		exp = (TextView) findViewById(R.id.character_exp);
		ToDoCharacter character = db.getCharacter();
		hp.setText(Integer.toString(character.getHP()));
		DecimalFormat df = new DecimalFormat("#.00");
		double curr_exp = character.getCurrExp()
				/ (double) (character.getLevel() * 100) * 100;
		String result = df.format(curr_exp).concat("%");
		exp.setText(result);
		ImageView icon = (ImageView) findViewById(R.id.character_activity);

		if (icon == null) {
			Log.e("appdebug", "icon is null");
		} else if (app == null) {
			Log.e("appdebug", "app is null");
		} else if (app.avatar == null) {
			Log.e("appdebug", "avatar is null");
		} else {
			Log.e("appdebug", "what is null??");
		}

		icon.setImageBitmap(app.avatar.getBitmap());
	}

	/**
	 * Invalidate Text whenever HP or EXP changed
	 */
	public static void TextValidate() {
		ToDoCharacter character = db.getCharacter();
		hp.setText(Integer.toString(character.getHP()));
		DecimalFormat df = new DecimalFormat("#.00");
		double curr_exp = character.getCurrExp()
				/ (double) (character.getLevel() * 100) * 100;
		String result = df.format(curr_exp).concat("%");
		exp.setText(result);
	}

	/**
	 * Dropdown menu handler
	 */
	ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
		@Override
		public boolean onNavigationItemSelected(final int itemPosition,
				long itemId) {
			switch (itemPosition) {

			case 1:
				Log.d("DropDown", "number one");
				return true;

			case 2:
				Log.d("DropDown", "number two");
				return true;

			case 3:
				Log.d("DropDown", "number three");
				return true;

			default:
				return true;
			}
		}
	};

	public void onClick(View view) {

	}

}