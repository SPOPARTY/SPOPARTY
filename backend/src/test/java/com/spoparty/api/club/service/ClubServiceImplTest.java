package com.spoparty.api.club.service;

import static com.spoparty.api.common.constants.DefaultSetting.*;
import static com.spoparty.api.common.constants.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.dto.ClubHostRequestDto;
import com.spoparty.api.club.dto.ClubMemberResponseDto;
import com.spoparty.api.club.dto.ClubRequestDto;
import com.spoparty.api.club.dto.ClubResponseDto;
import com.spoparty.api.club.dto.InviteRequestDto;
import com.spoparty.api.club.dto.InviteResponseDto;
import com.spoparty.api.club.repository.ClubMemberRepository;
import com.spoparty.api.club.repository.ClubRepository;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ClubServiceImplTest {

	@Autowired
	ClubServiceImpl clubService;

	@Autowired
	ClubRepository clubRepository;

	@Autowired
	ClubMemberRepository clubMemberRepository;

	@Autowired
	MemberRepository memberRepository;

	@Test
	void 그룹_생성_성공() throws Exception {
		long memberId = 1;
		String name = "아시안컵 16강 볼사람 모여";

		ClubRequestDto request = new ClubRequestDto(memberId, name);
		ClubResponseDto response = clubService.createClub(request);
		
		assertThat(response.getName()).isEqualTo(name);
		assertThat(response.getHostId()).isEqualTo(memberId);
	}

	@Test
	void 그룹_조회_성공() {
		long clubId = 18;
		ClubResponseDto response = clubService.findClub(clubId);
		assertThat(response.getClubId()).isEqualTo(clubId);
	}

	@Test
	void 그룹_조회_실패() {
		long clubId = 16;
		assertThatThrownBy(() -> clubService.findClub(clubId))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(NO_GROUP_ID.getMessage());
	}

	@Test
	void 그룹명_수정_성공() {
		long memberId = 3;
		long clubId = 18;
		String name = "전략은 이강인 개인기다";

		ClubRequestDto clubRequestDto = new ClubRequestDto(memberId, name);
		Member member = memberRepository.findByMemberId(memberId);
		ClubResponseDto response = clubService.updateClubName(member, clubId, clubRequestDto);

		assertThat(response.getName()).isEqualTo(name);
	}

	@Test
	void 그룹명_수정_실패_그룹장X() {
		long memberId = 2;
		long clubId = 18;
		String name = "들으와아아라";

		ClubRequestDto clubRequestDto = new ClubRequestDto(memberId, name);
		Member member = memberRepository.findByMemberId(memberId);

		assertThatThrownBy(() -> clubService.updateClubName(member, clubId, clubRequestDto))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(FORBIDDEN_USER.getMessage());
	}

	@Test
	void 그룹_삭제_성공() {
		long memberId = 2;
		long clubId = 20;

		Member member = memberRepository.findByMemberId(memberId);
		Long response = clubService.deleteClub(member, clubId);
	}

	@Test
	void 그룹_삭제_실패_이미_삭제됐거나_없는_그룹() {
		long memberId = 2;
		long clubId = 16;

		Member member = memberRepository.findByMemberId(memberId);

		assertThatThrownBy(() -> clubService.deleteClub(member, clubId))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(NO_GROUP_ID.getMessage());
	}

	@Test
	void 그룹_삭제_실패_그룹장X() {
		long memberId = 12;
		long clubId = 18;

		Member member = memberRepository.findByMemberId(memberId);

		assertThatThrownBy(() -> clubService.deleteClub(member, clubId))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(FORBIDDEN_USER.getMessage());
	}

	@Test
	void 그룹_초대링크_반환_성공() {
		long clubId = 20;
		InviteResponseDto response = clubService.getInviteUrl(clubId);
		System.out.println(response);
	}

	@Test
	void 그룹_초대_성공() {
		long memberId = 3;
		long clubId = 20;

		InviteResponseDto urlDto = clubService.getInviteUrl(clubId);
		InviteRequestDto request = new InviteRequestDto(memberId, urlDto.getInviteUrl());
		ClubMemberResponseDto response = clubService.createGroupMember(request);

		assertThat(response.getClubId()).isEqualTo(clubId);
		assertThat(response.getMemberId()).isEqualTo(memberId);
		assertThat(response.getRole()).isEqualTo(RoleType.guest.name());
	}

	@Test
	void 그룹_초대_실패_이미_존재하는_그룹원() {
		long memberId = 2;
		long clubId = 20;

		InviteResponseDto urlDto = clubService.getInviteUrl(clubId);
		InviteRequestDto request = new InviteRequestDto(memberId, urlDto.getInviteUrl());

		assertThatThrownBy(() -> clubService.createGroupMember(request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(ALREADY_GROUP_MEMBER.getMessage());
	}

	@Test
	void 그룹_초대_실패_유효하지_않은_링크() {
		long memberId = 3;
		long clubId = 20;
		LocalDateTime expirationTime = LocalDateTime.now();
		String inviteUrl = (String)INVITE_URL_DOMAIN.getValue() + clubId + "_" + expirationTime;
		InviteRequestDto request = new InviteRequestDto(memberId, inviteUrl);

		assertThatThrownBy(() -> clubService.createGroupMember(request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(INVALID_INVITE_URL.getMessage());
	}

	@Test
	void 그룹장_수정_성공() {
		long hostId = 2;
		long clubId = 20;
		long nextHostId = 1;
		ClubHostRequestDto request = new ClubHostRequestDto(hostId, nextHostId);
		Member host = memberRepository.findByMemberId(hostId);

		ClubMemberResponseDto response = clubService.assignHost(host, clubId, request);

		assertThat(response.getMemberId()).isEqualTo(nextHostId);
		assertThat(response.getRole()).isEqualTo(RoleType.host.name());
	}

	@Test
	void 그룹장_수정_실패_그룹장X() {
		long hostId = 1;
		long clubId = 20;
		long nextHostId = 2;
		Member host = memberRepository.findByMemberId(hostId);
		ClubHostRequestDto request = new ClubHostRequestDto(hostId, nextHostId);

		assertThatThrownBy(() -> clubService.assignHost(host, clubId, request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(FORBIDDEN_USER.getMessage());
	}

	@Test
	void 그룹장_수정_실패_존재하지_않은_회원() {
		long hostId = 2;
		long clubId = 20;
		long nextHostId = 15;
		Member host = memberRepository.findByMemberId(hostId);
		ClubHostRequestDto request = new ClubHostRequestDto(hostId, nextHostId);

		assertThatThrownBy(() -> clubService.assignHost(host, clubId, request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(NO_GROUP_MEMBER.getMessage());
	}

	@Test
	void 그룹원_삭제_성공() {
		long memberId = 8;
		long clubId = 18;

		Member member = memberRepository.findByMemberId(memberId);
		Long response = clubService.deleteGroupMember(member, clubId);
	}

	@Test
	void 그룹원_삭제_실패_그룹장을_넘기지_않은_경우() {
		long memberId = 3;
		long clubId = 18;

		Member member = memberRepository.findByMemberId(memberId);

		assertThatThrownBy(() -> clubService.deleteGroupMember(member, clubId))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(HOST_CANNOT_LEAVE_GROUP.getMessage());
	}

	@Test
	void 최근_활동_그룹_목록_조회() {
		long memberId = 3;
		Member member = memberRepository.findByMemberId(memberId);
		List<ClubResponseDto> response = clubService.findRecentClubs(member);
		response.forEach(System.out::println);

		assertThat(response.size()).isEqualTo(1);
	}

	@Test
	void 그룹원_목록_조회() {
		long clubId = 18;
		List<ClubMemberResponseDto> response = clubService.getGroupMembers(clubId);
		response.forEach(System.out::println);

		assertThat(response.size()).isEqualTo(4);
	}
}