package com.spoparty.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

/**
 * jwt 토큰 유틸 정의.
 */
@Component
public class JwtTokenUtil {
	private static String accessSecretKey;
	private static Integer accessExpirationTime;

	private static String refreshSecretKey;
	private static Integer refreshExpirationTime;

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String ISSUER = "ssafy.com";

	// 토큰 시크릿 키, 만료일 변수값 할당
	public JwtTokenUtil(@Value("${jwt.access.secret}") String accessSecretKey,
		@Value("${jwt.access.expiration}") Integer accessExpirationTime,
		@Value("${jwt.refresh.secret}") String refreshSecretKey,
		@Value("${jwt.refresh.expiration}") Integer refreshExpirationTime) {
		this.accessSecretKey = accessSecretKey;
		this.accessExpirationTime = accessExpirationTime;

		this.refreshSecretKey = refreshSecretKey;
		this.refreshExpirationTime = refreshExpirationTime;
	}



	// 현재 시간으로부터 토큰 만료 시간 얻기
	public static Date getTokenExpiration(int expirationTime) {
		Date now = new Date();
		return new Date(now.getTime() + expirationTime);
	}


	// access 토큰 얻기
	public static String getAccessToken(String userId) {
		Date expires = JwtTokenUtil.getTokenExpiration(accessExpirationTime);
		return JWT.create()
			.withSubject("accessToken")
			.withClaim("id", userId)
			.withExpiresAt(expires)
			.withIssuer(ISSUER)
			.withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
			.sign(Algorithm.HMAC512(accessSecretKey.getBytes()));
	}


	// 리프레쉬 토큰 얻기
	public static String getRefreshToken() {
		Date expires = JwtTokenUtil.getTokenExpiration(refreshExpirationTime);
		return JWT.create()
			.withSubject("refreshToken")
			.withExpiresAt(expires)
			.withIssuer(ISSUER)
			.withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
			.sign(Algorithm.HMAC512(refreshSecretKey.getBytes()));
	}


	// access 토큰 검증하기
	public static void verifyAccessToken(String token) {
		JWTVerifier verifier = JWT
			.require(Algorithm.HMAC512(accessSecretKey.getBytes()))
			.withIssuer(ISSUER)
			.build();

		try {
			verifier.verify(token.replace(TOKEN_PREFIX, ""));
		} catch (AlgorithmMismatchException ex) {
			throw ex;
		} catch (InvalidClaimException ex) {
			throw ex;
		} catch (SignatureGenerationException ex) {
			throw ex;
		} catch (SignatureVerificationException ex) {
			throw ex;
		} catch (TokenExpiredException ex) {
			throw ex;
		} catch (JWTCreationException ex) {
			throw ex;
		} catch (JWTDecodeException ex) {
			throw ex;
		} catch (JWTVerificationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
	}


	// 리프레쉬 토큰 검증하기
	public static void verifyRefreshToken(String token) {
		JWTVerifier verifier = JWT
			.require(Algorithm.HMAC512(refreshSecretKey.getBytes()))
			.withIssuer(ISSUER)
			.build();

		try {
			verifier.verify(token.replace(TOKEN_PREFIX, ""));
		} catch (AlgorithmMismatchException ex) {
			throw ex;
		} catch (InvalidClaimException ex) {
			throw ex;
		} catch (SignatureGenerationException ex) {
			throw ex;
		} catch (SignatureVerificationException ex) {
			throw ex;
		} catch (TokenExpiredException ex) {
			throw ex;
		} catch (JWTCreationException ex) {
			throw ex;
		} catch (JWTDecodeException ex) {
			throw ex;
		} catch (JWTVerificationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
	}

}
