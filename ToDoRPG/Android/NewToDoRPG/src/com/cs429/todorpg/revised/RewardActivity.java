package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class RewardActivity extends Activity {

	TextView rewards_heading;
	TextView gold;
	Character test_character = new Character();
	Button add_reward;
	EditText add_reward_edit;
	ArrayList<Reward> reward_data = new ArrayList<Reward>();
	ListView reward_list;
	
	private class RewardsAdapter extends ArrayAdapter<Reward>{

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<Reward> data = null;
	    
	    public RewardsAdapter(Context context, int layoutResourceId, ArrayList<Reward> data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        RewardHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new RewardHolder();
	            holder.cost = (TextView)row.findViewById(R.id.reward_cost);
	            holder.info = (TextView)row.findViewById(R.id.reward_title);
	            holder.button = (Button)row.findViewById(R.id.purchase_reward);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (RewardHolder)row.getTag();
	        }
	        
	        Reward reward = data.get(position);
	        holder.info.setText(reward.getInfo());
	        holder.cost.setText(Integer.toString(reward.getCost()));
	        final int cost = reward.getCost();
	        
	        holder.button.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					update(cost);
				}
	        });
	        
	        row.findViewById(R.id.purchase_reward).setTag(position);
	        
	        return row;
	    }
	    
	    public class RewardHolder
	    {
	        TextView cost;
	        TextView info;
	        Button button;
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rewards);
		FindViewById();
		setUpLayout();
	}
	
	private void FindViewById(){
		rewards_heading = (TextView) findViewById(R.id.rewards_heading);
		gold = (TextView) findViewById(R.id.gold);
		add_reward = (Button) findViewById(R.id.add_rewards_btn);
		add_reward_edit = (EditText) findViewById(R.id.add_rewards_edit);
		reward_list = (ListView) findViewById(R.id.rewards_list);
	}
	
	private void setUpLayout(){
	    
	    gold.setText("Gold: " + test_character.getGold());
	    
	    add_reward.setOnClickListener(ButtonListener);

	}
	
	public void update(int i) {
		int value = test_character.getGold() - i;
		test_character.setGold(value);
	    gold.setText("Gold: " + value );

	}
	
	private void addReward() {
		Reward new_reward = new Reward(add_reward_edit.getText().toString(), 10);
		reward_data.add(new_reward);
		add_reward_edit.setText("");
		
		RewardsAdapter adapter = new RewardsAdapter(this, R.layout.reward_list_item_row, reward_data);
		reward_list.setAdapter(adapter);


		
	}
	
	Button.OnClickListener ButtonListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.add_rewards_btn:
				addReward();
				break;
			

			}
		}
	};
}
