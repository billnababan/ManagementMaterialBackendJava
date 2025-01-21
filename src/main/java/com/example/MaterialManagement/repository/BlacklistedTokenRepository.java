package com.example.MaterialManagement.repository;

import com.example.MaterialManagement.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
    void deleteByExpiresAtBefore(LocalDateTime date);
}