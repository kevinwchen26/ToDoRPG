package com.cs429.todorpg.revised;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

/**
 * Status Activity
 * 
 * @author hlim10, kchen26
 * 
 */
public class StatusActivity extends BaseActivity {
	TextView current_level, current_hp, current_exp, completed_quests,
	current_money, total_battles;
	private SQLiteHelper db;
	private ToDoCharacter character;
	int completed_quest_count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.status_activity);
		setHeader(R.id.header);
		db = new SQLiteHelper(getBaseContext());
		FindViewById();
		GetCharacterInfo();

	}

	/**
	 * Connect Id
	 */
	private void FindViewById() {
		current_level = (TextView) findViewById(R.id.current_level);
		current_hp = (TextView) findViewById(R.id.current_hp);
		current_exp = (TextView) findViewById(R.id.current_exp);
		current_money = (TextView) findViewById(R.id.current_money);

	}

	/**
	 * Get Current Character Information(HP, EXP, MONEY, LEVEL) from DB.
	 */
	private void GetCharacterInfo() {
		character = db.getCharacter();
		if (character == null) {
			return;
		}
		TextView char_name = (TextView) findViewById(R.id.char_name);
		char_name.setText(character.getName());
		current_level.setText(Integer.toString(character.getLevel()));
		current_hp.setText(Integer.toString(character.getHP()));
		current_money.setText(Integer.toString(character.getGold()));
		DecimalFormat df = new DecimalFormat("#.00");
		double curr_exp = character.getCurrExp()
				/ (double) (character.getLevel() * 100) * 100;
		String result = df.format(curr_exp).concat("%");
		current_exp.setText(result);

	}

	/**
	 * Set user name
	 * 
	 * @param view
	 */
	public void setName(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter Hero Name");

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input
		// as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String name = input.getText().toString();
				db.addCharacter(new ToDoCharacter(db.getCharacter(), name));

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.show();
	}

}
