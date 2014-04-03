package com.cs429.todorpg.revised.utils;

public class Vice {

	private String name;
	private String stat;
	private int effect;

	public Vice(String name, String stat, int effect) {
		setName(name);
		setStat(stat);
		setEffect(effect);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public int getEffect() {
		return effect;
	}

	public void setEffect(int effect) {
		this.effect = effect;
	}

}
