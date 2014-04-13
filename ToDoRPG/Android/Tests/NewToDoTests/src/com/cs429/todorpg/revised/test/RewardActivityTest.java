package com.cs429.todorpg.revised.test;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.RewardActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class RewardActivityTest extends ActivityInstrumentationTestCase2<RewardActivity> {

	private Solo solo;
	private RewardActivity activity;
	
	public RewardActivityTest(){
		super(RewardActivity.class);
	}
	@Override
	public void setUp() throws Exception{
		super.setUp();
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testAddReward() throws Exception{
		ListView reward_list = (ListView)solo.getView(R.id.rewards_list);
		EditText new_reward = (EditText)solo.getView(R.id.add_reward_field);
		solo.clearEditText(new_reward);
		solo.enterText(new_reward, "Test");
		Button add = (Button)solo.getView(R.id.add_reward_button);
		solo.clickOnView(add);
		solo.sleep(2000);
		assertEquals(1, reward_list.getCount());
	}
	
	public void testDeleteReward() throws Exception {
		ListView reward_list = (ListView)solo.getView(R.id.rewards_list);
		ImageButton delete = (ImageButton)solo.getView(R.id.reward_delete_button);
		
	}
	
	@Override 
	protected void tearDown() throws Exception{
		ListView reward_list = (ListView)solo.getView(R.id.rewards_list);
		ImageButton delete = (ImageButton)solo.getView(R.id.reward_delete_button);

		//while(reward_list.getCount() > 0)
			//solo.clickOnView(delete);
		solo.finishOpenedActivities();
		
	}

}
