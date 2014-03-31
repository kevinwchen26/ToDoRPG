package com.example.avatarcreator;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.widget.ImageView;

public class ShowMergedResult extends Activity {

	@Override
	public void onCreate(Bundle bundle){
		
		super.onCreate(bundle);
		setContentView(R.layout.main);
		
		Bitmap bitmap = getIntent().getParcelableExtra("merged_image");
		ImageView image = (ImageView) findViewById(R.id.merged_image);
		image.setImageBitmap(bitmap);
			
		// Scale image size to match screen (requires minimum API level 13)
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;
		
		image.getLayoutParams().height = screenWidth / 2;
		image.getLayoutParams().width = screenHeight / 2;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_merged_result, menu);
		return true;
	}

}
