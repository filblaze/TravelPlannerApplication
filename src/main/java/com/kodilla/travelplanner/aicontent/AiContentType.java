package com.kodilla.travelplanner.aicontent;

import lombok.Getter;

@Getter
public enum AiContentType {
    DESTINATION_ABSTRACT("abstract"),
    TOURIST_ATTRACTIONS("attractions"),
    EVENTS_INFO("events"),
    BASIC_PRODUCT_PRICES("prices"),
    PACKING_TIPS("packing");

    private final String contentType;

    private AiContentType(String contentType) {
        this.contentType = contentType;
    }

    public static AiContentType translateContentType(String contentType) {
        for(AiContentType type : AiContentType.values()) {
            if(type.getContentType().equalsIgnoreCase(contentType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Nieznany typ treści: " + contentType);
    }
}

