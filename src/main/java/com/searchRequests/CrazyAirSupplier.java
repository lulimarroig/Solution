package com.searchRequests;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.CrazyAir.CrazyAir;
import com.CrazyAir.CrazyAirList;
import com.generic.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * CrazyAir Supplier implementation for the Supplier interface
 * 
 * @author Lucia
 */
public class CrazyAirSupplier implements Supplier{
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public List<FlightGenericResponse> performSearch(FlightSearchRequest request) {
		List<FlightGenericResponse> crazyAirResult = new ArrayList<FlightGenericResponse>(); 
		try {
			String jsonResponse = parseAndPerformRequest(request);
			CrazyAirList responseList = parseResponseResults(jsonResponse);
			
			for(CrazyAir flight : responseList.getList()){
				FlightGenericResponse responseFlight = convert(flight);
				crazyAirResult.add(responseFlight);
			}			
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Requesting data from CrazyAir failed!");
		}
		
		
		
		return crazyAirResult;
	}
	
	/*****************************Request Operations ********************************************************/
	
	/** This method takes a generic request gets the necessary information from it and performs an HTTP REST request
	 * returning a json result as String 
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private String parseAndPerformRequest(FlightSearchRequest request) throws ParseException{
		RestTemplate restTemplate = new RestTemplate();
		
		String departureDate = DateOperations.getDateFromIso(request.getDepartureDate());
		String returnDate = DateOperations.getDateFromIso(request.getReturnDate());
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);		

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://crazyAir.com/")
		        .queryParam("origin", request.getOrigin())
		        .queryParam("destination", request.getDestination())
		        .queryParam("departureDate", departureDate)
		        .queryParam("returnDate", returnDate)
		        .queryParam("numberOfPassengers", request.getNumberOfPassengers());

		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		
		URI uri = builder.build().encode().toUri();
		HttpEntity<String> response = restTemplate.exchange(
				uri, 
		        HttpMethod.GET, 
		        entity, 
		        String.class);
		
		String responseResult = "";
		if(response != null){
			responseResult = response.getBody();
		}	
		
		//Log request and response for provider
		logger.info("CrazyAir request: "+uri+". \n"
				+ "CrazyAir response: "+responseResult);
		
		return responseResult;
	}	
	
	
	/*****************************Response Operations********************************************************/
	
	/** This method receives a Json object (result of a request to CrazyAir)
	and uses Jackson to convert it into a list of Java CrazyAir objects
	 *
	 * @param json
	 */	
	private CrazyAirList parseResponseResults(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();		

		CrazyAirList result = mapper.readValue(json, CrazyAirList.class);
		return result;
	}
	
	/** This method takes a CrazyAir Object and do the corresponding operations to transform it to a 
	 * generic flight object
	 * 
	 * @param input
	 * @return
	 */
	private FlightGenericResponse convert(CrazyAir input){
		FlightGenericResponse result = new FlightGenericResponse();
		result.setAirline(input.getAirline());
		result.setDepartureAirportCode(input.getDepartureAirportCode());
		result.setDestinationAirportCode(input.getDestinationAirportCode());		
		
		try {
			result.setDepartureDate(DateOperations.getIsoFromDate(input.getDepartureDate()));
			result.setArrivalDate(DateOperations.getIsoFromDate(input.getArrivalDate()));
		} catch (ParseException e) {
			result.setDepartureDate("Invalid dates.");
			result.setArrivalDate("Invalid dates.");
		}
		
		//round two digits
		result.setFare(Math.round(input.getPrice() * 100.0) / 100.0);		
		result.setSupplier("CrazyAir");
		
		return result;
	}
	
}


