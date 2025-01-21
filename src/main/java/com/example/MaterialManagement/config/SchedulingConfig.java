package com.example.MaterialManagement.config;

import com.example.MaterialManagement.repository.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig {
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Scheduled(cron = "0 0 * * * *") // Setiap jam
    public void cleanupExpiredTokens() {
        blacklistedTokenRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}