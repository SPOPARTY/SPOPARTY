package com.spoparty.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.common.util.JwtTokenUtil;
import com.spoparty.security.model.PrincipalDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// JwtAuthorizationFilter는 모든 요청에 대해 토큰의 유효성을 검증하고, 있다면 시큐리티 세션이 인증 객체를 넣음.
// 하지만 권한을 확인하는 필터는 필요없는 경로에 대해선 인증객체를 검사하지 않음 -> 인증과 인가를 분리
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final MemberRepository memberRepository;
	private final JwtTokenUtil jwtTokenUtil;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository,
		JwtTokenUtil jwtTokenUtil) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
		this.jwtTokenUtil = jwtTokenUtil;
		log.info("JwtAuthorizationFilter 생성");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		log.info("JwtAuthorizationFilter.doFilterInternal() : 인증 및 권한 확인");
		String jwtHeader = request.getHeader(JwtTokenUtil.HEADER_STRING);
		log.info("jwtHeader: " + jwtHeader);

		if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			log.error("권한없음!!!!");
			chain.doFilter(request, response);
			return;
		}
		String token = jwtHeader.replace("Bearer ", "");
		String id = jwtTokenUtil.checkAccessToken(token);
		log.info("memberId: " + id);
		if (id != null) {
			Member member = memberRepository.findById(Long.parseLong(id)).get();
			PrincipalDetails principalDetails = new PrincipalDetails(member);
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.info("토큰 인증에 성공했어요 -> " + member);
		} else {
			log.info("토큰 인증에 실패했어요");
		}
		chain.doFilter(request, response);
	}

}
