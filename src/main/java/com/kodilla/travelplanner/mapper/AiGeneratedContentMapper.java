package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.AiGeneratedContent;
import com.kodilla.travelplanner.dto.AiGeneratedContentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AiGeneratedContentMapper {

    public AiGeneratedContentResponseDto toAiGeneratedContentDto(AiGeneratedContent aiGeneratedContent) {
        return AiGeneratedContentResponseDto.builder()
                .id(aiGeneratedContent.getId())
                .contentType(aiGeneratedContent.getContentType())
                .generatedContent(aiGeneratedContent.getGeneratedContent())
                .lastGenerationTime(aiGeneratedContent.getLastGenerationTime())
                .build();
    }
}
