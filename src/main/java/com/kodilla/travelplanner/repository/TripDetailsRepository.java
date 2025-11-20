package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.TripDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripDetailsRepository extends JpaRepository<TripDetails, Long> {

    TripDetails findByTripPlanId(Long tripPlanId);
}
