package com.avital.coupons.logic;

import java.util.List;

import com.avital.coupons.beans.Coupon;
import com.avital.coupons.dao.CouponsDao;
import com.avital.coupons.enums.CouponCategory;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.exceptions.ApplicationException;

public class CouponsController {

	private CouponsDao couponsDao;

	public CouponsController() {
		this.couponsDao = new CouponsDao();
	}

	public long createCoupon(Coupon coupons) throws ApplicationException {

		// Checking if coupon exists
		validateIsCouponExistss(coupons);

		// Validating before creating coupon
		validateCreateOrUpdateCoupon(coupons);

		// Creating the coupon
		return this.couponsDao.createCoupon(coupons);

	}

	public void updateCoupon(Coupon coupons) throws ApplicationException {

		// Validating before updating coupon
		validateCreateOrUpdateCoupon(coupons);

		// updating coupon
		this.couponsDao.updateCoupon(coupons);

	}

	private void validateCreateOrUpdateCoupon(Coupon coupons) throws ApplicationException {

		// Validating coupons name
		if (coupons.getName().isEmpty()) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, coupons.getName());
		}

		// Validating coupons name
		if (coupons.getName().length() < 2) {
			throw new ApplicationException(ErrorType.NAME_TOO_SHORT, coupons.getName());
		}

		// Validating coupons price
		if (coupons.getPrice() == 0) {
			throw new ApplicationException(ErrorType.INVALID_PRICE, coupons.toString());
		}

		// Validating coupons date
		if (coupons.getStartDate() == null) {
			throw new ApplicationException(ErrorType.INVALID_DATES, coupons.toString());
		}

		// Validating coupons date
		if (coupons.getEndDate() == null) {
			throw new ApplicationException(ErrorType.INVALID_DATES, coupons.toString());
		}

		// Validating coupons category
		if (coupons.getCouponCategory() == null) {
			throw new ApplicationException(ErrorType.INVALID_COUPON_TYPE, coupons.toString());
		}

		// Validating coupons amount
		if (Long.valueOf(coupons.getAmount()) == null) {
			throw new ApplicationException(ErrorType.INVALID_AMOUNT, coupons.toString());
		}

		// Validating coupons company Id
		if (Long.valueOf(coupons.getCompanyId()) == null) {
			throw new ApplicationException(ErrorType.INVALID_ID, coupons.toString());
		}

	}

	private void validateIsCouponExistss(Coupon coupons) throws ApplicationException {

		// Validating if coupon name exists
		if (this.couponsDao.isCouponNameExist(coupons.getName())) {
			throw new ApplicationException(ErrorType.COUPON_TITLE_EXIST, coupons.getName());

		}

	}

	public void deleteCoupon(long id) throws ApplicationException {

		// Deleting purchases by coupon id
		PurchasesController purchasesController = new PurchasesController();
		purchasesController.deletePurchasesByCouponId(id);

		// Deleting coupons
		this.couponsDao.deleteCoupon(id);

	}

	public Coupon getCoupon(long id) throws ApplicationException {

		// Execute get Coupon
		return this.couponsDao.getCoupon(id);

	}

	public List<Coupon> getAllCoupons() throws ApplicationException {

		// Execute get All Coupons
		return this.couponsDao.getAllCoupons();
	}

	public List<Coupon> getAllCouponsByCompanyId(long id) throws ApplicationException {

		// Execute get All Coupons By Company Id
		return this.couponsDao.getAllCouponsByCompanyId(id);
	}

	public List<Coupon> getAllCouponsByType(CouponCategory couponCategory) throws ApplicationException {

		// Execute get All Coupons By Type
		return this.couponsDao.getAllCouponsByType(couponCategory);
	}

	public void deleteCouponsByCompanyId(long companyId) throws ApplicationException {

		// Execute delete Coupons By Company Id
		this.couponsDao.deleteCouponsByCompanyId(companyId);

	}

	public int getAmountOfAvailableCoupons(long id) throws ApplicationException {

		// Execute get Amount Of Available Coupons
		return this.couponsDao.getCoupon(id).getAmount();

	}

}
