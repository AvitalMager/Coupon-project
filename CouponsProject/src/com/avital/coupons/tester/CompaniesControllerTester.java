package com.avital.coupons.tester;

import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.dao.CompaniesDao;
import com.avital.coupons.logic.CompaniesController;

public class CompaniesControllerTester {

	public static void main(String[] args) throws Exception {
	
	
		//testControllerCreateCompanies();
		//testerDeleteCompanies(); 
		testerGetCompanies();
		testerGetAllCompanies();
		//testUpdateCompanies();
	}

	public static void testControllerCreateCompanies() {

		CompaniesController companiesController = new CompaniesController();

		Company company = new Company("Boom", "0535259345", "Lod Ganey Aviv");
		try {
			companiesController.createCompany(company);
			System.out.println(company);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}
	public static void testUpdateCompanies() throws Exception {
		CompaniesController companiesController = new CompaniesController();
		Company companies = companiesController.getCompany(40);
		companies.setPhoneNumber("02385930");
		companiesController.updateCompanies(companies);
		System.out.println(companies);
	}
	
	public static void testerDeleteCompanies() throws Exception {
		
		CompaniesController companiesController = new CompaniesController();
	
		companiesController.deleteCompanies(38);
		System.out.println("Company was deleted");
		
	}
	public static void testerGetCompanies() throws Exception {
		
		CompaniesController companiesController = new CompaniesController();
		Company companies= companiesController.getCompany(40);
		System.out.println(companies);
	}
	
	public static void testerGetAllCompanies() throws Exception {
		CompaniesController companiesController = new CompaniesController();
		
		List<Company> companies = companiesController.getAllCompanies();
		System.out.println(companies);
	}
}
