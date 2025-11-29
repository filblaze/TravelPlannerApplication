package com.kodilla.travelplanner.aicontent;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AiContentFactory {

    private final Map<AiContentType, AiContentStrategy> strategyMap;

    public AiContentFactory(List<AiContentStrategy> strategyList) {
        this.strategyMap = strategyList.stream()
                .collect(Collectors.toMap(AiContentStrategy::getContentType, strategy -> strategy));
    }

    public AiContentStrategy getStrategy(AiContentType contentType) {
        AiContentStrategy strategy = strategyMap.get(contentType);
        if(strategy == null) {
            throw new IllegalArgumentException("Nie znaleziono takiego typu strategii" + contentType.name());
        }
        return strategy;
    }
}
