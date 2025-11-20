package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.TripPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TripPlanRepository extends JpaRepository<TripPlan, Long> {

    List<TripPlan> findAllByUserId(Long userId);
    List<TripPlan> findByDestinationNameContainingIgnoreCase(String destinationName);
    List<TripPlan> findByStartDateBetween(LocalDate from, LocalDate to);
}
