package com.cs429.todorpg.revised.model;

import android.util.Log;

import com.cs429.todorpg.revised.R;

/**
 * Daily class
 * 
 * @author hlim10, ssong25
 * 
 */
public class Daily extends Quest {

	/* status */
	private int FINISHED = R.color.finished;
	private int UNFINISHED = R.color.unfinished;
	private boolean finished;

	private String my_daily;
	private String extra;
	private int primary_key;
	private int week_primary_key;

	private int difficulty; // 0 - easy , 1 - medium, 2 - hard
	private boolean[] repeat = new boolean[7]; // length 7, each index

	// corresponds to days in a
	// week. starts from monday.

	/**
	 * Constructor
	 * 
	 * @param my_daily
	 */
	public Daily(String my_daily) {
		this.setDaily(my_daily);
		finished = false;
		difficulty = 0; // default set easy
		for (int i = 0; i < 7; ++i)
			repeat[i] = false; // default set no regular
	}

	/**
	 * Second Constructor
	 * 
	 * @param my_daily
	 * @param extra
	 * @param primary_key
	 */
	public Daily(String my_daily, String extra, int primary_key) {
		this.setDaily(my_daily);
		this.setExtra(extra);
		this.setKey(primary_key);
		week_primary_key = -1;
		finished = false;
		difficulty = 0;
		for (int i = 0; i < 7; ++i)
			repeat[i] = false;
	}

	/**
	 * getter for Daily
	 * 
	 * @return my_daily
	 */
	public String getDaily() {
		return my_daily;
	}

	/**
	 * setter for Daily
	 * 
	 * @param my_daily
	 */
	public void setDaily(String my_daily) {
		this.my_daily = my_daily;
	}

	/**
	 * gets the Extra text
	 * 
	 * @return extra text
	 */
	public String getExtra() {
		if (extra == null)
			return new String();
		return extra;
	}

	/**
	 * sets the Extra text
	 * 
	 * @param extra
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}

	/**
	 * gets the key
	 * 
	 * @return primary_key
	 */
	public int getKey() {
		return primary_key;
	}

	/**
	 * sets the key
	 * 
	 * @param primary_key
	 */
	public void setKey(int primary_key) {
		this.primary_key = primary_key;
	}

	/**
	 * gets the week key (for database)
	 * 
	 * @return week_primary_key
	 */
	public int getWeekKey() {
		return week_primary_key;
	}

	/**
	 * sets the week key
	 * 
	 * @param week_primary_key
	 */
	public void setWeekKey(int week_primary_key) {
		this.week_primary_key = week_primary_key;
	}

	/**
	 * changes the finished status
	 */
	public void toggleFinish() {
		finished = !finished;
	}

	/**
	 * gets the finished status
	 * 
	 * @return
	 */
	public boolean getBooleanStatus() {
		return finished;
	}

	/**
	 * gets the status
	 * 
	 * @return
	 */
	public int getStatus() {
		if (finished)
			return FINISHED;
		else
			return UNFINISHED;
	}

	/**
	 * sets the difficulty
	 * 
	 * @param difficult
	 */
	public void setDifficulty(int difficult) {
		this.difficulty = difficult;
	}

	/**
	 * 
	 * @return gets the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * toggles which days you need to finish the daily
	 * 
	 * @param day
	 */
	public void toggleRegularDate(int day) {
		repeat[day] = !repeat[day];
	}

	/**
	 * returns if you need to do the daily on that day
	 * 
	 * @param day
	 * @return if you need to do the daily on that day
	 */
	public boolean getRegularDate(int day) {
		return repeat[day];
	}

	/**
	 * equals function
	 */
	public boolean equals(Object o) {
		Daily daily = (Daily) o;
		Log.d("this.getWeekKey()", "" + this.getWeekKey());
		Log.d("daily.getWeekKey()", "" + daily.getWeekKey());
		boolean correctWeekDates = (this.getRegularDate(0) == daily
				.getRegularDate(0)
				&& this.getRegularDate(1) == daily.getRegularDate(1)
				&& this.getRegularDate(2) == daily.getRegularDate(2)
				&& this.getRegularDate(3) == daily.getRegularDate(3)
				&& this.getRegularDate(4) == daily.getRegularDate(4)
				&& this.getRegularDate(5) == daily.getRegularDate(5) && this
				.getRegularDate(6) == daily.getRegularDate(6));
		return (correctWeekDates && this.primary_key == daily.getKey()
				&& this.my_daily.equals(daily.getDaily())
				&& this.extra.equals(daily.getExtra())
				&& this.getBooleanStatus() == daily.getBooleanStatus()
				&& this.getDifficulty() == daily.getDifficulty() && this
				.getWeekKey() == daily.getWeekKey());
	}

}
