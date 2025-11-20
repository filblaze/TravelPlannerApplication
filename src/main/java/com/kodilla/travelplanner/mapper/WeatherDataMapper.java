package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.WeatherData;
import com.kodilla.travelplanner.dto.WeatherResponseDto;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataMapper {

    public WeatherResponseDto toWeatherResponseDto(WeatherData weatherData) {
        return WeatherResponseDto.builder()
                .id(weatherData.getId())
                .forecastDate(weatherData.getForecastDate())
                .avgTemperatureC(weatherData.getAvgTemperatureC())
                .precipitationChance(weatherData.getPrecipitationChance())
                .windSpeedMps(weatherData.getWindSpeedMps())
                .generalConditions(weatherData.getGeneralConditions())
                .lastFetchedAt(weatherData.getLastFetchedAt())
                .build();
    }
}
