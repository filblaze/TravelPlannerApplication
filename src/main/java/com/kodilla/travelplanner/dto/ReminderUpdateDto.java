package com.kodilla.travelplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderUpdateDto {

    private String title;
    private String content;
    private LocalDateTime reminderTime;
    private Boolean isSet;
    private Boolean isCompleted;
}
