package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs429.todorpg.revised.GameApplication;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.itemsystem.EquipCost;

/**
 * An Adapter that contains the list of all items in the Shop.
 * 
 * @author Leon Chen
 * 
 */
public class ShopListAdapter extends ArrayAdapter<EquipCost> {
	private final Context context;
	private final ArrayList<EquipCost> shopItems;
	private int layout;

	/**
	 * Constructor, sets the arraylist to shopitems.
	 * 
	 * @param context
	 * @param layoutResourceId
	 * @param shopItems
	 */
	public ShopListAdapter(Context context, int layoutResourceId,
			ArrayList<EquipCost> shopItems) {
		super(context, layoutResourceId, shopItems);
		this.context = context;
		this.shopItems = shopItems;
		this.layout = layoutResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		EquipCost shopItem = shopItems.get(position);
		View rowView = inflater.inflate(layout, parent, false);

		// Set Name
		TextView name = (TextView) rowView.findViewById(R.id.shop_item_name);
		name.setText(shopItem.getEquipment().getName());

		// Set Image Icon
		ImageView icon = (ImageView) rowView.findViewById(R.id.shop_img_icon);
		icon.setImageBitmap(BitmapFactory.decodeResource(GameApplication
				.getAppContext().getResources(), shopItem.getEquipment()
				.getResId()));
		return rowView;
	}
}
