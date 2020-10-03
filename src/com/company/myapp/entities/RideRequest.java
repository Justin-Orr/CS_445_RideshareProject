package com.company.myapp.entities;

import com.company.myapp.utilities.UniqueIdGenerator;

public class RideRequest {
	
	private int jid, rid, aid, passengers;
	private boolean ride_confirmed, pickup_confirmed;
	
	public RideRequest() {
		//Default Constructor
	}
	
	public RideRequest(int rid, int aid, int passengers, boolean ride_confirmed, boolean pickup_confirmed) {
		this.jid = UniqueIdGenerator.getUniqueID();
		this.aid = aid;
		this.passengers = passengers;
		this.ride_confirmed = ride_confirmed;
		this.pickup_confirmed = pickup_confirmed;
	}

	public int getJid() {
		return jid;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public boolean isRide_confirmed() {
		return ride_confirmed;
	}

	public void setRide_confirmed(boolean ride_confirmed) {
		this.ride_confirmed = ride_confirmed;
	}

	public boolean isPickup_confirmed() {
		return pickup_confirmed;
	}

	public void setPickup_confirmed(boolean pickup_confirmed) {
		this.pickup_confirmed = pickup_confirmed;
	}
	
}
