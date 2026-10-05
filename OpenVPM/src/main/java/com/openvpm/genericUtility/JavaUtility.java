package com.openvpm.genericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * This file contains Java methods for miscellaneous operations
 * 
 * @author Amit
 */

public class JavaUtility {
	
	//RANDOM NUMBER
	//Just Random
	public int randomNumber()
	{
		Random r = new Random();
		return r.nextInt();
	}
	
	// Positive Random Number
	public int posRandomNumber()
	{
		Random r = new Random();
		return r.nextInt(99999);
	}
	
	// Ten Digit Random
	public long tenDigitNum()
	{
		Random r = new Random();
		return r.nextLong(6000000000L, 9999999999L);
	}
	
	//SYSTEM DATE
	public String getCurrentDate() 
	{
		Date d = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("dd-MM-yyyy");
		return sim.format(d);
	}
	
	//SPECIFIC DATE
	public String getReqDate(int Days) {
		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
		s.format(d);
		Calendar cal = s.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH, Days);
		String dateReq = s.format(cal.getTime());
		
		return dateReq;
		
	}

}
