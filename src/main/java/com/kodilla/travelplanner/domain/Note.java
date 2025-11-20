package com.kodilla.travelplanner.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "notes")
@Data
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_plan_id", nullable = false)
    private TripPlan tripPlan;
}
