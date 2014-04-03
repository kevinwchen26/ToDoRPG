package com.cs429.todorpg.revised.utils;

public class Dailies extends ToDoItem {
	private String name; // name of the daily task
	private int reward; // reward given for completion

	public Dailies(String name, int reward) {
		this.name = name;
		this.reward = reward;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

}
