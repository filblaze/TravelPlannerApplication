package com.kodilla.travelplanner.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodilla.travelplanner.dto.CurrencyApiDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Component
public class NbpClient implements CurrencyClient{

    private final WebClient webClient;

    public NbpClient(WebClient.Builder webClientBuilder, @Value("${nbp.base.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public CurrencyApiDataDto getExchangeRate(String currencyCode) {
        String url = String.format("/api/exchangerates/rates/A/%s/?format=json", currencyCode.toLowerCase());

        NbpRateResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(NbpRateResponse.class)
                .block();

        if(response == null || response.rates().isEmpty()) {
            throw new RuntimeException("Nie udało się pobrać kursu dla waluty: " + currencyCode);
        }

        Rate rate = response.rates().get(0);

        return CurrencyApiDataDto.builder()
                .currencyCode(response.code())
                .currencyName(response.currency())
                .exchangeRateDate(rate.effectiveDate())
                .exchangeRateToPln(rate.mid())
                .build();
    }

    public record NbpRateResponse(String table, String currency, String code, List<Rate> rates) {}
    public record Rate(@JsonProperty("effectiveDate")LocalDate effectiveDate, @JsonProperty("mid") Double mid) {}
}
