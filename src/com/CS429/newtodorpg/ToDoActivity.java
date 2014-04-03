package com.CS429.newtodorpg;

import android.os.Bundle;

public class ToDoActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_activity);
		setHeader(R.id.header);
	}

}
