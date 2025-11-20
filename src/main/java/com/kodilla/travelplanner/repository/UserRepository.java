package com.kodilla.travelplanner.repository;

import com.kodilla.travelplanner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
    User findByEmail(String email);
}
