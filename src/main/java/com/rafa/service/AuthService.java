package com.rafa.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.dto.RegisterRequest;
import com.rafa.model.NotificationEmail;
import com.rafa.model.User;
import com.rafa.model.VerificationToken;
import com.rafa.repository.UserRepository;
import com.rafa.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	
	//Reemplazo Autowired por private final y agrego @AllArgsConstructor
	//@Autowired
	private final PasswordEncoder passwordEncoder;
	//@Autowired
	private final UserRepository userRepository;
	
	private final VerificationTokenRepository verificationTokenRepository;
	
	private final MailService mailService;
	
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(), 
				"Welcome to RedditClone, please activate your account: " + 
				"http://localhost:8080/api/auth/accountVerification/"+token));
	}
	
	private String generateVerificationToken(User user) {
		
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		return token;
		
	}

}
