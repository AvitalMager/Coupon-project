package com.avital.coupons.tester;

import java.sql.Timestamp;
import java.util.List;

import com.avital.coupons.beans.Purchase;
import com.avital.coupons.beans.User;
import com.avital.coupons.dao.PurchasesDao;
import com.avital.coupons.dao.UsersDao;
import com.avital.coupons.enums.UserType;

public class PurchasesTester {

	public static void main(String[] args) throws Exception {
		// testerGetAllPurchasesByUserId ();
		// testCreatePurchases();
		//testGetPurchases();
		 testerUdatePurchases();
		// testerGetAllPurchases();
		// testDeletePurchases();
		// testerGetAllPurchasesByCompanyId();
		// testergetPurchasedCouponsByMaxPrice();
		 //testerDeletePurchasesByCompanyId();
		//deletePurchasesByUserId();
		//deleteOldCouponsPurchases();

	}

	private static void testGetPurchases() throws Exception {

		PurchasesDao purchasesDao = new PurchasesDao();

		Purchase purchases = purchasesDao.getPurchase(48);

		System.out.println(purchases);
	}

	public static void testCreatePurchases() throws Exception {
		PurchasesDao purchasesDao = new PurchasesDao();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		Purchase purchases = new Purchase(1, 4, 1, timestamp);
		purchasesDao.createPurchase(purchases);
		System.out.println(purchases);

	}

	public static void testerGetAllPurchasesByUserId() throws Exception {

		PurchasesDao purchasesDao = new PurchasesDao();

		List<Purchase> purchases = purchasesDao.getAllPurchasesByUserId(1);

		System.out.println(purchases);

	}

	public static void testerUdatePurchases() throws Exception {

		PurchasesDao purchasesDao = new PurchasesDao();

		Purchase purchases = purchasesDao.getPurchase(54);

		purchases.setAmount(5);

		purchasesDao.updatePurchase(purchases);

		System.out.println(purchases);
	}

	public static void testerGetAllPurchases() throws Exception {
		PurchasesDao purchasesDao = new PurchasesDao();

		List<Purchase> purchases = purchasesDao.getAllPurchases();

		System.out.println(purchases);

	}

	public static void testDeletePurchases() throws Exception {

		PurchasesDao purchasesDao = new PurchasesDao();

		Purchase purchases = purchasesDao.deletePurchase(2);

	}

	public static void testerGetAllPurchasesByCompanyId() throws Exception {

		PurchasesDao purchasesDao = new PurchasesDao();

		List<Purchase> purchases = purchasesDao.getAllPurchasesByCompanyId(3);

		System.out.println(purchases);

	}

	public static void testergetPurchasedCouponsByMaxPrice() throws Exception {

		PurchasesDao purchasesDao = new PurchasesDao();

		List<Purchase> purchases = purchasesDao.getPurchasedCouponsByMaxPrice(12, 200);

		System.out.println(purchases);

	}

	public static void testerDeletePurchasesByCompanyId() throws Exception {

		PurchasesDao purchasesDao = new PurchasesDao();

		purchasesDao.deletePurchasesByCompanyId(15);

	}

	public static void deletePurchasesByUserId() {

		PurchasesDao purchasesDao = new PurchasesDao();
		try {
			purchasesDao.deletePurchasesByUserId(12);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteOldCouponsPurchases() {
		
		PurchasesDao purchasesDao = new PurchasesDao();
		try {
			purchasesDao.deleteOldCouponsPurchases();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
