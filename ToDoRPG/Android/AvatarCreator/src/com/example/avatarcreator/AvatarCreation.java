package com.example.avatarcreator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 
 * @author paulkim6, kwchen3
 * 
 * Class that holds relevant information of Avatar
 * Based some segments of code off of this URL: 
 * http://www.chansek.com/2012/06/merge-multiple-images-into-one-image-in.html
 *
 */
public class AvatarCreation extends Activity implements OnClickListener {
	
	ArrayList<Bitmap> smallImages;
	Avatar avatar;
	
	// Definitions for avatar size
	public final int AVATAR_WIDTH = 300;
	public final int AVATAR_HEIGHT = 400;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_creation);
        
        //ImageView image = new ImageView(getApplicationContext());
        //image.setImageResource(R.drawable.katewinslet);
        //int chunkNumbers = 25;
        //smallImages = splitImage(image, chunkNumbers);
        
        /*
         * This code is to show all the smaller image chunks into a grid structure
         */
        
        //GridView grid = (GridView) findViewById(R.id.gridview);
		//grid.setAdapter(new ImageAdapter(this, smallImages));
		//grid.setNumColumns(5);
		
		/*
		 * This gets the merge button and registers an click listener on it. 
		 */
		
        // Assign image resources
        Bitmap helmImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.pink_hat);
        Bitmap faceImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.face);
        avatar = new Avatar(helmImage, faceImage);
        
		Button mergeButton = (Button) findViewById(R.id.merge_button);
		mergeButton.setOnClickListener(this);
    }
    
    /**
     * Splits the source image and show them all into a grid in a new activity
     * 
     * @param image The source image to split
     * @param chunkNumbers The target number of small image chunks to be formed from the source image
     * @return ArrayList<Bitmap> The resulted smaller image chunks in a List of Bitmaps
     */

	private ArrayList<Bitmap> splitImage(ImageView image, int chunkNumbers) {
		
		//For the number of rows and columns of the grid to be displayed
		int rows, cols;
		rows = cols = 5;
		
		//For height and width of the small image chunks 
		int chunkHeight, chunkWidth;
		
		//To store all the small image chunks in bitmap format in this list 
		ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);
		
		//Getting the scaled bitmap of the source image
		BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
		
		rows = cols = (int) Math.sqrt(chunkNumbers);
		chunkHeight = bitmap.getHeight()/rows;
		chunkWidth = bitmap.getWidth()/cols;
		
		//xCoord and yCoord are the pixel positions of the image chunks
		int yCoord = 0;
		for(int x=0; x<rows; x++){
			int xCoord = 0;
			for(int y=0; y<cols; y++){
				chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
				xCoord += chunkWidth;
			}
			yCoord += chunkHeight;
		}
		return chunkedImages;
	}

	/*
	 * This method actually implements the code for merging the small images
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	
	@Override
	public void onClick(View view) {
		//Get the width and height of the smaller chunks
		//int chunkWidth = smallImages.get(0).getWidth();
		//int chunkHeight = smallImages.get(0).getHeight();
		
		//create a bitmap of a size which can hold the complete image after merging
		Bitmap bitmap = Bitmap.createBitmap(AVATAR_WIDTH, AVATAR_HEIGHT, Bitmap.Config.ARGB_4444);
		
		
		
		//create a canvas for drawing all those small images
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(avatar.getFaceImage(), 50,50, null);
		canvas.drawBitmap(avatar.getHelmImage(), 30,30, null);
		
		/*
		int count = 0;
		for(int rows = 0; rows < 5; rows++){
			for(int cols = 0; cols < 5; cols++){
				canvas.drawBitmap(smallImages.get(count), chunkWidth * cols, chunkHeight * rows, null);
				count++;
			}
		}
		
		*/
		
		
		
		// The result avatar is shown
		Intent intent = new Intent(AvatarCreation.this, ShowMergedResult.class);
		intent.putExtra("merged_image", bitmap);
		startActivity(intent);
	}
}