package com.cs429.todorpg.revised.model;
/**
 * Stat Class
 * @author kchen26, hlim10
 *
 */
public class Stat {

	private String name;
	private int count;
	private int id;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param name
	 * @param count
	 */
	public Stat(int id, String name, int count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}

	/**
	 * Secondary Constructors
	 * 
	 * @param name
	 * @param count
	 */
	public Stat(String name, int count) {
		this.name = name;
		this.count = count;
	}

	/**
	 * various setters and getters for the object
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean equals(Object o) {
		Stat other = (Stat) o;
		return this.name.equals(other.name) && this.count == other.count;

	}
}
