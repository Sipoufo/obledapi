package com.example.obledapi.repositories;

import com.example.obledapi.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUserId(long userId);
    Optional<Users> findByFirstName(String firstName);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}
