package com.cs429.todorpg.revised;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.model.Reward;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

/**
 * Reward Activity Class
 * 
 * @author Leon Chen
 * 
 */
public class RewardActivity extends BaseActivity {

	TextView rewards_heading;
	TextView gold;
	EditText new_reward;
	ToDoCharacter my_character;
	Button add_reward;
	ArrayList<Reward> reward_data;
	ListView reward_list;
	Intent intent;
	RewardsAdapter adapter;
	SQLiteHelper sql = new SQLiteHelper(this);

	/**
	 * list item object
	 * 
	 * @author Jun
	 * 
	 */
	private class RewardsAdapter extends BaseAdapter {

		Context context;
		private LayoutInflater inflater;
		RewardsAdapter adapter = this;
		ArrayList<Reward> rewards = null;

		public RewardsAdapter(Context context, ArrayList<Reward> rewards) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.context = context;
			this.rewards = rewards;
		}

		/**
		 * Return list size
		 */
		@Override
		public int getCount() {
			return rewards.size();
		}

		/**
		 * Get item at position
		 */
		@Override
		public Reward getItem(int position) {
			return rewards.get(position);
		}

		/**
		 * get item id at position
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * get view for a row, and places values from db
		 */
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.reward_list_item_row,
						parent, false);
			}
			final Reward reward = rewards.get(position);
			final TextView reward_cost = (TextView) convertView
					.findViewById(R.id.reward_cost);
			final TextView reward_title = (TextView) convertView
					.findViewById(R.id.reward_title);
			final Button purchase = (Button) convertView
					.findViewById(R.id.purchase_reward);

			final EditText change_reward_title = (EditText) convertView
					.findViewById(R.id.change_reward_title);
			final EditText extra_reward_notes = (EditText) convertView
					.findViewById(R.id.extra_reward_notes);
			final EditText change_reward_price = (EditText) convertView
					.findViewById(R.id.change_reward_price);
			final ImageButton edit_button = (ImageButton) convertView
					.findViewById(R.id.reward_edit_button);
			final ImageButton cancel_button = (ImageButton) convertView
					.findViewById(R.id.reward_cancel_button);
			final ImageButton save_button = (ImageButton) convertView
					.findViewById(R.id.reward_save_button);
			final ImageButton delete_button = (ImageButton) convertView
					.findViewById(R.id.reward_delete_button);
			final Button save_close_button = (Button) convertView
					.findViewById(R.id.reward_save_close);
			final View show_edit_field = (View) convertView
					.findViewById(R.id.show_edit_reward_field);

			/**
			 * edit button listener, for editing reward object
			 */
			edit_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					change_reward_title.setText(reward.getInfo());
					change_reward_price.setText(Integer.toString(reward
							.getCost()));
					edit_button.setVisibility(View.GONE);
					cancel_button.setVisibility(View.VISIBLE);
					save_button.setVisibility(View.VISIBLE);
					show_edit_field.setVisibility(View.VISIBLE);
				}

			});
			/**
			 * save reward, push to DB, close view
			 */
			save_close_button.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					String new_title = change_reward_title.getText().toString();
					if (new_title.length() == 0) {
						Toast.makeText(context, "Title can't be blank",
								Toast.LENGTH_SHORT).show();
						return;
					}
					String new_extra = extra_reward_notes.getText().toString();
					int new_cost = Integer.parseInt(change_reward_price
							.getText().toString());
					reward.setCost(new_cost);
					reward.setInfo(new_title);
					reward.setExtra(new_extra);
					adapter.notifyDataSetChanged();
					edit_button.setVisibility(View.VISIBLE);
					cancel_button.setVisibility(View.GONE);
					save_button.setVisibility(View.GONE);
					show_edit_field.setVisibility(View.GONE);

					if (!sql.updateReward(reward)) {
						Toast.makeText(context, "Reward couldn't be saved",
								Toast.LENGTH_SHORT).show();
					}

				}

			});
			/**
			 * saves reward to DB
			 */
			save_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String new_title = change_reward_title.getText().toString();
					if (new_title.length() == 0) {
						Toast.makeText(context, "Title can't be blank",
								Toast.LENGTH_SHORT).show();
						return;
					}
					String new_extra = extra_reward_notes.getText().toString();
					int new_cost = Integer.parseInt(change_reward_price
							.getText().toString());
					reward.setCost(new_cost);
					reward.setInfo(new_title);
					reward.setExtra(new_extra);
					adapter.notifyDataSetChanged();

					if (!sql.updateReward(reward)) {
						Toast.makeText(context, "Reward couldn't be saved",
								Toast.LENGTH_SHORT).show();
					}

				}

			});
			/**
			 * close view without saving
			 */
			cancel_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					edit_button.setVisibility(View.VISIBLE);
					cancel_button.setVisibility(View.GONE);
					save_button.setVisibility(View.GONE);
					show_edit_field.setVisibility(View.GONE);
				}

			});
			/**
			 * remove reward
			 */
			delete_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!sql.deleteReward(reward)) {
						Toast.makeText(context, "Reward couldn't be deleted",
								Toast.LENGTH_SHORT).show();
						return;
					}

					rewards.remove(position);
					adapter.notifyDataSetChanged();
					edit_button.setVisibility(View.VISIBLE);
					cancel_button.setVisibility(View.GONE);
					save_button.setVisibility(View.GONE);
					show_edit_field.setVisibility(View.GONE);

				}

			});
			/**
			 * Purchase reward, calls helper to check if can purchase
			 */
			purchase.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View view) {
					updateGold(reward.getCost(), reward.getInfo());
				}
			});

			reward_cost.setText(Integer.toString(reward.getCost()));
			reward_title.setText(reward.getInfo());

			return convertView;

		}
	}

	/**
	 * Create function
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rewards_activity);
		setHeader(R.id.header);
		FindViewById();
		my_character = sql.getCharacter();
		setUpLayout();
		pullRewards();

	}

	/**
	 * Pull rewards from DB, set empty list if null
	 */
	private void pullRewards() {
		reward_data = sql.getRewards();
		if (reward_data == null) {
			reward_data = new ArrayList<Reward>();
			return;
		}
		adapter = new RewardsAdapter(this, reward_data);
		adapter.notifyDataSetChanged();
		SetAdapter();
	}

	/**
	 * find views in layouts
	 */
	private void FindViewById() {
		rewards_heading = (TextView) findViewById(R.id.rewards_heading);
		gold = (TextView) findViewById(R.id.gold);
		new_reward = (EditText) findViewById(R.id.add_reward_field);
		add_reward = (Button) findViewById(R.id.add_reward_button);
		reward_list = (ListView) findViewById(R.id.rewards_list);
	}

	/**
	 * set gold value
	 */
	private void setUpLayout() {

		gold.setText("Gold: " + my_character.getGold());

		add_reward.setOnClickListener(ButtonListener);

	}

	/**
	 * updates the gold counter after a user purchases reward. Also saves gold
	 * value to db
	 * 
	 * @param cost
	 * @param itemName
	 */
	public void updateGold(int cost, String itemName) {
		if (!canPurchase(cost)) {
			Toast.makeText(this, "Insufficient Gold", Toast.LENGTH_SHORT)
			.show();
			return;
		}
		int value = my_character.getGold() - cost;
		my_character.setGold(value);
		sql.updateCharacter(my_character);
		gold.setText("Gold: " + value);

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String formattedDate = df.format(c.getTime());
		sql.addLogItem(new LogItem("Bought Item: " + itemName, formattedDate));

		ArrayList<Stat> stats = sql.getStats();
		for (Stat stat : stats) {
			if (stat.getName().equals("Gold Spent")) {
				stat.setCount(stat.getCount() + cost);
				sql.updateStat(stat);
			}
			if (stat.getName().equals("Items Bought")) {
				stat.setCount(stat.getCount() + 1);
				sql.updateStat(stat);
			}
		}

	}

	/**
	 * checks if user has the neccessary gold amount
	 */
	public boolean canPurchase(int cost) {
		if (cost > my_character.getGold())
			return false;
		return true;
	}

	/**
	 * add reward to list and db
	 * 
	 * @param description
	 * @param cost
	 */
	private void addReward(String description, int cost) {

		for (Reward x : reward_data) {
			if (x.getInfo().equals(description)) {
				Toast.makeText(
						RewardActivity.this,
						"\"" + description + "\" is alreay in your reward list",
						Toast.LENGTH_SHORT).show();
				return;
			}
		}

		Reward new_reward = new Reward(description, cost);
		int key = sql.addReward(new_reward);
		new_reward.setPrimary_key(key);

		reward_data.add(new_reward);
		adapter = new RewardsAdapter(this, reward_data);
		adapter.notifyDataSetChanged();

	}

	/**
	 * set list objects
	 */
	private void SetAdapter() {
		adapter = new RewardsAdapter(this, reward_data);
		reward_list.setAdapter(adapter);

	}

	/**
	 * button listener for add reward button
	 */
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.add_reward_button:
				String reward = new_reward.getText().toString();
				if (reward.isEmpty()) {
					Toast.makeText(RewardActivity.this, "Fill in the blank",
							Toast.LENGTH_SHORT).show();
					return;
				}
				addReward(reward, 10);
				new_reward.setText("");
				SetAdapter();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(new_reward.getWindowToken(), 0);
				break;

			}
		}
	};
}
