package com.cs429.todorpg.revised.model;

import com.cs429.todorpg.revised.R;

public class Daily extends Quest {
	/*status*/
	private int FINISHED = R.color.finished;
	private int UNFINISHED = R.color.unfinished;
	private boolean finished;
	
	private String my_daily;
	private String extra;
	private int primary_key;

	public Daily(String my_daily) {
		this.setDaily(my_daily);
		// this.setExtra(extra); // TODO Need to be implemented later
	}

	public Daily(String my_daily, String extra, int primary_key) {
		this.setDaily(my_daily);
		this.setExtra(extra);
		this.setKey(primary_key);
		finished = false;
	}

	public String getDaily() {
		return my_daily;
	}

	public void setDaily(String my_daily) {
		this.my_daily = my_daily;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public int getKey() {
		return primary_key;
	}

	public void setKey(int primary_key) {
		this.primary_key = primary_key;
	}

	public boolean equals(Object o) {
		Daily daily = (Daily) o;
		return (this.primary_key == daily.getKey()
				&& this.my_daily.equals(daily.getDaily()) && this.extra
					.equals(daily.getExtra()) && this.getBooleanStatus() == daily.getBooleanStatus());
	}
	
	public void toggleFinish(){
		finished = !finished;
	}
	
	public boolean getBooleanStatus(){
		return finished;
	}
	
	public int getStatus(){
		if(finished)
			return FINISHED;
		else
			return UNFINISHED;
	}
}
/*
 * private String description; private int icon;
 * 
 * public Daily(){ super(); this.setType(DAILY); icon = -1; }
 * 
 * public Daily(String title){ super(title); this.setType(DAILY); icon = -1; }
 * 
 * public void WriteDescription(String descrip){ if(descrip == null) return;
 * description = new String(descrip); }
 * 
 * public String getDescription(){ return description; }
 * 
 * public void setImage(int ResourceId){ icon = ResourceId; }
 * 
 * public int getImageResource(){ return icon; }
 */

