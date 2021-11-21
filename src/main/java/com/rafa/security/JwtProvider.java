package com.rafa.security;

import com.rafa.exceptions.RedditException;
//import com.rafa.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//beans.factory.annotation,Value
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
//sql.Date
//time.Instant


@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new RedditException("Exception ocurred while loading keystore ");
		}
	}
	
	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
					.setSubject(principal.getUsername())
					.signWith(getPrivateKey())
					.compact();
	}
	
	public boolean validateToken(String jwt) {
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}
	
	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parser()
					.setSigningKey(getPublicKey())
					.parseClaimsJws(token)
					.getBody();
		return claims.getSubject();
	}
	
	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		} catch ( KeyStoreException e) {
			throw new RedditException("Exception ocurred while retrieving public key from keystore");
		}
		
	}

	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new RedditException("Exception occured while retrieving public key from keystore");
		}
	}
	

}
