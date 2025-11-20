package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.domain.Note;
import com.kodilla.travelplanner.domain.TripPlan;
import com.kodilla.travelplanner.dto.NoteRequestDto;
import com.kodilla.travelplanner.dto.NoteResponseDto;
import com.kodilla.travelplanner.dto.NoteUpdateDto;
import com.kodilla.travelplanner.mapper.NoteMapper;
import com.kodilla.travelplanner.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final TripPlanService tripPlanService;

    @Transactional
    public NoteResponseDto createNote(Long tripPlanId, NoteRequestDto noteRequestDto) {
        TripPlan tripPlan = tripPlanService.getTripPlanById(tripPlanId);
        Note note = noteMapper.toNote(noteRequestDto);
        note.setTripPlan(tripPlan);
        Note savedNote = noteRepository.save(note);
        return noteMapper.toNoteResponseDto(savedNote);
    }

    @Transactional
    public NoteResponseDto updateNote(Long noteId, NoteUpdateDto noteUpdateDto) {
        Note updatedNote = noteRepository.save(noteMapper.updateNoteFromDto(noteUpdateDto, getNoteById(noteId)));
        return noteMapper.toNoteResponseDto(updatedNote);
    }

    @Transactional
    public List<NoteResponseDto> getNoteListByTripPlanId(Long tripPlanId) {
        return noteRepository.findAllByTripPlanId(tripPlanId).stream()
                .map(noteMapper::toNoteResponseDto)
                .toList();
    }

    @Transactional
    public void deleteNote(Long noteId) {
        if(!noteRepository.existsById(noteId)) {
            throw new EntityNotFoundException("Nie ma takiej notatki!");
        }
        noteRepository.deleteById(noteId);
    }

    @Transactional(readOnly = true)
    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Nie ma takiej notatki!"));
    }
}
