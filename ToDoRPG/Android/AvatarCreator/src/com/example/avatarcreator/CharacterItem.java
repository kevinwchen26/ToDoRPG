package com.example.avatarcreator;

import android.graphics.Bitmap;

public abstract class CharacterItem {
	private int level;
	private String name;
	private Bitmap bitmap;
	private int resourceId;

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public CharacterItem (int level, String name, Bitmap bitmap, int resourceId) {
		this.level = level;
		this.name = name;
		this.bitmap = bitmap;
		this.resourceId = resourceId;
	}

	public int getLevel() {
		return level;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
