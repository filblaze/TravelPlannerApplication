package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.aicontent.AiContentType;
import com.kodilla.travelplanner.domain.TripDetails;
import com.kodilla.travelplanner.domain.TripPlan;
import com.kodilla.travelplanner.dto.AiGeneratedContentResponseDto;
import com.kodilla.travelplanner.dto.CurrencyResponseDto;
import com.kodilla.travelplanner.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalDataService {

    private final CurrencyService currencyService;
    private final WeatherService weatherService;
    private final AiContentService aiContentService;
    private final TripPlanService tripPlanService;

    @Transactional
    public void fetchAllExternalData(TripPlan tripPlan) {
        TripDetails tripDetails = tripPlan.getDetails();
        weatherService.fetchWeatherData(tripDetails);
        currencyService.fetchCurrencyData(tripDetails);
        aiContentService.generateDestinationAbstract(tripPlan);
    }

    @Transactional
    public List<WeatherResponseDto> refreshWeatherForecast(Long tripPlanId) {
        TripPlan tripPlan = tripPlanService .getTripPlanById(tripPlanId);
        TripDetails tripDetails = tripPlan.getDetails();
        return weatherService.fetchWeatherData(tripDetails);
    }

    @Transactional
    public CurrencyResponseDto refreshCurrencyRate(Long tripPlanId) {
        TripPlan tripPlan = tripPlanService.getTripPlanById(tripPlanId);
        TripDetails tripDetails = tripPlan.getDetails();
        CurrencyResponseDto fetchedData = currencyService.fetchCurrencyData(tripDetails);
        if(fetchedData == null) {
            throw new IllegalArgumentException("Nie można odświeżyć kursu waluty!");
        }
        return fetchedData;
    }

    public List<WeatherResponseDto> getLatestWeatherForecast(Long tripDetailsId) {
        return weatherService.getMostRecentForecast(tripDetailsId);
    }

    public CurrencyResponseDto getLatestCurrencyRate(Long tripDetailsId) {
        return currencyService.getLatestCurrencyRate(tripDetailsId);
    }

    public List<AiGeneratedContentResponseDto> getAiContent(Long tripDetailsId, String contentType) {
        return aiContentService.getAiContentByType(tripDetailsId, contentType);
    }

    @Transactional
    public AiGeneratedContentResponseDto generateSpecificAiContent(TripPlan tripPlan, AiContentType contentType) {
        return aiContentService.generateContent(tripPlan, contentType);
    }
}
