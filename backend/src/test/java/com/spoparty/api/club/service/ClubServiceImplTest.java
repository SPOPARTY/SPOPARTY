// package com.spoparty.api.club.service;
//
// import static com.spoparty.api.common.constants.DefaultSetting.*;
// import static com.spoparty.api.common.constants.ErrorCode.*;
// import static org.assertj.core.api.Assertions.*;
//
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;
//
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.spoparty.api.club.dto.request.ClubHostRequestDto;
// import com.spoparty.api.club.dto.response.ClubMemberResponseDto;
// import com.spoparty.api.club.dto.request.ClubRequestDto;
// import com.spoparty.api.club.dto.response.ClubResponseDto;
// import com.spoparty.api.club.dto.request.InviteRequestDto;
// import com.spoparty.api.club.dto.response.InviteResponseDto;
// import com.spoparty.api.club.entity.Club;
// import com.spoparty.api.club.repository.ClubMemberRepository;
// import com.spoparty.api.club.repository.ClubRepository;
// import com.spoparty.api.common.entity.RoleType;
// import com.spoparty.api.member.entity.Member;
// import com.spoparty.api.member.repository.MemberRepository;
//
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// @Transactional
// class ClubServiceImplTest {
//
// 	@Autowired
// 	ClubServiceImpl clubService;
//
// 	@Autowired
// 	ClubRepository clubRepository;
//
// 	@Autowired
// 	ClubMemberRepository clubMemberRepository;
//
// 	@Autowired
// 	MemberRepository memberRepository;
//
// 	@Disabled("더미데이터 생성시 사용") // 클래스 트렌젝션 어노테이션 주석처리 필요
// 	@Test
// 	void 더미데이터_그룹_생성() {
// 		int size = 8;
// 		List<Long> memberIds = List.of(1L,2L,3L,4L,8L,11L,12L,14L);
// 		List<String> clubNames = List.of(
// 			"함께 축구 경기를 시청해요!",
// 			"이거 안 보면 바보",
// 			"클린스만호 이대로 가라앉을 것인가",
// 			"손흥민이강인김민재황희찬",
// 			"황희찬선발이다 모여라",
// 			"이강인 미친 폼 볼사람",
// 			"17반 긔요미들",
// 			"드르와드르와");
//
// 		// 그룹 생성
// 		for (int i=0; i<size; i++) {
// 			clubService.createClub(new ClubRequestDto(memberIds.get(i), clubNames.get(i)));
// 		}
//
// 		List<Long> clubIds = clubRepository.findAll().stream()
// 			.map(Club::getId)
// 			.toList();
//
// 		// 멤버 생성
// 		for (int i=0; i<size; i++) {
// 			String inviteUrl = clubService.getInviteUrl(clubIds.get(i)).getInviteUrl();
// 			Long inviteMemberId = memberIds.get(size-i-1);
// 			clubService.createGroupMember(new InviteRequestDto(inviteMemberId, inviteUrl));
//
// 			if (i == 0) { // memberId=1이 호스트인 그룹
// 				for (int j=1; j<5; j++) {
// 					clubService.createGroupMember(new InviteRequestDto(memberIds.get(j), inviteUrl));
// 				}
// 			}
// 		}
//
// 		// 멤버 삭제
// 		Member member = memberRepository.findById(memberIds.get(1)).get(); // memberId=2 삭제
// 		clubService.deleteGroupMember(member, clubIds.get(0));
//
// 		// 그룹 삭제
// 		clubService.deleteClub(member, clubIds.get(1));
// 	}
//
// 	@Test
// 	void 그룹_생성_성공() throws Exception {
// 		long memberId = 12;
// 		String name = "아시안컵 16강 볼사람 모여";
//
// 		ClubRequestDto request = new ClubRequestDto(memberId, name);
// 		ClubResponseDto response = clubService.createClub(request);
//
// 		assertThat(response.getName()).isEqualTo(name);
// 		assertThat(response.getHostId()).isEqualTo(memberId);
// 	}
//
// 	@Test
// 	void 그룹_조회_성공() {
// 		long clubId = 23;
// 		ClubResponseDto response = clubService.findClub(clubId);
// 		assertThat(response.getClubId()).isEqualTo(clubId);
// 	}
//
// 	@Test
// 	void 그룹_조회_실패() {
// 		long clubId = 24;
// 		assertThatThrownBy(() -> clubService.findClub(clubId))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(NO_GROUP_ID.getMessage());
// 	}
//
// 	@Test
// 	void 그룹명_수정_성공() {
// 		long memberId = 1;
// 		long clubId = 23;
// 		String name = "전략은 이강인 개인기다";
//
// 		ClubRequestDto clubRequestDto = new ClubRequestDto(memberId, name);
// 		Optional<Member> member = memberRepository.findById(memberId);
// 		ClubResponseDto response = clubService.updateClubName(member.get(), clubId, clubRequestDto);
//
// 		assertThat(response.getName()).isEqualTo(name);
// 	}
//
// 	@Test
// 	void 그룹명_수정_실패_그룹장X() {
// 		long memberId = 3;
// 		long clubId = 23;
// 		String name = "들으와아아라";
//
// 		ClubRequestDto clubRequestDto = new ClubRequestDto(memberId, name);
//
// 		assertThatThrownBy(() -> clubService.updateClubName(memberRepository.findById(memberId).get(), clubId, clubRequestDto))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(FORBIDDEN_USER.getMessage());
// 	}
//
// 	@Test
// 	void 그룹_삭제_성공() {
// 		long memberId = 14;
// 		long clubId = 30;
//
// 		Long response = clubService.deleteClub(memberRepository.findById(memberId).get(), clubId);
// 	}
//
// 	@Test
// 	void 그룹_삭제_실패_이미_삭제됐거나_없는_그룹() {
// 		long memberId = 2;
// 		long clubId = 24;
//
// 		assertThatThrownBy(() -> clubService.deleteClub(memberRepository.findById(memberId).get(), clubId))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(NO_GROUP_ID.getMessage());
// 	}
//
// 	@Test
// 	void 그룹_삭제_실패_그룹장X() {
// 		long memberId = 1;
// 		long clubId = 30;
//
// 		assertThatThrownBy(() -> clubService.deleteClub(memberRepository.findById(memberId).get(), clubId))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(FORBIDDEN_USER.getMessage());
// 	}
//
// 	@Test
// 	void 그룹_초대링크_반환_성공() {
// 		long clubId = 30;
// 		InviteResponseDto response = clubService.getInviteUrl(clubId);
// 		System.out.println(response);
// 	}
//
// 	@Test
// 	void 그룹_초대_성공() {
// 		long memberId = 3;
// 		long clubId = 30;
//
// 		InviteResponseDto urlDto = clubService.getInviteUrl(clubId);
// 		InviteRequestDto request = new InviteRequestDto(memberId, urlDto.getInviteUrl());
// 		ClubMemberResponseDto response = clubService.createGroupMember(request);
//
// 		assertThat(response.getClubId()).isEqualTo(clubId);
// 		assertThat(response.getMemberId()).isEqualTo(memberId);
// 		assertThat(response.getRole()).isEqualTo(RoleType.guest.name());
// 	}
//
// 	@Test
// 	void 그룹_초대_실패_이미_존재하는_그룹원() {
// 		long memberId = 1;
// 		long clubId = 30;
//
// 		InviteResponseDto urlDto = clubService.getInviteUrl(clubId);
// 		InviteRequestDto request = new InviteRequestDto(memberId, urlDto.getInviteUrl());
//
// 		assertThatThrownBy(() -> clubService.createGroupMember(request))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(ALREADY_GROUP_MEMBER.getMessage());
// 	}
//
// 	@Test
// 	void 그룹_초대_실패_유효하지_않은_링크() {
// 		long memberId = 3;
// 		long clubId = 30;
// 		LocalDateTime expirationTime = LocalDateTime.now();
// 		String inviteUrl = (String)INVITE_URL_DOMAIN.getValue() + clubId + "_" + expirationTime;
// 		InviteRequestDto request = new InviteRequestDto(memberId, inviteUrl);
//
// 		assertThatThrownBy(() -> clubService.createGroupMember(request))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(INVALID_INVITE_URL.getMessage());
// 	}
//
// 	@Test
// 	void 그룹장_수정_성공() {
// 		long hostId = 14;
// 		long clubId = 30;
// 		long nextHostId = 1;
// 		ClubHostRequestDto request = new ClubHostRequestDto(hostId, nextHostId);
// 		Member host = memberRepository.findById(hostId).get();
//
// 		ClubMemberResponseDto response = clubService.assignHost(host, clubId, request);
//
// 		assertThat(response.getMemberId()).isEqualTo(nextHostId);
// 		assertThat(response.getRole()).isEqualTo(RoleType.host.name());
// 	}
//
// 	@Test
// 	void 그룹장_수정_실패_그룹장X() {
// 		long hostId = 3;
// 		long clubId = 23;
// 		long nextHostId = 4;
// 		Member host = memberRepository.findById(hostId).get();
// 		ClubHostRequestDto request = new ClubHostRequestDto(hostId, nextHostId);
//
// 		assertThatThrownBy(() -> clubService.assignHost(host, clubId, request))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(FORBIDDEN_USER.getMessage());
// 	}
//
// 	@Test
// 	void 그룹장_수정_실패_존재하지_않은_회원() {
// 		long hostId = 1;
// 		long clubId = 23;
// 		long nextHostId = 2;
// 		Member host = memberRepository.findById(hostId).get();
// 		ClubHostRequestDto request = new ClubHostRequestDto(hostId, nextHostId);
//
// 		assertThatThrownBy(() -> clubService.assignHost(host, clubId, request))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(NO_GROUP_MEMBER.getMessage());
// 	}
//
// 	@Test
// 	void 그룹원_삭제_성공() {
// 		long memberId = 3;
// 		long clubId = 23;
//
// 		Long response = clubService.deleteGroupMember(memberRepository.findById(memberId).get(), clubId);
// 	}
//
// 	@Test
// 	void 그룹원_삭제_실패_그룹장을_넘기지_않은_경우() {
// 		long memberId = 1;
// 		long clubId = 23;
//
// 		assertThatThrownBy(() -> clubService.deleteGroupMember(memberRepository.findById(memberId).get(), clubId))
// 			.isInstanceOf(IllegalArgumentException.class)
// 			.hasMessageContaining(HOST_CANNOT_LEAVE_GROUP.getMessage());
// 	}
//
// 	@Test
// 	void 최근_활동_그룹_목록_조회() {
// 		long memberId = 1;
// 		Member member = memberRepository.findById(memberId).get();
// 		List<ClubResponseDto> response = clubService.findRecentClubs(member);
// 		response.forEach(System.out::println);
//
// 		assertThat(response.size()).isEqualTo(2);
// 	}
//
// 	@Test
// 	void 그룹원_목록_조회() {
// 		long clubId = 23;
// 		List<ClubMemberResponseDto> response = clubService.getGroupMembers(clubId);
// 		response.forEach(System.out::println);
//
// 		assertThat(response.size()).isEqualTo(5);
// 	}
// }