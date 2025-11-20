package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.domain.TripDetails;
import com.kodilla.travelplanner.domain.TripPlan;
import com.kodilla.travelplanner.domain.User;
import com.kodilla.travelplanner.dto.*;
import com.kodilla.travelplanner.mapper.TripPlanMapper;
import com.kodilla.travelplanner.repository.TripPlanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    private final TripPlanRepository tripPlanRepository;
    private final TripPlanMapper tripPlanMapper;
    private final GeoDetailsService geoDetailsService;
    private final UserService userService;

    @Transactional
    public TripPlanResponseDto createTripPlan(Long userId, TripPlanRequestDto tripPlanRequestDto) {
        GeoDetailsDto geoDetails = geoDetailsService.fetchGeoDetails(tripPlanRequestDto.getDestinationName(), tripPlanRequestDto.getDestinationCountry());
        User user = userService.getUserById(userId);
        TripPlan tripPlan = tripPlanMapper.toTripPlan(tripPlanRequestDto);
        tripPlan.setUser(user);
        TripDetails tripDetails = new TripDetails();
        tripDetails.setTripPlan(tripPlan);
        tripDetails.setGeoLatitude(geoDetails.geoLatitude());
        tripDetails.setGeoLongitude(geoDetails.geoLongitude());
        tripDetails.setCurrencyCode(geoDetails.currencyCode());
        tripPlan.setDetails(tripDetails);
        TripPlan savedTripPlan = tripPlanRepository.save(tripPlan);
        return tripPlanMapper.toTripPlanResponseDto(savedTripPlan);
    }

    @Transactional
    public void deleteTripPlan(Long tripPlanId) {
        if(!tripPlanRepository.existsById(tripPlanId)) {
            throw new EntityNotFoundException("Nie ma takiego planu!");
        }
        tripPlanRepository.deleteById(tripPlanId);
    }

    @Transactional
    public TripPlanResponseDto  updateTripPlan(Long tripPlanId, TripPlanUpdateDto tripPlanUpdateDto) {
        TripPlan updatedTripPlan = tripPlanRepository.save(tripPlanMapper.updatedTripPlanFromDto(tripPlanUpdateDto, getTripPlanById(tripPlanId)));
        return tripPlanMapper.toTripPlanResponseDto(updatedTripPlan);
    }

    @Transactional(readOnly = true)
    public TripPlan getTripPlanById(Long tripPlanId) {
        return tripPlanRepository.findById(tripPlanId)
                .orElseThrow(() -> new EntityNotFoundException("Nie ma takiego planu!"));
    }

    @Transactional(readOnly = true)
    public TripPlanResponseDto getTripPlanDtoById(Long tripPlanId) {
        return tripPlanMapper.toTripPlanResponseDto(getTripPlanById(tripPlanId));
    }

    @Transactional(readOnly = true)
    public TripPlanDetailedResponseDto getDetailedTripPlanDtoById(Long tripPlanId) {
        return tripPlanMapper.toTripPlanDetailedResponseDto(getTripPlanById(tripPlanId));
    }
}
