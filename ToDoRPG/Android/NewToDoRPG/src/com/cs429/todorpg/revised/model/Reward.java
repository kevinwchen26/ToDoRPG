package com.cs429.todorpg.revised.model;

public class Reward {
	private String info;
	private int cost;
	
	public Reward(String reward, int cost) {
		this.setInfo(reward);
		this.setCost(cost);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
