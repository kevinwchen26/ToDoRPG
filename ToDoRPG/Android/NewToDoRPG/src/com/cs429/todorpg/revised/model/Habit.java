package com.cs429.todorpg.revised.model;

import android.R.raw;
import android.util.Log;

import com.cs429.todorpg.revised.R;

public class Habit {

	/*status of habits - range is 3*/
	private int VERY_GOOD = R.color.very_good;	//8 - 11
	private int GOOD = R.color.good;		//4 - 7
	private int NORMAL = R.color.normal;		//0 - 3
	private int BAD = R.color.bad;			//-1 - -4
	private int VERY_BAD = R.color.very_bad;	//-5 - 8
	
	/*variable in habit*/
	private String my_habit;
	private String extra;
	private int primary_key;
	private int progress;
	
	private String characteristic;	//+: good, -: bad, +-: both, NA: none or not available
	private int difficulty;	//0 - easy / 1 - medium / 2 - hard

	public Habit(String my_habit) {
		this.setHabit(my_habit);
		progress = 0;
		difficulty = 0;	//default easy set
		characteristic = "+-";	//default set both
	}

	public Habit(String my_habit, String extra, int primary_key) {
		this.setHabit(my_habit);
		this.setExtra(extra);
		this.setKey(primary_key);
		progress = 0;
		difficulty = 0;	//default easy set
		characteristic = "+-";	//default set both
	}

	public String getHabit() {
		return my_habit;
	}

	public void setHabit(String my_habit) {
		this.my_habit = my_habit;
	}

	public String getExtra() {
		if(extra == null)
			return new String();
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public int getKey() {
		return primary_key;
	}

	public void setKey(int primary_key) {
		this.primary_key = primary_key;
	}
	
	public boolean equals(Object o) {
		Habit habit = (Habit) o;
		return (this.primary_key == habit.getKey()
				&& this.my_habit.equals(habit.getHabit()) && this.extra
					.equals(habit.getExtra()) && this.progress == habit.getProgress()
					&& this.getCharacteristic().equals(habit.getCharacteristic()) && this.getDifficulty() == habit.getDifficulty());
	}

	public void setProgress(int progress){
		this.progress = progress;
	}
	
	public int getProgress(){
		return progress;
	}
	
	public void plus_change(){
		progress++;
	}
	
	public void minus_change(){
		progress--;
	}
	
	/**
	 * private String VERY_GOOD = "#4682b4";	//8 - 11
	private String GOOD = "#7dff23";		//4 - 7
	private String NORMAL = "#ffff99";		//0 - 3
	private String BAD = "#ff6347";			//-1 - -4
	private String VERY_BAD = "#720000";	//-5 - 8
	 */
	
	public int getStatus(){
		//very bad
		if(progress < -4){
			Log.d("[HABIT", "very bad");
			return VERY_BAD;
		}
		//bad
		else if(progress >= -4 && progress < 0){
			Log.d("[HABIT", "bad");
			return BAD;
		}
		//normal
		else if(progress >= 0 && progress < 4){
			Log.d("[HABIT", "normal");
			return NORMAL;
		}
		//good
		else if(progress >= 4 && progress < 8){
			Log.d("[HABIT", "good");
			return GOOD;
		}
		///very good
		else{
			Log.d("[HABIT", "very good");
			return VERY_GOOD;
		}
	}
	
	public void setCharacteristic(String character){
		this.characteristic = character;
	}
	public String getCharacteristic(){
		if(this.characteristic == null)
			this.characteristic = "+-";
		
		return this.characteristic;
	}
	
	public void setDifficulty(int difficult){
		if(difficult < 0 || difficult > 2){
			Log.e("[Habit]", "invalid difficulty");
			return;
		}
		difficulty = difficult;
	}
	public int getDifficulty(){
		return difficulty;
	}
}
