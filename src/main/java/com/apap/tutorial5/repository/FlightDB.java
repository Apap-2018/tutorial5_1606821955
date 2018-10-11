package com.apap.tutorial5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tutorial5.model.FlightModel;

/**
 * FlightDB
 */
@Repository
public interface FlightDB extends JpaRepository<FlightModel, Long>{
	FlightModel findById(long id);
}
