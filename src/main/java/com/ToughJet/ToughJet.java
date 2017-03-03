package com.ToughJet;

public class ToughJet{
	
	String carrier;
	
	//Price without tax(doesn’t include discount)
	double basePrice;
	//Tax which needs to be charged along with the price
	double tax;
	
	//Discount which needs to be applied on the price(in percentage)
	double discount;
	
	String departureAirportName;
	String arrivalAirportName;
	int departureDay;
	int departureMonth;
	int departureYear;
	int returnDay;
	int returnMonth;
	int returnYear;
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getDepartureAirportName() {
		return departureAirportName;
	}
	public void setDepartureAirportName(String departureAirportName) {
		this.departureAirportName = departureAirportName;
	}
	public String getArrivalAirportName() {
		return arrivalAirportName;
	}
	public void setArrivalAirportName(String arrivalAirportName) {
		this.arrivalAirportName = arrivalAirportName;
	}
	public int getDepartureDay() {
		return departureDay;
	}
	public void setDepartureDay(int departureDay) {
		this.departureDay = departureDay;
	}
	public int getDepartureMonth() {
		return departureMonth;
	}
	public void setDepartureMonth(int departureMonth) {
		this.departureMonth = departureMonth;
	}
	public int getDepartureYear() {
		return departureYear;
	}
	public void setDepartureYear(int departureYear) {
		this.departureYear = departureYear;
	}
	public int getReturnDay() {
		return returnDay;
	}
	public void setReturnDay(int returnDay) {
		this.returnDay = returnDay;
	}
	public int getReturnMonth() {
		return returnMonth;
	}
	public void setReturnMonth(int returnMonth) {
		this.returnMonth = returnMonth;
	}
	public int getReturnYear() {
		return returnYear;
	}
	public void setReturnYear(int returnYear) {
		this.returnYear = returnYear;
	}
}
