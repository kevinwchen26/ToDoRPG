package com.CS429.todorpg;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

public class QuestCreation extends Activity {
	
	EditText title;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_creation);
		FindViewByID();
	}
	
	private void FindViewByID() {
		
	
	}

}
