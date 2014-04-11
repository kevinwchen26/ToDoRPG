package com.cs429.todorpg.revised.controller;

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

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.Daily;

public class DailyAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Daily> daily;
	private DailyAdapter adapter = this;
	private LayoutInflater inflater;

	public DailyAdapter(Context context, ArrayList<Daily> daily) {
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.daily = daily;
	}

	@Override
	public int getCount() {
		return daily.size();
	}

	@Override
	public Object getItem(int position) {
		return daily.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Daily day = daily.get(position);
		String blank = "    ";
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.daily_list_view_row, parent, false);
		}
		
		final TextView my_daily = (TextView) convertView.findViewById(R.id.daily_text);
		my_daily.setText(blank + daily.get(position).getDaily());

		final EditText change_title = (EditText) convertView.findViewById(R.id.change_title);
		EditText extra_notes = (EditText) convertView.findViewById(R.id.extra_notes);
		
		final Button check_button = (Button) convertView.findViewById(R.id.check_daily_button);
		final Button easy_button = (Button) convertView.findViewById(R.id.easy);
		final Button medium_button = (Button) convertView.findViewById(R.id.medium);
		final Button hard_button = (Button) convertView.findViewById(R.id.hard);
		final Button save_close_button = (Button) convertView.findViewById(R.id.save_close);
		
		change_title.setText(daily.get(position).getDaily());
		
		final ImageButton edit_button = (ImageButton) convertView.findViewById(R.id.daily_edit_button);
		final ImageButton cancel_button = (ImageButton) convertView.findViewById(R.id.daily_cancel_button);
		final ImageButton save_button = (ImageButton) convertView.findViewById(R.id.daily_save_button);
		final ImageButton delete_button = (ImageButton) convertView.findViewById(R.id.daily_delete_button);
		final View show_edit_field = (View) convertView.findViewById(R.id.show_edit_field);
		
		//set color beforehand
		if(day.getBooleanStatus()){
			check_button.setText(R.string.check);
			edit_button.setClickable(false);
			edit_button.setFocusable(false);
		}	
		else{
			check_button.setText(R.string.plus);
			edit_button.setClickable(true);
			edit_button.setFocusable(true);
		}
		
		my_daily.setBackgroundResource(day.getStatus());
		edit_button.setBackgroundResource(day.getStatus());
		cancel_button.setBackgroundResource(day.getStatus());
		save_button.setBackgroundResource(day.getStatus());
		delete_button.setBackgroundResource(day.getStatus());
		
		check_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				day.toggleFinish();
				
				if(day.getBooleanStatus()){
					check_button.setText(R.string.check);
					edit_button.setClickable(false);
					edit_button.setFocusable(false);
				}	
				else{
					check_button.setText(R.string.plus);
					edit_button.setClickable(true);
					edit_button.setFocusable(true);
				}
				
				my_daily.setBackgroundResource(day.getStatus());
				edit_button.setBackgroundResource(day.getStatus());
				cancel_button.setBackgroundResource(day.getStatus());
				save_button.setBackgroundResource(day.getStatus());
				delete_button.setBackgroundResource(day.getStatus());
			}
		});
		
		save_close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				daily.get(position).setDaily(change_title.getText().toString());
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
		
		edit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				change_title.setText(daily.get(position).getDaily());
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
				daily.get(position).setDaily(change_title.getText().toString());
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
				daily.remove(position);
				adapter.notifyDataSetChanged();
			}
		});

		return convertView;
	}

}
