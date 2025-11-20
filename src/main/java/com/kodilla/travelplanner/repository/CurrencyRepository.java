package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.CurrencyData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<CurrencyData, Long> {

    List<CurrencyData> findAllByTripDetailsId(Long tripDetailsId);
    List<CurrencyData> findAllByTripDetailsIdAndExchangeRateDate(Long tripDetailsId, LocalDate exchangeRateDate);

}
