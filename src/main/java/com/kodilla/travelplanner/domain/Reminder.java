package com.kodilla.travelplanner.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="reminders")
@NoArgsConstructor
@Data
public class Reminder {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime reminderTime;

    private boolean isSet;

    private boolean isCompleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_plan_id")
    private TripPlan tripPlan;
}
