package com.kodilla.travelplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDetailsResponseDto {

    private Long id;
    private double geoLatitude;
    private double geoLongitude;
    private String currencyCode;
    private String travelDestinationAbstract;
    private List<WeatherResponseDto> weatherDataList;
    private List<CurrencyResponseDto> currencyDataList;
    private List<AiGeneratedContentResponseDto> aiGeneratedContentList;
}
