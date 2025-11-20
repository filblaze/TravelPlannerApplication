package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.TripPlan;
import com.kodilla.travelplanner.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripPlanMapper {

    private final NoteMapper noteMapper;
    private final ReminderMapper reminderMapper;
    private final TripDetailsMapper tripDetailsMapper;

    public TripPlan toTripPlan(TripPlanRequestDto tripPlanRequestDto) {
        return new TripPlan(
                tripPlanRequestDto.getDestinationName(),
                tripPlanRequestDto.getDestinationCountry(),
                tripPlanRequestDto.getStartDate(),
                tripPlanRequestDto.getEndDate()
        );
    }

    public TripPlanResponseDto toTripPlanResponseDto(TripPlan tripPlan) {
        return TripPlanResponseDto.builder()
                .id(tripPlan.getId())
                .destinationName(tripPlan.getDestinationName())
                .destinationCountry(tripPlan.getDestinationCountry())
                .startDate(tripPlan.getStartDate())
                .endDate(tripPlan.getEndDate())
                .createdAt(tripPlan.getCreatedAt())
                .build();
    }

    public TripPlanDetailedResponseDto toTripPlanDetailedResponseDto(TripPlan tripPlan) {
        return TripPlanDetailedResponseDto.builder()
                .id(tripPlan.getId())
                .destinationName(tripPlan.getDestinationName())
                .destinationCountry(tripPlan.getDestinationCountry())
                .startDate(tripPlan.getStartDate())
                .endDate(tripPlan.getEndDate())
                .createdAt(tripPlan.getCreatedAt())
                .details(tripDetailsMapper.toTripDetailsResponseDto(tripPlan.getDetails()))
                .notes(tripPlan.getNotes().stream()
                        .map(noteMapper::toNoteResponseDto)
                        .toList())
                .reminders(tripPlan.getReminders().stream()
                        .map(reminderMapper::toReminderResponseDto)
                        .toList())
                .build();
    }

    public TripPlan updatedTripPlanFromDto(TripPlanUpdateDto tripPlanUpdateDto, TripPlan tripPlan) {
        if(tripPlanUpdateDto.getStartDate() != null) {
            tripPlan.setStartDate(tripPlanUpdateDto.getStartDate());
        }
        if(tripPlanUpdateDto.getEndDate() != null) {
            tripPlan.setEndDate(tripPlanUpdateDto.getEndDate());
        }
        return tripPlan;
    }
}
