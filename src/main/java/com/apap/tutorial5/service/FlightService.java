package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.FlightModel;

/**
 * FlightService
 */
public interface FlightService {
	FlightModel getFlightDetailById(String id);
	List<FlightModel> getFlightByFlightNumber(String flightNumber);
	void addFlight(FlightModel flight);
	void deleteFlight(FlightModel flight);
	void updateFlight(FlightModel newFlight, String flightNumber);
}
