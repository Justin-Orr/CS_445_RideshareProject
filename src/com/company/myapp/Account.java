package com.company.myapp;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.json.JSONObject;

public class Account {
	
	private int aid, numberOfRatings;
	private double averageRating;
	private boolean isActive;
	private String accountType, firstName, lastName, phoneNumber, picture, dateCreated;
	private Vehicle vehicle;
	
	public Account() {
		//Default constructor
	}
	
	public Account(String first_name, String last_name, String phone_number, String picture) {
		this.aid = UniqueIdGenerator.getUniqueID();
		this.numberOfRatings = 0;
		this.averageRating = 0;
		this.isActive = false;
		this.accountType = "unset";
		this.firstName = first_name; 
		this.lastName = last_name;
		this.phoneNumber = phone_number;
		this.picture = picture;
		this.vehicle = new Vehicle();
		this.dateCreated = TimeStamp.stamp();
		//this.dateCreated = "DD-MMM-YYYY, HH:MM";
	}
	
	public static Account jsonToAccount(JSONObject json) {
		Account a = new Account();
		a.setAid(json.getInt("aid"));
		a.setNumberOfRatings(json.getInt("numberOfRatings"));
		a.setAverageRating(json.getDouble("averageRating"));
		a.setActive(json.getBoolean("active"));
		a.setAccountType(json.getString("accountType"));
		a.setFirstName(json.getString("firstName"));
		a.setLastName(json.getString("lastName"));
		a.setPhoneNumber(json.getString("phoneNumber"));
		a.setPicture(json.getString("picture"));
		a.setDateCreated(json.getString("dateCreated"));
		a.setVehicle(Vehicle.jsonToVehicle(json.getJSONObject("vehicle")));
		return a;
	}
	
	public JSONObject toPrettyJson() {
		JSONObject obj = new JSONObject();
		obj.put("aid", aid);
		obj.put("name", firstName + " " + lastName);
		//obj.add("date_created", date_created);
		obj.put("is_active", isActive);
		
		return obj;
	}
	
	public int activateAccount(String type) {
		if (type.compareTo("driver") == 0)
			return activateDriver();
		else if (type.compareTo("rider") == 0)
			return activateRider();
		else
			return -1;
	}
	
	public int activateDriver() {
		//Place method call here to ping user to grab vehicle data
		isActive = true;
		vehicle = new Vehicle();
		return 0;
	}
	
	public int activateRider() {
		isActive = true;
		return 0;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}
