package com.example.avatarcreator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Inventory extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
		
		// Set image
		Bitmap bitmap = Bitmap.createBitmap(AvatarCreation.AVATAR_WIDTH, AvatarCreation.AVATAR_HEIGHT, Bitmap.Config.ARGB_4444);
		bitmap.eraseColor(Color.parseColor("green"));
		ImageView charImage = (ImageView) findViewById(R.id.character_image);
		charImage.setImageBitmap(bitmap);
		
		// Scale image size to match screen (requires minimum API level 13)
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;
		
		charImage.getLayoutParams().height = screenWidth / 2;
		//charImage.getLayoutParams().width = screenHeight / 2;
		
		// Set up inventory grid view
		GridView gridview = (GridView) findViewById(R.id.inventory_grid_view);
	    gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(Inventory.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
		return true;
	}

}
