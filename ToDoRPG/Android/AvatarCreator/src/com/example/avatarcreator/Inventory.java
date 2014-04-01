package com.example.avatarcreator;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<CharacterItem> itemList;
	
	// Item slots
	private Helmet helmet;
	
	public Helmet getHelmet() {
		return helmet;
	}

	public void setHelmet(Helmet helmet) {
		this.helmet = helmet;
	}

	public Inventory () {
		itemList = new ArrayList<CharacterItem>();
		helmet = null;
	}
	
	public int size() {
		return itemList.size();
	}
	
	public void add(CharacterItem i) {
		itemList.add(i);
	}
	
	public void remove(int index) {
		itemList.remove(index);
	}
	
	public boolean equipHelmet(int index) {
		if (itemList.get(index) instanceof Helmet) {
			if (helmet != null) {
				// If character already has helmet equipped, swap
				Helmet tempHelmet = helmet;
				helmet = (Helmet)itemList.get(index);
				itemList.remove(index);
				itemList.add(tempHelmet);
			}
			else {
				helmet = (Helmet) itemList.get(index);
				itemList.remove(index);
			}
			
			return true;
		}
		else
			return false;
	}
	
}
