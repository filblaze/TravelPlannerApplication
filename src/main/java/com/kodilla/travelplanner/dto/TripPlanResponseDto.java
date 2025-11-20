package com.kodilla.travelplanner.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripPlanResponseDto{

    private Long id;
    private String destinationName;
    private String destinationCountry;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
}
