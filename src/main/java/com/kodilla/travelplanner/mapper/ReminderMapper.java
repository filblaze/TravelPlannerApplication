package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.Reminder;
import com.kodilla.travelplanner.dto.ReminderRequestDto;
import com.kodilla.travelplanner.dto.ReminderResponseDto;
import com.kodilla.travelplanner.dto.ReminderUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderMapper {

    public Reminder toReminder(ReminderRequestDto reminderRequestDto) {
        Reminder reminder = new Reminder();
        reminder.setTitle(reminderRequestDto.getTitle());
        reminder.setContent(reminderRequestDto.getContent());
        reminder.setReminderTime(reminderRequestDto.getReminderTime());
        reminder.setSet(true);
        reminder.setCompleted(false);
        return reminder;
    }

    public Reminder updateReminderFromDto(ReminderUpdateDto reminderUpdateDto, Reminder reminder) {
        if(reminderUpdateDto.getTitle() != null) {
            reminder.setTitle(reminderUpdateDto.getTitle());
        }
        if(reminderUpdateDto.getContent() != null) {
            reminder.setContent(reminderUpdateDto.getContent());
        }
        if(reminderUpdateDto.getIsSet() != null) {
            reminder.setSet(reminderUpdateDto.getIsSet());
        }
        if(reminderUpdateDto.getIsCompleted() != null) {
            reminder.setCompleted(reminderUpdateDto.getIsCompleted());
        }
        return reminder;
    }

    public ReminderResponseDto toReminderResponseDto(Reminder reminder) {
        return ReminderResponseDto.builder()
                .id(reminder.getId())
                .title(reminder.getTitle())
                .content(reminder.getContent())
                .reminderTime(reminder.getReminderTime())
                .isSet(reminder.isSet())
                .isCompleted(reminder.isCompleted())
                .tripPlanId(reminder.getTripPlan().getId())
                .build();
    }
}
