package com.spoparty.api.club.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.dto.ClubHostRequestDto;
import com.spoparty.api.club.dto.ClubMemberResponseDto;
import com.spoparty.api.club.dto.ClubRequestDto;
import com.spoparty.api.club.dto.InviteRequestDto;
import com.spoparty.api.club.dto.InviteResponseDto;
import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.club.repository.ClubMemberRepository;
import com.spoparty.api.club.repository.ClubRepository;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.api.party.repository.PartyRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final PartyRepository partyRepository;
	private final MemberRepository memberRepository;

	public long createClub(ClubRequestDto clubRequestDto) {
		Member member = memberRepository.findByMemberId(clubRequestDto.getMemberId());

		ClubMember clubMember = ClubMember.createClubMember(member, RoleType.host);
		Club club = Club.createClub(clubRequestDto.getName(), clubMember);

		clubRepository.save(club);
		return club.getId();
	}

	public List<ClubRequestDto> findRecentClubs(long memberId) {
		return null;
	}

	public Boolean updateClubName(long clubId, ClubRequestDto clubRequestDto) {
		return null;
	}

	public Boolean deleteClub(long clubId) {
		return null;
	}

	public InviteResponseDto getInviteUrl(long clubId) {
		return null;
	}

	public List<ClubMemberResponseDto> getGroupMembers(long clubId) {
		return null;
	}

	public Boolean createGroupMember(long clubId, InviteRequestDto inviteRequestDto) {
		return null;
	}

	public Boolean assignHost(long clubId, ClubHostRequestDto clubHostRequestDto) {
		return null;
	}

	public Boolean deleteGroupMember(long clubId, long memberId) {
		return null;
	}
}
