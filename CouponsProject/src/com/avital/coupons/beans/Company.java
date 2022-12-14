package com.avital.coupons.beans;

public class Company {

	private long id;
	private String companyName;
	private String phoneNumber;
	private String address;

	public Company() {

	}

	// for tests
	public Company(String companyName, String phoneNumber, String address) {
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	// for DB extractions
	public Company(long id, String companyName, String phoneNumber, String address) {
		this(companyName, phoneNumber, address);
		this.id = id;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", phoneNumber=" + phoneNumber + ", address="
				+ address + "]";
	}

}