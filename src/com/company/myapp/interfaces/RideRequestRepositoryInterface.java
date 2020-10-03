package com.company.myapp.interfaces;

import org.json.JSONObject;

import com.company.myapp.entities.RideRequest;

public interface RideRequestRepositoryInterface {
	public int createRideRequest(int rid, int aid, int passengers, boolean ride_confirmed, boolean pickup_confirmed);
	public RideRequest getRequest(int rid);
	public void confirmDenyRequest(RideRequest rr, JSONObject obj);
}
