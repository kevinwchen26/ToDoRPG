package com.cs429.todorpg.revised.model;

public class Reward {
	private String info;
	private String extra;
	private int cost;
	
	public Reward(String reward, int cost) {
		this.setInfo(reward);
		this.setCost(cost);
		this.setExtra("");
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

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
