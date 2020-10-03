package com.company.myapp.interfaces;

import org.json.JSONObject;

import com.company.myapp.entities.DateTime;
import com.company.myapp.entities.Location;
import com.company.myapp.entities.Ride;
import com.company.myapp.entities.Vehicle;

public interface RideRepositoryInterface {
	public int createRide(int driverID, int maxNumberOfPassengers, double ammountPerPerson, Location location, DateTime dateTime, Vehicle vehicle, String conditions);
	public Ride getRide(int rid);
	public String viewAllRides();
	//public String searchAccounts(String key);
	public void updateRide(Ride r, JSONObject obj);
	public void deleteRide(int rid);
}
