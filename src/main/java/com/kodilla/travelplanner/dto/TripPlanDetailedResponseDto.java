package com.kodilla.travelplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripPlanDetailedResponseDto {

    private Long id;
    private String destinationName;
    private String destinationCountry;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private TripDetailsResponseDto details;
    private List<NoteResponseDto> notes;
    private List<ReminderResponseDto> reminders;
}
