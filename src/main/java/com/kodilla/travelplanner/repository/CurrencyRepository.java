package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.CurrencyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<CurrencyData, Long> {

    List<CurrencyData> findAllByTripDetailsId(Long tripDetailsId);

    List<CurrencyData> findAllByTripDetailsIdAndExchangeRateDate(Long tripDetailsId, LocalDate exchangeRateDate);

    CurrencyData findByTripDetailsIdAndLastFetchTime(Long tripDetailsId, LocalDateTime lastFetchTime);

    @Query("SELECT MAX(c.lastFetchTime) FROM CurrencyData c WHERE c.tripDetails.id = :tripDetailsId")
    Optional<LocalDateTime> findLatestFetchTime(@Param("tripDetailsId") Long tripDetailsId);
}