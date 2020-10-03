package com.company.myapp.repositories;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.company.myapp.entities.DateTime;
import com.company.myapp.entities.Location;
import com.company.myapp.entities.Ride;
import com.company.myapp.entities.Vehicle;
import com.company.myapp.interfaces.RideRepositoryInterface;

public class RideRepository implements RideRepositoryInterface {
	
	private static Hashtable<Integer, Ride> repo = new Hashtable<Integer, Ride>();
		
	public RideRepository() {
		//Default constructor
	}
	
	public int createRide(int driverID, int maxNumberOfPassengers, double ammountPerPerson, Location location, DateTime dateTime, Vehicle vehicle, String conditions) {
		Ride r = new Ride(driverID, maxNumberOfPassengers, ammountPerPerson, location, dateTime, vehicle, conditions);
		repo.put(r.getRid(), r);
		return r.getRid();
	}
	
	public Ride getRide(int rid) {
		return repo.get(rid);
	}
	
	public String viewAllRides() {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		for(Ride r : rides) {
			String out = r.toJson().toString();
			obj.put(new JSONObject(out));
		}
		return obj.toString();
	}
	
	/*public String searchAccounts(String key) {
		JSONArray obj = new JSONArray();
		String out = "";
		ArrayList<Account> accounts = new ArrayList<Account>(repo.values());
		for(Account a : accounts) {
			if(key.compareToIgnoreCase(a.getFirstName()) == 0 || key.compareToIgnoreCase(a.getLastName()) == 0 || key.compareToIgnoreCase(a.getPhoneNumber()) == 0) {
				out = a.toPrettyJson().toString();
				obj.put(new JSONObject(out)); //Make a Json array of json objects with a summary of the accounts, not all data
			}	
		}
		return obj.toString();
	}*/
	
	public void updateRide(Ride r, JSONObject obj) {
		r.setMaxNumberOfPassengers(obj.getInt("max_passengers"));
		r.setAmmountPerPerson(obj.getDouble("amount_per_passenger"));
		r.setConditions(obj.getString("conditions"));
		
		JSONObject loc = obj.getJSONObject("location_info");
		Location location = new Location(loc.getString("from_city"), loc.getString("from_zip"), loc.getString("to_city"),loc.getString("to_zip"));
		r.setLocation(location);
		
		JSONObject dat = obj.getJSONObject("date_time");
		DateTime dateTime = new DateTime(dat.getString("date"), dat.getString("time"));
		r.setDateTime(dateTime);
		
		JSONObject veh = obj.getJSONObject("car_info");
		Vehicle vehicle = new Vehicle(veh.getString("make"), veh.getString("model"), veh.getString("color"), veh.getString("plate_state"), veh.getString("plate_serial"));
		r.setVehicle(vehicle);
		
		repo.replace(r.getDriverID(), r);
	}
	
	public void deleteRide(int rid) {
		repo.remove(rid);
	}
	
}
