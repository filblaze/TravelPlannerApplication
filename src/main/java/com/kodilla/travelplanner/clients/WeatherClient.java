package com.kodilla.travelplanner.clients;

import com.kodilla.travelplanner.dto.WeatherApiDataDto;

import java.time.LocalDate;
import java.util.List;

public interface WeatherClient {
    List<WeatherApiDataDto> getWeatherForecast(double latitude, double longitude, LocalDate startDate, LocalDate endDate);
}
