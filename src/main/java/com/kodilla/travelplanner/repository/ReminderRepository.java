package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findAllByIsSetTrueAndIsCompletedFalseAndReminderTimeBefore(java.time.LocalDateTime currentTime);
    List<Reminder> findAllByTripPlanId(Long tripPlanId);
    List<Reminder> findByTitleContainingIgnoreCase(String title);
}
