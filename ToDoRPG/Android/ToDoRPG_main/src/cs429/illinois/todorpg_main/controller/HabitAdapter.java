package com.CS429.newtodorpg.controller;

import java.util.ArrayList;

import android.content.Context;
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

import com.CS429.newtodorpg.R;
import com.CS429.newtodorpg.model.Habit;

public class HabitAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Habit> habit;
	private HabitAdapter adapter = this;
	private LayoutInflater inflater;
	public HabitAdapter(Context context, ArrayList<Habit> habit) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.habit = habit;
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
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.habit_list_view_row, parent, false);
		}
		TextView my_habit = (TextView) convertView.findViewById(R.id.habit_text);
		my_habit.setText(blank + habit.get(position).getHabit());

		final EditText change_title = (EditText) convertView.findViewById(R.id.change_title);
		EditText extra_notes = (EditText) convertView.findViewById(R.id.extra_notes);
		Button change_plus_button = (Button) convertView.findViewById(R.id.change_plus_btn);
		Button change_minus_button = (Button) convertView.findViewById(R.id.change_minus_btn);
		final Button blank_button = (Button) convertView.findViewById(R.id.blank_button);
		final Button easy_button = (Button) convertView.findViewById(R.id.easy);
		final Button medium_button = (Button) convertView.findViewById(R.id.medium);
		final Button hard_button = (Button) convertView.findViewById(R.id.hard);
		final Button save_close_button = (Button) convertView.findViewById(R.id.save_close);
		
		change_title.setText(habit.get(position).getHabit());
		
		final Button good_button = (Button) convertView.findViewById(R.id.good_habit_button);
		final Button bad_button = (Button) convertView.findViewById(R.id.bad_habit_button);
		final ImageButton edit_button = (ImageButton) convertView.findViewById(R.id.habit_edit_button);
		final ImageButton cancel_button = (ImageButton) convertView.findViewById(R.id.habit_cancel_button);
		final ImageButton save_button = (ImageButton) convertView.findViewById(R.id.habit_save_button);
		final ImageButton delete_button = (ImageButton) convertView.findViewById(R.id.habit_delete_button);
		final View show_edit_field = (View) convertView.findViewById(R.id.show_edit_field);
		
		save_close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				habit.get(position).setHabit(change_title.getText().toString());
				adapter.notifyDataSetChanged();
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});
		
		easy_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Easy!", Toast.LENGTH_SHORT).show();
			}
		});
		
		medium_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Medium!", Toast.LENGTH_SHORT).show();
			}
		});
		
		hard_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Hard!", Toast.LENGTH_SHORT).show();
			}
		});
		
		change_plus_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(good_button.getVisibility() == View.VISIBLE) {
					good_button.setVisibility(View.GONE);
					blank_button.setVisibility(View.VISIBLE);
				} else {
					good_button.setVisibility(View.VISIBLE);
					blank_button.setVisibility(View.GONE);
				}
				
			}
		});
		
		change_minus_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(bad_button.getVisibility() == View.VISIBLE) {
					bad_button.setVisibility(View.GONE);
					blank_button.setVisibility(View.VISIBLE);
				} else {
					bad_button.setVisibility(View.VISIBLE);
					blank_button.setVisibility(View.GONE);
				}
			}
		});
		
		good_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
			}
		});
		
		bad_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Bad!", Toast.LENGTH_SHORT).show();
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

			}
		});

		save_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				habit.get(position).setHabit(change_title.getText().toString());
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
				habit.remove(position);
				adapter.notifyDataSetChanged();
			}
		});

		return convertView;
	}

}
