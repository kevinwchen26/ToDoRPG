package com.CS429.todorpg;

import java.util.ArrayList;

public class Quest {
	
	private String title;
	private String location; 
	private ArrayList<Character> party = new ArrayList<Character>();
	private String description;
	private int hours; 
	private int interval;
	private ArrayList<String> milestones;
	
	public Quest(String title, String location, Character leader, String description, int hours, ArrayList<String> milestones){
		this.setTitle(title);
		this.setLocation(location);
		this.party.add(leader);
		this.setDescription(description);
		this.setHours(hours);
		this.milestones = milestones;
		this.setInterval(hours/(this.milestones.size()));
		
	}
	
	public String getParty() {
		String party = "";
		for(Character curr : this.party)
			party += curr.getName() + " ";
		
		return party;
	}
	
	public String getMilestones() {
		String retval = "";
		for(String ms : this.milestones)
			retval += ms + " ";
		return retval;
	}
	
	//Getters and Setters

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
