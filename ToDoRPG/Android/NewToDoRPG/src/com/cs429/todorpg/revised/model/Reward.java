package com.cs429.todorpg.revised.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.cs429.todorpg.revised.utils.Constants;

public class Reward {
	private String info;
	private String extra;
	private int cost;
	
	public Reward(String info, String extra, int cost) {
		this.setInfo(info);
		this.setExtra(extra);
		this.setCost(cost);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public boolean equals(Object o) {
		Reward other = (Reward) o;
		return (this.info.equals(other.getInfo()) && this.extra.equals(other.getExtra())
				&& this.cost == other.getCost());
	}

}
