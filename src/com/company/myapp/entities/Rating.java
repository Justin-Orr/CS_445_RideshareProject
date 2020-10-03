package com.company.myapp.entities;

import com.company.myapp.utilities.TimeStamp;
import com.company.myapp.utilities.UniqueIdGenerator;

public class Rating {
	
	private int sid, rid, senderID, rating;
	private String date, comment;
	
	public Rating() {
		//Default Constructor
	}
	
	public Rating(int rid, int senderID, int rating, String comment) {
		this.setSid(UniqueIdGenerator.getUniqueID());
		this.rid = rid;
		this.senderID = senderID;
		this.rating = rating;
		this.date = TimeStamp.stampDate();
		this.comment = comment;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
