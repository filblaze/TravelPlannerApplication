package com.kodilla.travelplanner.clients;

import com.kodilla.travelplanner.dto.CurrencyApiDataDto;

public interface CurrencyClient {
    CurrencyApiDataDto getExchangeRate(String currencyCode);
}
