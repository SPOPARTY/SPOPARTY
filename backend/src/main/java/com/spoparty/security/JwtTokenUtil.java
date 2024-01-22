package com.spoparty.security;

import java.util.Date;

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
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenUtil {

	private static final String secretKey = "#!@qwe421dfs@WEQDssfa9";
	private static final Integer accessTokenExpirationTime = 1000 * 60 * 60;
	private static final Integer refreshTokenExpirationTime = 1000 * 60 * 60 * 16;
	private static final String prefix = "Bearer ";
	private static final String issuer = "bum";

	public static String createAccessToken(String id) {
		String jwtToken = JWT.create()
			.withSubject("accessToken")
			.withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
			.withIssuer(issuer)
			.withClaim("id", id)
			.sign(Algorithm.HMAC512(secretKey));

		return prefix + jwtToken;
	}

	public static String createRefreshToken() {
		String jwtToken = JWT.create()
			.withSubject("refreshToken")
			.withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
			.withIssuer(issuer)
			.sign(Algorithm.HMAC512(secretKey));
		return prefix + jwtToken;
	}

	public static String checkAccessToken(String token) {
		DecodedJWT decodedJWT = checkToken(token);
		if (decodedJWT.getSubject().equals("accessToken")) {
			return decodedJWT.getClaim("id").asString();
		} else {
			return null;
		}
	}

	public static boolean checkRefreshToken(String token) {
		DecodedJWT decodedJWT = checkToken(token);
		if (decodedJWT.getSubject().equals("refreshToken")) {
			return true;
		} else {
			return false;
		}
	}

	public static DecodedJWT checkToken(String token) {
		token = token.replace(prefix, "");
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secretKey)).withIssuer(issuer).build();
		DecodedJWT decodedJWT = null;

		try {
			decodedJWT = jwtVerifier.verify(token);
		} catch (AlgorithmMismatchException e) {
			throw e;
		} catch (InvalidClaimException e) {
			throw e;
		} catch (SignatureGenerationException e) {
			throw e;
		} catch (SignatureVerificationException e) {
			throw e;
		} catch (TokenExpiredException e) {
			throw e;
		} catch (JWTCreationException e) {
			throw e;
		} catch (JWTDecodeException e) {
			throw e;
		} catch (JWTVerificationException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		return decodedJWT;
	}

}
