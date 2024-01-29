package com.spoparty.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoparty.api.member.entity.Member;
import com.spoparty.common.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// 32번째 줄에 적인 경로로 오는 POST요청을 받아 회원정보를 DB에서 조회하고, 인증에 성공하면 successfulAuthentication()이
// 호출에서 토큰을 발행한다.
// successfulAuthentication()에서는 chain.doFilter(request, respose); 를 하지 않으면 다음 필터체인으로 이동하지 않고 바로 응답이 반환되게 된다.
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		setFilterProcessesUrl("/members/login");
		log.info("JwtAuthenticationFilter 생성");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		log.info("JwtAuthenticationFilter.attemptAuthentication() 실행");
		ObjectMapper mapper = new ObjectMapper();
		Member member = null;
		try {
			member = mapper.readValue(request.getInputStream(), Member.class);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member.getLoginId(),
				member.getLoginPwd());
			Authentication authentication = authenticationManager.authenticate(token);
			log.info(authentication.getPrincipal().toString());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authentication;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	//인증이 성공하면 실행되는 함수
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {
		log.info("JwtAuthenticationFilter.successfulAuthentication() 실행");
		request.getRequestDispatcher("/authentication/token").forward(request, response);
	}

}
