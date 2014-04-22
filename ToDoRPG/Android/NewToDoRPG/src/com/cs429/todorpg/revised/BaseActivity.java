package com.cs429.todorpg.revised;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

abstract class BaseActivity extends Activity {
	Intent intent;
	ActionBar actionbar;
	TextView hp, exp;
	PopupMenu popup;
	private SQLiteHelper db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			Intent homeintent = new Intent(BaseActivity.this,
					MainActivity.class);
			startActivity(homeintent);
			finish();
			return true;

		case R.id.character_status:
			/*
			 * intent = new Intent(BaseActivity.this, CharacterActivity.class);
			 * startActivity(intent); finish(); return true;
			 */
			popup = new PopupMenu(BaseActivity.this,
					(View) findViewById(R.id.character_status));
			popup.getMenuInflater().inflate(R.menu.character_menu,
					popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					if (item.getTitle().equals("Status")) {
						Intent intent = new Intent(BaseActivity.this,
								StatusActivity.class);
						startActivity(intent);
						finish();
					} else if (item.getTitle().equals("Inventory")) {
						Intent intent = new Intent(BaseActivity.this,
								InventoryActivity.class);
						startActivity(intent);
						finish();
					}
					return true;
				}
			});
			popup.show();
			return true;

			/*
			 * case R.id.inventory: intent = new Intent(BaseActivity.this,
			 * InventoryActivity.class); startActivity(intent); finish(); return
			 * true;
			 */

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

	private void setActionbar() {
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setIcon(R.drawable.ic_character);
		actionbar.setDisplayShowTitleEnabled(false);

	}

	protected void setHeader(int resId) {
		hp = (TextView) findViewById(R.id.character_hp);
		exp = (TextView) findViewById(R.id.character_exp);
		ToDoCharacter character = db.getCharacter();
		hp.setText(Integer.toString(character.getHP()));
		exp.setText(Integer.toString(character.getCurrExp()));
	}

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
		// TODO Auto-generated method stub

	}

}