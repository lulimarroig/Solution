package com.travixTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.generic.FlightGenericResponse;
import com.generic.FlightSearchRequest;
import com.searchRequests.Supplier;
import com.searchRequests.SupplierFactory;

@SpringBootApplication
public class TravixTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravixTestApplication.class, args);
		
		System.out.println("Welcome to the flight search service");
		
		//For simplifying the test it's assumed that all inputs are in the correct format
		FlightSearchRequest request = new FlightSearchRequest();		
		
		//Load input parameters into a generic Request object
		request.setOrigin(args[0]);
		request.setDestination(args[1]);
		request.setDepartureDate(args[2]);
		request.setReturnDate(args[3]);
		request.setNumberOfPassengers(Integer.parseInt(args[4]));
		
		List<FlightGenericResponse> flightResults = new ArrayList<FlightGenericResponse>();
		SupplierFactory factory = new SupplierFactory();
		
		// For each defined supplier use the input parameters to retrieve the flights that matches 
		//the parameters and correspond to that supplier
		for (SupplierType type : SupplierType.values()) {
			Supplier supplier = factory.getSupplier(type);
			
			if(supplier != null){
				List<FlightGenericResponse> result = supplier.performSearch(request);
				flightResults.addAll(result);
			}
		}		
		
		//Order the total results by Fare ASC
		flightResults = orderByFare(flightResults);
		
		//Convert the results into JSON format to retrieve them to the user
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(flightResults);
		} catch (JsonProcessingException e) {
			System.out.println("An error has occured, please try again or contact the administrator");
			e.printStackTrace();
		}
		
		//Write the output in the standard console in json format
		System.out.println(json);
		
		System.out.println("Good bye!");
	}
	
	
	/**
	 * Orders the flights by fare in ascending order
	 * 
	 * @param input
	 * @return
	 */
	private static List<FlightGenericResponse> orderByFare(List<FlightGenericResponse> input){		
		
		Collections.sort(input, new Comparator<FlightGenericResponse>() {
		    @Override
		    public int compare(FlightGenericResponse f1, FlightGenericResponse f2) {
		    	int result = -1;
		    	if(f1.getFare() > f2.getFare()){
		    		result = 1;
		    	}else if (f1.getFare() == f2.getFare()){
		    		result = 0;
		    	}
		        return result;
		    }
		});
		
		return input;		
	}
}
