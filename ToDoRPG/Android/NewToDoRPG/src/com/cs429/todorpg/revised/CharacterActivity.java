package com.cs429.todorpg.revised;

import android.os.Bundle;
import android.widget.ImageView;

public class CharacterActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_activity);
		setHeader(R.id.header);
		Avatar avatar = new Avatar();
		ImageView image = (ImageView) findViewById(R.id.character_activity);
		image.setImageBitmap(avatar.getBitmap());
		
		ImageView image2 = (ImageView) findViewById(R.id.character_icon);
		image2.setImageBitmap(avatar.getBitmap());
		
	}

}
