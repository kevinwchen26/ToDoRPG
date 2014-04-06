package com.cs429.todorpg.revised.utils;

public class Reward {
	private String name;
	private int cost;

	public Reward(String name, int cost) {
		this.setName(name);
		this.setCost(cost);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean equals(Object o) {
		Reward other = (Reward) o;
		return this.name.equals(other.getName())
				&& this.cost == other.getCost();
	}

}
