package com.kodilla.travelplanner.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="trip_plans")
@NoArgsConstructor
@Data
public class TripPlan {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String destinationName;

    @Column(nullable = false)
    private String destinationCountry;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="tripPlan",cascade = CascadeType.ALL)
    private List<Note> notes;

    @OneToOne(mappedBy="tripPlan", cascade = CascadeType.ALL)
    private TripDetails details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "tripPlan", cascade = CascadeType.ALL)
    private List<Reminder> reminders;

    public TripPlan(String destinationName, String destinationCountry, LocalDate startDate, LocalDate endDate) {
        this.destinationName = destinationName;
        this.destinationCountry = destinationCountry;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = new ArrayList<>();
        this.reminders = new ArrayList<>();
    }
}
