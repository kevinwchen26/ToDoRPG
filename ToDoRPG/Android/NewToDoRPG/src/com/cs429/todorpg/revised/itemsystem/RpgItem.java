package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

import com.cs429.todorpg.revised.model.Daily;

/**
 * 
 * @author paulkim6, jcheng26
 *
 *
 * Every item must have a name string and 
 * an associated resource(drawable) id for imageviews
 */

public abstract class RpgItem implements Serializable{

	private static final long serialVersionUID = 2257009856705996895L;
	private String name;
	private int resId;

	/**
	 * Constructor for RPG item
	 * @param name
	 * @param resId
	 */
	public RpgItem(String name, int resId) {
		this.name = name;
		this.resId = resId;
	}

	/**
	 * various getters and setters
	 * @return
	 */
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
