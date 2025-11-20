package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.clients.OpenCageClient;
import com.kodilla.travelplanner.dto.GeoDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoDetailsService {

    private final OpenCageClient openCageClient;

    public GeoDetailsDto fetchGeoDetails(String destinationName, String destinationCountry) {
        if(destinationName == null || destinationName.trim().isEmpty() || destinationCountry == null || destinationCountry.trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwa destynacji oraz kraj w którym się znajduje są wymagane do geolokacji!");
        }
        return openCageClient.fetchGeocodingData(destinationName, destinationCountry);
    }
}