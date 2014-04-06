package com.cs429.todorpg.revised.utils;

public class Item {
	private String name; // name of item
	private String stat; // stat effected by item
	private int effect; // effect of item on stat
	private String pic; // name of picture file for item

	public Item(String name, String stat, int effect, String pic) {
		this.setName(name);
		this.setStat(stat);
		this.setEffect(effect);
		this.setPic(pic);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEffect() {
		return effect;
	}

	public void setEffect(int effect) {
		this.effect = effect;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public boolean equals(Object o) {
		Item other = (Item) o;
		return this.name.equals(other.getName())
				&& this.stat.equals(other.getStat())
				&& this.getPic().equals(other.getPic())
				&& this.effect == other.getEffect();
	}

}
