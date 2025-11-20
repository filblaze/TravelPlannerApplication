package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.CurrencyData;
import com.kodilla.travelplanner.dto.CurrencyResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CurrencyDataMapper {

    public CurrencyResponseDto toCurrencyResponseDto(CurrencyData currencyData) {
        return CurrencyResponseDto.builder()
                .id(currencyData.getId())
                .currencyName(currencyData.getCurrencyName())
                .exchangeRateDate(currencyData.getExchangeRateDate())
                .exchangeRateToPln(currencyData.getExchangeRateToPln())
                .lastFetchTime(currencyData.getLastFetchTime())
                .build();
    }
}
