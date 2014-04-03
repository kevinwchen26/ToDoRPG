package com.CS429.newtodorpg;

import android.os.Bundle;

public class ViceActivity  extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vice_activity);
		setHeader(R.id.header);
	}

}