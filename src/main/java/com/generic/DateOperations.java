package com.generic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.searchRequests.Supplier;

/**
 * Generic class for managing general date operations
 * 
 * @author Lucia
 */
public class DateOperations {
	
	/** The date in iso format is calculated from the input date with the format "MM-dd-yyyy"
	 * The resulting date is returned as a String
	 * 
	 * @param dayOfMonth
	 * @param month
	 * @param year
	 * @return
	 * @throws ParseException
	 */
	public static String getIsoFromDate(String date) throws ParseException{
        final String inputFormat = "MM-dd-yyyy";
        final String outputFormat = Supplier.ISO_DATE_FORMAT;
        
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(inputFormat).parse(date));
	}

	/** The date in iso format is calculated from the input date with the format "MM-dd-yyyy"
	 * The resulting date is returned as a String
	 * 
	 * @param dayOfMonth
	 * @param month
	 * @param year
	 * @return
	 * @throws ParseException
	 */
	public static String getDateFromIso(String isoDate) throws ParseException{
		final String inputFormat = Supplier.ISO_DATE_FORMAT;
        final String outputFormat = "MM-dd-yyyy";
        
        
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(inputFormat).parse(isoDate));
	}
	
	/** The date in iso format is calculated from the input parameters and returned as a String
	 * 
	 * @param dayOfMonth
	 * @param month
	 * @param year
	 * @return
	 * @throws ParseException
	 */
	public static String getIsoDate(int dayOfMonth, int month, int year) throws ParseException{	    
	    final String outputFormat = Supplier.ISO_DATE_FORMAT;
	    
	    Calendar c = Calendar.getInstance();
	    
	    //Calendar months are zero based
	    c.set(year, month - 1, dayOfMonth, 0, 0);  
	    
	    return new SimpleDateFormat(outputFormat).format(c.getTime());
	}
	
	
}
