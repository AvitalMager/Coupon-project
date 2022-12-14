package com.avital.coupons.couponExpiration;

import java.util.Timer;
import java.util.TimerTask;

import javax.management.InvalidApplicationException;

import com.avital.coupons.dao.CouponsDao;
import com.avital.coupons.dao.PurchasesDao;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.exceptions.ApplicationException;

public class CouponTimerTask  extends TimerTask  {
	
	@Override
	public void run()  {
		
		//Creating coupons dao
		CouponsDao couponsDao = new CouponsDao();
		
		//Creating purchases dao
		PurchasesDao purchasesDao= new PurchasesDao();
		
			try {
				//deleting old coupons purchases
				purchasesDao.deleteOldCouponsPurchases();
				
				//deleting old coupons
				couponsDao.deleteOldCoupons();
				
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
