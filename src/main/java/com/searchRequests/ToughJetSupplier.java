package com.searchRequests;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ToughJet.ToughJet;
import com.ToughJet.ToughJetList;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generic.DateOperations;
import com.generic.FlightGenericResponse;
import com.generic.FlightSearchRequest;

/**
 * ToughtJet Supplier implementation for the Supplier interface
 * 
 * @author Lucia
 */
public class ToughJetSupplier implements Supplier{

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public List<FlightGenericResponse> performSearch(FlightSearchRequest request) {
		List<FlightGenericResponse> toughJetResult = new ArrayList<FlightGenericResponse>(); 
		try {
			String jsonResponse = parseAndPerformRequest(request);
			ToughJetList responseList = parseResponseResults(jsonResponse);
			
			for(ToughJet flight : responseList.getList()){
				FlightGenericResponse responseFlight = convert(flight);
				toughJetResult.add(responseFlight);
			}			
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Requesting data from ToughJet failed!");
		}
		
		return toughJetResult;
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
		
		
		//Getting departure date details
		Date departureDate = new SimpleDateFormat(Supplier.ISO_DATE_FORMAT).parse(request.getDepartureDate());		   
	    Calendar c = Calendar.getInstance();
	    c.setTime(departureDate);	    
	    //Months in Calendar are zero based
	    int departureDay = c.get(Calendar.DAY_OF_MONTH);
	    int departureMonth = c.get(Calendar.MONTH) + 1;	    
	    int departureYear = c.get(Calendar.YEAR);
	    
	    //Getting arrival date details
	    Date arrivalDate = new SimpleDateFormat(Supplier.ISO_DATE_FORMAT).parse(request.getReturnDate());
	    c.setTime(arrivalDate);	    
	    //Months in Calendar are zero based
	    int arrivalDay = c.get(Calendar.DAY_OF_MONTH);
	    int arrivalMonth = c.get(Calendar.MONTH) + 1;	    
	    int arrivalYear = c.get(Calendar.YEAR);		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		//TODO: Log request from ToughJet AspectJ
	
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://toughJet.com/")	
		        .queryParam("from", request.getOrigin())
		        .queryParam("to", request.getDestination())
		        .queryParam("departureDay", departureDay)
		        .queryParam("departureMonth", departureMonth)
		        .queryParam("departureYear", departureYear)
		        .queryParam("returnDay", arrivalDay)
		        .queryParam("returnMonth", arrivalMonth)
		        .queryParam("returnYear", arrivalYear)
		        .queryParam("numberOfAdults", request.getNumberOfPassengers());
	
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
		logger.info("ToughJet request: "+uri+". \n"
				+ "ToughJet response: "+responseResult);
		
		return responseResult;
	}
	
	/*****************************Response Operations********************************************************/
	
	/** This method receives a Json object (result of a request to ToughJet)
	 * and uses Jackson to convert it into a list of Java ToughJet objects
	 * 
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private ToughJetList parseResponseResults(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();		
	
		ToughJetList result = mapper.readValue(json, ToughJetList.class);
		return result;
	}
	
	
	/** This method takes a ToughJet Object and do the corresponding operations to transform it to a 
	 * generic flight object
	 * 
	 * @param input
	 * @return
	 */
	private FlightGenericResponse convert(ToughJet input){
		FlightGenericResponse result = new FlightGenericResponse();
		result.setAirline(input.getCarrier());
		result.setDepartureAirportCode(input.getArrivalAirportName());
		result.setDestinationAirportCode(input.getDepartureAirportName());		
		
		try {
			result.setDepartureDate(DateOperations.getIsoDate(input.getDepartureDay(), input.getDepartureMonth(), input.getDepartureYear()));
			result.setArrivalDate(DateOperations.getIsoDate(input.getReturnDay(), input.getDepartureMonth(), input.getReturnYear()));
		} catch (ParseException e) {
			result.setDepartureDate("Invalid dates.");
			result.setArrivalDate("Invalid dates.");
		}
		
		//round two digits
		result.setFare(Math.round(getFare(input.getBasePrice(), input.getDiscount(), input.getTax()) * 100.0) / 100.0);		
		result.setSupplier("CrazyAir");
		
		return result;
	}
	
	/** The Fare needs to be calculated taking into account its three parameters
	 * @param basePrice
	 * @param discount
	 * @param tax
	 */
	private double getFare(double basePrice, double discount, double tax) {
		double price = basePrice - (basePrice*discount)/100;		
		
		return price + tax;
	}

}
