package com.cs429.todorpg.revised.model;

public class Habit {
	private String my_habit;
	private String extra;
	private int primary_key;

	public Habit(String my_habit) {
		this.setHabit(my_habit);
	}

	public Habit(String my_habit, String extra, int primary_key) {
		this.setHabit(my_habit);
		this.setExtra(extra);
		this.setKey(primary_key);
	}

	public String getHabit() {
		return my_habit;
	}

	public void setHabit(String my_habit) {
		this.my_habit = my_habit;
	}

	public String getExtra() {
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
					.equals(habit.getExtra()));
	}

}
