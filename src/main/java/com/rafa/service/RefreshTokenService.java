package com.rafa.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.exceptions.RedditException;
import com.rafa.model.RefreshToken;
import com.rafa.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
	
		private final RefreshTokenRepository refreshTokenRepository;
		
		public RefreshToken generateRefreshToken() {
			RefreshToken refreshToken = new RefreshToken();
			refreshToken.setToken(UUID.randomUUID().toString());
			refreshToken.setCreatedDate(Instant.now());
			
			return refreshTokenRepository.save(refreshToken);
		}
		
		void validateRefreshToken(String token) {
			refreshTokenRepository.findByToken(token)
				.orElseThrow(()-> new RedditException("Invalid refreash token"));
		}
		
		public void deleteRefreshToken(String token) {
			refreshTokenRepository.deleteByToken(token);
		}
}
