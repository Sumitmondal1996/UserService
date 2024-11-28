package com.scaler.userservice.repository;

import com.scaler.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);
    Optional<Token> findByTokenAndActiveAndExpireAtGreaterThan(String value, boolean active, Date expiryAt);
}
