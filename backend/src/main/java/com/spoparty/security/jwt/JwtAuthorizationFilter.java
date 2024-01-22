package com.spoparty.security.jwt;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.security.JwtTokenUtil;
import com.spoparty.security.model.PrincipalDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final MemberRepository memberRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
		log.info("JwtAuthorizationFilter 생성");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		log.info("JwtAuthorizationFilter.doFilterInternal() : 인증 및 권한 확인");
		String jwtHeader = request.getHeader("Authorization");
		log.info("jwtHeader: " + jwtHeader);

		if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			log.error("권한없음!!!!");
			return;
		}
		String token = jwtHeader.replace("Bearer ", "");
		String id = JwtTokenUtil.checkAccessToken(token);
		log.info("memberId: " + id);
		if (id != null) {
			Member member = (Member)memberRepository.findByMemberId(Long.parseLong(id));
			PrincipalDetails principalDetails = new PrincipalDetails(member);
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.info("토큰 인증에 성공했어요 -> " + member);
			chain.doFilter(request, response);
		} else {
			throw new AuthenticationException();
		}
	}

}
