package com.CS429.todorpg;

public class Background {
	private int bgX, bgY, speedX;
	
	public Background(int x, int y){
		setBgX(x);
		setBgY(y);
		setSpeedX(0);
	}
	
	public void update() {
		// TODO Function that updates the background
		// Static image for this iteration
	}
	

	public int getBgY() {
		return bgY;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getBgX() {
		return bgX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}
}
