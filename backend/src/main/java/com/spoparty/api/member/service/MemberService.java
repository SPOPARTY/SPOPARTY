package com.spoparty.api.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.football.entity.Team;
import com.spoparty.api.football.repository.TeamRepository;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final TeamRepository teamRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Member findById(Long id) {
		return memberRepository.findById(id).orElse(null);
	}

	public Member findByLoginId(String loginId) {
		return memberRepository.findByLoginId(loginId).orElse(null);
	}

	public Member register(Member member) {
		member.setLoginPwd(bCryptPasswordEncoder.encode(member.getLoginPwd()));
		Team teamInfo = teamRepository.findById(member.getTeamInfo().getId()).orElse(null);
		member.setTeamInfo(teamInfo);
		memberRepository.save(member);
		return member;
	}

	@Transactional
	public Member update(Member member) {
		Member data = memberRepository.findById(member.getId()).orElse(null);
		if (data == null)
			return null;
		if (!member.getLoginPwd().isEmpty())
			data.setLoginPwd(bCryptPasswordEncoder.encode(member.getLoginPwd()));
		if (!member.getEmail().isEmpty())
			data.setEmail(member.getEmail());
		if (!member.getNickname().isEmpty())
			data.setNickname(member.getNickname());
		return data;
	}

	public void delete(Long id) {
		memberRepository.deleteById(id);
	}

	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email).orElse(null);
	}

}
