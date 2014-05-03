package com.cs429.todorpg.revised.itemsystem;

import java.io.Serializable;

/**
 * 
 * @author paulkim6, jcheng26
 * 
 * 
 *         Every item must have a name string and an associated
 *         resource(drawable) id for imageviews
 */

public abstract class RpgItem implements Serializable {

	private static final long serialVersionUID = 2257009856705996895L;
	private String name;
	private int resId;

	/**
	 * Constructor for RPG item
	 * 
	 * @param name
	 * @param resId
	 */
	public RpgItem(String name, int resId) {
		this.name = name;
		this.resId = resId;
	}

	/**
	 * retrieves the ResID
	 * 
	 * @return resID
	 */
	public int getResId() {
		return resId;
	}

	/**
	 * sets the ResId
	 * 
	 * @param resId
	 */
	public void setResId(int resId) {
		this.resId = resId;
	}

	/**
	 * gets the name of the weapon
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the weapon
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * equals function
	 */
	public boolean equals(Object o) {
		RpgItem rpgitem = (RpgItem) o;
		return (this.getName().equals(rpgitem.getName()) && this.getResId() == rpgitem
				.getResId());
	}
}
