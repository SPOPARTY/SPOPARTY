package com.spoparty.common.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenUtil {

	private final String accessSecretKey;

	private final String refreshSecretKey;
	private final Integer accessExpirationTime;
	private final Integer refreshExpirationTime;
	private static final String prefix = "Bearer ";
	private static final String issuer = "com.spoparty";
	public static final String HEADER_STRING = "Authorization";

	public JwtTokenUtil(@Value("${jwt.access.secret}") String accessSecretKey,
		@Value("${jwt.refresh.secret}") String refreshSecretKey,
		@Value("${jwt.access.expiration}") Integer accessExpirationTime,
		@Value("${jwt.refresh.expiration}") Integer refreshExpirationTime) {
		this.accessSecretKey = accessSecretKey;
		this.accessExpirationTime = accessExpirationTime;
		this.refreshSecretKey = refreshSecretKey;
		this.refreshExpirationTime = refreshExpirationTime;
	}

	public String createAccessToken(String id) {
		String jwtToken = JWT.create()
			.withSubject("accessToken")
			.withExpiresAt(new Date(System.currentTimeMillis() + accessExpirationTime))
			.withIssuer(issuer)
			.withClaim("id", id)
			.sign(Algorithm.HMAC512(accessSecretKey));

		return prefix + jwtToken;
	}

	public String createRefreshToken() {
		String jwtToken = JWT.create()
			.withSubject("refreshToken")
			.withExpiresAt(new Date(System.currentTimeMillis() + refreshExpirationTime))
			.withIssuer(issuer)
			.sign(Algorithm.HMAC512(refreshSecretKey));
		return prefix + jwtToken;
	}

	public String checkAccessToken(String token) {
		token = token.replace(prefix, "");
		DecodedJWT decodedJWT = verify(token, accessSecretKey);
		if (decodedJWT != null && decodedJWT.getSubject().equals("accessToken")) {
			return decodedJWT.getClaim("id").asString();
		} else {
			return null;
		}
	}

	public boolean checkRefreshToken(String token) {
		token = token.replace(prefix, "");
		DecodedJWT decodedJWT = verify(token, refreshSecretKey);
		if (decodedJWT != null && decodedJWT.getSubject().equals("refreshToken")) {
			return true;
		} else {
			return false;
		}
	}

	public DecodedJWT verify(String token, String key) {
		JWTVerifier jwtVerifier = null;
		DecodedJWT decodedJWT = null;
		try {
			jwtVerifier = JWT.require(Algorithm.HMAC512(key)).withIssuer(issuer).build();
			decodedJWT = jwtVerifier.verify(token);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return decodedJWT;
	}

}
