package com.avital.coupons.logic;

import java.sql.Date;
import java.util.List;

import com.avital.coupons.beans.Coupon;
import com.avital.coupons.beans.Purchase;

import com.avital.coupons.dao.PurchasesDao;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.exceptions.ApplicationException;

public class PurchasesController {

	private PurchasesDao purchasesDao;

	public PurchasesController() {
		this.purchasesDao = new PurchasesDao();
	}

	public void createPurchase(Purchase purchase) throws ApplicationException {

		// Validating before creating purchase
		validateCreateOrUpdatePurchase(purchase);

		// Creating coupon
		Coupon coupon = new Coupon();

		// Creating coupons controller
		CouponsController couponsController;
		couponsController = new CouponsController();

		// Receiving coupons Id
		coupon = couponsController.getCoupon(purchase.getCouponId());

		// Setting coupons new amount
		coupon.setAmount(coupon.getAmount() - purchase.getAmount());

		// Creating purchase
		this.purchasesDao.createPurchase(purchase);

		// Updating coupons amount
		couponsController.updateCoupon(coupon);
	}

	public void updatePurchase(Purchase newPurchase) throws ApplicationException {

		// Validating before updating purchase
		validateCreateOrUpdatePurchase(newPurchase);

		// Validating coupons amount
		validateAmountOfAvailableCoupons(newPurchase);

		// Creating purchase to get the old purchase information
		Purchase purchase = new Purchase();

		// Receiving id of the previous purchase that we already have in DB
		purchase = this.purchasesDao.getPurchase(newPurchase.getId());

		// Calculating the new amount
		int newAmount = newPurchase.getAmount() - purchase.getAmount();

		// Creating coupon
		Coupon coupon = new Coupon();

		// Creating coupons controller
		CouponsController couponsController;
		couponsController = new CouponsController();

		// Receiving coupons Id
		coupon = couponsController.getCoupon(purchase.getCouponId());

		// Setting coupons new amount
		coupon.setAmount(coupon.getAmount() - newAmount);

		// Updating purchase
		this.purchasesDao.updatePurchase(newPurchase);

		// Updating coupons amount
		couponsController.updateCoupon(coupon);

	}

	private void validateCreateOrUpdatePurchase(Purchase purchase) throws ApplicationException {

		// Validating purchase user Id
		if (Long.valueOf(purchase.getUserId()) == null) {
			throw new ApplicationException(ErrorType.INVALID_ID, purchase.toString());
		}

		// Validating purchase company Id
		if (Long.valueOf(purchase.getCouponId()) == null) {
			throw new ApplicationException(ErrorType.INVALID_ID, purchase.toString());
		}

		// Validating purchase amount
		if (Long.valueOf(purchase.getAmount()) == null) {
			throw new ApplicationException(ErrorType.INVALID_AMOUNT, purchase.toString());
		}

		// Validating purchase time of purchase
		if (purchase.getTimeOfPurchase() == null) {
			throw new ApplicationException(ErrorType.INVALID_DATES, purchase.toString());
		}

		// Validating coupons expiration date
		checkCouponsExperationDate(purchase);

	}

	public void DeletePurchase(long id) throws ApplicationException {

		// Executing Delete Purchases
		this.purchasesDao.deletePurchase(id);
	}

	public List<Purchase> getAllPurchases() throws ApplicationException {

		// Executing get All Purchases
		return this.purchasesDao.getAllPurchases();
	}

	public Purchase getPurchase(long id) throws ApplicationException {

		// Executing get Purchase
		return this.purchasesDao.getPurchase(id);

	}

	public List<Purchase> getAllPurchasesByUserId(long userId) throws ApplicationException {

		// Executing get All Purchases By User Id
		return this.purchasesDao.getAllPurchasesByUserId(userId);

	}

	public List<Purchase> getAllPurchasesByCompanyId(long companyId) throws ApplicationException {

		// Executing get All Purchases By Company Id
		return this.purchasesDao.getAllPurchasesByCompanyId(companyId);

	}

	public List<Purchase> getPurchasedCouponsByMaxPrice(long userId, float maxPrice) throws ApplicationException {

		// Executing get Purchased Coupons By Max Price
		return this.purchasesDao.getPurchasedCouponsByMaxPrice(userId, maxPrice);
	}

	public void deletePurchasesByCompanyId(long id) throws ApplicationException {

		// Executing delete Purchases By Company Id
		this.purchasesDao.deletePurchasesByCompanyId(id);

	}

	public void deletePurchasesByUserId(long userId) throws ApplicationException {

		// Executing delete Purchases By User Id
		this.purchasesDao.deletePurchasesByUserId(userId);
	}

	public void deletePurchasesByCouponId(long couponId) throws ApplicationException {

		// Executing delete Purchases By CouponId
		this.purchasesDao.deletePurchasesByCouponId(couponId);

	}

	private void validateAmountOfAvailableCoupons(Purchase purchase) throws ApplicationException {

		// Creating coupons controller
		CouponsController couponsController;
		couponsController = new CouponsController();

		// Validating coupons amount
		if (purchase.getAmount() <= 0) {
			throw new ApplicationException(ErrorType.INVALID_AMOUNT, "This purchase amount is 0" + purchase.toString());
		}

		// Receiving amount of available coupons
		int amountOfAvailableCoupons = couponsController.getAmountOfAvailableCoupons(purchase.getCouponId());

		// Checking if there enough coupons for this purchase
		if (amountOfAvailableCoupons < purchase.getAmount()) {
			throw new ApplicationException(ErrorType.INVALID_AMOUNT, "Out of stock" + purchase.toString());

		}
	}

	private void checkCouponsExperationDate(Purchase purchases) throws ApplicationException {

		// Creating today's date
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);

		// Creating coupon controller
		CouponsController couponsController = new CouponsController();

		// Receiving coupons expiration date
		Date endDate = couponsController.getCoupon(purchases.getCouponId()).getEndDate();

		// Checking if this coupon expired
		if (date.after(endDate)) {
			throw new ApplicationException(ErrorType.COUPON_EXPIERED, "expired coupon" + purchases.toString());
		}
		// Printing coupons expiration date just to make sure
		System.out.println(endDate);
	}
}
