package com.cs429.todorpg.revised.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
/**
 * 
 * @author hlim10, ssong25
 *
 */
public class DailyAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Daily> daily;
	private DailyAdapter adapter = this;
	private LayoutInflater inflater;
	private SQLiteHelper db;
	private int difficulty;
	private String title;

	public DailyAdapter(Context context, ArrayList<Daily> daily) {
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.daily = daily;
		db = new SQLiteHelper(context);
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
		difficulty = day.getDifficulty();
		title = "Completed Daily: " + day.getDaily();
		String blank = "    ";
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.daily_list_view_row, parent, false);
		}
		
		final TextView my_daily = (TextView) convertView.findViewById(R.id.daily_text);
		my_daily.setText(blank + daily.get(position).getDaily());

		final EditText change_title = (EditText) convertView.findViewById(R.id.change_title);
		final EditText extra_notes = (EditText) convertView.findViewById(R.id.extra_notes);
		
		final Button check_button = (Button) convertView.findViewById(R.id.check_daily_button);
		final Button hard = (Button)convertView.findViewById(R.id.hard);
		final Button medium = (Button)convertView.findViewById(R.id.medium);
		final Button easy = (Button)convertView.findViewById(R.id.easy);
		final Button save_close_button = (Button) convertView.findViewById(R.id.save_close);
		
		change_title.setText(daily.get(position).getDaily());
		
		final ImageButton edit_button = (ImageButton) convertView.findViewById(R.id.daily_edit_button);
		final ImageButton cancel_button = (ImageButton) convertView.findViewById(R.id.daily_cancel_button);
		final ImageButton save_button = (ImageButton) convertView.findViewById(R.id.daily_save_button);
		final ImageButton delete_button = (ImageButton) convertView.findViewById(R.id.daily_delete_button);
		final View show_edit_field = (View) convertView.findViewById(R.id.show_edit_field);
		
		final Button mon = (Button)convertView.findViewById(R.id.mo_btn);
		final Button tue = (Button)convertView.findViewById(R.id.tu_btn);
		final Button wed = (Button)convertView.findViewById(R.id.we_btn);
		final Button thu = (Button)convertView.findViewById(R.id.th_btn);
		final Button fri = (Button)convertView.findViewById(R.id.fr_btn);
		final Button sat = (Button)convertView.findViewById(R.id.sa_btn);
		final Button sun = (Button)convertView.findViewById(R.id.su_btn);
		
		//set color beforehand		
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
					UpdateCharacterStatus();
					BaseActivity.TextValidate();
					edit_button.setClickable(false);
					edit_button.setFocusable(false);
				}	
				else{
					check_button.setText(R.string.plus);
					edit_button.setClickable(true);
					edit_button.setFocusable(true);
				}
				db.updateDaily(day);
				
				Calendar c = Calendar.getInstance();
				System.out.println("Current time => " + c.getTime());

				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String formattedDate = df.format(c.getTime());
				db.addLogItem(new LogItem(title, formattedDate));
				
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
				day.setExtra(extra_notes.getText().toString());
				daily.get(position).setDaily(change_title.getText().toString());
				db.updateDaily(day);
				
				adapter.notifyDataSetChanged();
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});
		
		OnClickListener difficultyListener = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
				case R.id.hard:
					Log.d("[HABIT]", "difficult hard");
					day.setDifficulty(2);
					hard.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;
					
				case R.id.medium:
					Log.d("[HABIT]", "difficult medium");
					day.setDifficulty(1);
					medium.setBackgroundResource(R.color.selected);
					hard.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;
					
				case R.id.easy:
					Log.d("[HABIT]", "difficult easy");
					day.setDifficulty(0);
					easy.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				}
				difficulty = day.getDifficulty();
			}
		};
		hard.setOnClickListener(difficultyListener);
		medium.setOnClickListener(difficultyListener);
		easy.setOnClickListener(difficultyListener);
		
		edit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				change_title.setText(daily.get(position).getDaily());
				edit_button.setVisibility(View.GONE);
				cancel_button.setVisibility(View.VISIBLE);
				save_button.setVisibility(View.VISIBLE);
				show_edit_field.setVisibility(View.VISIBLE);
				
				if(day.getExtra() != null)
					extra_notes.setText(day.getExtra());

				switch(day.getDifficulty()){
				case 0:	//easy
					easy.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				case 1:	//medium
					medium.setBackgroundResource(R.color.selected);
					easy.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				case 2:	//hard
					hard.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;
				}
				
				for(int i = 0; i < 7; ++i)
					if(day.getRegularDate(i)){
						Log.d("[Day]", "regular set");
						switch(i){
						
						case 0:
							Log.d("[Day]", "regular mon");
							if(day.getRegularDate(0))
								mon.setBackgroundResource(R.color.selected);
							else
								mon.setBackgroundResource(R.color.original);
							break;
							
						case 1:
							Log.d("[Day]", "regular tue");
							if(day.getRegularDate(1))
								tue.setBackgroundResource(R.color.selected);
							else
								tue.setBackgroundResource(R.color.original);
							break;
							
						case 2:
							Log.d("[Day]", "regular wed");
							if(day.getRegularDate(02))
								wed.setBackgroundResource(R.color.selected);
							else
								wed.setBackgroundResource(R.color.original);
							break;
							
						case 3:
							Log.d("[Day]", "regular thu");
							if(day.getRegularDate(3))
								thu.setBackgroundResource(R.color.selected);
							else
								thu.setBackgroundResource(R.color.original);
							break;
							
						case 4:
							Log.d("[Day]", "regular fri");
							if(day.getRegularDate(4))
								fri.setBackgroundResource(R.color.selected);
							else
								fri.setBackgroundResource(R.color.original);
							break;
							
						case 5:
							Log.d("[Day]", "regular sat");
							if(day.getRegularDate(5))
								sat.setBackgroundResource(R.color.selected);
							else
								sat.setBackgroundResource(R.color.original);
							break;
							
						case 6:
							Log.d("[Day]", "regular sun");
							if(day.getRegularDate(6))
								sun.setBackgroundResource(R.color.selected);
							else
								sun.setBackgroundResource(R.color.original);
							break;
						}
					}
			}
		});

		save_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				day.setExtra(extra_notes.getText().toString());

				daily.get(position).setDaily(change_title.getText().toString());
				db.updateDaily(daily.get(position));
				
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
				db.deleteDaily(daily.get(position));
				daily.remove(position);
				adapter.notifyDataSetChanged();
			}
		});

		OnClickListener RegularListener = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
				
				case R.id.mo_btn:
					day.toggleRegularDate(0);
					if(day.getRegularDate(0))
						mon.setBackgroundResource(R.color.selected);
					else
						mon.setBackgroundResource(R.color.original);
					break;
					
				case R.id.tu_btn:
					day.toggleRegularDate(1);
					if(day.getRegularDate(1))
						tue.setBackgroundResource(R.color.selected);
					else
						tue.setBackgroundResource(R.color.original);
					break;
					
				case R.id.we_btn:
					day.toggleRegularDate(2);
					if(day.getRegularDate(2))
						wed.setBackgroundResource(R.color.selected);
					else
						wed.setBackgroundResource(R.color.original);
					break;
					
				case R.id.th_btn:
					day.toggleRegularDate(3);
					if(day.getRegularDate(3))
						thu.setBackgroundResource(R.color.selected);
					else
						thu.setBackgroundResource(R.color.original);
					break;
					
				case R.id.fr_btn:
					day.toggleRegularDate(4);
					if(day.getRegularDate(4))
						fri.setBackgroundResource(R.color.selected);
					else
						fri.setBackgroundResource(R.color.original);
					break;
					
				case R.id.sa_btn:
					day.toggleRegularDate(5);
					if(day.getRegularDate(5))
						sat.setBackgroundResource(R.color.selected);
					else
						sat.setBackgroundResource(R.color.original);
					break;
					
				case R.id.su_btn:
					day.toggleRegularDate(6);
					if(day.getRegularDate(6))
						sun.setBackgroundResource(R.color.selected);
					else
						sun.setBackgroundResource(R.color.original);
					break;
				}
			}
		};
		mon.setOnClickListener(RegularListener);
		tue.setOnClickListener(RegularListener);
		wed.setOnClickListener(RegularListener);
		thu.setOnClickListener(RegularListener);
		fri.setOnClickListener(RegularListener);
		sat.setOnClickListener(RegularListener);
		sun.setOnClickListener(RegularListener);
		
		//set button either enabled or disabled based on its status
		if(day.getBooleanStatus()){
			Log.d("[Day]", "pos: " + position + " finished...");
			check_button.setText(R.string.check);
			edit_button.setClickable(false);
			edit_button.setFocusable(false);
			
		}	
		else{
			Log.d("[Day]", "pos: " + position + " not yet finished...");
			check_button.setText(R.string.plus);
			edit_button.setClickable(true);
			edit_button.setFocusable(true);
		}
		
		return convertView;
	}
	private void UpdateCharacterStatus() {
		ToDoCharacter character = db.getCharacter();
		switch(difficulty) {
			case 0:
				character = new ToDoCharacter(character.getName(), character.getGold() + 10, character.getHP(),
						character.getLevel(), character.getCurrExp() + 10, character.getNextExp()- 10);
				break;
			case 1:
				character = new ToDoCharacter(character.getName(), character.getGold() + 20, character.getHP(),
						character.getLevel(), character.getCurrExp() + 20, character.getNextExp()- 20);
				break;
			case 2:
				character = new ToDoCharacter(character.getName(), character.getGold() + 30, character.getHP(),
						character.getLevel(), character.getCurrExp() + 40, character.getNextExp()- 30);
				break;
		}
		if (character.getCurrExp() >= character.getLevel() * 100) {
			character.setLevel(character.getLevel() + 1);
			character.setCurrExp(0);
			character.setHP(character.getHP() + 20);
		} else if(character.getLevel() == 1 && character.getCurrExp() < 0) {
			character.setCurrExp(0);
		} else if (character.getCurrExp() <= 0 && character.getLevel() > 1) {
			character.setLevel(character.getLevel() - 1);
			character.setHP(character.getHP() - 20);
			character.setCurrExp(character.getLevel() * 100);
			if(character.getHP() < 100)
				character.setHP(100);
			
		}
		if(character.getGold() < 0) 
			character.setGold(0);
		db.updateCharacter(character);
//		character = new ToDoCharacter(character.getGold(), HP, level, currentEXP, nextEXP)
	}
	
}
