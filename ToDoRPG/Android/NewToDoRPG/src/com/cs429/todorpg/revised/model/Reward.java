package com.cs429.todorpg.revised.model;

/**
 * Reward Class
 * 
 * @author Leon Chen
 * 
 */
public class Reward {
	private String info;
	private String extra;
	private int cost;
	private int primary_key;

	/**
	 * Constructor
	 * 
	 * @param reward
	 * @param cost
	 */
	public Reward(String reward, int cost) {
		this.setInfo(reward);
		this.setCost(cost);
		this.setExtra("");
	}

	/**
	 * Secondary Constructor
	 * 
	 * @param key
	 * @param reward
	 * @param extra
	 * @param cost
	 */
	public Reward(int key, String reward, String extra, int cost) {
		this.setPrimary_key(key);
		this.setInfo(reward);
		this.setExtra(extra);
		this.setCost(cost);
	}

	/**
	 * gets the info of the reward
	 * 
	 * @return info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * sets the info text
	 * 
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * gets the cost of reward
	 * 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * sets the cost of reward
	 * 
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * gets the extra text of reward
	 * 
	 * @return extra text
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * sets the extra text
	 * 
	 * @param extra
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}

	/**
	 * gets the primary key
	 * 
	 * @return primary_key
	 */
	public int getPrimary_key() {
		return primary_key;
	}

	/**
	 * sets the primary key
	 * 
	 * @param primary_key
	 */
	public void setPrimary_key(int primary_key) {
		this.primary_key = primary_key;
	}

	/**
	 * equals function
	 */
	public boolean equals(Object o) {
		Reward reward2 = (Reward) o;
		return (this.primary_key == reward2.getPrimary_key()
				&& this.info.equals(reward2.getInfo())
				&& this.extra.equals(reward2.getExtra()) && this.cost == reward2
				.getCost());
	}
}
