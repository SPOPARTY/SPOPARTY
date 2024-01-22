package com.spoparty.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoparty.api.member.entity.Member;
import com.spoparty.security.JwtTokenUtil;
import com.spoparty.security.model.PrincipalDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
		log.info("JwtAuthenticationFilter 생성");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		log.info("JwtAuthenticationFilter.attemptAuthentication() 실행");
		log.info("@@@@@@@@@@@@@@@@@@@@@@" + request.getMethod());
		log.info("@@@@@@@@@@@@@@@@@@@@@@" + request.getRequestURL());
		ObjectMapper mapper = new ObjectMapper();
		Member member = null;
		try {
			member = mapper.readValue(request.getInputStream(), Member.class);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member.getLoginId(),
				member.getLoginPwd());
			Authentication authentication = authenticationManager.authenticate(token);
			log.info(authentication.getPrincipal().toString());
			return authentication;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	//인증이 성공하면 실행되는 함수
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {
		log.info("JwtAuthenticationFilter.successfulAuthentication() 실행");
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		Member member = principalDetails.getMember();
		String accessToken = JwtTokenUtil.createAccessToken(member.getMemberId() + "");
		String refreshToken = JwtTokenUtil.createRefreshToken();
		response.addHeader("accessToken", accessToken);
		response.addHeader("refreshToken", refreshToken);
		log.info("accessToken: " + accessToken);
		log.info("refreshToken: " + refreshToken);
	}

}
