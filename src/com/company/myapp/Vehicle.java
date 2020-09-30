package com.company.myapp;

public class Vehicle {
	
private String make, model, color, plate_number, plate_state;
	
	public Vehicle() {
		String make = "N/A"; 
		String model = "N/A";
		String color = "N/A"; 
		String plate_number = "N/A";
		String plate_state = "N/A";
	}
	
	public Vehicle (String make, String model, String color, String plate_number, String plate_state) {
		this.setMake(make);
		this.setModel(model);
		this.setColor(color);
		this.setPlate_number(plate_number);
		this.setPlate_state(plate_state);
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

	public String getPlate_number() {
		return plate_number;
	}

	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}

	public String getPlate_state() {
		return plate_state;
	}

	public void setPlate_state(String plate_state) {
		this.plate_state = plate_state;
	}

}
