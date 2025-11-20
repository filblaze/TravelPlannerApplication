package com.kodilla.travelplanner.config;

import com.kodilla.travelplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConfig {

    private final UserRepository userRepository;

    private static final String TEST_USERNAME = "admin";
    private static final String TEST_EMAIL = "admin@admin.com";
    private static final String TEST_COUNTRY = "Polska";
}
