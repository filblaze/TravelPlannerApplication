package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    List<WeatherData> findAllByTripDetailsId(Long tripDetailsId);
    List<WeatherData> findByTripDetailsIdAndForecastDate(Long tripDetailsId, LocalDate forecastDate);
}
