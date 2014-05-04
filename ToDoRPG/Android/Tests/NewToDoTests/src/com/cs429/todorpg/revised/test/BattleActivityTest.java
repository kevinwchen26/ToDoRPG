package com.cs429.todorpg.revised.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.cs429.todorpg.battlelogic.AttackResult;
import com.cs429.todorpg.revised.MockBattleActivity;
import com.cs429.todorpg.revised.R;
import com.robotium.solo.Solo;

/**
 * 
 * @author paulkim6
 * 
 * Test the validity of the battle logic inside a mock battle activity
 *
 */
public class BattleActivityTest extends ActivityInstrumentationTestCase2<MockBattleActivity> {
	
	private MockBattleActivity mActivity, mockContext;
	private Solo solo;
	
	private Button attackButton;
	
	public BattleActivityTest() {
		super(MockBattleActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	protected void setUp() throws Exception {
		System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
		mActivity = getActivity();
		mockContext = mActivity.getMockContext();
		solo = new Solo(getInstrumentation(), mActivity);

		super.setUp();
	}
	
	public void testAttack() {
		TextView enemyHPtext = (TextView) solo.getView(R.id.battle_enemy_info_hp);
		
		Log.d("TEST", "Initial: " + (String) enemyHPtext.getText());
		attackButton = (Button) solo.getView(R.id.attack_button);
		
		solo.clickOnView(attackButton);
		solo.sleep(1000);
		
		// Check turn, should be false.
		assertFalse(mockContext.isMyTurn());
		
		// Check Enemy HP, must less than starting HP
		Log.d("TEST", "After attack: " + Integer.toString(mockContext.getEnemy().getHP()));
		assertTrue(mockContext.getEnemy().getHP() < 100);
	}
	
	public void testDefendHit() {
		mActivity.runOnUiThread(new Runnable() {
			  @Override
			  public void run() {
				  ArrayList<Boolean> critChanceList = new ArrayList<Boolean>();
				  critChanceList.add(false);
				  AttackResult ar = new AttackResult(true, 10, 1, critChanceList);
				  mockContext.defendAttack(ar);
			  }
			});
		
		solo.sleep(1000);
		// Check turn, should be true.
		assertTrue(mockContext.isMyTurn());
		
		// Your HP should be less than 100
		assertTrue(mockContext.getPlayer().getHP() < 100);
	}
	
	public void testDefendMiss() {
		mActivity.runOnUiThread(new Runnable() {
			  @Override
			  public void run() {
				  ArrayList<Boolean> critChanceList = new ArrayList<Boolean>();
				  critChanceList.add(false);
				  AttackResult ar = new AttackResult(false, 10, 1, critChanceList);
				  mockContext.defendAttack(ar);
			  }
			});
		
		solo.sleep(1000);
		// Check turn, should be true.
		assertTrue(mockContext.isMyTurn());
		
		// Your HP must be in tact
		assertTrue(mockContext.getPlayer().getHP() == 100);
	}
}
