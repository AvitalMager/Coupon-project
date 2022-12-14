package com.avital.coupons.couponExpiration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.exceptions.ApplicationException;

public class TimerTaskApplication {

	public static void main(String[] args) throws ApplicationException {

		// Crating the timer task
		CouponTimerTask timerTask = new CouponTimerTask();
		Timer timer = new Timer();

		// Setting delay
		long period = 1000 * 60 * 60 * 24;

		// Creating date format
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			// Setting date
			date = dateFormatter.parse("2020-11-14 00:00:00");

			// Setting schedule
			timer.schedule(timerTask, date, period);

			System.out.println("TimerTask started!");

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed run TimerTask");
		}

		// Canceling Timer task
		timer.cancel();
		System.out.println("TimerTask cancelled!");

	}

}
