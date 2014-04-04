package com.cs429.todorpg.revised.utils;

public class ToDoItem {
	private String name;
	private int reward;

	public ToDoItem(String name, int reward) {
		setName(name);
		setReward(reward);

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

	public boolean equals(Object o) {
		ToDoItem other = (ToDoItem) o;
		return this.name.equals(other.getName()) && this.reward == other.reward;
	}
}
