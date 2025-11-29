package com.kodilla.travelplanner.clients;

import com.kodilla.travelplanner.dto.WeatherApiDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Component
public class OpenWeatherMapClient implements WeatherClient{

    private final WebClient webClient;
    private final String apiKey;
    private final String baseUrlString;

    public OpenWeatherMapClient(WebClient.Builder webClientBuilder,
                                @Value("${openweathermap.base.url}") String baseUrl,
                                @Value("${openweathermap.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
        this.baseUrlString = baseUrl;
    }

    @Override
    public List<WeatherApiDataDto> getWeatherForecast(double latitude, double longitude, LocalDate startDate, LocalDate endDate) {
        URI url = buildForecastUrl(latitude,longitude);

        OneCallResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(OneCallResponse.class)
                .block();

        if(response == null || response.dailyForecast() == null) {
            throw new RuntimeException("Nie udało się pobrać prognozy pogody!");
        }

        return response.dailyForecast().stream()
                .map(dailyForecast -> {
                    long epochDay = dailyForecast.dt/86400;
                    LocalDate forecastDate = LocalDate.ofEpochDay(epochDay);
                    return new SimpleForecastDay(forecastDate, dailyForecast);
                })
                .filter(simpleForecastDay -> {
                    LocalDate forecastDate = simpleForecastDay.forecastDate;
                    return !forecastDate.isBefore(startDate) && !forecastDate.isAfter(endDate);
                })
                .map(simpleForecastDay -> WeatherApiDataDto.builder()
                        .forecastDate(simpleForecastDay.forecastDate)
                        .avgTemperatureC(simpleForecastDay.dayData.temp.day)
                        .precipitationChance(simpleForecastDay.dayData.pop*100)
                        .windSpeedMps(simpleForecastDay.dayData.wind_speed)
                        .generalConditions(simpleForecastDay.dayData.weather.isEmpty() ? "Nie udało się pobrać opisu warunków!" : simpleForecastDay.dayData.weather.get(0).description)
                        .build())
                .toList();
    }

    private URI buildForecastUrl(double latitude, double longitude) {
        return UriComponentsBuilder.fromUriString(baseUrlString)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("exclude", "current,minutely,hourly,alerts")
                .queryParam("lang", "pl")
                .build()
                .toUri();
    }

    public record OneCallResponse(List<DailyForecast> dailyForecast) {}
    public record DailyForecast(long dt, Temp temp, double wind_speed, double pop, List<Weather> weather) {}
    public record Temp(double day) {}
    public record Weather(String description) {}
    public record SimpleForecastDay(LocalDate forecastDate, DailyForecast dayData) {}
}
