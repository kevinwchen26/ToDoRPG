package com.cs429.todorpg.revised.model;

public class Vice extends Quest {

	private int icon;

	public Vice() {
		super();
		this.setType(VICE);
		icon = -1;
	}

	public Vice(String title) {
		super(title);
		this.setType(VICE);
		icon = -1;
	}

	public void setImage(int ResourceId) {
		icon = ResourceId;
	}

	public int getImageResource() {
		return icon;
	}
}
