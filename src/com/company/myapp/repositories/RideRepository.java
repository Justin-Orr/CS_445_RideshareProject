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
	
	public String searchRides(String from, String to, String date) {
		
		//Are any of the attributes empty?
		boolean emptyFrom = from.compareToIgnoreCase("") == 0;
		boolean emptyTo = to.compareToIgnoreCase("") == 0;
		boolean emptyDate = date.compareToIgnoreCase("") == 0;
		
		if(emptyFrom && emptyTo && emptyDate) {
			return viewAllRides();
		}
		else if(emptyFrom && !emptyTo && !emptyDate) {
			return searchEmpty_From(to, date);
		}
		else if(!emptyFrom && emptyTo && !emptyDate) {
			return searchEmpty_To(from, date);
		}
		else if(!emptyFrom && !emptyTo && emptyDate) {
			return searchEmpty_Date(from, to);		
		}
		else if(emptyFrom && emptyTo && !emptyDate) {
			return searchEmpty_From_To(date);
		}
		else if(emptyFrom && !emptyTo && emptyDate) {
			return searchEmpty_From_Date(to);
		}
		else if(!emptyFrom && emptyTo && emptyDate) {
			return searchEmpty_To_Date(from);
		}
		else {
			return searchAllAttributes(from, to, date);
		}
	}
	
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
	
	private String searchAllAttributes(String from, String to, String date) {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		Location loc;
		DateTime dt;
		for(Ride r : rides) {
			loc = r.getLocation();
			dt = r.getDateTime();
			if(from.compareToIgnoreCase(loc.getFromCity()) == 0 && to.compareToIgnoreCase(loc.getToCity()) == 0 && date.compareToIgnoreCase(dt.getDate()) == 0) {
				String out = r.toJson().toString();
				obj.put(new JSONObject(out));
			}
		}
		return obj.toString();
	}
	
	private String searchEmpty_From(String to, String date) {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		Location loc;
		DateTime dt;
		for(Ride r : rides) {
			loc = r.getLocation();
			dt = r.getDateTime();
			if(to.compareToIgnoreCase(loc.getToCity()) == 0 && date.compareToIgnoreCase(dt.getDate()) == 0) {
				String out = r.toJson().toString();
				obj.put(new JSONObject(out));
			}
		}
		return obj.toString();
	}
	
	private String searchEmpty_To(String from, String date) {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		Location loc;
		DateTime dt;
		for(Ride r : rides) {
			loc = r.getLocation();
			dt = r.getDateTime();
			if(from.compareToIgnoreCase(loc.getFromCity()) == 0 && date.compareToIgnoreCase(dt.getDate()) == 0) {
				String out = r.toJson().toString();
				obj.put(new JSONObject(out));
			}
		}
		return obj.toString();
	}
	
	private String searchEmpty_Date(String from, String to) {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		Location loc;
		for(Ride r : rides) {
			loc = r.getLocation();
			if(from.compareToIgnoreCase(loc.getFromCity()) == 0 && to.compareToIgnoreCase(loc.getToCity()) == 0) {
				String out = r.toJson().toString();
				obj.put(new JSONObject(out));
			}
		}
		return obj.toString();
	}
	
	private String searchEmpty_From_To(String date) {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		DateTime dt;
		for(Ride r : rides) {
			dt = r.getDateTime();
			if(date.compareToIgnoreCase(dt.getDate()) == 0) {
				String out = r.toJson().toString();
				obj.put(new JSONObject(out));
			}
		}
		return obj.toString();
	}
	
	private String searchEmpty_From_Date(String to) {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		Location loc;
		for(Ride r : rides) {
			loc = r.getLocation();
			if(to.compareToIgnoreCase(loc.getToCity()) == 0) {
				String out = r.toJson().toString();
				obj.put(new JSONObject(out));
			}
		}
		return obj.toString();
	}
	
	private String searchEmpty_To_Date(String from) {
		JSONArray obj = new JSONArray();
		ArrayList<Ride> rides = new ArrayList<Ride>(repo.values());
		Location loc;
		for(Ride r : rides) {
			loc = r.getLocation();
			if(from.compareToIgnoreCase(loc.getFromCity()) == 0) {
				String out = r.toJson().toString();
				obj.put(new JSONObject(out));
			}
		}
		return obj.toString();
	}
}
