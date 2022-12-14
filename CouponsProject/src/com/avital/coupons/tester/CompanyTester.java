package com.avital.coupons.tester;

import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.beans.Coupon;
import com.avital.coupons.beans.User;
import com.avital.coupons.dao.CompaniesDao;
import com.avital.coupons.dao.CouponsDao;
import com.avital.coupons.dao.UsersDao;
import com.avital.coupons.enums.UserType;

public class CompanyTester {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 testCreateCompany();
		// testDeleteCompanies();
		// testGetCompanies();
		 //testUpdateCompanies();
		// testerGetAllCompanies();
	}

	public static void testCreateCompany() throws Exception {
		CompaniesDao companyDao = new CompaniesDao();
		Company company = new Company("Google", "ariel@gmail.com", "1234");
		companyDao.createCompany(company);
		System.out.println(company);

	}

	public static void testDeleteCompanies() throws Exception {

		CompaniesDao companyDao = new CompaniesDao();
		companyDao.deleteCompany(13);

	}

	private static void testGetCompanies() throws Exception {

		CompaniesDao companiesDao = new CompaniesDao();
		Company companies = companiesDao.getCompany(3);
		System.out.println(companies);
	}

	private static void testUpdateCompanies() throws Exception {
		CompaniesDao companiesDao = new CompaniesDao();
		Company companies = companiesDao.getCompany(19);
		companies.setAddress("LalaLand");
		companiesDao.updateCompany(companies);
		System.out.println(companies);
	}

	public static void testerGetAllCompanies() throws Exception {

		CompaniesDao companiesDao = new CompaniesDao();

		List<Company> companies = companiesDao.getAllCompanies();

		System.out.println(companies);

	}

}
