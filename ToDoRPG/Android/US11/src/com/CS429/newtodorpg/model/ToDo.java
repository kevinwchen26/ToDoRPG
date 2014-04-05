package com.CS429.newtodorpg.model;


public class ToDo extends Quest{
	private String my_todo;
	public ToDo(String my_todo) {
		this.my_todo = my_todo;
	}
	
	public String getToDo() {
		return my_todo;
	}
	public void setToDo(String my_todo) {
		this.my_todo = my_todo;
	}
	

/*	private int icon;
	private String description;
	private ArrayList<String> milestone;
	
	public ToDo(){
		super();
		this.setType(TODO);
		icon = -1;
	}
	
	public ToDo(String title){
		super(title);
		this.setType(TODO);
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
	
	public void WriteMileStone(String milestone){
		if(this.milestone == null){
			this.milestone = new ArrayList<String>();
		}
		this.milestone.add(milestone);
	}
	
	public void WriteMileStone(ArrayList<String> milelist){
		if(milelist == null)
			return;
		if(milestone == null)
			milestone = new ArrayList<String>(milelist);
		else
			milestone = milelist;
	}
 
	public ArrayList<String> getMileStone(){
		return milestone;
	}*/
}
