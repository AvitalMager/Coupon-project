package com.avital.coupons.tester;

import java.sql.Date;
import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.beans.Coupon;
import com.avital.coupons.dao.CouponsDao;
import com.avital.coupons.enums.CouponCategory;
import com.avital.coupons.logic.CompaniesController;
import com.avital.coupons.logic.CouponsController;

public class CouponsControllerTester {

	public static void main(String[] args) throws Exception {

		testerDeleteCoupons();
		// testerGetAmountOfAvailableCoupons();
//		testerupdateAmountOfAvaliableCoupons();
		// testerCreateCoupon();
		// testerUpdateCoupon();
		// testerGetCoupons();
		// testerGetAllCoupons();
		// testergetAllCouponsByCompanyId();
		// testergetAllCouponsByType();
		// testerdeleteCouponsByCompanyId();
	}

	public static void testerCreateCoupon() throws Exception {
		Date date = new Date(System.currentTimeMillis());
		CouponsController couponsController = new CouponsController();
		Coupon coupons = new Coupon("snacks", (float) 12.90, "chips", date, date, CouponCategory.FoodAndDrink, 1, "bla",
				19);
		couponsController.createCoupon(coupons);
		System.out.println(coupons);

	}

	public static void testerUpdateCoupon() throws Exception {

		CouponsController couponsController = new CouponsController();

		Coupon coupons = couponsController.getCoupon(37);

		coupons.setAmount(31);

		couponsController.updateCoupon(coupons);

		System.out.println(coupons);

	}

	public static void testerDeleteCoupons() throws Exception {

		CouponsController couponsController = new CouponsController();
		couponsController.deleteCoupon(48);
	}

	public static void testerGetCoupons() throws Exception {

		CouponsController couponsController = new CouponsController();
		Coupon coupons = couponsController.getCoupon(37);
		System.out.println(coupons);
	}

	public static void testerGetAllCoupons() throws Exception {
		CouponsController couponsController = new CouponsController();

		List<Coupon> coupons = couponsController.getAllCoupons();
		System.out.println(coupons);
	}

	public static void testergetAllCouponsByCompanyId() throws Exception {
		CouponsController couponsController = new CouponsController();

		List<Coupon> coupons = couponsController.getAllCouponsByCompanyId(19);
		System.out.println(coupons);
	}

	public static void testergetAllCouponsByType() throws Exception {
		CouponsController couponsController = new CouponsController();

		List<Coupon> coupons = couponsController.getAllCouponsByType(CouponCategory.Travel);
		System.out.println(coupons);
	}

	public static void testerdeleteCouponsByCompanyId() throws Exception {
		CouponsController couponsController = new CouponsController();
		couponsController.deleteCouponsByCompanyId(26);

	}

	public static void testerGetAmountOfAvailableCoupons() throws Exception {

		CouponsController couponsController = new CouponsController();
		int amount = couponsController.getAmountOfAvailableCoupons(37);
		System.out.println(amount);
	}

}
