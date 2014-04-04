package com.CS429.newtodorpg.model;

public class Daily extends Quest{

	private String description;
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
	
}
