package com.company.myapp.interfaces;

import com.company.myapp.entities.DateTime;
import com.company.myapp.entities.Location;
import com.company.myapp.entities.Ride;
import com.company.myapp.entities.Vehicle;

public interface RideRepositoryInterface {
	public int createRide(int driverID, int maxNumberOfPassengers, double ammountPerPerson, Location location, DateTime dateTime, Vehicle vehicle, String conditions);
	public Ride getRide(int rid);
	public String viewAllRides();
	public String searchAccounts(String key);
}
