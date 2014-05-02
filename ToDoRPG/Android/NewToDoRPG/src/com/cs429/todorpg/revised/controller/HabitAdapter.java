package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.revised.BaseActivity;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
/**
 * 
 * @author hlim10, ssong25
 *
 */
public class HabitAdapter extends BaseAdapter  {
	private Context context;
	private ArrayList<Habit> habit;
	private HabitAdapter adapter = this;
	private LayoutInflater inflater;
	private SQLiteHelper db;
	private int difficulty;
	String change;

	public HabitAdapter(Context context, ArrayList<Habit> habit) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.habit = habit;
		db = new SQLiteHelper(context);
	}

	@Override
	public int getCount() {
		return habit.size();
	}

	@Override
	public Object getItem(int position) {
		return habit.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		String blank = "    ";
		final Habit onehabit = this.habit.get(position);
		difficulty = onehabit.getDifficulty();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.habit_list_view_row,
					parent, false);
		}

		final View returnView = convertView;

		// returnView.setBackgroundColor(onehabit.getStatus());
		final TextView my_habit = (TextView) returnView
				.findViewById(R.id.habit_text);
		my_habit.setText(blank + habit.get(position).getHabit());

		// final View habitview =
		// (View)returnView.findViewById(R.id.habit_view);
		final EditText change_title = (EditText) returnView
				.findViewById(R.id.change_title);
		final EditText extra_notes = (EditText) returnView
				.findViewById(R.id.extra_notes);
		Button change_plus_button = (Button) returnView
				.findViewById(R.id.change_plus_btn);
		Button change_minus_button = (Button) returnView
				.findViewById(R.id.change_minus_btn);
		final Button blank_button = (Button) returnView
				.findViewById(R.id.blank_button);
		final Button hard = (Button) convertView.findViewById(R.id.hard);
		final Button medium = (Button) convertView.findViewById(R.id.medium);
		final Button easy = (Button) convertView.findViewById(R.id.easy);
		final Button save_close_button = (Button) returnView
				.findViewById(R.id.save_close);

		change_title.setText(habit.get(position).getHabit());

		final Button good_button = (Button) returnView
				.findViewById(R.id.good_habit_button);
		final Button bad_button = (Button) returnView
				.findViewById(R.id.bad_habit_button);
		final ImageButton edit_button = (ImageButton) returnView
				.findViewById(R.id.habit_edit_button);
		final ImageButton cancel_button = (ImageButton) returnView
				.findViewById(R.id.habit_cancel_button);
		final ImageButton save_button = (ImageButton) returnView
				.findViewById(R.id.habit_save_button);
		final ImageButton delete_button = (ImageButton) returnView
				.findViewById(R.id.habit_delete_button);
		final View show_edit_field = (View) returnView
				.findViewById(R.id.show_edit_field);

		// habitview.setBackgroundColor(onehabit.getStatus());
		// set color beforehand
		good_button.setBackgroundResource(onehabit.getStatus());
		bad_button.setBackgroundResource(onehabit.getStatus());
		my_habit.setBackgroundResource(onehabit.getStatus());
		edit_button.setBackgroundResource(onehabit.getStatus());
		cancel_button.setBackgroundResource(onehabit.getStatus());
		save_button.setBackgroundResource(onehabit.getStatus());
		delete_button.setBackgroundResource(onehabit.getStatus());

		OnClickListener mListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.good_habit_button) {
					// Toast.makeText(context, "Good",
					// Toast.LENGTH_SHORT).show();
					onehabit.plus_change();
					UpdateCharacterStatus(1);
					Log.d("[HABIT]", "progress: " + onehabit.getProgress());
					good_button.setBackgroundResource(onehabit.getStatus());
					bad_button.setBackgroundResource(onehabit.getStatus());
					my_habit.setBackgroundResource(onehabit.getStatus());
					edit_button.setBackgroundResource(onehabit.getStatus());
					cancel_button.setBackgroundResource(onehabit.getStatus());
					save_button.setBackgroundResource(onehabit.getStatus());
					delete_button.setBackgroundResource(onehabit.getStatus());
					// my_habit.setBackgroundResource(color.blue);
				} else if (v.getId() == R.id.bad_habit_button) {
					// Toast.makeText(context, "Bad",
					// Toast.LENGTH_SHORT).show();
					onehabit.minus_change();
					UpdateCharacterStatus(2);
					Log.d("[HABIT]", "progress: " + onehabit.getProgress());
					good_button.setBackgroundResource(onehabit.getStatus());
					bad_button.setBackgroundResource(onehabit.getStatus());
					my_habit.setBackgroundResource(onehabit.getStatus());
					edit_button.setBackgroundResource(onehabit.getStatus());
					cancel_button.setBackgroundResource(onehabit.getStatus());
					save_button.setBackgroundResource(onehabit.getStatus());
					delete_button.setBackgroundResource(onehabit.getStatus());
					// my_habit.setBackgroundResource(Color.YELLOW);
				}
				BaseActivity.TextValidate();
				db.updateHabit(onehabit);
			}
		};
		good_button.setOnClickListener(mListener);
		bad_button.setOnClickListener(mListener);

		save_close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (extra_notes.getText().toString() != null) {
					habit.get(position).setExtra(
							extra_notes.getText().toString());
				}
				habit.get(position).setHabit(change_title.getText().toString());
				db.updateHabit(habit.get(position));

				adapter.notifyDataSetChanged();
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});

		change_plus_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (good_button.getVisibility() == View.VISIBLE) {
					good_button.setVisibility(View.GONE);
					blank_button.setVisibility(View.VISIBLE);
					// in case bad button is visible -- action - only
					if (bad_button.getVisibility() == View.VISIBLE) {
						onehabit.setCharacteristic("-");
						Log.d("[HABIT]", "action - only");
					}
					// in case bad button is invisible --action none
					else {
						onehabit.setCharacteristic("NA");
						Log.d("[HABIT]", "action none");
					}
				} else {
					good_button.setVisibility(View.VISIBLE);
					blank_button.setVisibility(View.GONE);
					// in case bad button is visible -- action both
					if (bad_button.getVisibility() == View.VISIBLE) {
						onehabit.setCharacteristic("+-");
						Log.d("[HABIT]", "action both");
					}
					// in case bad button is invisible --action + only
					else {
						onehabit.setCharacteristic("+");
						Log.d("[HABIT]", "action + only");
					}
				}

			}
		});

		change_minus_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bad_button.getVisibility() == View.VISIBLE) {
					bad_button.setVisibility(View.GONE);
					blank_button.setVisibility(View.VISIBLE);
					// in case good button is visible - action + only
					if (good_button.getVisibility() == View.VISIBLE) {
						onehabit.setCharacteristic("+");
						Log.d("[HABIT]", "action + only");
					}
					// in case good button is invisible - action none
					else {
						onehabit.setCharacteristic("NA");
						Log.d("[HABIT]", "action none");
					}
				} else {
					bad_button.setVisibility(View.VISIBLE);
					blank_button.setVisibility(View.GONE);
					// in case good button is visible - action both
					if (good_button.getVisibility() == View.VISIBLE) {
						onehabit.setCharacteristic("+-");
						Log.d("[HABIT]", "action both");
					}
					// in case good button is invisible - action - only
					else {
						onehabit.setCharacteristic("-");
						Log.d("[HABIT]", "action - only");
					}
				}
			}
		});

		edit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				change_title.setText(habit.get(position).getHabit());
				edit_button.setVisibility(View.GONE);
				cancel_button.setVisibility(View.VISIBLE);
				save_button.setVisibility(View.VISIBLE);
				show_edit_field.setVisibility(View.VISIBLE);

				extra_notes.setText(onehabit.getExtra());
				switch (onehabit.getDifficulty()) {
				case 0: // easy
					easy.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				case 1: // medium
					medium.setBackgroundResource(R.color.selected);
					easy.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				case 2: // hard
					hard.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;
				}
			}
		});

		save_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (extra_notes.getText().toString() != null) {
					habit.get(position).setExtra(
							extra_notes.getText().toString());
				}
				habit.get(position).setHabit(change_title.getText().toString());
				db.updateHabit(habit.get(position));

				adapter.notifyDataSetChanged();
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});

		cancel_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});

		delete_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				db.deleteHabit(habit.get(position));
				habit.remove(position);
				adapter.notifyDataSetChanged();
			}
		});

		OnClickListener difficultyListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.hard:
					Log.d("[HABIT]", "difficult hard");
					onehabit.setDifficulty(2);
					hard.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;

				case R.id.medium:
					Log.d("[HABIT]", "difficult medium");
					onehabit.setDifficulty(1);
					medium.setBackgroundResource(R.color.selected);
					hard.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;

				case R.id.easy:
					Log.d("[HABIT]", "difficult easy");
					onehabit.setDifficulty(0);
					easy.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				}
				difficulty = onehabit.getDifficulty();
			}

		};
		hard.setOnClickListener(difficultyListener);
		medium.setOnClickListener(difficultyListener);
		easy.setOnClickListener(difficultyListener);

		return returnView;
	}

	private void UpdateCharacterStatus(int sign) {
		ToDoCharacter character = db.getCharacter();
		switch (difficulty) {
		case 0:
			if (sign == 1) {
				change = "Earned [EXP: 10], [GOLD: 10]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() + 10, character.getHP(),
						character.getLevel(), character.getCurrExp() + 10,
						character.getNextExp() - 10);
			} else {
				change = "Lost [EXP: 10], [GOLD: 10]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() - 10, character.getHP(),
						character.getLevel(), character.getCurrExp() - 10,
						character.getNextExp() + 10);
			}
			break;
		case 1:
			if(sign == 1) {
				change = "Earned [EXP: 20], [GOLD: 20]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() + 20, character.getHP(),
						character.getLevel(), character.getCurrExp() + 20,
						character.getNextExp() - 20);
			} else {
				change = "Lost [EXP: 20], [GOLD: 20]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() - 20, character.getHP(),
						character.getLevel(), character.getCurrExp() - 20,
						character.getNextExp() + 20);
			}
			break;
		case 2:
			if(sign == 1) {
				change = "Earned [EXP: 30], [GOLD: 30]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() + 30, character.getHP(),
						character.getLevel(), character.getCurrExp() + 30,
						character.getNextExp() - 30);
			} else {
				change = "Lost [EXP: 30], [GOLD: 30]";
				character = new ToDoCharacter(character.getName(),
						character.getGold() - 30, character.getHP(),
						character.getLevel(), character.getCurrExp() - 30,
						character.getNextExp() + 30);
			}
			break;
		}
		if (character.getCurrExp() >= character.getLevel() * 100) {
			change = "LEVEL UP";
			character.setLevel(character.getLevel() + 1);
			character.setCurrExp(0);
			character.setHP(character.getHP() + 20);
		} else if(character.getLevel() == 1 && character.getCurrExp() < 0) {
			character.setCurrExp(0);
		} else if (character.getCurrExp() <= 0 && character.getLevel() > 1) {
			change = "LEVEL DOWN";
			character.setLevel(character.getLevel() - 1);
			character.setHP(character.getHP() - 20);
			character.setCurrExp(character.getLevel() * 100);
			if(character.getHP() < 100)
				character.setHP(100);
			
		}
		if(character.getGold() < 0) 
			character.setGold(0);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view = inflater.inflate(R.layout.toast, null);
	    TextView text = (TextView) view.findViewById(R.id.textView2);
	    text.setText(change);
	    Toast toast = new Toast(context);
	    toast.setView(view);
	    toast.setDuration(Toast.LENGTH_LONG);
	    toast.show();
	    
	    
		db.updateCharacter(character);
		// character = new ToDoCharacter(character.getGold(), HP, level,
		// currentEXP, nextEXP)
	}

}
