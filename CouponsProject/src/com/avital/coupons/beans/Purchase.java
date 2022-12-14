package com.avital.coupons.beans;

import java.sql.Timestamp;

public class Purchase {

	private long id;
	private long userId;
	private long couponId;
	private int amount;
	private Timestamp timeOfPurchase;
	
	
	public Purchase() {
		
	}
	// for tests
	public Purchase(long userId, long couponId, int amount, Timestamp timeOfPurchase) {
		this.userId = userId;
		this.couponId = couponId;
		this.amount = amount;
		this.timeOfPurchase = timeOfPurchase;
		
	}
	// for DB extractions 
	public Purchase(long id, long userId, long couponId, int amount, Timestamp timeOfPurchase) {
		this(userId, couponId, amount, timeOfPurchase);
		this.id = id;
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Timestamp getTimeOfPurchase() {
		return timeOfPurchase;
	}

	public void setTimeOfPurchase(Timestamp timeOfPurchase) {
		this.timeOfPurchase = timeOfPurchase;
	}

	@Override
	public String toString() {
		return "\nPurchases [id=" + id + ", userId=" + userId + ", couponId=" + couponId + ", amount=" + amount
				+ ", timeOfPurchase=" + timeOfPurchase + "]";
	}
}
