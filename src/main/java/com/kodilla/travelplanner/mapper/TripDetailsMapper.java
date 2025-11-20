package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.TripDetails;
import com.kodilla.travelplanner.dto.TripDetailsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripDetailsMapper {

    private final WeatherDataMapper weatherDataMapper;
    private final CurrencyDataMapper currencyDataMapper;
    private final AiGeneratedContentMapper aiGeneratedContentMapper;

    public TripDetailsResponseDto toTripDetailsResponseDto(TripDetails tripDetails) {
        return TripDetailsResponseDto.builder()
                .id(tripDetails.getId())
                .geoLatitude(tripDetails.getGeoLatitude())
                .geoLongitude(tripDetails.getGeoLongitude())
                .currencyCode(tripDetails.getCurrencyCode())
                .travelDestinationAbstract(tripDetails.getTravelDestinationAbstract())
                .weatherDataList(tripDetails.getWeatherDataList().stream()
                        .map(weatherDataMapper::toWeatherResponseDto)
                        .toList())
                .currencyDataList(tripDetails.getCurrencyDataList().stream()
                        .map(currencyDataMapper::toCurrencyResponseDto)
                        .toList())
                .aiGeneratedContentList(tripDetails.getAiGeneratedContentList().stream()
                        .map(aiGeneratedContentMapper::toAiGeneratedContentDto)
                        .toList())
                .build();
    }
}
