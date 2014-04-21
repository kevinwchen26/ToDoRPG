package com.cs429.todorpg.revised;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.model.ToDoCharacter;

public class MainActivity extends BaseActivity {

	Button rewardsButton;
	Intent intent;
	SQLiteHelper sql;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setHeader(R.id.header);
		sql = new SQLiteHelper(this);
		CreateCharacter();

	}

	private void CreateCharacter() {
		ToDoCharacter character = sql.getCharacter();
		if (character == null) {
			character = new ToDoCharacter("Hello", 0, 100, 1, 0, 100);
			sql.addCharacter(character);
		}

	}

}
