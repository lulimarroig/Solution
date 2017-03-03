package com.searchRequests;
import java.util.List;

import com.generic.FlightGenericResponse;
import com.generic.FlightSearchRequest;

/**
 * Supplier interface for homogeneous access
 * 
 * @author Lucia
 */
public interface Supplier {
	
	static String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	
	List<FlightGenericResponse> performSearch(FlightSearchRequest request);
	
}
