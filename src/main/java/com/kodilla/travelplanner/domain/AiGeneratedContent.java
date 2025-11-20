package com.kodilla.travelplanner.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="ai_generated_content")
@NoArgsConstructor
@Data
public class AiGeneratedContent {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String contentType;

    @Column(length = 2048)
    private String generatedContent;

    private LocalDateTime lastGenerationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_details_id")
    private TripDetails tripDetails;
}
