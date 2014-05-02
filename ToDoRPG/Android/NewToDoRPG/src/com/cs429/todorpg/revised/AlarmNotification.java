package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.DailyAdapter;
import com.cs429.todorpg.revised.model.Daily;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.Constants;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
/**
 * 
 * @author hlim10, ssong25
 *
 */
public class AlarmNotification extends Activity {
	private ListView finished_list, missed_list;
	private SQLiteHelper db;
	private Context context;
	String change;
	ArrayList<Daily> finished_arr, missed_arr;
	private DailyAdapter finished_adapter, missed_adapter;
	private Vibrator v;
	// private Ringtone r;
	private static long[] pattern = { 1000, 200, 1000, 2000, 1200 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_notification);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		findViewById();
		init();
		this.context = this;
		db = new SQLiteHelper(this);
		getData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		v.cancel();
	}

	private void init() {
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(pattern, 0);
	}

	private void findViewById() {
		finished_list = (ListView)findViewById(R.id.finished_list);
		missed_list = (ListView)findViewById(R.id.missed_list);
	}
	private void getData() {
		finished_arr = db.getDailies(1);
		missed_arr = db.getDailies(2);
		finished_adapter = new DailyAdapter(AlarmNotification.this, finished_arr);
		finished_list.setAdapter(finished_adapter);
		missed_adapter = new DailyAdapter(AlarmNotification.this, missed_arr);
		missed_list.setAdapter(missed_adapter);
		UpdateCharacter();
	}
	private void UpdateCharacter() {
		ToDoCharacter character = db.getCharacter();
		change = "You Lost [EXP : " + missed_arr.size()*10 + "], [GOLD : " + missed_arr.size()*10 + "]";
		System.out.println(change);
		character = new ToDoCharacter(character.getName(), character.getGold() - (missed_arr.size()*10), character.getHP(),
				character.getLevel(), character.getCurrExp() - (missed_arr.size()*10), character.getNextExp() + (missed_arr.size()*10));
		
		if (character.getCurrExp() >= character.getLevel() * 100) {
			character.setLevel(character.getLevel() + 1);
			character.setCurrExp(0);
			character.setHP(character.getHP() + 20);
		} else if(character.getLevel() == 1 && character.getCurrExp() < 0) {
			character.setCurrExp(0);
		} else if (character.getCurrExp() <= 0 && character.getLevel() > 1) {
			change = change.concat(" + LEVEL DOWN");
			character.setLevel(character.getLevel() - 1);
			character.setHP(character.getHP() - 20);
			character.setCurrExp(character.getLevel() * 100);
			if(character.getHP() < 100)
				character.setHP(100);
			
		}
		if(character.getGold() < 0) 
			character.setGold(0);
		
		LayoutInflater inflater = getLayoutInflater();
	    View view = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.relativeLayout1));
	    Constants.ToastMessage(context, view, change);
		db.updateCharacter(character);
	}

	Button.OnClickListener mListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			default:
				break;
			}
		}
	};

}
