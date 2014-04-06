package com.cs429.todorpg.revised;

import java.util.ArrayList;

import com.cs429.todorpg.revised.controller.HabitAdapter;
import com.cs429.todorpg.revised.model.Reward;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RewardActivity extends BaseActivity {

	TextView rewards_heading;
	TextView gold;
	EditText new_reward;
	Character test_character = new Character();
	Button add_reward;
	ArrayList<Reward> reward_data = new ArrayList<Reward>();
	ListView reward_list;
	Intent intent;
	RewardsAdapter adapter;
	
	private class RewardsAdapter extends ArrayAdapter<Reward>{

	    Context context; 
	    int layoutResourceId;    
		private LayoutInflater inflater;
		RewardsAdapter adapter = this;
		ArrayList<Reward> rewards = null;
	    
	    
	    public RewardsAdapter(Context context, int layoutResourceId, ArrayList<Reward> rewards) {
	        super(context, layoutResourceId, rewards);
	        inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.rewards = rewards;
	    }
	    
	    @Override
		public int getCount() {
			return rewards.size();
		}

		@Override
		public Reward getItem(int position) {
			return rewards.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	    	if (convertView == null) {
				convertView = inflater.inflate(R.layout.reward_list_item_row, parent, false);
			}
	    	final Reward reward = rewards.get(position);
	    	final TextView reward_cost = (TextView)convertView.findViewById(R.id.reward_cost);
	    	final TextView reward_title = (TextView)convertView.findViewById(R.id.reward_title);
	    	final Button purchase = (Button)convertView.findViewById(R.id.purchase_reward);
	    	
	    	
	    	
	    	final EditText change_reward_title = (EditText)convertView.findViewById(R.id.change_reward_title);
	    	final EditText extra_reward_notes = (EditText)convertView.findViewById(R.id.extra_reward_notes);
	    	final EditText change_reward_price = (EditText)convertView.findViewById(R.id.change_reward_price);
	    	final ImageButton edit_button = (ImageButton) convertView.findViewById(R.id.reward_edit_button);
			final ImageButton cancel_button = (ImageButton) convertView.findViewById(R.id.reward_cancel_button);
			final ImageButton save_button = (ImageButton) convertView.findViewById(R.id.reward_save_button);
			final ImageButton delete_button = (ImageButton) convertView.findViewById(R.id.reward_delete_button);
			final Button save_close_button = (Button)convertView.findViewById(R.id.reward_save_close);
			final View show_edit_field = (View) convertView.findViewById(R.id.show_edit_reward_field);
			
			
			
			edit_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					change_reward_title.setText(reward.getInfo());
					change_reward_price.setText(Integer.toString(reward.getCost()));
					edit_button.setVisibility(View.GONE);
					cancel_button.setVisibility(View.VISIBLE);
					save_button.setVisibility(View.VISIBLE);
					show_edit_field.setVisibility(View.VISIBLE);
				}
				
			});
			
			save_close_button.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String new_title = change_reward_title.getText().toString();
					if(new_title.length() == 0) {
						Toast.makeText(context, "Title can't be blank", Toast.LENGTH_SHORT).show();
						return;
					}
					String new_extra = extra_reward_notes.getText().toString();
					int new_cost = Integer.parseInt(change_reward_price.getText().toString());
					reward.setCost(new_cost);
					reward.setInfo(new_title);
					reward.setExtra(new_extra);
					adapter.notifyDataSetChanged();
					edit_button.setVisibility(View.VISIBLE);
					cancel_button.setVisibility(View.GONE);
					save_button.setVisibility(View.GONE);
					show_edit_field.setVisibility(View.GONE);
					
				}
				
			});
			
			
			save_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String new_title = change_reward_title.getText().toString();
					if(new_title.length() == 0) {
						Toast.makeText(context, "Title can't be blank", Toast.LENGTH_SHORT).show();
						return;
					}
					String new_extra = extra_reward_notes.getText().toString();
					int new_cost = Integer.parseInt(change_reward_price.getText().toString());
					reward.setCost(new_cost);
					reward.setInfo(new_title);
					reward.setExtra(new_extra);
					adapter.notifyDataSetChanged();
					
				}
				
			});
			
			cancel_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//change_title.setText(habit.get(position).getHabit());
					edit_button.setVisibility(View.VISIBLE);
					cancel_button.setVisibility(View.GONE);
					save_button.setVisibility(View.GONE);
					show_edit_field.setVisibility(View.GONE);
				}
				
			});
			
			delete_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rewards.remove(position);
					adapter.notifyDataSetChanged();
				}
				
			});
			
			purchase.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View view) {
					updateGold(reward.getCost());
				}
			});
			
			
			
	    	reward_cost.setText(Integer.toString(reward.getCost()));
	    	reward_title.setText(reward.getInfo());
	    	
	    	return convertView;
	    

	    }
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rewards_activity);
		setHeader(R.id.header);

		FindViewById();
		setUpLayout();
	}
	
	private void FindViewById(){
		rewards_heading = (TextView) findViewById(R.id.rewards_heading);
		gold = (TextView) findViewById(R.id.gold);
		new_reward = (EditText) findViewById(R.id.add_reward_field);
		add_reward = (Button) findViewById(R.id.add_reward_button);
		reward_list = (ListView) findViewById(R.id.rewards_list);
	}
	
	private void setUpLayout(){
	    
	    gold.setText("Gold: " + test_character.getGold());
	    
	    add_reward.setOnClickListener(ButtonListener);

	}
	
	public void updateGold(int cost) {
		if(!canPurchase(cost)){
			Toast.makeText(this, "Insufficient Gold", Toast.LENGTH_SHORT).show();
			return;
		}
		int value = test_character.getGold() - cost;
		test_character.setGold(value);
	    gold.setText("Gold: " + value );

	}
	
	public boolean canPurchase(int cost) {
		if(cost > test_character.getGold())
			return false;
		return true;
	}
	private void addReward(String description, int cost) {
		
		for(Reward x : reward_data) {
			if(x.getInfo().equals(description)) {
				Toast.makeText(RewardActivity.this, "\"" + description +"\" is alreay in your reward list", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		Reward new_reward = new Reward(description, cost);
		reward_data.add(new_reward);		
		adapter = new RewardsAdapter(this, R.layout.reward_list_item_row, reward_data);
		adapter.notifyDataSetChanged();

	}
	
	private void SetAdapter() {
		adapter = new RewardsAdapter(this, R.layout.reward_list_item_row, reward_data);
		reward_list.setAdapter(adapter);
		

	}
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.add_reward_button:
				String reward = new_reward.getText().toString();
				if(reward.isEmpty()){
					Toast.makeText(RewardActivity.this, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				addReward(reward, 10);
				new_reward.setText("");
				SetAdapter();
				break;
			

			}
		}
	};
}
