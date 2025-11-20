package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByTripPlanId(Long tripPlanId);
    List<Note> findByTitleContainingIgnoreCase(String title);
}
