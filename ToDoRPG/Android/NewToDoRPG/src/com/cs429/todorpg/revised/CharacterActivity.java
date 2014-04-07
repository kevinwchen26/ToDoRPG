package com.cs429.todorpg.revised;

import android.os.Bundle;
import android.widget.ImageView;

public class CharacterActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_activity);
		setHeader(R.id.header);
	}
}
