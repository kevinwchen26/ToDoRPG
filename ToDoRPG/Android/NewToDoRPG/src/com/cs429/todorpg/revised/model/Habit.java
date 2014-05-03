package com.cs429.todorpg.revised.model;

import android.util.Log;

import com.cs429.todorpg.revised.R;

/**
 * Habits Class
 * 
 * @author hlim10, ssong25
 * 
 */
public class Habit {

	/* status of habits - range is 3 */
	private int VERY_GOOD = R.color.very_good; // 8 - 11
	private int GOOD = R.color.good; // 4 - 7
	private int NORMAL = R.color.normal; // 0 - 3
	private int BAD = R.color.bad; // -1 - -4
	private int VERY_BAD = R.color.very_bad; // -5 - 8

	/* variable in habit */
	private String my_habit;
	private String extra;
	private int primary_key;
	private int progress;

	private String characteristic; // +: good, -: bad, +-: both, NA: none or not
	// available
	private int difficulty; // 0 - easy / 1 - medium / 2 - hard

	/**
	 * Habits Constructor
	 * 
	 * @param my_habit
	 */
	public Habit(String my_habit) {
		this.setHabit(my_habit);
		progress = 0;
		difficulty = 0; // default easy set
		characteristic = "+-"; // default set both
	}

	/**
	 * Habit Constructor
	 * 
	 * @param my_habit
	 * @param extra
	 * @param primary_key
	 */
	public Habit(String my_habit, String extra, int primary_key) {
		this.setHabit(my_habit);
		this.setExtra(extra);
		this.setKey(primary_key);
		progress = 0;
		difficulty = 0; // default easy set
		characteristic = "+-"; // default set both
	}

	/**
	 * Getter for habit
	 * 
	 * @return my_habit
	 */
	public String getHabit() {
		return my_habit;
	}

	/**
	 * setter for habit text
	 * 
	 * @param my_habit
	 */
	public void setHabit(String my_habit) {
		this.my_habit = my_habit;
	}

	/**
	 * gets the extra text
	 * 
	 * @return
	 */
	public String getExtra() {
		if (extra == null)
			return new String();
		return extra;
	}

	/**
	 * sets the extra text
	 * 
	 * @param extra
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}

	/**
	 * gets the key of the habit
	 * 
	 * @return primary_key
	 */
	public int getKey() {
		return primary_key;
	}

	/**
	 * sets the key of the habit
	 * 
	 * @param primary_key
	 */
	public void setKey(int primary_key) {
		this.primary_key = primary_key;
	}

	/**
	 * sets the progress of the habit
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}

	/**
	 * gets the progress of the habit
	 * 
	 * @return
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * adds a positive to the progress
	 */
	public void plus_change() {
		progress++;
	}

	/**
	 * adds a negative to the progress
	 */
	public void minus_change() {
		progress--;
	}

	/**
	 * gets the status of the progress.
	 * 
	 * @return
	 */
	public int getStatus() {
		// very bad
		if (progress < -4) {
			Log.d("[HABIT", "very bad");
			return VERY_BAD;
		}
		// bad
		else if (progress >= -4 && progress < 0) {
			Log.d("[HABIT", "bad");
			return BAD;
		}
		// normal
		else if (progress >= 0 && progress < 4) {
			Log.d("[HABIT", "normal");
			return NORMAL;
		}
		// good
		else if (progress >= 4 && progress < 8) {
			Log.d("[HABIT", "good");
			return GOOD;
		}
		// /very good
		else {
			Log.d("[HABIT", "very good");
			return VERY_GOOD;
		}
	}

	/**
	 * sets the Characteristic of
	 * 
	 * @param character
	 */
	public void setCharacteristic(String character) {
		this.characteristic = character;
	}

	/**
	 * gets the Characteristic
	 * 
	 * @return characteristic
	 */
	public String getCharacteristic() {
		if (this.characteristic == null)
			this.characteristic = "+-";

		return this.characteristic;
	}

	/**
	 * sets the difficulty of the habit
	 * 
	 * @param difficult
	 */
	public void setDifficulty(int difficult) {
		if (difficult < 0 || difficult > 2) {
			Log.e("[Habit]", "invalid difficulty");
			return;
		}
		difficulty = difficult;
	}

	/**
	 * gets the difficulty of the habit
	 * 
	 * @return difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * equals function
	 */
	public boolean equals(Object o) {
		Habit habit = (Habit) o;
		return (this.primary_key == habit.getKey()
				&& this.my_habit.equals(habit.getHabit())
				&& this.extra.equals(habit.getExtra())
				&& this.progress == habit.getProgress()
				&& this.getCharacteristic().equals(habit.getCharacteristic()) && this
				.getDifficulty() == habit.getDifficulty());
	}
}
