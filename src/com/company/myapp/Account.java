package com.company.myapp;

import org.json.JSONObject;

public class Account {
	
	private int aid, numberOfRatings;
	private double averageRating;
	private boolean isActive;
	private String firstName, lastName, phoneNumber, picture, dateCreated;
	
	public Account() {
		//Default constructor
	}
	
	public Account(String first_name, String last_name, String phone_number, String picture) {
		this.aid = UniqueIdGenerator.getUniqueID();
		this.numberOfRatings = 0;
		this.averageRating = 0;
		this.isActive = false;
		this.firstName = first_name; 
		this.lastName = last_name;
		this.phoneNumber = phone_number;
		this.picture = picture;
		this.dateCreated = TimeStamp.stamp();
	}
	
	public static Account jsonToAccount(JSONObject json) {
		Account a = new Account();
		a.setAid(json.getInt("aid"));
		a.setNumberOfRatings(json.getInt("numberOfRatings"));
		a.setAverageRating(json.getDouble("averageRating"));
		a.setActive(json.getBoolean("active"));
		a.setFirstName(json.getString("firstName"));
		a.setLastName(json.getString("lastName"));
		a.setPhoneNumber(json.getString("phoneNumber"));
		a.setPicture(json.getString("picture"));
		a.setDateCreated(json.getString("dateCreated"));
		return a;
	}
	
	public JSONObject toPrettyJson() {
		JSONObject obj = new JSONObject();
		obj.put("aid", aid);
		obj.put("name", firstName + " " + lastName);
		obj.put("date_created", dateCreated);
		obj.put("is_active", isActive);
		
		return obj;
	}
	
	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(int numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

}
