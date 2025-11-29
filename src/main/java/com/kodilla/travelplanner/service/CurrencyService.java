package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.clients.CurrencyClient;
import com.kodilla.travelplanner.domain.CurrencyData;
import com.kodilla.travelplanner.domain.TripDetails;
import com.kodilla.travelplanner.dto.CurrencyApiDataDto;
import com.kodilla.travelplanner.dto.CurrencyResponseDto;
import com.kodilla.travelplanner.mapper.CurrencyDataMapper;
import com.kodilla.travelplanner.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyDataMapper currencyDataMapper;
    private final CurrencyClient currencyClient;

    @Transactional
    public CurrencyResponseDto fetchCurrencyData(TripDetails tripDetails) {
        String currencyCode = tripDetails.getCurrencyCode();
        if("PLN".equalsIgnoreCase(currencyCode) || currencyCode == null) {
            return null;
        }

        CurrencyApiDataDto apiDataDto = currencyClient.getExchangeRate(currencyCode);
        CurrencyData currencyData = new CurrencyData();
        currencyData.setCurrencyName(apiDataDto.getCurrencyName());
        currencyData.setExchangeRateDate(apiDataDto.getExchangeRateDate());
        currencyData.setExchangeRateToPln(apiDataDto.getExchangeRateToPln());
        currencyData.setLastFetchTime(LocalDateTime.now());
        currencyData.setTripDetails(tripDetails);

        CurrencyData savedData = currencyRepository.save(currencyData);

        return currencyDataMapper.toCurrencyResponseDto(savedData);
    }

    @Transactional(readOnly = true)
    public CurrencyResponseDto getLatestCurrencyRate(Long tripDetailsId) {
        LocalDateTime lastFetchTime = currencyRepository.findLatestFetchTime(tripDetailsId)
                .orElseThrow(() -> new RuntimeException("Brak zapisanych kursów dla tego wyjazdu."));
        CurrencyData latestData = currencyRepository.findByTripDetailsIdAndLastFetchTime(tripDetailsId, lastFetchTime);

        if(latestData == null) {
            throw new RuntimeException("Błąd danych: nie znaleziono ostatniego rekordu.");
        }

        return currencyDataMapper.toCurrencyResponseDto(latestData);
    }

    @Transactional(readOnly = true)
    public List<CurrencyResponseDto> getAllCurrencyData(Long tripDetailsId) {

        return currencyRepository.findAllByTripDetailsId(tripDetailsId).stream()
                .sorted((a, b) -> b.getLastFetchTime().compareTo(a.getLastFetchTime()))
                .map(currencyDataMapper::toCurrencyResponseDto)
                .toList();
    }
}
