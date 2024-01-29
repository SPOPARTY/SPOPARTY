package com.spoparty.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.spoparty.security.model.PrincipalDetails;
import com.spoparty.security.service.PrincipalDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// principalDetailService를 호출하여 DB에서 사용자를 조회하고 Authentication객체를 반환한다.
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final PrincipalDetailService principalDetailService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("JwtAuthenticationProvider.authenticate()");
		log.info(authentication.getPrincipal().toString());
		String id = (String)authentication.getPrincipal();
		String pwd = (String)authentication.getCredentials();
		PrincipalDetails user = (PrincipalDetails)principalDetailService.loadUserByUsername(id);
		if (bCryptPasswordEncoder.matches(pwd, user.getPassword())) {
			log.info("비밀번호 일치!!");
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		} else {
			log.info("비밀번호 불일치!!");
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		log.info("JwtAuthenticationProvider.supports({})", authentication);
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

