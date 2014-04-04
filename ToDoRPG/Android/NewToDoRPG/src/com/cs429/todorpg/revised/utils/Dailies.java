package com.cs429.todorpg.revised.utils;

public class Dailies extends ToDoItem {

	public Dailies(String name, int reward) {
		super(name, reward);
	}

	public boolean equals(Object o) {
		Dailies other = (Dailies) o;
		return this.getName().equals(other.getName())
				&& this.getReward() == other.getReward();
	}
}
