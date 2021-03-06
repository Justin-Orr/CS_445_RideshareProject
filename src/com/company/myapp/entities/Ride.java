package com.company.myapp.entities;

import org.json.JSONObject;

import com.company.myapp.utilities.UniqueIdGenerator;

public class Ride {
	
	private int rid, driverID, maxNumberOfPassengers;
	private double ammountPerPerson;
	private String conditions;
	private Location location;
	private DateTime dateTime; //The nomenclature is for JSON purposes
	private Vehicle vehicle;
	
	public Ride() {
		//Default Constructor
	}
	
	public Ride(int driverID, int maxNumberOfPassengers, double ammountPerPerson, Location location, DateTime dateTime, Vehicle vehicle, String conditions) {
		this.rid = UniqueIdGenerator.getUniqueID();
		this.driverID = driverID;
		this.maxNumberOfPassengers = maxNumberOfPassengers;
		this.ammountPerPerson = ammountPerPerson;
		this.location = location;
		this.dateTime = dateTime;
		this.vehicle = vehicle;
	}
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("rid", rid);
		obj.put("location_info", location.toJson());
		obj.put("date_time", dateTime.toJson());
		return obj;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public int getMaxNumberOfPassengers() {
		return maxNumberOfPassengers;
	}

	public void setMaxNumberOfPassengers(int maxNumberOfPassengers) {
		this.maxNumberOfPassengers = maxNumberOfPassengers;
	}

	public double getAmmountPerPerson() {
		return ammountPerPerson;
	}

	public void setAmmountPerPerson(double ammountPerPerson) {
		this.ammountPerPerson = ammountPerPerson;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}
