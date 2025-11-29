package com.kodilla.travelplanner.clients;

import org.springframework.stereotype.Component;

@Component
public class GeminiClient implements AiClient{

    @Override
    public String generateContent(String prompt) {
        String mock = "mock";
        return mock;
    }
}
