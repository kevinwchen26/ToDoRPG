package com.cs429.todorpg.revised;

import android.os.Bundle;

public class InventoryActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_activity);
		setHeader(R.id.header);
	}

}
