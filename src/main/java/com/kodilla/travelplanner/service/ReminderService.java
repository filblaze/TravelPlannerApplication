package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.domain.Note;
import com.kodilla.travelplanner.domain.Reminder;
import com.kodilla.travelplanner.domain.TripPlan;
import com.kodilla.travelplanner.dto.ReminderRequestDto;
import com.kodilla.travelplanner.dto.ReminderResponseDto;
import com.kodilla.travelplanner.dto.ReminderUpdateDto;
import com.kodilla.travelplanner.mapper.ReminderMapper;
import com.kodilla.travelplanner.repository.ReminderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;
    private final TripPlanService tripPlanService;

    @Transactional
    public ReminderResponseDto createReminder(Long tripPlanId, ReminderRequestDto reminderRequestDto) {
        TripPlan tripPlan = tripPlanService.getTripPlanById(tripPlanId);
        Reminder reminder = reminderMapper.toReminder(reminderRequestDto);
        reminder.setTripPlan(tripPlan);
        Reminder savedReminder = reminderRepository.save(reminder);
        return reminderMapper.toReminderResponseDto(savedReminder);
    }

    @Transactional
    public ReminderResponseDto updateReminder(Long reminderId, ReminderUpdateDto reminderUpdateDto) {
        Reminder updatedReminder = reminderRepository.save(reminderMapper.updateReminderFromDto(reminderUpdateDto, getReminderById(reminderId)));
        return reminderMapper.toReminderResponseDto(updatedReminder);
    }

    @Transactional
    public void deleteReminder(Long reminderId) {
        if(!reminderRepository.existsById(reminderId)) {
            throw new EntityNotFoundException("Nie odnaleziono taakie przypomnienia!");
        }
        reminderRepository.deleteById(reminderId);
    }

    @Transactional
    public void markReminderAsComplete(Long reminderId) {
        Reminder reminder = getReminderById(reminderId);
        reminder.setCompleted(true);
        reminder.setSet(false);
        reminderRepository.save(reminder);
    }

    @Transactional(readOnly = true)
    public Reminder getReminderById(Long reminderId) {
        return reminderRepository.findById(reminderId).
                orElseThrow(() -> new EntityNotFoundException("Nie odnaleziono takiego przypomnienia!"));
    }

    @Transactional(readOnly = true)
    public List<Reminder> getDueReminders() {
        return reminderRepository.findAllByIsSetTrueAndIsCompletedFalseAndReminderTimeBefore(LocalDateTime.now());
    }

}
