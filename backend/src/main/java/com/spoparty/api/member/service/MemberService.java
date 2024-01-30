package com.spoparty.api.member.service;

import java.util.List;

import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.football.entity.Team;
import com.spoparty.api.football.repository.TeamRepository;
import com.spoparty.api.member.entity.FollowingTeam;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.entity.MemberToken;
import com.spoparty.api.member.repository.FollowingTeamRepository;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.api.member.repository.MemberTokenRepository;
import com.spoparty.api.member.repository.projection.FollowingTeamProjection;
import com.spoparty.common.util.JwtTokenUtil;

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
	private final JwtTokenUtil jwtTokenUtil;
	private final MemberTokenRepository memberTokenRepository;
	private final EmailService emailService;

	// Member 기능

	public Member findById(Long id) {
		return memberRepository.findById(id, Member.class).orElse(null);
	}

	public Member findByLoginId(String loginId) {
		return memberRepository.findByLoginId(loginId, Member.class).orElse(null);
	}

	public Member registerMember(Member member) {
		member.setLoginPwd(bCryptPasswordEncoder.encode(member.getLoginPwd()));
		if (member.getTeam() != null) {
			Team teamInfo = teamRepository.findById(member.getTeam().getId(), Team.class).orElse(null);
			member.setTeam(teamInfo);
		}
		memberRepository.save(member);
		return member;
	}

	@Transactional
	public Member updateMember(Member member) {
		Member data = memberRepository.findById(member.getId(), Member.class).orElse(null);
		if (data == null)
			return null;
		if (member.getLoginPwd() != null)
			data.setLoginPwd(bCryptPasswordEncoder.encode(member.getLoginPwd()));
		if (member.getEmail() != null)
			data.setEmail(member.getEmail());
		if (member.getNickname() != null)
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
		if (!isExist.isEmpty())
			return null;

		// FollowingTeam객체를 만들어 Member와 Team을 주입
		Member member = memberRepository.findById(memberId, Member.class).orElse(null);
		Team team = teamRepository.findById(teamId, Team.class).orElse(null);
		if (member == null || team == null)
			return null;
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

	public String generateAccessToken(Long id) {
		return jwtTokenUtil.createAccessToken(id + "");
	}

	@Transactional
	public String generateRefreshToken(Long id) {
		String refreshToken = jwtTokenUtil.createRefreshToken();
		Member member = memberRepository.findById(id, Member.class).orElse(null);
		MemberToken token = new MemberToken();
		token.setMember(member);
		token.setRefreshToken(refreshToken);
		memberTokenRepository.deleteByMember_id(member.getId());
		memberTokenRepository.save(token);
		return refreshToken;
	}

	public String regenerateToken(String accessToken, String refreshToken) {
		MemberToken memberToken = memberTokenRepository.findByRefreshToken(refreshToken, MemberToken.class)
			.orElse(null);
		if (accessToken == null || memberToken == null)
			return null;
		return jwtTokenUtil.createAccessToken(memberToken.getMember().getId() + "");
	}

	public boolean checkRefreshToken(Long id) {
		MemberToken token = memberTokenRepository.findByMember_id(id, MemberToken.class).orElse(null);
		return token != null;
	}

	@Transactional
	public void deleteToken(Long id) {
		memberTokenRepository.deleteByMember_id(id);
	}

	@Transactional
	public boolean tempPwd(Member member) throws MailSendException, InterruptedException {
		Member data = memberRepository.findByLoginIdAndEmail(member.getLoginId(), member.getEmail(), Member.class)
			.orElse(null);
		if (data != null) {
			int code = (int)(Math.random() * 100000000);
			emailService.sendEmail(data.getEmail(), "SPOPARTY 비밀번호 찾기", "임시 비밀번호 : [" + code + "]");
			data.setLoginPwd(bCryptPasswordEncoder.encode(code + ""));
			return true;
		}
		return false;
	}

	public List<Team> getTeamList() {
		return teamRepository.findAll();
	}

}
