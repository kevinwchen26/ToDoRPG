package com.example.avatarcreator;

import android.graphics.Bitmap;

/**
 * 
 * @author paulkim6, kwchen3
 * 
 * Class that holds relevant information of Avatar
 *
 */
public class Avatar {
	private Bitmap helmImage;
	private Bitmap faceImage;
	
	public Avatar (Bitmap helmImage, Bitmap faceImage) {
		this.helmImage = helmImage;
		this.faceImage = faceImage;
	}
	
	public Bitmap getHelmImage() {
		return helmImage;
	}
	public void setHelmImage(Bitmap headImage) {
		this.helmImage = headImage;
	}
	public Bitmap getFaceImage() {
		return faceImage;
	}
	public void setFaceImage(Bitmap faceImage) {
		this.faceImage = faceImage;
	}
}
