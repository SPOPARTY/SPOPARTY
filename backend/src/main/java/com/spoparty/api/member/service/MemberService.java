package com.spoparty.api.member.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.football.entity.Team;
import com.spoparty.api.football.repository.TeamRepository;
import com.spoparty.api.member.entity.FollowingTeam;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.FollowingTeamRepository;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.api.member.repository.projection.FollowingTeamProjection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final TeamRepository teamRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final FollowingTeamRepository followingTeamRepository;

	// Member 기능

	public Member findById(Long id) {
		return memberRepository.findById(id, Member.class).orElse(null);
	}

	public Member findByLoginId(String loginId) {
		return memberRepository.findByLoginId(loginId, Member.class).orElse(null);
	}

	public Member registerMember(Member member) {
		member.setLoginPwd(bCryptPasswordEncoder.encode(member.getLoginPwd()));
		Team teamInfo = teamRepository.findById(member.getTeam().getId()).orElse(null);
		member.setTeam(teamInfo);
		memberRepository.save(member);
		return member;
	}

	@Transactional
	public Member updateMember(Member member) {
		Member data = memberRepository.findById(member.getId(), Member.class).orElse(null);
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

	@Transactional
	public Member deleteMember(Long id) {
		Member member = memberRepository.findById(id, Member.class).orElse(null);
		if (member == null || member.getState() == 2)
			return null;
		member.setState(2);
		return member;
	}

	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email, Member.class).orElse(null);
	}

	// Follow 기능
	public List<FollowingTeamProjection> getFollowList(Long memberId) {
		return followingTeamRepository.findByMember_id(memberId, FollowingTeamProjection.class);
	}

	public FollowingTeamProjection registerFollow(Long memberId, Long teamId) {
		// Follow를 이미 했으면 null을 반환
		List<FollowingTeamProjection> isExist = followingTeamRepository.findByMember_idAndTeam_id(memberId, teamId,
			FollowingTeamProjection.class);
		log.info("@@@@@@@@@@@@@@@@" + isExist.size());
		if (!isExist.isEmpty())
			return null;

		// FollowingTeam객체를 만들어 Member와 Team을 주입
		Member member = memberRepository.findById(memberId, Member.class).orElse(null);
		Team team = teamRepository.findById(teamId, Team.class).orElse(null);
		FollowingTeam followingTeam = new FollowingTeam();
		followingTeam.setMember(member);
		followingTeam.setTeam(team);

		// save하고 저장된 id값을 Projection형태로 얻어옴
		FollowingTeam tmp = followingTeamRepository.save(followingTeam);
		return followingTeamRepository.findById(tmp.getId(), FollowingTeamProjection.class).orElse(null);
	}

	@Transactional
	public FollowingTeam deleteFollow(Long followTeamId) {
		FollowingTeam followingTeam = followingTeamRepository.findById(followTeamId, FollowingTeam.class).orElse(null);
		if (followingTeam == null || followingTeam.isDeleted())
			return null;

		followingTeam.softDelete();
		return followingTeam;
	}

}
