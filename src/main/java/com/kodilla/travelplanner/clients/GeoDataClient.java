package com.kodilla.travelplanner.clients;

import com.kodilla.travelplanner.dto.GeoDetailsDto;

public interface GeoDataClient {
    GeoDetailsDto fetchGeocodingData(String destinationName, String destinationCountry);
}
