package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.aicontent.AiContentFactory;
import com.kodilla.travelplanner.aicontent.AiContentStrategy;
import com.kodilla.travelplanner.aicontent.AiContentType;
import com.kodilla.travelplanner.clients.AiClient;
import com.kodilla.travelplanner.domain.AiGeneratedContent;
import com.kodilla.travelplanner.domain.TripPlan;
import com.kodilla.travelplanner.dto.AiGeneratedContentResponseDto;
import com.kodilla.travelplanner.mapper.AiGeneratedContentMapper;
import com.kodilla.travelplanner.repository.AiContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiContentService {

    private final AiContentRepository aiContentRepository;
    private final AiClient aiClient;
    private final AiContentFactory aiContentFactory;
    private final AiGeneratedContentMapper aiGeneratedContentMapper;

    @Transactional
    public AiGeneratedContentResponseDto generateContent(TripPlan tripPlan, AiContentType contentType) {
        AiContentStrategy strategy = aiContentFactory.getStrategy(contentType);
        String prompt = strategy.generatePrompt(tripPlan);
        String generatedContent = aiClient.generateContent(prompt);

        AiGeneratedContent content = new AiGeneratedContent();
        content.setContentType(contentType.getContentType());
        content.setGeneratedContent(generatedContent);
        content.setLastGenerationTime(LocalDateTime.now());
        content.setTripDetails(tripPlan.getDetails());
        AiGeneratedContent savedContent = aiContentRepository.save(content);

        return aiGeneratedContentMapper.toAiGeneratedContentDto(savedContent);
    }

    @Transactional
    public AiGeneratedContentResponseDto generateDestinationAbstract(TripPlan tripPlan) {
        AiGeneratedContentResponseDto contentDto = generateContent(tripPlan, AiContentType.DESTINATION_ABSTRACT);
        tripPlan.getDetails().setTravelDestinationAbstract(contentDto.getGeneratedContent());
        return contentDto;
    }

    @Transactional(readOnly = true)
    public List<AiGeneratedContentResponseDto> getAiContentByType(Long tripDetailsId, String contentType) {
        AiContentType type = AiContentType.translateContentType(contentType);
        return aiContentRepository.findAllByTripDetailsIdAndContentType(tripDetailsId, type.getContentType()).stream()
                .map(aiGeneratedContentMapper::toAiGeneratedContentDto)
                .toList();
    }
}
