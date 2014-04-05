package com.cs429.todorpg.revised.model;


public class Habit {
	private String my_habit;
	public Habit(String my_habit) {
		this.my_habit = my_habit;
	}
	
	public String getHabit() {
		return my_habit;
	}
	public void setHabit(String my_habit) {
		this.my_habit = my_habit;
	}
	
}
