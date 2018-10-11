package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FlightController
 */
@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) throws ParseException {
		PilotModel pilot = new PilotModel();
		pilot.setPilotFlight(new ArrayList<FlightModel>());
		
		FlightModel flight = new FlightModel();
		flight.setTime(flightService.getTodayDefaultFlightDate());
		pilot.getPilotFlight().add(flight);
		
		model.addAttribute("pilot", pilot);
		model.addAttribute("licenseNumber", licenseNumber);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params={"addRow"})
	public String addRow(@PathVariable(value = "licenseNumber") String licenseNumber,
					@ModelAttribute PilotModel pilot, Model model) throws ParseException {
		FlightModel flight = new FlightModel();
		flight.setTime(flightService.getTodayDefaultFlightDate());
		pilot.getPilotFlight().add(flight);
		
		model.addAttribute("pilot", pilot);
		model.addAttribute("licenseNumber", licenseNumber);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params={"removeRow"})
	public String removeRow(@PathVariable(value = "licenseNumber") String licenseNumber,
						@ModelAttribute PilotModel pilot, Model model,
						final BindingResult bindingResult, final HttpServletRequest req) {
		PilotModel pilotOld = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		final Integer intId = Integer.valueOf(req.getParameter("removeRow"));
		
		pilot.getPilotFlight().get(intId.intValue()).setPilot(pilotOld);
		pilot.getPilotFlight().remove(intId.intValue());
		
		model.addAttribute("licenseNumber", licenseNumber);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
	private String addFlightSubmit(@PathVariable(value = "licenseNumber") String licenseNumber,
						@ModelAttribute PilotModel pilot, Model model) {
		PilotModel pilotOld = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		for (FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(pilotOld);
			flightService.addFlight(flight);
		}
		
		model.addAttribute("licenseNumber", licenseNumber);
		return "add";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable("id") String id, Model model) {
		FlightModel flight = flightService.getFlightDetailById(id);
		model.addAttribute("flight", flight);
		return "update-flight";
	}
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	public String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			FlightModel flightDel = flightService.getFlightDetailById(Long.toString(flight.getId()));
			
			flightService.deleteFlight(flightDel);
		}
		
		return "delete";
	}
	
	@RequestMapping(value = "/flight/update", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.updateFlight(flight, Long.toString(flight.getId()));
		return "update";
	}
	
	@RequestMapping(value = "/flight/view")
	public String viewFlightNumber(@RequestParam("flightNumber") String flightNumber, Model model) {
		List<FlightModel> flight = flightService.getFlightByFlightNumber(flightNumber);
		
		model.addAttribute("flight", flight);
		return "view-flight";
	}
}
