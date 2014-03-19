package com.CS429.todorpg;

import java.util.ArrayList;
import com.CS429.todorpg.Class.Character;
public class Quest {

	private String title;
	private String location; 
	private String leader;
	private ArrayList<Character> party = new ArrayList<Character>();
	private String description;
	private int hours; 
	private int interval;
	private ArrayList<String> milestones;
	private String status;
	private String due_date;
	private String member;
	private String milestone;
	private String progress_status;
	private String done_status;
	private int quest_id;

	public Quest(String title, String location, Character leader, String description, int hours, ArrayList<String> milestones){
		this.setTitle(title);
		this.setLocation(location);
		this.party.add(leader);
		this.setDescription(description);
		this.setHours(hours);
		this.milestones = milestones;
		this.setInterval(hours/(this.milestones.size()));

	}
	
	/*This constructor is for Search Bar*/
	public Quest(int quest_id, String title, String leader, String due_date, String member, 
			String status, String location, String milestone, String description, String progress_status, String done_status){
		this.quest_id = quest_id;
		this.title = title;
		this.leader = leader;
		this.due_date = due_date;
		this.member = member;
		this.status = status;
		this.location = location;
		this.milestone = milestone;
		this.description = description;
		this.progress_status = progress_status;
		this.done_status = done_status;

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
	public int getQuestId() {
		return quest_id;
	}

	//Getters and Setters
	public String getProgressStstus() {
		return progress_status;
	}
	public String getDoneStatus() {
		return done_status;
	}
	public String getLeader() {
		return leader;
	}
	public String getStatus() {
		return status;
	}
	public String getTitle() {
		return title;
	}
	public String getDueDate() {
		return due_date;
	}
	public String getMember() {
		return member;
	}
	public String getMilestone() {
		return milestone;
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