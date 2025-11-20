package com.kodilla.travelplanner.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="trip_details")
@Data
@NoArgsConstructor
public class TripDetails {

    @Id
    @GeneratedValue
    private Long id;

    private double geoLatitude;
    private double geoLongitude;

    @Column(length=3)
    private String currencyCode;

    @Column(length=1024)
    private String travelDestinationAbstract;

    @OneToOne
    @JoinColumn(name="trip_plan_id")
    @MapsId
    private TripPlan tripPlan;

    @OneToMany(mappedBy="tripDetails", cascade = CascadeType.ALL)
    private List<CurrencyData> currencyDataList;

    @OneToMany(mappedBy="tripDetails", cascade = CascadeType.ALL)
    private List<WeatherData> weatherDataList;

    @OneToMany(mappedBy="tripDetails", cascade = CascadeType.ALL)
    private List<AiGeneratedContent> aiGeneratedContentList;
}
