package com.kodilla.travelplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record GeoDetailsDto(
        double geoLatitude,
        double geoLongitude,
        String currencyCode
) {}
