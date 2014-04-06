package com.cs429.todorpg.revised;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class CreateRewardActivity extends Activity {
	
	EditText reward_desc, reward_cost;
	Button submit;
	Intent result_intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_reward);
		FindViewById();
		submit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				result_intent = new Intent();
				result_intent.putExtra("description", reward_desc.getText().toString());
				result_intent.putExtra("cost", Integer.parseInt(reward_cost.getText().toString()));
				setResult(Activity.RESULT_OK, result_intent);
				finish();
			}	
		});
	}
	
	private void FindViewById(){
		reward_desc = (EditText)findViewById(R.id.create_reward_description);
		reward_cost = (EditText)findViewById(R.id.create_reward_cost);
		submit = (Button)findViewById(R.id.create_reward_btn);
	}
}
