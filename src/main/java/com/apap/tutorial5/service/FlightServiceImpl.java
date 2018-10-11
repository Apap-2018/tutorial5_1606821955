package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.repository.FlightDB;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FlightServiceImpl
 */
@Service
@Transactional
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightDB flightDb;
	
	@Override
	public FlightModel getFlightDetailById(String id) {
		long idLong = Long.parseLong(id);
		return flightDb.findById(idLong);
	}
	
	@Override
	public List<FlightModel> getFlightByFlightNumber(String flightNumber) {
		List<FlightModel> allFlight = flightDb.findAll();
		List<FlightModel> selectedFlight = new ArrayList<>();
		
		for (FlightModel flight : allFlight) {
			if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
				selectedFlight.add(flight);
			}
		}
		
		return selectedFlight;
	}
	
	@Override
	public void addFlight(FlightModel flight) {
		flightDb.save(flight);
	}
	
	@Override
	public void deleteFlight(FlightModel flight) {
		flightDb.delete(flight);
	}
	
	@Override
	public void updateFlight(FlightModel newFlight, String id) {
		long idLong = Long.parseLong(id);
		FlightModel flight = flightDb.findById(idLong);
		
		flight.setFlightNumber(newFlight.getFlightNumber());
		flight.setOrigin(newFlight.getOrigin());
		flight.setDestination(newFlight.getDestination());
		flight.setTime(newFlight.getTime());
		flightDb.save(flight);
	}
}
