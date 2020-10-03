package com.company.myapp.interfaces;

import com.company.myapp.entities.Rating;

public interface RatingRepositoryInterface {
	public int rateAccount(int rid, int senderID, int rating, String comment);
	public Rating getAccount(int sid);
}
