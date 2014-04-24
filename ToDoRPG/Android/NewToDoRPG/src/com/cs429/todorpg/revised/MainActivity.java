package com.cs429.todorpg.revised;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.cs429.todorpg.revised.model.ToDoCharacter;

public class MainActivity extends BaseActivity {

	Button rewardsButton;
	Intent intent;
	SQLiteHelper sql;
	ToDoCharacter character;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sql = new SQLiteHelper(this);
		CreateCharacter();
		setHeader(R.id.header);

	}

	private void CreateCharacter() {
		character = sql.getCharacter();
		if (character == null) {
			character = new ToDoCharacter("Hello", 0, 100, 1, 0, 100);
			sql.addCharacter(character);
		}

	}

}
