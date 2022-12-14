package com.avital.coupons.tester;

import java.sql.BatchUpdateException;
import java.sql.Date;

import java.util.Calendar;
import java.util.List;

import com.avital.coupons.beans.Coupon;
import com.avital.coupons.beans.Purchase;
import com.avital.coupons.dao.CouponsDao;
import com.avital.coupons.dao.PurchasesDao;
import com.avital.coupons.enums.CouponCategory;

public class CouponsTester {

	public static void main(String[] args) throws Exception {

		 //testerGetAllCouponsByCompanyId();
		// testerGetAllCouponsByType();
		// testerGetAllCoupons();
	testerCreatecoupons();
		// testGetCoupons();
		// testDeleteCoupons();
		 //testerUdateCoupons();
		// isCouponNameExist();
		// testerDeleteOldCoupons();
		// testerDeleteCouponsByCompanyId();
		// updateCouponsAmount();
	}

	public static void testerCreatecoupons() {

		CouponsDao couponsDao = new CouponsDao();

		Date date = new Date(System.currentTimeMillis());
		
		Coupon coupons = new Coupon("Spagetti", (float) 12.90, "chips", date, date, CouponCategory.FoodAndDrink, 1,
				"bla", 19);

		try {
			couponsDao.createCoupon(coupons);
			System.out.println(coupons);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void isCouponNameExist() {
		CouponsDao couponsDao = new CouponsDao();
		try {
			boolean coupons = couponsDao.isCouponNameExist("Pizza");
			System.out.println(coupons);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testGetCoupons() {
		CouponsDao couponsDao = new CouponsDao();

		try {
			Coupon coupons = couponsDao.getCoupon(4);
			System.out.println(coupons);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testerGetAllCouponsByCompanyId() throws Exception {

		CouponsDao couponsDao = new CouponsDao();

		List<Coupon> coupons = couponsDao.getAllCouponsByCompanyId((long) 19);

		System.out.println(coupons);
	}

	public static void testerGetAllCouponsByType() throws Exception {

		CouponsDao couponsDao = new CouponsDao();

		List<Coupon> coupons = couponsDao.getAllCouponsByType(CouponCategory.FoodAndDrink);

		System.out.println(coupons);

	}

	public static void testerGetAllCoupons() {

		CouponsDao couponsDao = new CouponsDao();
		try {
			List<Coupon> coupons = couponsDao.getAllCoupons();

			System.out.println(coupons);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testDeleteCoupons() throws Exception {

		CouponsDao couponsDao = new CouponsDao();

		Coupon coupons = couponsDao.deleteCoupon(7);

	}

	public static void testerUdateCoupons() throws Exception {

		CouponsDao couponsDao = new CouponsDao();

		Coupon coupons = couponsDao.getCoupon(37);

		coupons.setName("Pizzaaa");

		couponsDao.updateCoupon(coupons);

		System.out.println(coupons);
	}

	public static void testerDeleteOldCoupons() {

		CouponsDao couponsDao = new CouponsDao();

		try {
			couponsDao.deleteOldCoupons();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testerDeleteCouponsByCompanyId() {
		CouponsDao couponsDao = new CouponsDao();
		try {
			couponsDao.deleteCouponsByCompanyId(15);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void updateCouponsAmount() {
//		CouponsDao couponsDao = new CouponsDao();
//		couponsDao.updateCouponsAmount(37, 30);
//	}
}
