package com.kodilla.travelplanner.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodilla.travelplanner.dto.GeoDetailsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class OpenCageClient implements GeoDataClient{

    private final WebClient webClient;
    private final String apiKey;
    private final String baseUrlString;

    public OpenCageClient(WebClient.Builder webClientBuilder,
                          @Value("${opencage.base.url}") String baseUrl,
                          @Value("${opencage.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
        this.baseUrlString = baseUrl;
    }

    @Override
    public GeoDetailsDto fetchGeocodingData(String destinationName, String destinationCountry) {
        URI url = buildOpenCageUrl(destinationName,destinationCountry);

        OpenCageResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(OpenCageResponse.class)
                .block();
        if(response == null || response.results().isEmpty()) {
            throw new RuntimeException("Nie można znaleźć informacji geograficznych dla wskazanej lokalizacji!");
        }
        OpenCageResult result = response.results().get(0);
        double lat = result.geometry().lat();
        double lon = result.geometry().lng();
        String currencyCode = result.annotations().currency().isoCode();

        return new GeoDetailsDto(lat, lon, currencyCode);
    }

    private URI buildOpenCageUrl(String destinationName, String destinationCountry) {
        return UriComponentsBuilder.fromUriString(baseUrlString)
                .queryParam("key", apiKey)
                .queryParam("q", destinationName + ", " + destinationCountry)
                .queryParam("limit", 1)
                .build()
                .toUri();
    }


    public record OpenCageResponse(List<OpenCageResult> results) {}
    public record OpenCageResult(Geometry geometry, Annotations annotations) {}
    public record Geometry(double lat, double lng) {}
    public record Annotations(Currency currency) {}
    public record Currency(@JsonProperty("iso_code") String isoCode) {}
}
