package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.itemsystem.Equipment;

public class EquipmentListAdapter extends ArrayAdapter<Equipment> {
	private final Context context;
	private final ArrayList<Equipment> equipment;
	private int layout;

	public EquipmentListAdapter(Context context, int layoutResourceId, ArrayList<Equipment> equipment) {
		super(context, layoutResourceId, equipment);
		this.context = context;
		this.equipment = equipment;
		this.layout = layoutResourceId;
	}
	
	/*
	@Override
	public void add(CharacterItem object) {
		
	};
	*/
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Equipment equippedItem = equipment.get(position);
		View rowView = inflater.inflate(layout, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.equipped_item_name);
		name.setText(equippedItem.getName());
		
		TextView description = (TextView) rowView.findViewById(R.id.equipment_description);
		
		String equipment_descr = "Lvl " + equippedItem.getLevel();
		description.setText(equipment_descr);
		
		ImageView icon = (ImageView)rowView.findViewById(R.id.imgIcon);
		icon.setImageBitmap(equippedItem.getBitmap());
		return rowView;
	}
}
