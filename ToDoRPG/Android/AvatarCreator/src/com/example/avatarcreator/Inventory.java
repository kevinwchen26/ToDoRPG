package com.example.avatarcreator;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<CharacterItem> itemList;
	
	public ArrayList<CharacterItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<CharacterItem> itemList) {
		this.itemList = itemList;
	}

	// Item slots
	private Helmet helmet;
	
	public Helmet getHelmet() {
		return helmet;
	}

	public void initHelmet(Helmet helmet) {
		this.helmet = helmet;
	}
	
	public void unequipHelmet() {
		if (this.helmet != null) {
			Helmet temp = this.helmet;
			this.helmet = null;
			itemList.add(temp);
		}
		
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
	
	public CharacterItem get(int i) {
		return itemList.get(i);
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
	
	public ArrayList<CharacterItem> getEquipment() {
		ArrayList<CharacterItem> retList = new ArrayList<CharacterItem>();
		if (helmet != null)
			retList.add(helmet);
		return retList;
	}
}
