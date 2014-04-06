package com.cs429.todorpg.revised.model;

public class Daily extends Quest{
	private String my_daily;
	public Daily(String my_daily) {
		this.my_daily = my_daily;
	}
	
	public String getDaily() {
		return my_daily;
	}
	public void setDaily(String my_daily) {
		this.my_daily = my_daily;
	}
	/*private String description;
	private int icon;
	
	public Daily(){
		super();
		this.setType(DAILY);
		icon = -1;
	}
	
	public Daily(String title){
		super(title);
		this.setType(DAILY);
		icon = -1;
	}
	
	public void WriteDescription(String descrip){
		if(descrip == null)
			return;
		description = new String(descrip);
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setImage(int ResourceId){
		icon = ResourceId;
	}
	
	public int getImageResource(){
		return icon;
	}
	*/
}
