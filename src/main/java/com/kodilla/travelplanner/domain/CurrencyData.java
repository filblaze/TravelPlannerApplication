package com.kodilla.travelplanner.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="currency_data")
@Data
@NoArgsConstructor
public class CurrencyData {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String currencyName;

    @Column
    private LocalDate exchangeRateDate;

    @Column
    private Double exchangeRateToPln;

    @Column
    private LocalDateTime lastFetchTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_details_id")
    private TripDetails tripDetails;
}
