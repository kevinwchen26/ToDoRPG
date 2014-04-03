package com.CS429.newtodorpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

abstract class BaseActivity extends Activity {
	Intent intent;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void setHeader(int resId) {
		findViewById(R.id.character_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.vice_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.todo_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.inventory_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.rewards_activity).setOnClickListener(ImageViewClick);
	}
	/** Header Handler */
	ImageView.OnClickListener ImageViewClick = new ImageView.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.character_activity :
					intent = new Intent(BaseActivity.this, CharacterActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.vice_activity :
					intent = new Intent(BaseActivity.this, ViceActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.todo_activity :
					intent = new Intent(BaseActivity.this, ToDoActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.inventory_activity :
					intent = new Intent(BaseActivity.this, InventoryActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.rewards_activity :
					intent = new Intent(BaseActivity.this, RewardsActivity.class);
					startActivity(intent);
					finish();
					break;
					
			}
		}
		
	};
}