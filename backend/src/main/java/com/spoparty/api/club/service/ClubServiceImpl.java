package com.spoparty.api.club.service;

import static com.spoparty.api.common.constants.DefaultSetting.*;
import static com.spoparty.api.common.constants.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.dto.ClubHostRequestDto;
import com.spoparty.api.club.dto.ClubMemberResponseDto;
import com.spoparty.api.club.dto.ClubRequestDto;
import com.spoparty.api.club.dto.ClubResponseDto;
import com.spoparty.api.club.dto.InviteRequestDto;
import com.spoparty.api.club.dto.InviteResponseDto;
import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.club.repository.ClubMemberRepository;
import com.spoparty.api.club.repository.ClubRepository;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubServiceImpl implements ClubService {
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final MemberRepository memberRepository;

	public List<ClubResponseDto> findRecentClubs(Member member) {
		List<ClubMember> clubMembers = clubMemberRepository.findAllByMember(member);
		return clubMembers.stream()
			.map(clubMember -> ClubResponseDto.entityToDto(clubMember.getClub()))
			.sorted(Comparator.comparing(ClubResponseDto::getUpdatedTime).reversed())
			.collect(Collectors.toList());
	}

	@Transactional
	public ClubResponseDto createClub(ClubRequestDto clubRequestDto) {
		Member member = memberRepository.findByMemberId(clubRequestDto.getMemberId());
		Club club = Club.createClub(clubRequestDto.getName(), member);
		clubRepository.save(club);

		ClubMember clubMember = ClubMember.createClubMember(member, club, RoleType.host); // 그룹회원 생성 - 호스트
		clubMemberRepository.save(clubMember);
		return ClubResponseDto.entityToDto(club);
	}

	public ClubResponseDto findClub(Long clubId) {
		return ClubResponseDto.entityToDto(findClubById(clubId));
	}

	@Transactional
	public ClubResponseDto updateClubName(Member member, Long clubId, ClubRequestDto clubRequestDto) { // 그룹장 권한
		Club club = findClubById(clubId);
		validateHost(club, member); //호스트 권한 체크
		club.setName(clubRequestDto.getName());
		return ClubResponseDto.entityToDto(club);
	}

	@Transactional
	public Long deleteClub(Member member, Long clubId) { // 그룹장 권한
		Club club = findClubById(clubId);
		validateHost(club, member); //호스트 권한 체크
		club.deleteClub();
		return club.getId();
	}

	public InviteResponseDto getInviteUrl(Long clubId) {
		Club club = findClubById(clubId);
		LocalDateTime createdTime = LocalDateTime.now();
		LocalDateTime expirationTime = createdTime.plusDays((Long)INVITE_EXPIRATION_PERIOD.getValue());

		String inviteUrl = (String)INVITE_URL_DOMAIN.getValue() + club.getId() + "_" + expirationTime;
		log.debug("inviteUrl - {}", inviteUrl);
		return new InviteResponseDto(inviteUrl, createdTime, expirationTime);
	}

	@Transactional
	public ClubMemberResponseDto createGroupMember(InviteRequestDto inviteRequestDto) {
		Club club = validateInviteUrl(inviteRequestDto.getInviteUrl());
		Member member = memberRepository.findByMemberId(inviteRequestDto.getMemberId());

		validateNewClubMember(club, member); // 이미 존재하는 멤버인지 확인
		ClubMember clubMember = ClubMember.createClubMember(member, club, RoleType.guest);
		clubMemberRepository.save(clubMember);
		return ClubMemberResponseDto.entityToDto(clubMember);
	}

	public List<ClubMemberResponseDto> getGroupMembers(Long clubId) {
		Club club = findClubById(clubId);
		List<ClubMember> clubMembers = club.getClubMembers();
		return clubMembers.stream()
			.filter(clubMember -> !clubMember.isDeleted())
			.map(ClubMemberResponseDto::entityToDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public ClubMemberResponseDto assignHost(Member hostMember, Long clubId, ClubHostRequestDto clubHostRequestDto) {
		Club club = findClubById(clubId);
		validateHost(club, hostMember); //호스트 권한 체크
		Member nextHostMember = memberRepository.findByMemberId(clubHostRequestDto.getNextHostId());

		// 그룹원 확인
		ClubMember currentHost = findClubMember(club, hostMember);
		ClubMember nextHost = findClubMember(club, nextHostMember);

		if (!currentHost.equals(nextHost)) {
			club.setHostMember(nextHostMember);
			currentHost.setRole(RoleType.guest);
			nextHost.setRole(RoleType.host);
		}
		return ClubMemberResponseDto.entityToDto(nextHost);
	}

	@Transactional
	public Long deleteGroupMember(Member member, Long clubId) {
		Club club = findClubById(clubId);
		ClubMember clubMember = findClubMember(club, member);

		clubMember.deleteClubMember(club);
		return clubMember.getId();
	}

	private Club findClubById(Long clubId) {
		Optional<Club> club = clubRepository.findById(clubId);
		if (club.isEmpty()) {
			throw new IllegalArgumentException(NO_GROUP_ID.getMessage());
		}
		return club.get();
	}

	private void validateHost(Club club, Member member) {
		if (!member.getMemberId().equals(club.getHostMember().getMemberId())) {
			throw new IllegalArgumentException(FORBIDDEN_USER.getMessage());
		}
	}

	private Club validateInviteUrl(String inviteUrl) {
		try {
			String[] tokens = inviteUrl.split("/|_");
			log.debug("urlInfo - {}", Arrays.toString(tokens));
			if (tokens.length == 5) {
				Long clubId = Long.parseLong(tokens[3]);
				LocalDateTime expirationTime = LocalDateTime.parse(tokens[4]);
				log.debug("currentTime - {}", LocalDateTime.now());
				log.debug("expirationTime - {}", expirationTime);
				if (LocalDateTime.now().isBefore(expirationTime)) { // 만료시간 이내인 경우만 유효함
					return findClubById(clubId);
				}
			}
		} catch (Exception e) {
			log.debug("연산 중 오류");
		}
		throw new IllegalArgumentException(INVALID_INVITE_URL.getMessage());
	}

	private void validateNewClubMember(Club club, Member member) {
		Optional<ClubMember> clubMember = clubMemberRepository.findByClubAndMember(club, member);
		if (clubMember.isPresent()) {
			throw new IllegalArgumentException(ALREADY_GROUP_MEMBER.getMessage());
		}
	}

	private ClubMember findClubMember(Club club, Member member) {
		return clubMemberRepository.findByClubAndMember(club, member)
			.orElseThrow(() -> new IllegalArgumentException(NO_GROUP_MEMBER.getMessage()));
	}
}
