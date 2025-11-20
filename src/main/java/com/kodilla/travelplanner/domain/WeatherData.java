package com.kodilla.travelplanner.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="weather_data")
@NoArgsConstructor
@Data
public class WeatherData {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDate forecastDate;

    @Column
    private double avgTemperatureC;

    @Column
    private double precipitationChance;

    @Column
    private double windSpeedMps;

    @Column
    private String generalConditions;

    @Column
    private LocalDateTime lastFetchedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_details_id")
    private TripDetails tripDetails;
}
