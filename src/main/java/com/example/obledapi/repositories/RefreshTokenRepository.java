package com.example.obledapi.repositories;

import com.example.obledapi.Entities.RefreshToken;
import com.example.obledapi.Entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Page<RefreshToken> findAllByUser(Pageable pageable, Users user);
    @Modifying
    void deleteByUser(Users user);
}
