package com.company.myapp;

import org.json.JSONObject;

public class Vehicle {
	
private String make, model, color, plateNumber, plateState;
	
	public Vehicle() {
		make = "N/A"; 
		model = "N/A";
		color = "N/A"; 
		plateNumber = "N/A";
		plateState = "N/A";
	}
	
	public Vehicle (String make, String model, String color, String plate_number, String plate_state) {
		this.make = make;
		this.model = model;
		this.color = color;
		this.plateNumber = plate_number;
		this.plateState = plate_state;
	}
	
	public static Vehicle jsonToVehicle(JSONObject json) {
		Vehicle v = new Vehicle();
		v.setColor(json.getString("color"));
		v.setMake(json.getString("make"));
		v.setModel(json.getString("model"));
		v.setPlateNumber(json.getString("plateNumber"));
		v.setPlateState(json.getString("plateState"));
		return v;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getPlateState() {
		return plateState;
	}

	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}

}
