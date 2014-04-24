package com.cs429.todorpg.revised;

import android.os.Bundle;
import android.view.WindowManager;

public class BattleActivity  extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.battle_activity);
	}
}
