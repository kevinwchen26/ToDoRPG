package com.cs429.todorpg.revised.model;

import android.util.Log;

public class ToDo extends Quest {
	private boolean finished;
	private String my_todo;
	private String extra;
	private int difficulty;	//0 - easy / 1 - medium / 2 - hard
	private int primary_key;

	public ToDo(String my_todo) {
		this.setToDo(my_todo);
		finished = false;
		difficulty = 0;
	}

	public ToDo(String my_todo, String extra, int primary_key) {
		this.setToDo(my_todo);
		this.setExtra(extra);
		this.setKey(primary_key);
		finished = false;
		difficulty = 0;	//default set - easy
	}

	public String getToDo() {
		return my_todo;
	}

	public void setToDo(String my_todo) {
		this.my_todo = my_todo;
	}

	public String getExtra() {
		if(extra == null)
			return new String();
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
	
	public void setFinish(){
		finished = true;
	}
	public boolean getStatus(){
		return finished;
	}
	
	public void setDifficulty(int rate){
		if(rate < 0 || rate > 2){//error...
			Log.e("[ToDo]", "difficuty out of range...");
			return;
		}
		this.difficulty = rate;
	}
	
	public int getDifficulty(){
		return difficulty;
	}

	public boolean equals(Object o) {
		ToDo todo = (ToDo) o;
		return (this.primary_key == todo.getKey()
				&& this.my_todo.equals(todo.getToDo()) && this.extra
					.equals(todo.getExtra()) && this.finished == todo.getStatus());
	}
}

	/*
	 * private int icon; private String description; private ArrayList<String>
	 * milestone;
	 * 
	 * public ToDo(){ super(); this.setType(TODO); icon = -1; }
	 * 
	 * public ToDo(String title){ super(title); this.setType(TODO); icon = -1; }
	 * 
	 * public void WriteDescription(String descrip){ if(descrip == null) return;
	 * description = new String(descrip); }
	 * 
	 * public String getDescription(){ return description; }
	 * 
	 * public void setImage(int ResourceId){ icon = ResourceId; }
	 * 
	 * public int getImageResource(){ return icon; }
	 * 
	 * public void WriteMileStone(String milestone){ if(this.milestone == null){
	 * this.milestone = new ArrayList<String>(); }
	 * this.milestone.add(milestone); }
	 * 
	 * public void WriteMileStone(ArrayList<String> milelist){ if(milelist ==
	 * null) return; if(milestone == null) milestone = new
	 * ArrayList<String>(milelist); else milestone = milelist; }
	 * 
	 * public ArrayList<String> getMileStone(){ return milestone; }
	 */
