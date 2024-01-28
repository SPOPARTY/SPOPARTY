package com.spoparty.api.club.service;

import java.util.List;

import com.spoparty.api.club.dto.ClubHostRequestDto;
import com.spoparty.api.club.dto.ClubMemberResponseDto;
import com.spoparty.api.club.dto.ClubRequestDto;
import com.spoparty.api.club.dto.ClubResponseDto;
import com.spoparty.api.club.dto.InviteRequestDto;
import com.spoparty.api.club.dto.InviteResponseDto;
import com.spoparty.api.member.entity.Member;

public interface ClubService {
	List<ClubResponseDto> findRecentClubs(Member member);

	ClubResponseDto createClub(ClubRequestDto clubRequestDto);

	ClubResponseDto findClub(Long clubId);

	ClubResponseDto updateClubName(Member member, Long clubId, ClubRequestDto clubRequestDto);

	Long deleteClub(Member member, Long clubId);

	InviteResponseDto getInviteUrl(Long clubId);

	ClubMemberResponseDto createGroupMember(InviteRequestDto inviteRequestDto);

	List<ClubMemberResponseDto> getGroupMembers(Long clubId);

	ClubMemberResponseDto assignHost(Member hostMember, Long clubId, ClubHostRequestDto clubHostRequestDto);

	Long deleteGroupMember(Member member, Long clubId);
}
