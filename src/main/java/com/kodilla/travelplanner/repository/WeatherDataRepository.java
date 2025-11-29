package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    List<WeatherData> findAllByTripDetailsId(Long tripDetailsId);
    List<WeatherData> findByTripDetailsIdAndForecastDate(Long tripDetailsId, LocalDate forecastDate);
    List<WeatherData> findAllByTripDetailsIdAndLastFetchedAt(Long tripDetailsId, LocalDateTime lastFetchedAt);
    @Query("SELECT MAX(w.lastFetchedAt) FROM WeatherData w WHERE w.tripDetails.id = :tripDetailsId")
    Optional<LocalDateTime> findLatestFetchTime(@Param("tripDetailsId") Long tripDetailsId);
}
