package com.spoparty.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginId(loginId).orElse(null);
		log.info("loadUserByUsername - {}", member);
		if (member == null)
			throw new UsernameNotFoundException("User Not Found");
		return new PrincipalDetails(member);
	}
}
