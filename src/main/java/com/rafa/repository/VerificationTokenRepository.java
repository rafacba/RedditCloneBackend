package com.rafa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	
	Optional<VerificationToken> findByToken(String token);

}
