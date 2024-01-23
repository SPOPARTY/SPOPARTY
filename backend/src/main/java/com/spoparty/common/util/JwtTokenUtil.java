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

	private final String ACCESS_SECRET_KEY;

	private final String REFRESH_SECRET_KEY;
	private final Integer ACCESS_EXPIRATION_TIME;
	private final Integer REFRESH_EXPIRATION_TIME;
	public static final String PREFIX = "Bearer ";
	private static final String ISSUER = "com.spoparty";
	public static final String HEADER_STRING = "Authorization";

	public JwtTokenUtil(@Value("${jwt.access.secret}") String ACCESS_SECRET_KEY,
		@Value("${jwt.refresh.secret}") String REFRESH_SECRET_KEY,
		@Value("${jwt.access.expiration}") Integer ACCESS_EXPIRATION_TIME,
		@Value("${jwt.refresh.expiration}") Integer REFRESH_EXPIRATION_TIME) {
		this.ACCESS_SECRET_KEY = ACCESS_SECRET_KEY;
		this.ACCESS_EXPIRATION_TIME = ACCESS_EXPIRATION_TIME;
		this.REFRESH_SECRET_KEY = REFRESH_SECRET_KEY;
		this.REFRESH_EXPIRATION_TIME = REFRESH_EXPIRATION_TIME;
	}

	public String createAccessToken(String id) {
		String jwtToken = JWT.create()
			.withSubject("accessToken")
			.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
			.withIssuer(ISSUER)
			.withClaim("id", id)
			.sign(Algorithm.HMAC512(ACCESS_SECRET_KEY));

		return PREFIX + jwtToken;
	}

	public String createRefreshToken() {
		String jwtToken = JWT.create()
			.withSubject("refreshToken")
			.withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
			.withIssuer(ISSUER)
			.sign(Algorithm.HMAC512(REFRESH_SECRET_KEY));
		return PREFIX + jwtToken;
	}

	public String checkAccessToken(String token) {
		token = token.replace(PREFIX, "");
		DecodedJWT decodedJWT = verify(token, ACCESS_SECRET_KEY);
		if (decodedJWT != null && decodedJWT.getSubject().equals("accessToken")) {
			return decodedJWT.getClaim("id").asString();
		} else {
			return null;
		}
	}

	public boolean checkRefreshToken(String token) {
		token = token.replace(PREFIX, "");
		DecodedJWT decodedJWT = verify(token, REFRESH_SECRET_KEY);
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
			jwtVerifier = JWT.require(Algorithm.HMAC512(key)).withIssuer(ISSUER).build();
			decodedJWT = jwtVerifier.verify(token);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return decodedJWT;
	}

}
