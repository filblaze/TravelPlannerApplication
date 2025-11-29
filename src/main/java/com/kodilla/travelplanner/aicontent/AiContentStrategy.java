package com.kodilla.travelplanner.aicontent;

import com.kodilla.travelplanner.domain.TripPlan;

public interface AiContentStrategy {
    AiContentType getContentType();
    String generatePrompt(TripPlan tripPlan);
}
