package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.AiGeneratedContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiContentRepository extends JpaRepository<AiGeneratedContent, Long> {

    List<AiGeneratedContent> findAllByTripDetailsId(Long tripDetailsId);
    List<AiGeneratedContent> findAllByTripDetailsIdAndContentType(Long tripDetailsId, String contentType);
}
