package com.avital.coupons.logic;

import java.sql.SQLException;
import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.dao.CompaniesDao;
import com.avital.coupons.dao.CouponsDao;
import com.avital.coupons.dao.PurchasesDao;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.exceptions.ApplicationException;

public class CompaniesController {

	private CompaniesDao companiesDao;

	public CompaniesController() {
		this.companiesDao = new CompaniesDao();
	}

	public long createCompany(Company company) throws ApplicationException {

		// checking if company exists
		validateIsCompanyExists(company);

		// validating before creating company
		validateCreateCompany(company);

		// creating the company
		return this.companiesDao.createCompany(company);

	}

	public void updateCompanies(Company company) throws ApplicationException {

		// validating before company update
		validateCreateCompany(company);

		// company update
		this.companiesDao.updateCompany(company);

	}

	public void deleteCompanies(long id) throws ApplicationException {

		// Deleting purchases by company id
		PurchasesController purchasesController = new PurchasesController();
		purchasesController.deletePurchasesByCompanyId(id);

		// Deleting coupons by company Id
		CouponsController couponsController = new CouponsController();
		couponsController.deleteCouponsByCompanyId(id);

		// Deleting companies
		this.companiesDao.deleteCompany(id);

	}

	private void validateIsCompanyExists(Company company) throws ApplicationException {

		// Validating if company name exists
		if (this.companiesDao.isCompanyNameExist(company.getCompanyName())) {
			throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, company.getCompanyName());

		}
	}

	private void validateCreateCompany(Company company) throws ApplicationException {

		// Validating company name
		if (company.getCompanyName() == null) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, company.getCompanyName());

		}
		// Validating company name
		if (company.getCompanyName().isEmpty()) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, company.getCompanyName());

		}
		// Validating company name
		if (company.getCompanyName().length() < 2) {
			throw new ApplicationException(ErrorType.NAME_TOO_SHORT, company.getCompanyName());

		}
		// Validating company address
		if (company.getAddress() == null) {
			throw new ApplicationException(ErrorType.INVALID_ADDRESS, company.getAddress());

		}
		// Validating company phone number
		if (company.getPhoneNumber() == null) {
			throw new ApplicationException(ErrorType.INVALID_PHONE_NUMBER, company.getPhoneNumber());
		}

	}

	public Company getCompany(long id) throws ApplicationException {

		// Executing getCompanies
		return this.companiesDao.getCompany(id);

	}

	public List<Company> getAllCompanies() throws ApplicationException {

		// Executing getAllCompanies
		return this.companiesDao.getAllCompanies();

	}

}
