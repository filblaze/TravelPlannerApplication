package com.kodilla.travelplanner.mapper;

import com.kodilla.travelplanner.domain.TripPlan;
import com.kodilla.travelplanner.domain.User;
import com.kodilla.travelplanner.dto.UserRequestDto;
import com.kodilla.travelplanner.dto.UserResponseDto;
import com.kodilla.travelplanner.dto.UserUpdateDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User toUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setCountryOfResidence(userRequestDto.getCountryOfResidence());
        return user;
    }

    public UserResponseDto toUserResponseDto(User user) {
        int tripPlanCount = 0;
        List<TripPlan> tripPlans = user.getTripPlans();
        if(tripPlans != null) {
            tripPlanCount = tripPlans.size();
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .countryOfResidence(user.getCountryOfResidence())
                .tripPlanCount(tripPlanCount)
                .build();
    }

    public User updateUser(UserUpdateDto userUpdateDto, User user) {
        if(userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if(userUpdateDto.getCountryOfResidence() != null) {
            user.setCountryOfResidence(userUpdateDto.getCountryOfResidence());
        }
        return user;
    }
}
