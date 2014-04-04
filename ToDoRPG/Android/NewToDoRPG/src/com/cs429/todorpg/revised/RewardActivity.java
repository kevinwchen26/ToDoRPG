package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RewardActivity extends Activity {

	TextView rewards_heading;
	static TextView gold;
	static Character test_character = new Character();
	Button add_reward;
	EditText add_reward_edit;
	static ArrayList<Reward> reward_data = new ArrayList<Reward>();
	ListView reward_list;
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
	
	public static void update(int i) {
		int value = test_character.getGold() - i;
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
