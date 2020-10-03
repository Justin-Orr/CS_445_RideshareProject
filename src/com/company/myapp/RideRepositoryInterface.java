package com.company.myapp;

public interface RideRepositoryInterface {
	public int createRide(int driverID, int maxNumberOfPassengers, double ammountPerPerson, Location location, DateTime dateTime, Vehicle vehicle, String conditions);
	
}
