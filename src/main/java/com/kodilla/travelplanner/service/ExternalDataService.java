package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.repository.AiContentRepository;
import com.kodilla.travelplanner.repository.CurrencyRepository;
import com.kodilla.travelplanner.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExternalDataService {

    private final CurrencyService currencyService;
    private final WeatherService weatherService;
    private final AiContentService aiContentService;
    private final CurrencyRepository currencyRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final AiContentRepository aiContentRepository;
}
