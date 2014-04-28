package com.cs429.todorpg.revised.test;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.RewardActivity;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class RewardActivityTest extends ActivityInstrumentationTestCase2<RewardActivity> {

	private Solo solo;
	private RewardActivity activity;
	private SQLiteHelper db;

	public RewardActivityTest(){
		super(RewardActivity.class);
	}
	@Override
	public void setUp() throws Exception{
		super.setUp();
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
		RenamingDelegatingContext context = new RenamingDelegatingContext(getActivity().getBaseContext(), "test_");
		db = new SQLiteHelper(context);
	}
	
	public void test_0_InitialConditions() throws Exception{
		TextView gold = (TextView) solo.getView(R.id.gold);
		assertEquals(gold.getText().toString(), "Gold: 100");
	}
	
	public void test_1_EmptyReward() throws Exception{
		Button add = (Button)solo.getView(R.id.add_reward_button);
		solo.clickOnView(add);
		assertTrue(solo.waitForText("Fill in the blank"));
	}
	
	public void test_2_AddAReward() throws Exception{
		ListView reward_list = (ListView)solo.getView(R.id.rewards_list);
		EditText new_reward = (EditText)solo.getView(R.id.add_reward_field);
		solo.clearEditText(new_reward);
		solo.enterText(new_reward, "Test");
		Button add = (Button)solo.getView(R.id.add_reward_button);
		solo.clickOnView(add);
		solo.sleep(200);
		assertEquals(1, reward_list.getCount());
	}
	
	public void test_3_AddDuplicateReward() throws Exception{
		EditText new_reward = (EditText)solo.getView(R.id.add_reward_field);
		solo.clearEditText(new_reward);
		solo.enterText(new_reward, "Test");
		Button add = (Button)solo.getView(R.id.add_reward_button);
		solo.clickOnView(add);
		assertTrue(solo.waitForText("\"Test\" is alreay in your reward list"));
	}
	
	
	public void test_4_EditReward() {
		ImageButton edit_button = (ImageButton) solo.getView(R.id.reward_edit_button);
		solo.clickOnView(edit_button);
		TextView reward_title = (TextView)solo.getView(R.id.reward_title);
		TextView reward_cost = (TextView)solo.getView(R.id.reward_cost);
		EditText change_reward_title = (EditText)solo.getView(R.id.change_reward_title);
		EditText change_reward_price = (EditText)solo.getView(R.id.change_reward_price);
		ImageButton cancel_button = (ImageButton) solo.getView(R.id.reward_cancel_button);
		Button save_close_button = (Button)solo.getView(R.id.reward_save_close);
		
		solo.clickOnView(cancel_button);
		assertEquals(reward_title.getText().toString(), "Test"); 
		assertEquals(reward_cost.getText().toString(), "10");
		
		solo.clickOnView(edit_button);
		solo.clearEditText(change_reward_title);
		solo.enterText(change_reward_title, "New Title");
		solo.clearEditText(change_reward_price);
		solo.enterText(change_reward_price, "20");
		solo.clickOnView(save_close_button);
		
		solo.sleep(200);

		
		assertEquals(reward_title.getText().toString(), "New Title"); 
		assertEquals(reward_cost.getText().toString(), "20");
		
		
	}
	
	public void test_5_Purchase(){
		Button purchase = (Button)solo.getView(R.id.purchase_reward);
		solo.clickOnView(purchase);
		TextView gold = (TextView) solo.getView(R.id.gold);
		solo.sleep(200);

		assertEquals(gold.getText().toString(), "Gold: 100");
	}
	
	
	public void test_6_DeleteReward() throws Exception {
		ListView reward_list = (ListView)solo.getView(R.id.rewards_list);
		ImageButton delete = (ImageButton)solo.getView(R.id.reward_delete_button);
		solo.clickOnView(delete);
		solo.sleep(200);
		assertEquals(0, reward_list.getCount());
	}
	
	@Override 
	protected void tearDown() throws Exception{

		
		solo.finishOpenedActivities();
		
	}

}
