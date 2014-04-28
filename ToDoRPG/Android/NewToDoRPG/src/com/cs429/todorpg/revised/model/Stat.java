package com.cs429.todorpg.revised.model;

public class Stat {

	private String name;
	private int count;
	private int id;

	public Stat(int id, String name, int count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}

	public Stat(String name, int count) {
		this.name = name;
		this.count = count;
	}

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
