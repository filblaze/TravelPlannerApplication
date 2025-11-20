package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.Note;
import com.kodilla.travelplanner.dto.NoteRequestDto;
import com.kodilla.travelplanner.dto.NoteResponseDto;
import com.kodilla.travelplanner.dto.NoteUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoteMapper {

    public Note toNote(NoteRequestDto noteRequestDto) {
        Note note = new Note();
        note.setTitle(noteRequestDto.getTitle());
        note.setContent(noteRequestDto.getContent());
        return note;
    }

    public NoteResponseDto toNoteResponseDto(Note note) {
        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }

    public Note updateNoteFromDto(NoteUpdateDto noteUpdateDto, Note note) {
        if(noteUpdateDto.getTitle() != null) {
            note.setTitle(noteUpdateDto.getTitle());
        }
        if(noteUpdateDto.getContent() != null) {
            note.setContent(noteUpdateDto.getContent());
        }
        return note;
    }
}
