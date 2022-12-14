package com.avital.coupons.beans;

import java.sql.Date;

import com.avital.coupons.enums.CouponCategory;

public class Coupon {

	private long id;
	private String name;
	private float price;
	private String description;
	private Date startDate;
	private Date endDate;
	private CouponCategory couponCategory;
	private int amount;
	private String image;
	private long companyId;

	public Coupon() {

	}

	// for tests
	public Coupon(String name, float price, String description, Date startDate, Date endDate,
			CouponCategory couponCategory, int amount, String image, long companyId) {

		this.name = name;
		this.price = price;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.couponCategory = couponCategory;
		this.amount = amount;
		this.image = image;
		this.companyId = companyId;
	}

	// for DB extractions
	public Coupon(long id, String name, float price, String description, Date startDate, Date endDate,
			CouponCategory couponCategory, int amount, String image, long companyId) {
		this(name, price, description, startDate, endDate, couponCategory, amount, image, companyId);
		this.id = id;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CouponCategory getCouponCategory() {
		return couponCategory;
	}

	public void setCouponCategory(CouponCategory couponCategory) {
		this.couponCategory = couponCategory;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "\nCoupons [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", couponCategory=" + couponCategory
				+ ", amount=" + amount + ", image=" + image + ", companyId=" + companyId + "]";
	}
}
