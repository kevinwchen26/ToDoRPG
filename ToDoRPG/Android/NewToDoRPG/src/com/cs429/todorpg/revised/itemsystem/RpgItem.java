package com.cs429.todorpg.revised.itemsystem;

import com.cs429.todorpg.revised.model.Daily;

/**
 * 
 * @author paulkim6, jcheng26
 *
 *
 * Every item must have a name string and 
 * an associated resource(drawable) id for imageviews
 */

public abstract class RpgItem {
	private String name;
	private int resId;
	
	public RpgItem(String name, int resId) {
		this.name = name;
		this.resId = resId;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		RpgItem rpgitem= (RpgItem) o;
		return (this.getName().equals(rpgitem.getName()) && this.getResId() == rpgitem.getResId());
	}
}
