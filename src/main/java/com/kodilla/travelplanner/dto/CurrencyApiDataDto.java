package com.kodilla.travelplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyApiDataDto {

    private String currencyCode;
    private String currencyName;
    private LocalDate exchangeRateDate;
    private Double exchangeRateToPln;
}
