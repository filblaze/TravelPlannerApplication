package com.kodilla.travelplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherApiDataDto {

    private LocalDate forecastDate;
    private double avgTemperatureC;
    private double precipitationChance;
    private double windSpeedMps;
    String generalConditions;
}
