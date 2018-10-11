package com.apap.tutorial5.service;

import com.apap.tutorial5.model.PilotModel;

/**
 * PilotService
 */
public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	void addPilot(PilotModel pilot);
	void deletePilot(PilotModel pilot);
	void updatePilot(PilotModel newPilot, String licenseNumber);
}