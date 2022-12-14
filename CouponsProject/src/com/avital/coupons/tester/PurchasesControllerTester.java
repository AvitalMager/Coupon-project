package com.avital.coupons.tester;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.beans.Purchase;
import com.avital.coupons.logic.CompaniesController;
import com.avital.coupons.logic.PurchasesController;

public class PurchasesControllerTester {

	public static void main(String[] args) throws Exception {

		//testerCreatePurchases ();
		//testerValidateCouponsAmountValid();
		//testerValidateCouponsAmountInvalid();
		//testerDeletePurchasesByCompanyId();
		//tsetUpdatePurchases();
		//testerCheckCouponsExperationDate();
		//testerDeletePurchases();
		//testerGetPurchases();
		//testerGetAllPurchases();
		//testerGetAllPurchasesByUserId();
		//testereletePurchasesByCompanyId();
		//testerGetPurchasedCouponsByMaxPrice();
		//testerDeletePurchasesByUserId();
	}

	public static void testerCreatePurchases() throws Exception {

		PurchasesController purchasesController = new PurchasesController();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Purchase purchases = new Purchase(15, 45, 100, timestamp);
		purchasesController.createPurchase(purchases);
		System.out.println(purchases);

	}

	public static void testerDeletePurchases() throws Exception {

		PurchasesController purchasesController = new PurchasesController();

		purchasesController.DeletePurchase(52);

	}

	public static void testerGetPurchases() throws Exception {

		PurchasesController purchasesController = new PurchasesController();
		Purchase purchases= purchasesController.getPurchase(48);
		System.out.println(purchases);
	}

	public static void testereletePurchasesByCompanyId() throws Exception {

		PurchasesController purchasesController = new PurchasesController();

		List<Purchase> purchases = purchasesController.getAllPurchasesByCompanyId(19);
		System.out.println(purchases);
	}

	public static void testerGetAllPurchases() throws Exception {

		PurchasesController purchasesController = new PurchasesController();

		List<Purchase> purchases = purchasesController.getAllPurchases();
		System.out.println(purchases);
	}

	public static void testerGetPurchasedCouponsByMaxPrice() throws Exception {

		PurchasesController purchasesController = new PurchasesController();

		List<Purchase> purchases = purchasesController.getPurchasedCouponsByMaxPrice(15, 200);
		System.out.println(purchases);
	}

	public static void testerGetAllPurchasesByUserId() throws Exception {

		PurchasesController purchasesController = new PurchasesController();

		List<Purchase> purchases = purchasesController.getAllPurchasesByUserId(15);
		System.out.println(purchases);
	}

	private static void testerDeletePurchasesByCompanyId () throws Exception {

		PurchasesController purchasesController = new PurchasesController();
		purchasesController.deletePurchasesByCompanyId(15);
	}

	public static void testerDeletePurchasesByUserId() throws Exception {

		PurchasesController purchasesController = new PurchasesController();

		purchasesController.deletePurchasesByUserId(15);

	}


//	public static void testerCheckCouponsExperationDate() throws Exception {
//
//		PurchasesController purchasesController = new PurchasesController();
//		Purchase purchases = purchasesController.getPurchase(54);
//		purchasesController.checkCouponsExperationDate(purchases);
//		System.out.println(purchases);
//
//	}

	public static void tsetUpdatePurchases () throws Exception {

		PurchasesController purchasesController = new PurchasesController();
		Purchase purchases= purchasesController.getPurchase(58);

		purchases.setAmount(3);
		
		purchasesController.updatePurchase(purchases);
		System.out.println(purchases.toString());
		

	}

//	public static void testerValidateCouponsAmountValid() throws Exception {
//
//		PurchasesController purchasesController = new PurchasesController();
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		Purchase purchase = new Purchase(19, 37, 2, timestamp);
//		purchasesController.validateAmountOfAvailableCoupons(purchase);
//	}
//
//	public static void testerValidateCouponsAmountInvalid() throws Exception {
//
//		try {
//			PurchasesController purchasesController = new PurchasesController();
//			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//			Purchase purchase = new Purchase(15, 37, 300, timestamp);
//
//			purchasesController.validateCouponsAmount(purchase);
//		}
//		catch(Exception e) {
//			// Getting an exception here is a good thing because we asked for too many Pizas 
//			// and we expect the validate function to throw an exception.
//			// IN OTHER WORDS = NO EXCEPTION IN THIS CASE IS !! BAD !!
//			return;
//		}
//
//		// There we 30 Pizas for sale
//		// We asked for 300
//		// The validate function should have thrown an exception
//		// IT DIDN'T and so we fail the test
//		throw new Exception("A validate function got a purchase request with an amount too big, but failed to abort the process");
//	}

}
