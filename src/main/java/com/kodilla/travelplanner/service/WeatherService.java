package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.clients.WeatherClient;
import com.kodilla.travelplanner.domain.TripDetails;
import com.kodilla.travelplanner.domain.WeatherData;
import com.kodilla.travelplanner.dto.WeatherApiDataDto;
import com.kodilla.travelplanner.dto.WeatherResponseDto;
import com.kodilla.travelplanner.mapper.WeatherDataMapper;
import com.kodilla.travelplanner.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherDataRepository weatherDataRepository;
    private final WeatherClient weatherClient;
    private final WeatherDataMapper weatherDataMapper;

    @Transactional
    public List<WeatherResponseDto> fetchWeatherData(TripDetails tripDetails) {
        List<WeatherApiDataDto> apiForecast = weatherClient.getWeatherForecast(
                tripDetails.getGeoLatitude(),
                tripDetails.getGeoLongitude(),
                tripDetails.getTripPlan().getStartDate(),
                tripDetails.getTripPlan().getEndDate()
        );

        final LocalDateTime fetchAtTimestamp = LocalDateTime.now();

        List<WeatherData> weatherDataList = apiForecast.stream()
                .map(dto -> {
                    WeatherData data = new WeatherData();
                    data.setForecastDate(dto.getForecastDate());
                    data.setAvgTemperatureC(dto.getAvgTemperatureC());
                    data.setPrecipitationChance(dto.getPrecipitationChance());
                    data.setWindSpeedMps(dto.getWindSpeedMps());
                    data.setGeneralConditions(dto.getGeneralConditions());
                    data.setLastFetchedAt(fetchAtTimestamp);
                    data.setTripDetails(tripDetails);
                    return data;
                })
                .toList();

        List<WeatherData> savedData = weatherDataRepository.saveAll(weatherDataList);

        return savedData.stream()
                .map(weatherDataMapper::toWeatherResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<WeatherResponseDto> getMostRecentForecast(Long tripDetailsId) {

        LocalDateTime latestFetchTime = weatherDataRepository.findLatestFetchTime(tripDetailsId)
                .orElseThrow(() -> new RuntimeException("Brak zapisanych prognoz dla tego wyjazdu."));

        return weatherDataRepository
                .findAllByTripDetailsIdAndLastFetchedAt(tripDetailsId, latestFetchTime).stream()
                .map(weatherDataMapper::toWeatherResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<WeatherResponseDto> getForecastHistory(Long tripDetailsId) {
        return weatherDataRepository.findAllByTripDetailsId(tripDetailsId).stream()
                .sorted((a, b) -> b.getLastFetchedAt().compareTo(a.getLastFetchedAt()))
                .map(weatherDataMapper::toWeatherResponseDto)
                .toList();
    }
}
