package com.cs429.todorpg.revised.model;

import com.cs429.todorpg.revised.utils.Vice;

public class Reward {
	private String info;
	private String extra;
	private int cost;
	private int primary_key;
	
	public Reward(String reward, int cost) {
		this.setInfo(reward);
		this.setCost(cost);
		this.setExtra("");
	}
	
	public Reward(int key, String reward, String extra, int cost){
		this.setPrimary_key(key);
		this.setInfo(reward);
		this.setExtra(extra);
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

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public int getPrimary_key() {
		return primary_key;
	}

	public void setPrimary_key(int primary_key) {
		this.primary_key = primary_key;
	}
	
	public boolean equals(Object o) {
		Reward reward2 = (Reward) o;
		return (this.primary_key == reward2.getPrimary_key() && this.info.equals(reward2.getInfo())
				&& this.extra.equals(reward2.getExtra()) && this.cost == reward2.getCost());
	}
}
