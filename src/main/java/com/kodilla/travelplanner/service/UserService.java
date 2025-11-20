package com.kodilla.travelplanner.service;

import com.kodilla.travelplanner.domain.User;
import com.kodilla.travelplanner.dto.UserRequestDto;
import com.kodilla.travelplanner.dto.UserResponseDto;
import com.kodilla.travelplanner.dto.UserUpdateDto;
import com.kodilla.travelplanner.mapper.UserMapper;
import com.kodilla.travelplanner.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if(userRepository.findByUserName(userRequestDto.getUserName()) != null) {
            throw new RuntimeException("Ta nazwa użytkownika jest już zajęta!");
        }
        User savedUser = userRepository.save(userMapper.toUser(userRequestDto));
        return userMapper.toUserResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto updateUserProfile(Long userId, UserUpdateDto updateDto) {
        User updatedUser = userRepository.save(userMapper.updateUser(updateDto, getUserById(userId)));
        return userMapper.toUserResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Nie ma takiego użytkownika!");
        }
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Nie ma takiego użytkownika!"));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserProfile(Long userId) {
        return userMapper.toUserResponseDto(getUserById(userId));
    }
}
